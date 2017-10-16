package cn.szjlxh.websocket.server.broadcast.message;

public class ResponseMsg {
    private String status;
    private int id;
    private String msg;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "{status:" + status + ",id:" + id + ",msg:" + msg + "}";
    }
}
