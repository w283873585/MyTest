package jun.learn.tools.network.bioServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Stack;

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
				try {
					assign(socket);
				} catch (Exception ignore) {}
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
		pools.pop().accept(socket);
	}
	
	public void recycle(Robot r) {
		pools.add(r);
	}
	
	public static void main(String[] args){
		new Server().start();
	}
}
