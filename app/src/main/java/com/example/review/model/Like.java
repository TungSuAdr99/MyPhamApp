package com.example.review.model;

public class Like {
    private String key;
    private String spinner;
    private String position;
    private String uidUser;

    public Like() {
    }

    public Like(String spinner, String position, String uidUser) {
        this.spinner = spinner;
        this.position = position;
        this.uidUser = uidUser;
    }

    public Like(String key, String isLike, String spinner, String position, String uidUser) {
        this.key = key;
        this.spinner = spinner;
        this.position = position;
        this.uidUser = uidUser;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSpinner() {
        return spinner;
    }

    public void setSpinner(String spinner) {
        this.spinner = spinner;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUidUser() {
        return uidUser;
    }

    public void setUidUser(String uidUser) {
        this.uidUser = uidUser;
    }
}
