package jun.learn.tools.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jun.learn.tools.network.bioServer.StringPacket;

public class Client {
	
	public static void main(String[] args) {
		connect(1);
	}
	
	public static void connect(int size) {
		for (int i = 0; i < size; i++) {
			new Thread() {
				public void run() {
					doConnect();
				}
			}.start();;
		}
	}
	
	private static void doConnect() {
		Socket socket = new Socket();
		try {
			socket.connect(new InetSocketAddress(12020));
			DataPacket<String> packet = new StringPacket();
			
			/**
			 * 独立线程去读取内容， 
			 * 以解包的方式读取， 每次只读取一个包
			 */
			ExecutorService service = Executors.newFixedThreadPool(1);
			service.execute(new Runnable() {
				@Override
				public void run() {
					try {
						while (true) {
							String msg = packet.read(socket.getInputStream());
							if (Util.isShutdown(msg)) {
								System.out.println("客户端准备关闭..");
								socket.close();
								service.shutdown();
								System.out.println("客户端已经关闭..");
								break;
							}
							System.out.println(msg);
						}
					} catch (IOException e) { 
						e.printStackTrace();
					}
				}
			});
			
			/**
			 * 循环发布1000个消息
			 */
			for (int i = 0; i < 10; i++)
				packet.write(socket.getOutputStream(), "helloworld");
			
			Scanner sc = new Scanner(System.in);
			sc.nextLine();
			sc.close();
			
			// 通知服务器，我要关闭连接了。
			Util.sendShutdownCommand(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
