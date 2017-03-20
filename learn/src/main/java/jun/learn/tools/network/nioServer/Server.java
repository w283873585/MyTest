package jun.learn.tools.network.nioServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Stack;

import jun.learn.tools.network.Util;

public class Server {
	private Stack<Robot> pools = new Stack<Robot>();
	private int poolSize = 10;
	
	public void start() {
		/**
		 *	推送消息给客户端,
		 *  接收客户端的消息 
		 */
		try {
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			boolean shutdown = false;
			serverSocketChannel.bind(new InetSocketAddress(12020));
			
			initPool();
			while (!shutdown) {
 				assign(serverSocketChannel.accept());
			}
			serverSocketChannel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void initPool() {
		for (int i = 0; i < poolSize; i++) {
			Robot2 robot = new Robot2(this);
			Thread thread = new Thread(robot);
			thread.setDaemon(true);
			thread.start();
			pools.add(robot);
		}
	}
	
	public void assign(SocketChannel socket) {
		try {
			pools.pop().accept(socket);
		} catch (Exception e) {
			System.out.println("已经没有多余的机器人!");
			try {
				Util.write(socket, "连接失败， 连接数已达上限");
				// 分配失败则强制关闭。
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void recycle(Robot r) {
		pools.add(r);
	}
	
	public static void main(String[] args){
		new Server().start(); 
	}
}
