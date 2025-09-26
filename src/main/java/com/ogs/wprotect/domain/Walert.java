package com.ogs.wprotect.domain;

public class Walert {
    private Integer id;
    private String message;
    private String latitude;
    private String longitude;
    private String timestamp;
    private Integer userId;
    private Wuser wuser;
    private Integer contactsNotified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Wuser getWuser() {
        return wuser;
    }

    public void setWuser(Wuser wuser) {
        this.wuser = wuser;
    }

    public Integer getContactsNotified() {
        return contactsNotified;
    }

    public void setContactsNotified(Integer contactsNotified) {
        this.contactsNotified = contactsNotified;
    }
}
