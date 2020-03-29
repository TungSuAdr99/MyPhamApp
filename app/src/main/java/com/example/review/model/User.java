package com.example.review.model;

public class User {
    private String image;
    private String name;
    private String uid;
    private String key;

    public User() {
    }

    public User(String image, String name, String uid, String key) {
        this.image = image;
        this.name = name;
        this.uid = uid;
        this.key = key;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
