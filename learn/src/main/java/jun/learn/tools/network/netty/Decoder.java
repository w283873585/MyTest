package jun.learn.tools.network.netty;

import java.nio.charset.Charset;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import jun.learn.tools.network.netty.core.Message;

public class Decoder extends ByteToMessageDecoder{

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (in.readableBytes() < 4 )
			return;
		
		in.markReaderIndex();
		int length = in.readInt();
		int type = 0; // (int) in.readByte();
		if (in.readableBytes() < length) {
			in.resetReaderIndex();
			return;
		}
		
		String recvBody = in.readBytes(length)
				.toString(Charset.forName("utf-8"));
		
		Message msg = new Message() {
			private JSONObject body = JSONObject.parseObject(recvBody);
			@Override
			public int getType() {
				return type;
			}
			
			@Override
			public String getClientId() {
				return ctx.channel().id().toString();
			}
			
			@Override
			public String getBody() {
				return body.toJSONString();
			}
			
			@Override
			public Object get(String key) {
				return body.get(key);
			}
		};
		out.add(msg);
	}
}
