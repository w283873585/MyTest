package jun.learn.tools.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

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
			Scanner sc = new Scanner(System.in);
			boolean flag = false;
			while (!flag) {
				DataPacket<String> packet = new StringPacket();
				packet.write(socket.getOutputStream(), sc.nextLine());	// "客户端说: i love you" sc.nextLine()
				System.out.println(packet.read(socket.getInputStream()));
			}
			
			sc.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
