package cn.szjlxh.websocket.server.broadcast;

import cn.szjlxh.websocket.server.broadcast.message.PushMsg;
import cn.szjlxh.websocket.server.util.PushServerUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BroadcastServerChannelInBoundHandler extends ChannelInboundHandlerAdapter {

    private static Gson gson = new GsonBuilder().create();

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        /**
         *
         *  {"channelId":"saddasdhjsahdjash","data":""}
         *
         **/

        if (!(msg instanceof ByteBuf))
            return;
        ByteBuf buf = (ByteBuf) msg;

        if (buf.isReadable()) {

            String message = buf.toString(CharsetUtil.UTF_8);

            PushMsg pushMsg = gson.fromJson(message, PushMsg.class);

            if(pushMsg == null)
                return;

            log.info("receive the message : " + pushMsg);

            PushServerUtil.noticeMessage(pushMsg, ctx.channel());

            buf.release();
        } else {
            return;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

}
