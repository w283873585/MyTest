package jun.learn.tools.network;

import static jun.learn.tools.network.Util.byte2Int;
import static jun.learn.tools.network.Util.int2byte;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public interface DataPacket2<T> {
	/*
	int length();
	boolean isEmpty();
	*/
	//  
	T read() throws IOException;
	
	void write(T content) throws IOException;
	
	void close();
	
	boolean isClose();
	
	public class Helper implements DataPacket2<String>{
		private Socket socket;
		private boolean closed;
		byte[] head = new byte[4];
		private byte buff[] = new byte[1024];
		
		public Helper(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public String read() throws IOException {
			InputStream input = socket.getInputStream();
			StringBuilder sb = new StringBuilder();
			
			/**
			 * 每个数据报的前4个字节用来表示数据包的长度
			 */
			int hasReaded = 0;
			while (hasReaded < 4) {
				int readLen = input.read(head, hasReaded, 4 - hasReaded); 
				if (readLen == -1) closed = true;
				hasReaded += readLen;
			}
			
			/**
			 * 获取真实数据
			 */
			int surplus = byte2Int(head);
			while (surplus > 0 && !closed) {
				int readLen = input.read(buff);
				if (readLen == -1) closed = true;
				surplus -= readLen;
				sb.append(new String(buff, 0, readLen));
			}
			return sb.toString();
		}
		
		@Override
		public void write(String content) throws IOException {
			OutputStream out = socket.getOutputStream();
			out.write(int2byte(content.getBytes().length));
			out.write(content.getBytes());
			out.flush();
		}
		
		@Override
		public void close() {
			try {
				socket.close();
				closed = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public boolean isClose() {
			return closed;
		} 
	}
	
	public static void main(String[] args) throws IOException {
		ServerSocketChannel server = ServerSocketChannel.open();
		server.configureBlocking(false);
		Selector seletor = Selector.open();
		server.register(seletor, 1);
		seletor.select();
	}
}

