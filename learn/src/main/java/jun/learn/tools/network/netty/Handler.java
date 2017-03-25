package jun.learn.tools.network.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import jun.learn.tools.network.netty.core.Manager;
import jun.learn.tools.network.netty.core.Message;
import jun.learn.tools.network.netty.core.support.ManagerSupport;

public class Handler extends ChannelInboundHandlerAdapter{
	Manager manager = new ManagerSupport(); 
	
	@Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) { // (2)
		manager.dispath((Message) msg, ctx);
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
