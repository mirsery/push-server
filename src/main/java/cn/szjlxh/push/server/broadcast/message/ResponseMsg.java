package cn.szjlxh.push.server.broadcast.message;

public class ResponseMsg {
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "{status:" + status + "}";
    }
}
