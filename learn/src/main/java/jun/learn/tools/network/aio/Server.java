package jun.learn.tools.network.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class Server {
	
	
	public void serve(int port) throws IOException {
		System.out.println("Listening for connections on port " + port);
		final AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open();
		
		InetSocketAddress address = new InetSocketAddress(port);
		serverChannel.bind(address);
		
		final CountDownLatch latch = new CountDownLatch(1);
		serverChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {

			@Override
			public void completed(AsynchronousSocketChannel channel, Object attachment) {
				serverChannel.accept(null, this);
				ByteBuffer buffer = ByteBuffer.allocate(100);
				channel.read(buffer, buffer, new EchoCompletionHandler(channel));
			}

			@Override
			public void failed(Throwable exc, Object attachment) {
				try {
					serverChannel.close();
				} catch (IOException e) {
					// ingnore on close
				} finally {
					latch.countDown();
				}
			}
		});
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	
	public static void main(String[] args) {
		try {
			new Server().serve(12020);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
