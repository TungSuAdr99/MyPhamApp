package com.example.review.model;

public class Comment {
    private String key;
    private String imageComment;
    private String comment;
    private String imageUser;
    private String nameUser;
    private String uidUser;
    private String position;
    private String spinner;

    public Comment() {
    }

    public Comment(String key, String imageComment, String comment, String imageUser, String nameUser, String uidUser, String position, String spinner) {
        this.key = key;
        this.imageComment = imageComment;
        this.comment = comment;
        this.imageUser = imageUser;
        this.nameUser = nameUser;
        this.uidUser = uidUser;
        this.position = position;
        this.spinner = spinner;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImageComment() {
        return imageComment;
    }

    public void setImageComment(String imageComment) {
        this.imageComment = imageComment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImageUser() {
        return imageUser;
    }

    public void setImageUser(String imageUser) {
        this.imageUser = imageUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getUidUser() {
        return uidUser;
    }

    public void setUidUser(String uidUser) {
        this.uidUser = uidUser;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSpinner() {
        return spinner;
    }

    public void setSpinner(String spinner) {
        this.spinner = spinner;
    }
}
