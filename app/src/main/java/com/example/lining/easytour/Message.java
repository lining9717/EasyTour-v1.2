package com.example.lining.easytour;

/**
 * Created by Administrator on 2018/4/1.
 * the Message should contain the User name ,the content and the time when is was send
 */

public class Message {
    private int mImage;
    private String mTitle;
    private String mContent;
    private String mtime;
    public Message(int picture, String title, String content, String mtime){
        this.mImage = picture;
        this.mTitle = title;
        this.mContent = content;
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
    public String getTime() {
        return mtime;
    }
    public void setTime(String mContent) {
        this.mtime = mContent;
    }
}
