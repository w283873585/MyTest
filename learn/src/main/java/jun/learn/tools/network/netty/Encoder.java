package jun.learn.tools.network.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import jun.learn.tools.network.netty.core.Message;

public class Encoder extends MessageToByteEncoder<Message>{

	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
		byte[] body = msg.getBody().getBytes("urf-8");
	    out.writeInt(body.length);  
	    out.writeByte(msg.getType());
	    out.writeBytes(body);
	}
}
