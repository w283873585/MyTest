package jun.learn.tools.network.netty;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import jun.learn.tools.network.Util;

public class Handler extends ChannelInboundHandlerAdapter{
	
	@Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) { // (2)
		ByteBuf data = (ByteBuf) msg;
		String recMsg = data.toString(Charset.forName("utf-8"));
		String sendMsg = "服务器说#### 我知道你说了->" + recMsg;
        if (!Util.isShutdown(recMsg)) {
        	ctx.writeAndFlush(sendMsg);
        	return;
        }
        
        ChannelFuture f = ctx.writeAndFlush("886");
        f.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				assert f == future;
				ctx.close();
			}
		});
    }
	
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
