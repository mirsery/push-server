package cn.szjlxh.push.server.util;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import java.util.concurrent.TimeUnit;

/**
 * Created by mirsery on 23/03/2017.
 */
public class SchedulerTaskFactory {

    private static HashedWheelTimer instance;

    private static synchronized HashedWheelTimer getInstance() {
        if (instance == null) {
            instance = new HashedWheelTimer();
        }
        return instance;
    }

    public static Timeout createTask(BasicTask task, long delay, TimeUnit timeUnit) {
        task.setDelay(delay);
        task.setTimeUnit(timeUnit);
        return getInstance().newTimeout(task, delay, timeUnit);
    }

}