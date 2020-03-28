package com.example.review.CmtActivity.RecyclerView_Atvt_Cmt;

public class ContactRecycler {
    private int image;
    private String name;
    private String cmt;

    public ContactRecycler(int image, String name, String cmt) {
        this.image = image;
        this.name = name;
        this.cmt = cmt;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCmt() {
        return cmt;
    }

    public void setCmt(String cmt) {
        this.cmt = cmt;
    }
}
