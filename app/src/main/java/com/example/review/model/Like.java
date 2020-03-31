package com.example.review.model;

public class Like {
    private String key;
    private String spinner;
    private String keyProduct;
    private String uidUser;

    public Like() {
    }

    public Like(String spinner, String keyProduct, String uidUser) {
        this.spinner = spinner;
        this.keyProduct = keyProduct;
        this.uidUser = uidUser;
    }

    public Like(String key, String spinner, String keyProduct, String uidUser) {
        this.key = key;
        this.spinner = spinner;
        this.keyProduct = keyProduct;
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

    public String getKeyProduct() {
        return keyProduct;
    }

    public void setKeyProduct(String keyProduct) {
        this.keyProduct = keyProduct;
    }

    public String getUidUser() {
        return uidUser;
    }

    public void setUidUser(String uidUser) {
        this.uidUser = uidUser;
    }
}
