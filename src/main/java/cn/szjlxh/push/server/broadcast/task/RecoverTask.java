package cn.szjlxh.push.server.broadcast.task;


import cn.szjlxh.push.server.broadcast.dal.PushDal;
import cn.szjlxh.push.server.broadcast.dal.RecoverDal;
import cn.szjlxh.push.server.broadcast.message.ThreeDMsg;
import cn.szjlxh.push.server.util.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class RecoverTask {

    private RecoverDal recoverDal;
    private Configuration configuration;

    public RecoverTask(Configuration configuration) {
        recoverDal = RecoverDal.getInstance();
        this.configuration = configuration;
    }

    public void startRepeatTransport() {
        SchedulerTaskFactory.createTask(new BasicTask() {
            @Override
            protected void task() {
                List<ThreeDMsg> lists = recoverDal.getErrorMsg();
                for (ThreeDMsg msg : lists) {
                    PushServerUtil.recover(ThreeDConfig.threeDAPI, msg, PushDal.getInstance());
                }
            }
        }, configuration.getRecoverTime(), TimeUnit.MINUTES);
    }

}