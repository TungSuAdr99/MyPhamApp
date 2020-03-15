package com.example.review.model;

public class SanPham {
    private int image;
    private  String mName;
    private String mGia;
    private String mKhuyenmai;

    public SanPham(int image) {}
    public SanPham(int image, String mName, String mGia, String mKhuyenmai) {
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
