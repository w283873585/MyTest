package jun.learn.tools.network.nioServer;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

import jun.learn.tools.network.Util;

public class Robot extends Thread{
	private static AtomicInteger index = new AtomicInteger();
	
	
	private volatile boolean started = false;
	
	private final int id;
	private final Server server;
	private SocketChannel socket = null;
	private AtomicInteger replyIndex =  new AtomicInteger();
	
	public Robot(Server server) {
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
			 * 当被唤醒时, 与客户端进行通信, 提供自动回复功能
			 */
			System.out.println(this + "开始工作");
			try {
				Util.write(socket, "成功连接->" + this);
				while (started) {
					String msg = Util.read(socket);
					
					// 收到shutdown命令
					if (Util.isShutdown(msg)) {
						reset();
						break;
					}
					
					// 自动回复
					Util.write(socket, reply(msg));
				}
			} catch (IOException e) {
				reset();
			}
		}
	}
	
	
	public synchronized void accept(SocketChannel socket) {
		if (started) {
        	System.out.println("有两个线程使用了同一个robot");
            return;
        }
		this.started = true;
		this.socket = socket;
		this.notifyAll();
	}
	
	private String reply(String msg) {
		return "服务端第"+ replyIndex.incrementAndGet() + "次回复: " + "我知道你的说的是->" + msg;
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
			e.printStackTrace();
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
