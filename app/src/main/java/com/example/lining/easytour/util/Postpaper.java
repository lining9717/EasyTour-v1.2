package com.example.lining.easytour.util;

/**
 * Created by Administrator on 2018/3/26.
 */

public class Postpaper {
    private int mImage;
    private String mContent;
    private String mUrl;
    public Postpaper(int Image,String Content,String Url){
        mImage = Image;
        mContent = Content;
        mUrl = Url;
    }
    public int getPicture() {
        return mImage;
    }
    public void setmPicture(int mPicture) {
        this.mImage = mPicture;
    }
    public String getmContent() {
        return mContent;
    }
    public void setmContent(String mTitle) {
        this.mContent = mTitle;
    }
    public String getmUrl() {
        return mUrl;
    }
    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}
