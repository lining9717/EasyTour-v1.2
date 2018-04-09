package com.example.lining.easytour.photo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.lining.easytour.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by wangpeiyu on 2018/3/30.
 */

public class BitmapUtil {

    public static Bitmap getBitmapFormPath(Context context, String temppath) {
        try {
            FileInputStream fis = new FileInputStream(temppath);
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.et_logo);
    }
}
