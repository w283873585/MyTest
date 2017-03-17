package jun.learn.tools.network.bioServer;

import static jun.learn.tools.network.Util.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import jun.learn.tools.network.DataPacket;

public class StringPacket implements DataPacket<String>{

	@Override
	public String read(InputStream input) throws IOException {

		StringBuilder sb = new StringBuilder();
		byte buff[] = new byte[1024];
		
		/**
		 * 每个数据报的前4个字节用来表示数据包的长度
		 */
		byte[] head = new byte[4];
		int hasReaded = 0;
		while (hasReaded < 4)
			hasReaded += input.read(head, hasReaded, 4 - hasReaded);
		
		/**
		 * 获取真实数据
		 */
		int surplus = byte2Int(head);
		while (surplus > 0) {
			int length = input.read(buff);
			surplus -= length;
			sb.append(new String(buff, 0, length));
		}
		
		return sb.toString();
	}

	@Override
	public void write(OutputStream out, String content) throws IOException {
		out.write(int2byte(content.getBytes().length));
		out.write(content.getBytes());
		out.flush();
	}
}