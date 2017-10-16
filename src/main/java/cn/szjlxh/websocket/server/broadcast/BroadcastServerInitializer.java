package cn.szjlxh.websocket.server.broadcast;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.util.CharsetUtil;

public class BroadcastServerInitializer extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("delimiter", new DelimiterBasedFrameDecoder(1024 * 60, Unpooled.copiedBuffer("#", CharsetUtil.UTF_8),
                Unpooled.copiedBuffer("#", CharsetUtil.UTF_8)));

        pipeline.addLast(new BroadcastServerChannelInBoundHandler());

        pipeline.addLast(new BroadcastServerOutboundHandler());

    }
}
