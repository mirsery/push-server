package cn.szjlxh.push.server.broadcast.dal;

import cn.szjlxh.push.server.broadcast.message.ThreeDMsg;
import cn.szjlxh.push.server.util.CallBack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PushDal implements CallBack {

    private static final Logger log = LoggerFactory.getLogger(PushDal.class);

    private static PushDal pushDal;

    private JdbcTemplate jdbcTemplate;

    private PushDal() {
        jdbcTemplate = DatabaseManager.getSqlJdbcTemplate();
    }

    synchronized public static PushDal getInstance() {
        if (pushDal == null)
            pushDal = new PushDal();
        return pushDal;
    }

    @Override
    public void onSuccess(ThreeDMsg threeDMsg) {
        delMessage(threeDMsg);
        log.info("push message to 3D success .");
        recordMessage(threeDMsg, "push3Dlog");
    }

    @Override
    public void onError(ThreeDMsg threeDMsg) {
        log.info("push message to 3D error .");
        recordMessage(threeDMsg, "push3DlogError");
    }

    private void delMessage(ThreeDMsg threeDMsg) {
        String sql = "delete from push3DlogError where id=? and type=? and client_type = ?";
        jdbcTemplate.update(sql, threeDMsg.getId(), threeDMsg.getType(), threeDMsg.getClientType());
    }

    private void recordMessage(ThreeDMsg threeDMsg, String tableName) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = simpleDateFormat.format(new Date());

        String sql = "insert into " + tableName + " (id,name,type,client_type,message,longitude,latitude,url,community_id,total_number,dateTime) values (?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, preparedStatement -> {
            preparedStatement.setInt(1, threeDMsg.getId());
            preparedStatement.setString(2, threeDMsg.getName());
            preparedStatement.setInt(3, threeDMsg.getType());
            preparedStatement.setInt(4, threeDMsg.getClientType());
            preparedStatement.setString(5, threeDMsg.getMessage());
            preparedStatement.setString(6, threeDMsg.getLatitude());
            preparedStatement.setString(7, threeDMsg.getLongitude());
            preparedStatement.setString(8, threeDMsg.getUrl());
            preparedStatement.setInt(9, threeDMsg.getCommunityId());
            preparedStatement.setInt(10, threeDMsg.getTotalNumber());
            preparedStatement.setString(11, dateTime);
        });

    }


}
