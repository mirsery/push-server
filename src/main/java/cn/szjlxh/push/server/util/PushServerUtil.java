package cn.szjlxh.push.server.util;


import cn.szjlxh.push.server.broadcast.message.ThreeDMsg;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PushServerUtil {

    public final static ExecutorService work = Executors.newSingleThreadExecutor();

    public final static ExecutorService recover = Executors.newSingleThreadExecutor();

    public static void pushMsg(final String url, final ThreeDMsg content, final CallBack callBack) {
        work.submit(() ->{
                new HttpRequest().sendPost(url, content, callBack);
        });
    }

    public static void recover(final String url, final ThreeDMsg content, final CallBack callBack) {
        recover.submit(() ->{
            new HttpRequest().sendPost(url, content, callBack);
        });
    }

}