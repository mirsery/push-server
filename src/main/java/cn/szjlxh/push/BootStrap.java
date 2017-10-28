package cn.szjlxh.push;

import cn.szjlxh.push.server.broadcast.Broadcast;
import cn.szjlxh.push.server.broadcast.dal.DatabaseManager;
import cn.szjlxh.push.server.broadcast.task.RecoverTask;
import cn.szjlxh.push.server.util.Configuration;

public class BootStrap {


    private static Configuration configuration;

    public static void initConf(String file) {
        configuration = new Configuration(file);
        DatabaseManager.init(configuration);

        new RecoverTask(configuration).startRepeatTransport();
    }

    public static void startPushServer(final Configuration configuration) {
        new Broadcast(configuration).start();
    }

    public static void main(String[] args) {


        initConf("project.properties");

        startPushServer(configuration);
    }
}
