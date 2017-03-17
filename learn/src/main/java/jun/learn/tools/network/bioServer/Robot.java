package jun.learn.tools.network.bioServer;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

import jun.learn.tools.network.DataPacket;

public class Robot extends Thread{
	private static AtomicInteger index = new AtomicInteger();
	
	private volatile boolean started = false;
	
	private final Server server;
	private Socket socket = null;
	private final int id;
	
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
			synchronized (this) {
				try {
					while (!started) {
						System.out.println(this + "进入休眠");
						this.wait();
					}
				} catch (InterruptedException e) {}
			}
			
			/**
			 * 当被唤醒时, 与客户端进行通信, 提供自动回复功能
			 */
			System.out.println(this + "开始工作");
			while (started) {
				try {
					DataPacket<String> packet = new StringPacket();
					String msg = packet.read(socket.getInputStream());
					
					// 收到shutdown命令
					if (isShutdownCommand(msg)) {
						reset();
						break;
					}
					
					// 自动回复
					packet.write(socket.getOutputStream(), reply(msg));
				} catch (IOException e) {
					e.printStackTrace();
					reset();
				}
			}
		}
	}
	
	
	public synchronized void accept(Socket socket) {
		while (started) {
            try {
            	System.out.println("有两个线程使用了同一个robot");
                wait();
            } catch (InterruptedException e) {}
        }
		this.started = true;
		this.socket = socket;
		this.notifyAll();
	}
	
	private boolean isShutdownCommand(String msg) {
		String command = "886";
		return command.equals(msg);
	}
	
	private String reply(String msg) {
		return "服务端: " + "我知道你的说的是->" + msg;
	}
	
	/**
	 * reset this 
	 */
	private void reset() {
		System.out.println(this + "重置该连接");
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		socket = null;
		started = false;
		
		synchronized (this) {
			this.notifyAll();
		}
		if (started == false) {
			System.out.println(this + "被回收");
			server.recycle(this);
		}
	}
	
	public String toString() {
		return "robot" + id + "号: ";
	}
}
