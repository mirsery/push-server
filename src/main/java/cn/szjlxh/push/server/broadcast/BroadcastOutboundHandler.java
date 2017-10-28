package cn.szjlxh.push.server.broadcast;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.CharsetUtil;

public class BroadcastOutboundHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println(msg.toString());
        ByteBuf buf = Unpooled.copiedBuffer("#" + msg.toString() + "#", CharsetUtil.UTF_8);
        ctx.write(buf);
        ctx.flush();
    }
}
