package jun.learn.tools.network.nioServer2;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import jun.learn.tools.network.Util;

public class Robot2 implements Robot{
	private static AtomicInteger index = new AtomicInteger();
	
	private final int id;
	private final Server server;
	private SocketChannel socket = null;
	private AtomicInteger replyIndex = new AtomicInteger();
	private volatile boolean started = false;
	
	private Packet packet = new Packet();
	private ByteBuffer buff = ByteBuffer.allocate(1024);
	
	public Robot2(Server server) {
		this.id = index.incrementAndGet();
		this.server = server;
	}
	
	public void run() {
		while (true) {
			/**
			 * 没有被启动, 则直接进入睡眠状态
			 */
			while (!started) {
				System.out.println(this + "进入休眠");
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {}
				}
			}
				
			try {
				socket.read(buff);
				process(buff);
			} catch (IOException e) {
				reset();
			}
		}
	}

	private void process(ByteBuffer buff) throws IOException {
		buff.flip();
		if (packet.isReadyState()) {
			if (buff.remaining() < 4)
				return;
			packet.expect(buff.getInt());
		}
		
		packet.append(buff);
		if (packet.expected()) {
			reply(packet.getContent());
			packet.clear();
		}
		buff.compact();
	}
	
	public void reply(String msg) throws IOException {
		if (Util.isShutdown(msg)) {
			reset();
		} else {
			doReply(msg);
		}
	}
	
	/**
	 * 自动回复
	 */
	private void doReply(String msg) throws IOException {
		String replyMsg = "服务端第" 
			+ replyIndex.incrementAndGet() + "次回复: " 
			+ "我知道你的说的是->" + msg;
		Util.write(socket, replyMsg);
	}
	
	/**
	 * reset this 
	 */
	private void reset() {
		System.out.println(this + "重置该连接");
		
		try {
			//　回收时，通知客户端，我要关闭连接了
			Util.sendShutdownCommand(socket);
			socket.close();
		} catch (IOException e) {
			// socket关闭时发生异常, 但不影响正常关闭
			System.out.println(this + "关闭时发生异常");
		}
		socket = null;
		started = false;
		
		System.out.println(this + "被回收");
		server.recycle(this);
	}
	
	public String toString() {
		return "robot" + id + "号: ";
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

	/**
	 * 接收SocketChannel参数, 唤醒悬停的work线程
	 */
	public synchronized void accept(SocketChannel socket) {
		if (started) {
        	System.out.println("有两个线程使用了同一个robot");
            return;
        }
		this.started = true;
		this.socket = socket;
		this.notifyAll();
	}
}
