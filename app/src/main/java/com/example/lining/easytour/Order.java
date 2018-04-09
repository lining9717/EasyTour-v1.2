package com.example.lining.easytour;

/**
 * Created by Administrator on 2018/3/24.
 */

public class Order {
    private int mImage;
    private String mTitle;
    private String mContent;
    private String mdate;
    private String mtime;
    public Order(int picture, String title, String content, String mdate, String mtime){
        this.mImage = picture;
        this.mTitle = title;
        this.mContent = content;
        this.mdate = mdate;
        this.mtime = mtime;
    }

    public int getPicture() {
        return mImage;
    }
    public void setmPicture(int mPicture) {
        this.mImage = mPicture;
    }
    public String getTitle() {
        return mTitle;
    }
    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }
    public String getContent() {
        return mContent;
    }
    public void setContent(String mContent) {
        this.mContent = mContent;
    }
    public String getDate() {
        return mdate;
    }
    public void setDate(String mContent) {
        this.mdate = mContent;
    }
    public String getTime() {
        return mtime;
    }
    public void setTime(String mContent) {
        this.mtime = mContent;
    }
}
