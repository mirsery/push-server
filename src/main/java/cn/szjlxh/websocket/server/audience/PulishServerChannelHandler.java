package cn.szjlxh.websocket.server.audience;

import cn.szjlxh.websocket.server.audience.message.AudienceMsg;
import cn.szjlxh.websocket.server.audience.model.AudienceClient;
import cn.szjlxh.websocket.server.util.PushServerUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.internal.ConcurrentSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PulishServerChannelHandler extends SimpleChannelInboundHandler<Object> {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private static Gson gson = new GsonBuilder().create();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        PushServerUtil.concurrentMap.put("channelId", new ConcurrentSet<Channel>());
        log.info("new Client connect on the server");
    }

    @SuppressWarnings("static-access")
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof HttpRequest) {

            HttpHeaders headers = ((HttpRequest) msg).headers();
            HttpRequest request = (HttpRequest) msg;

            if (!request.getMethod().equals(HttpMethod.GET) || !"websocket".equalsIgnoreCase(headers.get("Upgrade"))) {
                DefaultHttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1,
                        HttpResponseStatus.BAD_REQUEST);
                ctx.writeAndFlush(response);
                ctx.close();
            }

            WebSocketServerHandshakerFactory wsShakerFactory = new WebSocketServerHandshakerFactory(
                    "ws://" + headers.get(HttpHeaders.Names.HOST), headers.get(HttpHeaders.Names.WEBSOCKET_PROTOCOL),
                    false);

            WebSocketServerHandshaker wsShakerHandler = wsShakerFactory.newHandshaker(request);
            if (wsShakerHandler == null) {
                // 无法处理的websocket版本
                wsShakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            } else {
                // 向客户端发送websocket握手,完成握手
                // 客户端收到的状态是101 sitching protocol
                wsShakerHandler.handshake(ctx.channel(), (FullHttpRequest) request);

            }

        } else if (msg instanceof WebSocketFrame) {

            WebSocketFrame request = (WebSocketFrame) msg;

            if (request instanceof CloseWebSocketFrame) {

                log.info("close the socket");
                PushServerUtil.removeAudience(ctx.channel());
                ctx.close();
            } else if (request instanceof PingWebSocketFrame) {
                ctx.writeAndFlush(new PongWebSocketFrame(request.content()));
            } else if (request instanceof TextWebSocketFrame) {

                TextWebSocketFrame txtReq = (TextWebSocketFrame) request;

                String message = txtReq.text();

                AudienceMsg audienceMsg = gson.fromJson(message, AudienceMsg.class);

                if (audienceMsg.getClientId() == null || "".equals(audienceMsg.getClientId())) {
                    audienceMsg.setClientId(ctx.channel().toString());
                }

                String clientId = audienceMsg.getClientId();
                String channelId = audienceMsg.getChannelId();
                log.info("Received : " + message);

                if (PushServerUtil.allBroadcastChannels.containsKey(channelId)) {//channelId

                    ConcurrentSet<String> clientIds = PushServerUtil.allBroadcastChannels.get(channelId);
                    if (!clientIds.contains(clientId)) {
                        clientIds.add(clientId);
                        Channel channel = ctx.channel();
                        PushServerUtil.allChannels.put(channel, clientId);
                        PushServerUtil.allClients.put(clientId, new AudienceClient(channelId, channel,clientId));
                        log.info("clientId:" + clientId + " subscribe the channelId: " + channelId);
                    }
                } else {
                    ctx.disconnect();
                }
//				// 向websocket客户端发送多个响应
//				for (int i = 1; i <= 20; i++) {
//					ctx.writeAndFlush(new TextWebSocketFrame("hello word" + i));
//					try {
//						Thread.sleep(300);
//					} catch (Exception ex) {
//						ex.printStackTrace();
//					}
//				}
            }
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        PushServerUtil.removeAudience(ctx.channel());
        ctx.disconnect();
    }
}
