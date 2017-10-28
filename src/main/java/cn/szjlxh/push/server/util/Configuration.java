package cn.szjlxh.push.server.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Configuration {

    private int broadcastServerPort;

    private String url;
    private String userName;
    private String password;

    private int recoverTime;


    public Configuration(String file) {
        Properties properties = new Properties();
        try {
            if (!file.contains(File.separator)) {
                properties.load(Thread.currentThread().getClass().getResourceAsStream("/" + file));
            } else {
                properties.load(new FileReader(file));
            }

            this.broadcastServerPort = Integer.valueOf(properties.getProperty("BroadcastServerPort"));
            this.url = properties.getProperty("url");
            this.userName = properties.getProperty("userName");
            this.password = properties.getProperty("password");
            ThreeDConfig.threeDAPI = properties.getProperty("threeDAPI");
            this.recoverTime = Integer.valueOf(properties.getProperty("recoverTime"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getBroadcastServerPort() {
        return broadcastServerPort;
    }

    public String getUrl() {
        return url;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getRecoverTime() {
        return recoverTime;
    }
}
