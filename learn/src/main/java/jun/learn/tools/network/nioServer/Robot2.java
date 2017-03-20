package jun.learn.tools.network.nioServer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import jun.learn.tools.network.Util;

public class Robot2 implements Robot{
	private static AtomicInteger index = new AtomicInteger();
	
	private volatile boolean started = false;
	
	private final int id;
	private final Server server;
	private SocketChannel socket = null;
	private AtomicInteger replyIndex =  new AtomicInteger();
	
	private PacketStatus status = PacketStatus.ready;
	private ByteBuffer buff = ByteBuffer.allocate(1024);
	private Packet curPacket = new Packet();
	
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
		if (status == PacketStatus.ready) {
			if (buff.remaining() < 4) {
				return;
			}
			status = PacketStatus.processing;
			curPacket.length = buff.getInt();
		}
		
		if (buff.remaining() < curPacket.length) {
			curPacket.append(buff);
			buff.flip();
			return;
		}
		
		status = PacketStatus.ready;
		curPacket.append(buff);
		doWork(curPacket.getContent());
		buff.compact();
	}
	
	public void doWork(String content) throws IOException {
		if (Util.isShutdown(content)) {
			reset();
		} else {
			autoReply(content);
		}
	}
	
	/**
	 * 自动回复
	 */
	private void autoReply(String msg) throws IOException {
		String replyMsg = "服务端第"+ replyIndex.incrementAndGet() + "次回复: " + "我知道你的说的是->" + msg;
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
	
	
	public enum PacketStatus{
		ready,
		processing;
	}
	
	public class Packet {
		public int length;
		public byte[] content;
		
		public void append(ByteBuffer buff) {
			int curLen = content == null ? 0 : content.length;
			int surplus = Math.min(length - curLen, buff.remaining());
			if (content == null) {
				content = new byte[surplus];
			} else {
				content = Arrays.copyOf(content, surplus + curLen);
			}
			buff.get(curPacket.content, curLen, surplus);
		}
		
		public void clear() {
			length = 0;
			content = null;
		}
		
		public String getContent() {
			return new String(content);
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
