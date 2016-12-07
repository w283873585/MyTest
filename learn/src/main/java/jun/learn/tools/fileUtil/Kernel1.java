package jun.learn.tools.fileUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Kernel1 {
	private static final int CAPACITY = 1024;
	private static final String CHARSET = "GBK";
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
	private String filePath = null;
	private Map<String, String> curMap = new HashMap<String, String>();
	private List<Map<String, String>> result = new ArrayList<Map<String, String>>();
	private StringBuilder sb = new StringBuilder();
	private boolean isEnd = false;
	
	
	public Kernel1(String filePath) {
		this.filePath = filePath;
	}
	
	public List<Map<String, String>> getResources() {
		FileChannel channel = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filePath);
			channel = fis.getChannel();
			ByteBuffer buff = ByteBuffer.allocate(CAPACITY);
			while (!isEnd) {
				readData(buff, channel);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (channel != null) channel.close();
				if (fis != null) fis.close();
			} catch (IOException e) {}
		}
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
	
	// 主动读取channel的数据，并以一定格式放入charBuffer
	// 且返回该charBuffer
	private CharBuffer getData(FileChannel channel, ByteBuffer buff) {
		try {
			channel.read(buff);
		} catch (IOException e) { isEnd = true; }
		buff.flip();
		CharBuffer cBuff = Charset.forName(CHARSET).decode(buff);
		buff.clear();
		if (!cBuff.hasRemaining()) isEnd = true;
		return cBuff;
	}
	
	// 从文件中读出数据
	private void readData(ByteBuffer buff, FileChannel channel) throws UnsupportedEncodingException {
		CharBuffer cBuff = getData(channel, buff);
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
				cBuff = getData(channel, buff);
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
	
	public static void main(String[] args) {
		 Kernel1 k = new Kernel1("C:/Users/xnxs/Desktop/b.txt");
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
