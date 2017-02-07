package jun.learn.tools.netty.bootstrap;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@SuppressWarnings("deprecation")
public class DiscardServerHandler extends ChannelInboundHandlerAdapter{
	@Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) { // (2)
		final ByteBuf time = ctx.alloc().buffer(4);
		
		int responseTime = (int) (System.currentTimeMillis() / 1000L + 220898880L);
		System.out.println(responseTime);
		time.writeInt(responseTime);
		
        final ChannelFuture f = ctx.writeAndFlush(time);
        f.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				assert f == future;
				ctx.close();
			}
		});
        
        try {
        	/*
        while (in.isReadable()) { // (1)
            System.out.print((char) in.readByte());
            System.out.flush();
        }
        	 */
        } finally {
        	// ReferenceCountUtil.release(msg); // (2)
        }
    }
	
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
    
    public static void main(String[] args) throws Exception {
		new DiscardServer(8088).run();
	}
}
