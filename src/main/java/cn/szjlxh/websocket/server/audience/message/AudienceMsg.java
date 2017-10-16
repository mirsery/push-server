package cn.szjlxh.websocket.server.audience.message;

public class AudienceMsg {

    private String channelId;
    private String clientId;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "{\"channelId\":\"" + channelId + "\",\"clientId\":\"" + clientId + "\"";
    }
}
