package com.example.review.ProductActivity.GirdView_ProductActivity;

public class ProductContact {
    private int prd_gv_img;
    private String prd_gv_name;

    public ProductContact(int prd_gv_img, String prd_gv_name) {
        this.prd_gv_img = prd_gv_img;
        this.prd_gv_name = prd_gv_name;
    }

    public int getPrd_gv_img() {
        return prd_gv_img;
    }

    public void setPrd_gv_img(int prd_gv_img) {
        this.prd_gv_img = prd_gv_img;
    }

    public String getPrd_gv_name() {
        return prd_gv_name;
    }

    public void setPrd_gv_name(String prd_gv_name) {
        this.prd_gv_name = prd_gv_name;
    }
}
