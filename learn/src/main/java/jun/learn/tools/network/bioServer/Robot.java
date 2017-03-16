package jun.learn.tools.network.bioServer;

import static jun.learn.tools.network.Util.*;

import java.io.IOException;
import java.net.Socket;

public class Robot implements Runnable{

	private boolean started = false;
	private Socket socket = null; 
	
	@Override
	public void run() {
		while (started) {
			try {
				Msg msg = new Msg(socket.getInputStream());
				if (msg.isReady()) {
					
					// 收到shutdown命令
					if (isShutdownCommand(msg.toString())) {
						started = false;
						socket.close();
						break;
					}
					
					// 自动回复
					String reply = reply(msg.toString());
					socket.getOutputStream().write(int2byte(reply.getBytes().length));
					socket.getOutputStream().write(reply.getBytes());
					socket.getOutputStream().flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
				if (socket.isClosed()) 
					break;
				else 
					continue;
			}
		}
	}
	
	private boolean isShutdownCommand(String msg) {
		return "shutdown".equals(msg);
	}
	
	public String reply(String msg) {
		return "helloworld" + "->" + msg;
	}
	
	
	public void accept(Socket socket) {
		this.started = true;
		this.socket = socket;
	}
}
