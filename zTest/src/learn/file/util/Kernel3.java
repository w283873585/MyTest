package learn.file.util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import learn.file.util.FileUtil_Mixin.Carrier;
import learn.file.util.FileUtil_Mixin.Consumer;

public class Kernel3 {
	private FileUtil_Mixin util;
	
	public Kernel3() {
		util = new FileUtil_Mixin();
	}
	public Kernel3(String charset) {
		util = new FileUtil_Mixin(charset);
	}
	public Kernel3(String charset, int capacity) {
		util = new FileUtil_Mixin(capacity, charset);
	}
	public Kernel3(int capacity) {
		util = new FileUtil_Mixin(capacity);
	}
	
	private static class DataContainer{
		private static final String[] KEYARRAYS = {
			"user_id",
			"user_name",
			"password",
			"mobile_phone",
			"email",
			"sex",
			"birthday",
			"FROM_UNIXTIME(left(reg_time,10))",
			"is_special",
			"is_activate",
			"is_rename",
			"ec_salt"
		};
		// 当前字段的索引
		private int curKeyIndex = 0;
		// 临时字符串
		private StringBuilder sb = new StringBuilder();
		// 某一行数据的map对象
		private Map<String, String> map = new HashMap<String, String>();
		// 最终结果的集合
		private List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		
		/**
		 * 添加普通的字符到临时字符串中，
		 * 用来拼接成完整的字段值
		 * @param c
		 */
		public void append(char c) {
			sb.append(c);
		}
		/**
		 * 将临时字符串加入行数据对象，
		 * 如果已到达数据行的末端，
		 * 则将其加入至结果集合中
		 * @param isRowNode 是否到达数据行的临界点
		 */
		public void evolve(boolean isRowNode) {
			// 放入行数据节点
			map.put(KEYARRAYS[curKeyIndex], sb.toString());
			// 清空临时字符串的值
			sb.delete(0, sb.length());
			// 如果是行数据的最后一条
			// 将其插入列表集合
			if (isRowNode) {
				result.add(map);
				map = new HashMap<String, String>();
				curKeyIndex = 0;
			} else {
				curKeyIndex++;
			}
		}
		
		public void evolve() {
			evolve(false);
		}
		
		public List<Map<String, String>> getData() {
			return result;
		}
	}
	
	public List<Map<String, String>> getResources(String filePath) {
		final DataContainer dc = new DataContainer();
		util.read(filePath, new Consumer() {
			public void consume(ByteBuffer buff, Carrier carrier) {
				CharBuffer cBuff = carrier.getChar(buff);
				while (cBuff.hasRemaining()) {
					char cur = cBuff.get();
					// 如果是普通字符，则直接放入临时字符串sb中，
					// 等待临界点到来时集体放入list
					if (cur != ',' && cur != ';') {
						dc.append(cur);
						continue;
					}
					// 不是普通字符的情况下，需要主动获取新数据
					// 当获取的数据为空时，说明数据已经没有了，
					// 则直接返回，即放弃之前已获取的数据（因为没有后续的标识说明此数据不合法）
					if (!cBuff.hasRemaining()) {
						cBuff = carrier.getChar();
						if (!cBuff.hasRemaining()) {
							return;
						}
					}
					// 获取下一个标识，如果合法则添加，否则当做普通数据放入临时字符串
					char next = cBuff.get();
					if (cur == ',' && next == ' ') {	// 如果正好是获取字段的临界点
						dc.evolve();
					} else if (cur == ';' && (next == '\r' || next == '\n')) {	// 如果正好是获取一条数据的临界点
						dc.evolve(true);
					} else {
						dc.append(cur);
						dc.append(next);
					}
				}
			}
		});
		return dc.getData();
	}
	
	public static void main(String[] args) {
		 Kernel3 k = new Kernel3();
		 long a = System.currentTimeMillis();
		 List<Map<String, String>> list = k.getResources("C:/Users/xnxs/Desktop/b.txt");
		 System.out.println(System.currentTimeMillis() - a);
		 System.out.println(list.size());
		 System.out.println(list.get(2));
		 System.out.println(list.get(list.size() -1));
		 System.out.println(list.get(list.size() -32));
	}
}
