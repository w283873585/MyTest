package jun.learn.scene.nettyChain;

public interface Handler {
	public interface OutHandler extends Handler{
		void read(Context ctx);
	}
	
	public interface InHandler extends Handler{
		void read(Context ctx);
	}
}
