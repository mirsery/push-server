package cn.szjlxh.websocket;

import cn.szjlxh.websocket.server.audience.AudienceServer;
import cn.szjlxh.websocket.server.broadcast.BroadcastServer;
import cn.szjlxh.websocket.server.util.Configuration;
import cn.szjlxh.websocket.server.util.PushServerUtil;
import io.netty.util.internal.ConcurrentSet;

public class BootStrap {


    private static Configuration configuration;

    public static void initConf(String file) {
        configuration = new Configuration(file);
        String[] channels = configuration.getChannels();
        for (String channel : channels) {//初始化channel
            PushServerUtil.allBroadcastChannels.put(channel, new ConcurrentSet<String>());
        }
    }

    public static void startAudienceServer(final Configuration configuration) {
        new Thread(new Runnable() {
            public void run() {
                new AudienceServer(configuration).start();
            }
        }).start();
    }

    public static void startPushServer(final Configuration configuration) {
        new Thread(new Runnable() {
            public void run() {
                new BroadcastServer(configuration).start();
            }
        }).start();
    }

    public static void main(String[] args) {

        initConf("project.properties");

        startAudienceServer(configuration);
        startPushServer(configuration);
    }
}
