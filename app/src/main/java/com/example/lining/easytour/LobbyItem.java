package com.example.lining.easytour;

/**
 * Created by lining on 2018/3/24.
 */

public class LobbyItem {
    String title;
    String date;
    String content;
    int day;

    public LobbyItem(String title, String date, String content, int day) {
        this.title = title;
        this.date = date;
        this.content = content;
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }
}
