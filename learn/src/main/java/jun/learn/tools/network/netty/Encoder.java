package jun.learn.tools.network.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class Encoder extends MessageToByteEncoder<String>{

	@Override
	protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
		byte[] body = msg.getBytes("utf-8");
		int dataLength = body.length;
	    out.writeInt(dataLength);  
	    out.writeBytes(body);
	}
}
