package cn.szjlxh.push.server.broadcast.message;

public class ThreeDMsg {
    private int id;
    private String name;
    private int type;
    private int clientType;
    private String message;
    private String longitude;
    private String latitude;
    private String url;
    private int communityId;
    private int totalNumber;

    public ThreeDMsg(int id, String name, int type, int clientType, String message, String longitude, String latitude, String url, int communityId, int totalNumber) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.clientType = clientType;
        this.message = message;
        this.longitude = longitude;
        this.latitude = latitude;
        this.url = url;
        this.communityId = communityId;
        this.totalNumber = totalNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getClientType() {
        return clientType;
    }

    public void setClientType(int clientType) {
        this.clientType = clientType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCommunityId() {
        return communityId;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ",\"name\":" + name +
                ",\"type\":" + type +
                ",\"clientType\":" + clientType +
                ",\"message\":" + message +
                ",\"longitude\":" + longitude +
                ",\"latitude\":" + latitude +
                ",\"url\":" + url +
                ",\"communityId\":" + communityId +
                ",\"totalNumber\":" + totalNumber +
                "}";
    }
}
