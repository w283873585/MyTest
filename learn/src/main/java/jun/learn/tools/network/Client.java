package jun.learn.tools.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

import jun.learn.tools.network.bioServer.Msg;

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
				String s = sc.nextLine();
				byte[] content = s.getBytes();
				socket.getOutputStream().write(Util.int2byte(content.length));
				socket.getOutputStream().write(content);
				socket.getOutputStream().flush();
				Msg msg = new Msg(socket.getInputStream());
				if (msg.isReady()) {
					System.out.println(msg);
				}
			}
			
			sc.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public static void send(OutputStream out, String msg) {
		try {
			out.write(msg.getBytes());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String print(InputStream input) {
		byte[] buff = new byte[1024];
		StringBuilder sb = new StringBuilder();
		try {
			while (input.read(buff) != -1) {
				sb.append(new String(buff));
				System.out.println("读到的数据是" + sb);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
