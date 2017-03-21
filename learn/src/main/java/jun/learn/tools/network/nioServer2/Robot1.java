package jun.learn.tools.network.nioServer2;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

import jun.learn.tools.network.Util;

public class Robot1 implements Robot{
	private static AtomicInteger index = new AtomicInteger();
	
	private volatile boolean started = false;
	
	private final int id;
	private final Server server;
	private SocketChannel socket = null;
	private AtomicInteger replyIndex =  new AtomicInteger();
	
	public Robot1(Server server) {
		this.id = index.incrementAndGet();
		this.server = server;
	}
	
	@Override
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
			
			/**
			 * 与客户端进行通信, 提供自动回复功能
			 */
			try {
				doWork();
			} catch (IOException e) {
				reset();
			}
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
	
	/**
	 * 与客户端进行通信, 提供自动回复功能
	 */
	private void doWork() throws IOException {
		System.out.println(this + "开始工作");
		
		// 告知客户端, 已成功连接
		Util.write(socket, "成功连接->" + this);
		
		// 轮询处理接收的数据包
		while (started) {
			String msg = Util.read(socket);
			if (Util.isShutdown(msg)) {
				reset();
			} else {
				autoReply(msg);
			}
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
}
