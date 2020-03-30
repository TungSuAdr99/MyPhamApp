package com.example.review.model;

public class Shop {
    private String key;
    private String image;
    private String information;

    public Shop() {
    }

    public Shop(String key, String image, String information) {
        this.key = key;
        this.image = image;
        this.information = information;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
