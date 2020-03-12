package com.example.review.model;

public class Contact {
    private int image;
    private String mName;
    private String mGia;
    private String mSodiadiem;
    private String mKhuyenmai;

    public Contact(int image, String mName, String mGia, String mSodiadiem, String mKhuyenmai) {
        this.image = image;
        this.mName = mName;
        this.mGia = mGia;
        this.mSodiadiem = mSodiadiem;
        this.mKhuyenmai = mKhuyenmai;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmGia() {
        return mGia;
    }

    public void setmGia(String mGia) {
        this.mGia = mGia;
    }

    public String getmSodiadiem() {
        return mSodiadiem;
    }

    public void setmSodiadiem(String mSodiadiem) {
        this.mSodiadiem = mSodiadiem;
    }

    public String getmKhuyenmai() {
        return mKhuyenmai;
    }

    public void setmKhuyenmai(String mKhuyenmai) {
        this.mKhuyenmai = mKhuyenmai;
    }
}
