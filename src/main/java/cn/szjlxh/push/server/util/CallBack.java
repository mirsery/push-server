package cn.szjlxh.push.server.util;

import cn.szjlxh.push.server.broadcast.message.ThreeDMsg;

public interface CallBack {

    void onSuccess(ThreeDMsg threeDMsg);

    void onError(ThreeDMsg threeDMsg);

}
