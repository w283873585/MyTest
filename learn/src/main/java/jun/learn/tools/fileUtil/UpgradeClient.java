package jun.learn.tools.fileUtil;

import java.util.List;

public class UpgradeClient {
	/**
	 * 	1. 获取版本信息, 根据版本信息判断是否需要升级
	 * 		最新版本升级失败, 则不需要升级
	 * 		最新版本升级成功，且最新版本等于当前版本则不需要升级
	 * 		否则，需要升级。
	 * 	 2. 获取当前需要升级的sql,
	 * 		先执行ddl, 再执行dml。
	 * 		步骤中抛错则停止升级，记录升级失败日志。
	 * 		否则记录升级成功日志
	 * 	
	 *  Upgrader upgrader = new Upgrader();
	 *  SqlResourceLoader loader = new SqlResourceLoader();
	 *  while (upgrader.requireUpgraded()) {
	 * 		SqlGroup sqlGroup = loader.load(upgrader.getUpgradeSqlPath());
	 *  	SqlResult result = sqlGroup.execute();
	 *		upgrader.saveUpgradeLog(result);
	 *  }
	 */
	
	public interface Upgrader{
		/**
		 * 需要升级的情况
		 * 		当前数据库最高版本升级状态为success
		 * 		当前数据库最高版本低于当前版本
		 */
		public boolean requireUpgraded();
		
		/**
		 * 获取升级地址
		 */
		public String getUpgradeSqlPath();
		
		/**
		 * 保存升级日志
		 */
		public void saveUpgradeLog(SqlResult result);
	}
	
	public interface SqlResourceLoader{
		SqlGroup load(String path);
	}
	
	public interface Sql{
		public void fill(char val);
		
		public boolean isDML();
		public boolean isDDL();
		public boolean isCompleted();
		public String getSqlString();
	}
	
	public interface SqlGroup{
		public void add(Sql sql);
		public SqlResult execute();
	}
	
	public interface SqlResult{
		public boolean success();
		
		public List<Sql> executeSuccess();
		public List<Sql> executeFailed();
		public List<Sql> notExecuted();
		
		public String getErrorInfo();
	}
}
