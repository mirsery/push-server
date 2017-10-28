package cn.szjlxh.push.server.broadcast.dal;

import cn.szjlxh.push.server.util.Configuration;
import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;


public class DatabaseManager {

    public static String passwd;
    public static String user;
    public static String dbUrl;
    private static final Logger log = LoggerFactory.getLogger(DatabaseManager.class);
    private static BasicDataSource dataSource;

    public static void init(Configuration configure) {
        try {

            if (configure != null) {
                dbUrl = configure.getUrl();
                user = configure.getUserName();
                passwd = configure.getPassword();
            }

            if (dataSource == null) {
                dataSource = new BasicDataSource();
                dataSource.setUrl(dbUrl);
                dataSource.setPassword(passwd);
                dataSource.setUsername(user);
                dataSource.setDriverClassName("com.mysql.jdbc.Driver");
                dataSource.setValidationQuery("select 1 as dbcp_connection_test");
                dataSource.addConnectionProperty("characterEncoding", "utf-8");
                dataSource.addConnectionProperty("useUnicode", "true");
            }

            new JdbcTemplate(dataSource);

            log.info("the database is initialized successfully. ");

        } catch (Exception e) {
            log.error("database failed for reasons of " + e.getMessage());
        }
    }

    public static JdbcTemplate getSqlJdbcTemplate() {

        JdbcTemplate template = new JdbcTemplate(dataSource);
        return template;
    }
}
