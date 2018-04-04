package com.example.lining.easytour;

/**
 * Created by Administrator on 2018/3/26.
 */

public class Postpaper {
    private int mImage;
    private String mContent;

    public Postpaper(int Image, String Content) {
        mImage = Image;
        mContent = Content;
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
        this.mContent = mContent;
    }
}
