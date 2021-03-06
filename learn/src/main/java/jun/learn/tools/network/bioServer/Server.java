package jun.learn.tools.network.bioServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
		ServerSocket serverSocket;
		try {
			boolean shutdown = false;
			serverSocket = new ServerSocket(12020);
			
			initPool();
			
			while (!shutdown) {
				Socket socket = serverSocket.accept();
				/**
				 * 单线程环境， 分配到不同线程
				 */
				assign(socket);
			}
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void initPool() {
		for (int i = 0; i < poolSize; i++) {
			Robot thread = new Robot(this);
			thread.setDaemon(true);
			thread.start();
			pools.add(thread);
		}
	}
	
	public void assign(Socket socket) {
		try {
			pools.pop().accept(socket);
		} catch (Exception e) {
			System.out.println("已经没有多余的机器人!");
			try {
				Util.write(socket.getOutputStream(), "连接失败， 连接数已达上限");
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
