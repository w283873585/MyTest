package jun.learn.scene.nettyChain;

import jun.learn.scene.nettyChain.Handler.InHandler;
import jun.learn.scene.nettyChain.Handler.OutHandler;

public class Client {
	public static void main(String[] args) {
		Pipeline pipeline = new Pipeline();
		
		pipeline.addFirst("first", new OutHandler() {
			@Override
			public void read(Context ctx) {
				System.out.println("first handler excute");
			}
		});
		
		pipeline.addLast("last", new InHandler() {
			@Override
			public void read(Context ctx) {
				System.out.println("last handler excute");
			}
		});
		
		pipeline.fireFirst();
		pipeline.fireLast();
	}
}
