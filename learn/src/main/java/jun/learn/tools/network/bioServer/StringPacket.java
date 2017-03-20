package jun.learn.tools.network.bioServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import jun.learn.tools.network.DataPacket;
import jun.learn.tools.network.Util;

public class StringPacket implements DataPacket<String>{

	@Override
	public String read(InputStream input) throws IOException {
		return Util.read(input);
	}

	@Override
	public void write(OutputStream out, String content) throws IOException {
		Util.write(out, content);
	}
}