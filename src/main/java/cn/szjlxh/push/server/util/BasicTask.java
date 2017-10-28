package cn.szjlxh.push.server.util;

import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.util.concurrent.TimeUnit;

/**
 * Created by mirsery on 24/03/2017.
 */
public abstract class BasicTask implements TimerTask{

    private long delay;
    private TimeUnit timeUnit;
    private boolean cycleSwitch = true;

    public void setCycleSwitch(boolean cycleSwitch) {
        this.cycleSwitch = cycleSwitch;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    abstract protected void task();

    @Override
    public void run(Timeout timeout) throws Exception {
        task();
        if(cycleSwitch)
            SchedulerTaskFactory.createTask(this,getDelay(),getTimeUnit());
    }
}
