package cn.szjlxh.websocket.server.util;

import cn.szjlxh.websocket.server.audience.model.AudienceClient;
import cn.szjlxh.websocket.server.broadcast.message.PushMsg;
import cn.szjlxh.websocket.server.broadcast.message.ResponseMsg;
import io.netty.channel.Channel;
import io.netty.util.internal.ConcurrentSet;

import java.util.Map;
import java.util.concurrent.*;

public class PushServerUtil {

    public final static ExecutorService work = Executors.newSingleThreadExecutor();
    public final static ExecutorService remove = Executors.newSingleThreadExecutor();

    public final static ConcurrentMap<String, ConcurrentSet<String>> allBroadcastChannels = new ConcurrentHashMap();    //频道 -> 用户id

    public final static Map<Channel, String> allChannels = new ConcurrentHashMap<Channel, String>();   // channel -> 用户id

    public final static Map<String, AudienceClient> allClients = new ConcurrentHashMap<String, AudienceClient>(); // id -> channel


    public static void removeAudience(final Channel channel) {

        remove.submit(new Runnable() {
            public void run() {

                if (allChannels.containsKey(channel)) {
                    String clientId = allChannels.get(channel);

                    if (allClients.containsKey(clientId)) {
                        AudienceClient audienceClient = allClients.get(clientId);
                        audienceClient.onDisConnect();
                        allClients.remove(clientId);

                    }
                    allChannels.remove(channel);
                }

            }
        });
    }


    public static void noticeMessage(final PushMsg pushMsg, final Channel channel) {
        work.submit(new Runnable() {
            public void run() {
                boolean flag = false;
                String channelId = pushMsg.getChannelId();
                String message = pushMsg.getData();

                ResponseMsg responseMsg = new ResponseMsg();
                responseMsg.setId(pushMsg.getId());

                if (allBroadcastChannels.containsKey(channelId)) {
                    ConcurrentSet<String> clientIds = allBroadcastChannels.get(channelId);
                    for (String clientId : clientIds) {
                        allClients.get(clientId).messageNotice(message);
                        flag = true;
                    }

                    if (flag) {
                        responseMsg.setStatus("200");
                        responseMsg.setMsg("推送成功");
                    } else {
                        responseMsg.setStatus("204");
                        responseMsg.setMsg("当前没有客户端在线");
                    }

                } else {
                    responseMsg.setStatus("500");
                    responseMsg.setMsg("channelId不存在");
                }
                channel.writeAndFlush(responseMsg);
            }
        });

    }
}
