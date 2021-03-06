package com.example.review.model;

public class Review {
    private String key;
    private String uidUser;
    private String reviewUser;
    private String spinner;
    private String keyProduct;

    public Review() {
    }

    public Review(String uidUser, String reviewUser, String spinner, String keyProduct) {
        this.uidUser = uidUser;
        this.reviewUser = reviewUser;
        this.spinner = spinner;
        this.keyProduct = keyProduct;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUidUser() {
        return uidUser;
    }

    public void setUidUser(String uidUser) {
        this.uidUser = uidUser;
    }

    public String getReviewUser() {
        return reviewUser;
    }

    public void setReviewUser(String reviewUser) {
        this.reviewUser = reviewUser;
    }

    public String getSpinner() {
        return spinner;
    }

    public void setSpinner(String spinner) {
        this.spinner = spinner;
    }

    public String getKeyProduct() {
        return keyProduct;
    }

    public void setKeyProduct(String keyProduct) {
        this.keyProduct = keyProduct;
    }
}
