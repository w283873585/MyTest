package jun.learn.tools.network.bioServer;

import static jun.learn.tools.network.Util.*;

import java.io.IOException;
import java.io.InputStream;

public class Msg2{
	public Msg2(InputStream input) {
		this.input = input;
	}
	
	private StringBuilder sb = new StringBuilder();
	private InputStream input;
	byte buff[] = new byte[1024];
	
	public boolean isReady() {
		try {
			/**
			 * 每个数据报的前二个字节用来表示数据包的长度
			 */
			byte[] head = new byte[4];
			int hasReaded = 0;
			while (hasReaded < 4)
				hasReaded += input.read(head, hasReaded, 4 - hasReaded);
			
			int surplus = byte2Int(head);
			while (surplus > 0) {
				surplus -= input.read(buff);
				sb.append(new String(buff));
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public String toString() {
		return sb.toString();
	}
}