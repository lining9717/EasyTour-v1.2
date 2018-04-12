package com.example.lining.easytour.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

public class UploadImage extends AsyncTask<File,Integer,String> {
    String uri = "http://118.89.18.136/EasyTour/EasyTour-bk/uploadImage.php/";
    private Context context;
    private ProgressDialog progressDialog;

    public UploadImage(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Prompt");
        progressDialog.setMessage("Uploading Image...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(File... files) {
        // 保存需上传文件信息
        RequestParams requestParams=new RequestParams(uri);
        //requestParams.addBodyParameter("username",name);
        //requestParams.addBodyParameter("title",title);
        //requestParams.addBodyParameter("identify",type);
        requestParams.addBodyParameter("file",files[0]);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("result",result);
                progressDialog.dismiss();
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("result",ex.getMessage());
                progressDialog.dismiss();
            }
            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
            }
        });
        return null;
    }

    @Override
    protected void onPostExecute(String s) {

    }
}
