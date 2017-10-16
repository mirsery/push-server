package cn.szjlxh.websocket.server.audience.model;


import cn.szjlxh.websocket.server.util.PushServerUtil;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.internal.ConcurrentSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AudienceClient {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private String channelId;

    private Channel channel;

    private String clientId;

    public AudienceClient(String channelId, Channel channel,String clientId) {
        this.channelId = channelId;
        this.channel = channel;
        this.clientId = clientId;
    }

    public void messageNotice(String message) {
        channel.writeAndFlush(new TextWebSocketFrame(message));
    }

    public void onDisConnect() {
        if (PushServerUtil.allBroadcastChannels.containsKey(channelId)) {
            ConcurrentSet<String> concurrentSet = PushServerUtil.allBroadcastChannels.get(channelId);
            concurrentSet.remove(channelId);
            log.info("client remover . channelId: "+ channelId + ",clientId: "+ clientId);
        }
    }

}
