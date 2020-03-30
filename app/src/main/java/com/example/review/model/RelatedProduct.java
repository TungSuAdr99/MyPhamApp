package com.example.review.model;

public class RelatedProduct {
    private String image;
    private String name;
    private String like;

    public RelatedProduct() {
    }

    public RelatedProduct(String image, String name, String like) {
        this.image = image;
        this.name = name;
        this.like = like;
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

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }
}
