package jun.learn.tools.network.netty.core;

public interface Connection {
	
	String getId();
	
	void write(Message message);
	
	void writeAndClose(Message message);
	
	void close();
}
