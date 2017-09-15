package jun.learn.tools.fileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sinocare.helper.common.FileUtil;
import com.sinocare.helper.common.FileUtil.CharConsumer;
import com.sinocare.helper.common.PropertiesUtil;

public class SqlUtil2 {
	private static final Logger log = Logger.getLogger(SqlUtil2.class);
	
	private int localVersion;
	private int onlineVersion = Integer.parseInt(PropertiesUtil.getProperty("application.properties", "project.version"));
	
	private static final String DRIVER_NAME = PropertiesUtil.getProperty("application.properties", "jdbc.driver");
	private static final String MYSQL_PREFIX = "mysql_from_";
	private static final String PREFIX = "from_";
	private static final String SUFFIX = ".sql";
	
	/**
	 * 自动升级入口
	 */
	public static void upgrade(int localVersion, JdbcTemplate jdbc) {
		new SqlUtil2(localVersion, jdbc).doUpgrade();
	}
	
	private JdbcTemplate jdbc;
	
	public SqlUtil2(int localVersion, JdbcTemplate jdbc) {
		this.localVersion = localVersion;
		this.jdbc = jdbc;
	}
	
	/**
	 * 执行升级
	 */
	public void doUpgrade() {
		/**
		 * 1. 获取数据库版本与状态
		 * 2. 如果状态为失败，停止升级
		 * 3. 如果状态为成功，则根据版本号判断是否需要升级，执行对应升级
		 * 
		 * 升级流程：
		 * 		1. 获取需要升级的sql文件，
		 * 		2. 解析sql文件为一个个单独的sql语句，
		 * 		3. 执行sql 
		 * 		遍历执行sql语句，先执行ddl语句，
		 * 		开启事务
		 * 			再次遍历执行sql语句， 执行dml语句。
		 * 		关闭事务
		 * 		4. 如果失败则插入升级失败日志, 停止升级
		 * 		5. 如果成功则插入升级成功日志，继续升级 -> 1
		 */
		
		log.info("当前版本是：" + localVersion);
		log.info("最新版本是：" + onlineVersion);
		
		if (!upgradeRequired()) {
			return;
		}
		
		try {
			for (int i = localVersion + 1; i <= onlineVersion; i++) {
				log.info("获取第" + i + "版本的sql");
				
				final List<String> dmls = new ArrayList<String>();
				final List<String> ddls = new ArrayList<String>();
				final Sql sql = new Sql();
				
				FileUtil.read(getUpgradeSqlLocation(i), new CharConsumer() {
					@Override
					public void consume(char c) {
						sql.pull(c);
						if (sql.isCompleted()) {
							if (sql.isDML()) {
								dmls.add(sql.toString());
							} else if (sql.isDDL()) {
								ddls.add(sql.toString());
							} else if (sql.isComment()) {
								// comment
							} else {
								// error
							}
							sql.init();
						}
					}
				});

				
				// ddl执行
				for (String sqlStr : ddls) {
					jdbc.execute(sqlStr);
				}
				
				// dml执行
				for (String sqlStr : ddls) {
					jdbc.execute(sqlStr);
				}
			}
		} catch (Exception e) {
			log.info("### 执行sql时报错, 已回滚");
			throw e;
		}
		log.info("### 自动升级成功！已将版本由【" + localVersion + "】升级到【" + onlineVersion + "】");
	}
	
	/**
	 * 查询数据库，是否能够升级
	 */
	private boolean upgradeRequired() {
		return localVersion < onlineVersion;
	}
	
	/**
	 * 获取sql存放的位置
	 */
	private String getUpgradeSqlLocation(int version) {
		String folder = this.getClass().getResource("").getPath();
		int prevVersion = version - 1;
		String path = folder + "/" 
				+ (DRIVER_NAME.contains("mysql") ? MYSQL_PREFIX : PREFIX)
				+ prevVersion + "_to_"
				+ version
				+ SUFFIX;
		return path;
	}
	
	/**
	 *	sql的抽象
	 */
	public static class Sql {
		private boolean initted = false;
		private boolean isEnd = false;
		
		private SqlType type = null;
		private StringBuilder sql = new StringBuilder();
		private Stack<KeyType> keywords = new Stack<KeyType>();
		
		public void init() {
			this.type = null;
			this.initted = true;
			this.isEnd = false;
			this.keywords.clear();
			this.keywords.push(KeyType.none);
			this.sql = sql.delete(0, sql.length());
		}
		
		public void pull(char val) {
			if (!initted) init();
			
			sql.append(val);
			KeyType next = keywords.peek().next(sql);
			if (next != null) {
				if (next == keywords.peek() 
						&& next == KeyType.begin) {
					keywords.push(next);
				} else {
					keywords.pop();
					if (!next.isEnd()) {
						if (next.type() != null) {
							type = next.type();
						}
						keywords.push(next);
					}
				}
			}
			
			if (keywords.isEmpty()) {
				isEnd = true;
			}
		}
		
		public boolean isCompleted() {
			return isEnd;
		}
		
		public boolean isComment() {
			return type == SqlType.COMMENT;
		}
		
		public boolean isDDL() {
			return type == SqlType.DDL;
		}
		
		public boolean isDML() {
			return type == SqlType.DML;
		}
		
		public String toString() {
			return sql.toString();
		}
		
		public enum SqlType {
			DML, DQL, DDL, COMMENT;
		}
		
		public enum KeyType {
			none(3, ""),
			terminal(2, ";"),
			end(2, "end;"),
			nextLine(2, "\r\n"),
			
			begin(1, "begin", end),
			function(1, "function", begin),
			procedure(1, "procedure", begin),
			table(1, "table", terminal),
			view(1, "view", terminal),
			
			/**
			 * ddl
			 */
			create(0, "create", table, view, function, procedure),
			alter(0, "alter", terminal),
			drop(0, "drop", terminal),
			
			/**
			 * dml
			 */
			insert(0, "insert", terminal),
			select(0, "select", terminal),
			delete(0, "delete", terminal),
			update(0, "update", terminal),
			
			/**
			 * comment
			 */
			comment(0, "--", nextLine),
			comment1(0, "#", nextLine);
			private KeyType(int state, String value, KeyType... next){
				this.state = state;
				this.required = next;
				this.value = value;
			}
			
			private int state;		// 0,1,2
			private String value;
			private KeyType[] required;
			
			public KeyType next(StringBuilder key) {
				KeyType candidate[] = null;
				if (isInit()) {
					candidate = getStartKeys();
				} else {
					candidate = required;
				}
				
				if (this == begin && begin.match(key)) {
					return begin;
				}
				
				for (KeyType type : candidate) {
					if  (type.match(key)) {
						return type;
					}
				}
				return null;
			}
			
			private KeyType[] getStartKeys() {
				List<KeyType> starts = new ArrayList<KeyType>();
				for (KeyType type : KeyType.values()) {
					if (type.isStart()) {
						starts.add(type);
					}
				}
				KeyType[] candidate = new KeyType[starts.size()];
				starts.toArray(candidate);
				return candidate;
			}
			
			public boolean match(StringBuilder str) {
				String spiltVal = null;
				try {
					spiltVal = str.substring(str.length() - value.length(), str.length());
				} catch (Exception e) {
					spiltVal = "";
				}
				return value.equalsIgnoreCase(spiltVal);
			}
			
			public boolean isStart() {
				return state == 0;
			}
			
			public boolean isEnd() {
				return state == 2;
			}
			
			public boolean isInit() {
				return state == 3;
			}
			
			public SqlType type() {
				if (this == comment || this == comment1) return SqlType.COMMENT;
				if (this == delete || this == insert || this == update) return SqlType.DML;
				if (this == create || this == alter || this == update) 	return SqlType.DDL;
				return null;
			}
		}
	}
}
