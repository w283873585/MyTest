package com.thread.testThreadTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Executor
 *
 */
public class TestExecutor{
	private static final int NTHREADS = 100;
	private static final ExecutorService exec = 
			Executors.newFixedThreadPool(NTHREADS);
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		ServerSocket server = new ServerSocket(80, 1, InetAddress.getByName("127.0.0.1"));
		while (true) {
			final Socket socket = server.accept();
			Runnable task = new Runnable() {
				public void run() {
					processRequest(socket);
				}
			};
			exec.execute(task);
		}
		/**
		InputStream input = socket.getInputStream();
		byte[] b = new byte[1024];
		int i = 0;
		ByteBuffer buff = ByteBuffer.allocate(1024);
		while (i != -1) {
			i = input.read(b);
			output(buff.put(b));
		}
		input.close();
		server.close();
		*/
	}
	
	private static void processRequest(Socket socket) {}
	
	private static void output(ByteBuffer buff) {
		buff.flip();
		while (buff.hasRemaining()) {
			System.out.print((char)buff.get());
		}
		buff.clear();
	}
}
