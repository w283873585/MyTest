package jun.learn.tools.network.nioServer2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

import jun.learn.tools.network.Util;

public class Server {
	private Stack<Robot> pools = new Stack<Robot>();
	private int poolSize = 10;
	
	public void start() {
		/**
		 *	推送消息给客户端,
		 *  接收客户端的消息 
		 */
		try {
			initPool();
			boolean shutdown = false;
			
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.bind(new InetSocketAddress(12020));
			serverSocketChannel.configureBlocking(false);
			
			Selector selector = Selector.open();
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			
			while (!shutdown) {
			  int readyChannels = selector.select();
			  if(readyChannels == 0) continue;
			  Set<SelectionKey> selectedKeys = selector.selectedKeys();
			  Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
			  while (keyIterator.hasNext()) {
			    SelectionKey key = keyIterator.next();
			    if (key.isAcceptable()) {
			    	ServerSocketChannel server = (ServerSocketChannel) key.channel();
			    	SocketChannel channel = server.accept();
			    	channel.configureBlocking(false);
			    	if (channel != null) {
			    		SelectionKey readKey = channel.register(selector, SelectionKey.OP_READ);
			    		readKey.attach(channel);
			    	}
			        // a connection was accepted by a ServerSocketChannel.
			    } else if (key.isReadable()) {
			        // a channel is ready for reading
			    	SocketChannel s = (SocketChannel) key.attachment();
			    	System.out.println("-?" + Util.read(s));
			    }
			    
			    else if (key.isWritable()) {
			    	// a channel is ready for writing
			    } else if (key.isConnectable()) {
			        // a connection was established with a remote server.
			    } 
			    
			    keyIterator.remove();
			  }
			}
			serverSocketChannel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void initPool() {
		for (int i = 0; i < poolSize; i++) {
			Robot2 robot = new Robot2(this);
			Thread thread = new Thread(robot);
			thread.setDaemon(true);
			thread.start();
			pools.add(robot);
		}
	}
	
	public void assign(SocketChannel socket) {
		try {
			pools.pop().accept(socket);
		} catch (Exception e) {
			System.out.println("已经没有多余的机器人!");
			try {
				Util.write(socket, "连接失败， 连接数已达上限");
				// 分配失败则强制关闭。
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void recycle(Robot r) {
		pools.add(r);
	}
	
	public static void main(String[] args){
		new Server().start(); 
	}
}
