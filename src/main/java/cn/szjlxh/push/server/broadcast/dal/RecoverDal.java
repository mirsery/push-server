package cn.szjlxh.push.server.broadcast.dal;

import cn.szjlxh.push.server.broadcast.message.ThreeDMsg;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RecoverDal {

    private static RecoverDal recoverDal;

    private JdbcTemplate jdbcTemplate;

    private RecoverDal() {
        jdbcTemplate = DatabaseManager.getSqlJdbcTemplate();
    }

    synchronized public static RecoverDal getInstance() {
        if (recoverDal == null)
            recoverDal = new RecoverDal();
        return recoverDal;
    }

    public List<ThreeDMsg> getErrorMsg() {

        String sql = "select id,name,type,client_type,message,longitude,latitude,url,community_id,total_number from push3DlogError limit 0,100";
        List<ThreeDMsg> list = jdbcTemplate.query(sql, (resultSet, i) -> {

            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            int type = resultSet.getInt(3);
            int clientType = resultSet.getInt(4);
            String message = resultSet.getString(5);
            String longitude = resultSet.getString(6);
            String latitude = resultSet.getString(7);
            String url = resultSet.getString(8);
            int communityId = resultSet.getInt(9);
            int totalNumber = resultSet.getInt(10);

            return new ThreeDMsg(id, name, type, clientType, message, longitude, latitude, url, communityId, totalNumber);
        });
        return list;
    }

}
