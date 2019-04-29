package com.unis.db.model;

/**
 * @author xuli
 * @date 2019/3/28
 */
public class FaceArchive {
    /**
     * 人脸id
     */
    private Long faceID;
    /**
     * 人员id
     */
    private Long peopleID;
    /**
     * 类型 0或1
     */
    private Integer type;
    /**
     * 卡口
     */
    private String tollgateID;
    /**
     * 设备id
     */
    private String deviceID;
    /**
     * 图片url
     */
    private String imageUrl;
    /**
     * 人脸url
     */
    private String faceUrl;
    /**
     * 时间戳
     */
    private Long timestamp;
    /**
     * 分区时间
     */
    private String date;

    public Long getFaceID() {
        return faceID;
    }

    public void setFaceID(Long faceID) {
        this.faceID = faceID;
    }

    public Long getPeopleID() {
        return peopleID;
    }

    public void setPeopleID(Long peopleID) {
        this.peopleID = peopleID;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTollgateID() {
        return tollgateID;
    }

    public void setTollgateID(String tollgateID) {
        this.tollgateID = tollgateID;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return  faceID +
                "," + peopleID +
                "," + type +
                "," + tollgateID +
                "," + deviceID +
                "," + imageUrl +
                "," + faceUrl +
                "," + timestamp +
                "," + date;
    }
}
