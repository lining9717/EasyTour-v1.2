package com.example.lining.easytour.clip;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lining.easytour.R;
import com.example.lining.easytour.clip.clip_image.ClipImageLayout;
import com.example.lining.easytour.clip.clip_image.ImageTools;


import java.io.File;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class AcClipImg extends AppCompatActivity {

    private static final int STATUS_BAR_COLOR = Color.parseColor("#00dec9");

    private ClipImageLayout mClipImageLayout;

    private ProgressDialog mProgressDialog;

    private Button btnSave;

    private static final String[] PERMISSION = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_PHONE_STATE,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_clip_img);

        bindView();
        initView();
        bindEvent();
        loadImg();
    }

    private void saveImg() {
        mProgressDialog.show();
        Observable.just("")
                .observeOn(Schedulers.io())
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        Bitmap clipBitmap = mClipImageLayout.clip();
                        File file = new File(getFilesDir() + "/crop_photo");
                        if (!file.exists()) {
                            file.mkdir();
                        }
                        Bitmap compressBitmap = ImageTools.compressBitmap(clipBitmap);
                        String savePath = getFilesDir() + "/crop_photo" + "/" + System.currentTimeMillis() + ".png";
                        ImageTools.saveBitmapToSDCard(compressBitmap, savePath);
                        return savePath;
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String savePath) throws Exception {
                        mProgressDialog.dismiss();
                        Intent intent = new Intent();
                        intent.putExtra("path", savePath);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
    }

    private void loadImg() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("提示");
        mProgressDialog.setMessage("正在读取图片，请稍后...");
        String path;
        path = getIntent().getStringExtra("path");
        if (path != null)
            if (TextUtils.isEmpty(path) || !(new File(path).exists())) {
                Toast.makeText(this,"can not load img",Toast.LENGTH_LONG).show();
                return;
            }
        Bitmap bitmap = ImageTools.convertToBitmap(path, 800, 1000);
        if (bitmap == null) {
            Toast.makeText(this,"can not load img",Toast.LENGTH_LONG).show();
            return;
        }
        mClipImageLayout.setBitmap(bitmap);
    }

    private void bindView() {
        mClipImageLayout = (ClipImageLayout) findViewById(R.id.id_ac_clipimage_clipimagelayout);
        btnSave = findViewById(R.id.save);
    }

    private void initView() {
        mClipImageLayout.setBackgroundColor(Color.WHITE);
    }

    private void bindEvent() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    saveImg();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
