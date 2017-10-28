package cn.szjlxh.push;


import cn.szjlxh.push.server.broadcast.dal.DatabaseManager;
import cn.szjlxh.push.server.broadcast.dal.PushDal;
import cn.szjlxh.push.server.broadcast.message.ThreeDMsg;
import cn.szjlxh.push.server.util.Configuration;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class PushDalTest {

    private PushDal pushDal;

    @Before
    public void init(){
        DatabaseManager.init(new Configuration("project.properties"));
        pushDal = PushDal.getInstance();
    }

    @Test
    public void testInsert(){
        int id = 1;
        String name = "test";
        int type = 2;
        int clientType = 2;
        String message = "hello word";
        String longitude = "123.122121";
        String latitude = "47.333";
        String url = "http://www.baidu.com";
        int communityId = 34;
        int totalNumber = 200;
        pushDal.onSuccess(new ThreeDMsg(id, name, type, clientType, message, longitude, latitude, url, communityId, totalNumber));
    }
}
