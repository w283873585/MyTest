package jun.learn.tools.fileUtil;

import java.util.ArrayList;
import java.util.List;

public class SqlUtil {
	public void invoke() {
		/*
		 * 
			List<String> result = new ArrayList<String>();
			Sql sql = new Sql();
			FileUtil.each(val => {
				sql.pull(val);
				if (sql.isEnd()) {
					result.add(sql.toString());
					sql.clear();
				}
			});
			
			
			
			void pull(val) {
				
			}
		*/
	}
	
	public static class Sql {
		private KeyType curKey = KeyType.none;
		
		private StringBuilder sql = new StringBuilder();
		private boolean isEnd = false;
		
		public void pull(char val) {
			sql.append(val);
			
			KeyType next = curKey.next(sql);
			if (next != null) {
				curKey = next;
			}
			
			if (curKey.isEnd()) {
				isEnd = true;
			}
		}
		
		public boolean isEnd() {
			return isEnd;
		}
		
		public void clear() {
			clear(sql);
			isEnd = false;
		}
		
		private void clear(StringBuilder sb) {
			sb.delete(0, sb.length() - 1);
		}
		
		public String toString() {
			return sql.toString();
		}
		
		public enum KeyType {
			none(3, ""),
			end(2, ";"),
			table(1, "table", end),
			view(1, "view", end),
			create(0, "create", table, view),
			insert(0, "insert", end),
			select(0, "select", end),
			delete(0, "delete", end),
			alter(0, "alter", end),
			update(0, "update", end),
			drop(0, "drop", end);
			
			private KeyType(int state, String value, KeyType... next){
				this.state = state;
				this.next = next;
				this.value = value;
			}
			
			private int state;		// 0,1,2
			private KeyType[] next;
			
			public KeyType next(StringBuilder key) {
				KeyType candidate[] = null;
				if (isInit()) {
					List<KeyType> starts = new ArrayList<KeyType>();
					for (KeyType type : KeyType.values()) {
						if (type.isStart()) {
							starts.add(type);
						}
					}
					candidate = new KeyType[starts.size()];
					starts.toArray(candidate);
				} else {
					candidate = next;
				}
				
				for (KeyType type : candidate) {
					String spiltVal = key.substring(key.length() - 1 - type.value.length(), key.length() - 1);
					if  (type.value.equalsIgnoreCase(spiltVal)) {
						return type;
					}
				}
				return null;
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
			
			private String value;
		}
	}
}
