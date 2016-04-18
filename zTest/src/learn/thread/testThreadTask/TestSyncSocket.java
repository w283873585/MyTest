package learn.thread.testThreadTask;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



/**
 * 异步处理socket
 * @author Administrator
 *
 */
public class TestSyncSocket{
	private final ExecutorService exec = Executors.newFixedThreadPool(1);
	
	public void start() throws IOException {
		ServerSocket socket = new ServerSocket(80);
		while (!exec.isShutdown()) {
			final Socket conn = socket.accept();
			exec.execute(new Runnable() {
				public void run() {
					handleRequest(conn);
				}
			});
		}
	}
	private void stop() { exec.shutdown(); }
	private void handleRequest(Socket conn) {
		if (isShutdownRequest(conn)) {
			stop();
		} else {
			doRequest(conn);
		}
	}
	private boolean isShutdownRequest(Socket socket) {
		return false;
	}
	private void doRequest(Socket socket) {}
}
