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
	private int curKeyIndex = 0;
	private FileUtil_Mixin util;
	private String filePath = null;
	private StringBuilder sb = new StringBuilder();
	private Map<String, String> curMap = new HashMap<String, String>();
	private List<Map<String, String>> result = new ArrayList<Map<String, String>>();
	
	public Kernel3(String filePath) {
		this.filePath = filePath;
		util = new FileUtil_Mixin();
	}
	public Kernel3(String filePath, String charset) {
		this.filePath = filePath;
		util = new FileUtil_Mixin(charset);
	}
	public Kernel3(String filePath, String charset, int capacity) {
		this.filePath = filePath;
		util = new FileUtil_Mixin(capacity, charset);
	}
	public Kernel3(String filePath, int capacity) {
		this.filePath = filePath;
		util = new FileUtil_Mixin(capacity);
	}
	
	
	public List<Map<String, String>> getResources() {
		util.read(filePath, new Consumer() {
			public void consume(ByteBuffer buff, Carrier carrier) {
				CharBuffer cBuff = carrier.getChar(buff);
				while (cBuff.hasRemaining()) {
					char cur = cBuff.get();
					// 如果是普通字符，则直接放入临时字符串sb中，
					// 等待临界点到来时集体放入list
					if (cur != ',' && cur != ';') {
						sb.append(cur);
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
						put(sb.toString(), false);
						sb.delete(0, sb.length());
					} else if (cur == ';' && (next == '\r' || next == '\n')) {	// 如果正好是获取一条数据的临界点
						put(sb.toString(), true);
						sb.delete(0, sb.length());
					} else {
						sb.append(cur);
						sb.append(next);
					}
				}
			}
		});
		return result;
	}
	
	/**
	 * 如果是字段节点，只放入临时map
	 * 如果是行节点，不仅放入临时map，还讲map放入list
	 * @param val
	 * @param isRowNode	是否为行节点，false则代表为字段节点
	 */
	private void put(String val, boolean isRowNode) {
		curMap.put(KEYARRAYS[curKeyIndex], val.trim());
		if (isRowNode) {
			result.add(curMap);
			curMap = new HashMap<String, String>();
			curKeyIndex = 0;
		} else {
			curKeyIndex++;
		}
	}
	
	public static void main(String[] args) {
		 Kernel3 k = new Kernel3("C:/Users/xnxs/Desktop/b.txt");
		 long a = System.currentTimeMillis();
		 List<Map<String, String>> list = k.getResources();
		 System.out.println(System.currentTimeMillis() - a);
		 System.out.println(list.size());
		 System.out.println(list.get(list.size() -1));
		 System.out.println(list.get(list.size() -778));
		 System.out.println(list.get(list.size() -32));
		 System.out.println(list.get(list.size() -15000));
	}
}
