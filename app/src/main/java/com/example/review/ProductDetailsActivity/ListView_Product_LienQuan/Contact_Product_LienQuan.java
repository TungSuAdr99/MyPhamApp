package com.example.review.ProductDetailsActivity.ListView_Product_LienQuan;

public class Contact_Product_LienQuan {
    private int image;
    private String mName;
    private String mNumber;

    public Contact_Product_LienQuan(int mImg, String mName, String mNumber) {
        this.image = mImg;
        this.mName = mName;
        this.mNumber = mNumber;
    }

    public int getmImg() {
        return image;
    }

    public void setmImg(int mImg) {
        this.image = mImg;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmNumber() {
        return mNumber;
    }

    public void setmNumber(String mNumber) {
        this.mNumber = mNumber;
    }
}
