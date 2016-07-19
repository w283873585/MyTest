package learn.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TestSocket {
	public static void main(String[] args) throws IOException {
		Socket socket = new Socket();
		ServerSocket server = new ServerSocket(8080);
	}
}
