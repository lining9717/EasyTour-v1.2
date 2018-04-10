package com.example.lining.easytour.orders;

import android.widget.ImageView;
import android.widget.TextView;

public class OrderHolder
{
    ImageView imageView;
    TextView title;
    TextView content;
    TextView date;
    TextView time;

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setContent(TextView content) {
        this.content = content;
    }

    public void setDate(TextView date) {
        this.date = date;
    }

    public void setTime(TextView time) {
        this.time = time;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public TextView getContent() {
        return content;
    }

    public TextView getDate() {
        return date;
    }

    public TextView getTime() {
        return time;
    }

    public TextView getTitle() {
        return title;
    }
}
