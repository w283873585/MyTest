package jun.learn.tools.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface DataPacket<T> {
	
	public T read(InputStream input) throws IOException;
	
	void write(OutputStream out, T content) throws IOException;
}
