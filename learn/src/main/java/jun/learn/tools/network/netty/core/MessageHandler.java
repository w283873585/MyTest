package jun.learn.tools.network.netty.core;

public interface MessageHandler {
	
	public Manager getManager();
	
	public void handle(Message message);
}
