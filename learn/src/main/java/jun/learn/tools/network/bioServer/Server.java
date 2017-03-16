package jun.learn.tools.network.bioServer;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public static void main(String[] args) throws Exception {
		/**
		 *	推送消息给客户端,
		 *  接收客户端的消息 
		 */
		ServerSocket serverSocket = new ServerSocket(12020);
		boolean shutdown = false;
		while (!shutdown) {
			Socket socket = serverSocket.accept();
			Robot r = new Robot();
			r.accept(socket);
			new Thread(r).start();
		}
		serverSocket.close();
	}
}
