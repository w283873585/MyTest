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
        
        /**
         *	Manager 获取 Message
         *	Message: {
         *		type: "",
         *		clientId: "",
         *		body: {}
         *		attribute: {} ? 
         *	}
         * 
         *  dispatch(Message) 
         *  	-> 根据type和clientId, 获取PipeHandler, (需要连接的type类型, 临时type类型, 需要授权的类型)
         *  	-> auth的请求, 缓存Connection在Manager, 该Connection可通过手动关闭, 以及手动发送消息
         *  
         *  
         *  -> 心跳相关
         *  -> 关闭策略
         */
    }
	
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
