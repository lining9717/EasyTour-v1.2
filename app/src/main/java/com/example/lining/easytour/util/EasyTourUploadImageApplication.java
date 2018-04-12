package com.example.lining.easytour.util;

import android.app.Application;

import org.xutils.x;

public class EasyTourUploadImageApplication extends Application{


    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
