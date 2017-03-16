package jun.learn.tools.network.bioServer;

import static jun.learn.tools.network.Util.*;

import java.io.IOException;
import java.io.InputStream;

public class Msg{
	public Msg(InputStream input) {
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
			byte[] len = new byte[4];
			input.read(len, 0, 4);
			
			int surplus = byte2Int(len);
			while (surplus > 0) {
				input.read(buff, 0, surplus > 1024 ? 1024 : surplus);
				sb.append(new String(buff));
				surplus -= 1024;
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