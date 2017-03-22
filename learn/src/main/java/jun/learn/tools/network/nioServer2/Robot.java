package jun.learn.tools.network.nioServer2;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import jun.learn.tools.network.Util;

public class Robot {
	private static AtomicInteger index = new AtomicInteger();
	
	private final int id;
	private ReplyResolver replyResolver = new ReplyResolver();
	
	public Robot() {
		this.id = index.incrementAndGet();
	}
	
	public void recvAndReply(SelectionKey key) {
		PacketGenerator packetGenerator = new PacketGenerator();
		SocketChannel socket = (SocketChannel) key.channel();
		try {
			packetGenerator.parse(socket);
			while (packetGenerator.hasNext()) {
				String msg = packetGenerator.next();
				if (Util.isShutdown(msg)) {
					shutdown(socket);
					key.cancel();
				} else {
					Util.write(socket, replyResolver.reslove(msg));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			shutdown(socket);
			key.cancel();
		}
	}
	
	/**
	 * shutdown this 
	 */
	private void shutdown(SocketChannel socket) {
		try {
			//　回收时，通知客户端，我要关闭连接了
			Util.sendShutdownCommand(socket);
			socket.close();
		} catch (IOException e) {
			// socket关闭时发生异常, 但不影响正常关闭
			System.out.println(this + "关闭时发生异常");
		}
		socket = null;
	}
	
	public String toString() {
		return "robot" + id + "号: ";
	}
	
	private static class ReplyResolver {
		private AtomicInteger replyIndex = new AtomicInteger();
		public String reslove(String msg) {
			return "服务端第" 
					+ replyIndex.incrementAndGet() + "次回复: " 
					+ "我知道你的说的是->" + msg;
		}
	}
	
	private static class PacketGenerator implements Iterator<String>{
		private int index = 0;
		private List<String> packets = new ArrayList<String>();
		private ByteBuffer buff = ByteBuffer.allocate(1024);
		
		public void parse(SocketChannel socket) throws IOException {
			Packet packet = new Packet();
			while (socket.read(buff) > 0) {
				buff.flip();
				while (buff.hasRemaining()) {
					if (packet.isReadyState()) {
						if (buff.remaining() < 4)
							break;
						packet.expect(buff.getInt());
					}
					
					packet.append(buff);
					if (!packet.expected()) {
						break;
					}
					packets.add(packet.getContent());
					packet.clear();
				}
				buff.compact();
			}
		}
		
		public String next() {
			return packets.get(index++);
		}
		
		@Override
		public boolean hasNext() {
			return index < packets.size();
		}
	}
	
	public static class Packet {
		public int current;
		public int expect;
		private byte[] content;
		private PacketStatus status = PacketStatus.ready;
		
		public void append(ByteBuffer buff) {
			int canObtain = Math.min(buff.remaining(), expectedSurplus());
			if (content == null) {
				content = new byte[canObtain];
			} else {
				content = Arrays.copyOf(content, canObtain + current);
			}
			buff.get(content, current, canObtain);
			current = content.length;
		}
		
		public boolean isReadyState() {
			return status == PacketStatus.ready;
		}
		
		public int expectedSurplus() {
			return expect - current;
		}
		
		public void expect(int expect) {
			this.expect = expect;
			this.status = PacketStatus.processing;
		}

		public boolean expected() {
			return current == expect;
		}
		
		public void clear() {
			this.current = 0;
			this.expect = 0;
			this.content = null;
			this.status = PacketStatus.ready;
		}
		
		public String getContent() {
			if (!expected()) 
				throw new RuntimeException("包处于不完整状态, 不能获取其中的内容");
			try {
				return new String(content, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		public enum PacketStatus{
			ready,
			processing;
		}
	}
}
