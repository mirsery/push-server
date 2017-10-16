package cn.szjlxh.websocket.server.broadcast.message;

public class PushMsg {
    private String channelId;
    private String data;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{id:\"" + id + "\",channelId:\"" + channelId + "\",data:\"" + data + "\"}";
    }
}
