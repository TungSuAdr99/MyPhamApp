package com.example.review.model;

public class Product {
    String image;
    String key;
    String name;
    String price;
    String ingredient1;
    String ingredient2;
    String detail;

    public Product() {
    }

    public Product(String image, String key, String name, String price, String ingredient1, String ingredient2, String detail) {
        this.image = image;
        this.key = key;
        this.name = name;
        this.price = price;
        this.ingredient1 = ingredient1;
        this.ingredient2 = ingredient2;
        this.detail = detail;
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

    public String getIngredient1() {
        return ingredient1;
    }

    public void setIngredient1(String ingredient1) {
        this.ingredient1 = ingredient1;
    }

    public String getIngredient2() {
        return ingredient2;
    }

    public void setIngredient2(String ingredient2) {
        this.ingredient2 = ingredient2;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
