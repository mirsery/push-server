package cn.szjlxh.websocket.server.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Configuration {

    private int broadcastServerPort;
    private int audienceServerPort;
    private String[] channels;

    public Configuration(String file) {
        Properties properties = new Properties();
        try {
            if (!file.contains(File.separator)) {
                properties.load(Thread.currentThread().getClass().getResourceAsStream("/" + file));
            } else {
                properties.load(new FileReader(file));
            }

            this.broadcastServerPort = Integer.valueOf(properties.getProperty("BroadcastServerPort"));
            this.audienceServerPort = Integer.valueOf(properties.getProperty("AudienceServerPort"));
            this.channels = properties.getProperty("channels").split(":");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getBroadcastServerPort() {
        return broadcastServerPort;
    }

    public int getAudienceServerPort() {
        return audienceServerPort;
    }

    public String[] getChannels() {
        return channels;
    }
}
