package com.example.review.model;

public class Product {
    String image;
    String key;
    String name;
    String price;
    String ingredient;
    String detail;
    String spinner;
    String keyShop;
    String promotional;

    public Product() {
    }

    public Product(String image, String key, String name, String price, String ingredient,
                   String detail, String spinner, String keyShop, String promotional) {
        this.image = image;
        this.key = key;
        this.name = name;
        this.price = price;
        this.ingredient = ingredient;
        this.detail = detail;
        this.spinner = spinner;
        this.keyShop = keyShop;
        this.promotional = promotional;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient1) {
        this.ingredient = ingredient1;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getSpinner() {
        return spinner;
    }

    public void setSpinner(String spinner) {
        this.spinner = spinner;
    }

    public String getKeyShop() {
        return keyShop;
    }

    public void setKeyShop(String keyShop) {
        this.keyShop = keyShop;
    }

    public String getPromotional() {
        return promotional;
    }

    public void setPromotional(String promotional) {
        this.promotional = promotional;
    }
}
