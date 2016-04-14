package com.thread.testThreadAbort;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;






/**
 */
public class AbortSocketIO extends Thread{
	private final Socket socket;
	private final InputStream input;
	private static final int BUFSZ = 1024;
	
	public AbortSocketIO(Socket socket) throws IOException {
		this.socket = socket;
		this.input = socket.getInputStream();
	}
	
	public void run() {
		try {
			byte[] buf = new byte[BUFSZ];
			while (true) {
				int count;
				count = input.read(buf);
				if (count < 0) {
					break;
				} else if (count > 0) {
					processBuff(buf, count);
				}
			}
		} catch (IOException e) {/* 允许程序正常退出 */}
	}
	
	private void processBuff(byte[] buf, int count) {
	}

	public void interrupt() {
		try {
			socket.close();
		} catch (IOException e) { }
		finally {
			super.interrupt();
		}
	}
}
