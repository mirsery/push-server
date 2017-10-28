package cn.szjlxh.push.server.broadcast;

import cn.szjlxh.push.server.broadcast.dal.PushDal;
import cn.szjlxh.push.server.broadcast.message.PushMsg;
import cn.szjlxh.push.server.broadcast.message.ThreeDMsg;
import cn.szjlxh.push.server.util.PushServerUtil;
import cn.szjlxh.push.server.util.ThreeDConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BroadcastInBoundHandler extends ChannelInboundHandlerAdapter {

    private static Gson gson = new GsonBuilder().create();

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        /**
         *
         **/

        if (!(msg instanceof ByteBuf))
            return;
        ByteBuf buf = (ByteBuf) msg;

        if (buf.isReadable()) {

            String message = buf.toString(CharsetUtil.UTF_8);

            log.info("receive the message : " + message);

            PushMsg pushMsg = gson.fromJson(message, PushMsg.class);

            if (pushMsg == null)
                return;

            if ("ssha".equals(pushMsg.getChannelId())) {
                ThreeDMsg threeDMsg = pushMsg.getData();
                PushServerUtil.pushMsg(ThreeDConfig.threeDAPI, threeDMsg, PushDal.getInstance());
            }

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
