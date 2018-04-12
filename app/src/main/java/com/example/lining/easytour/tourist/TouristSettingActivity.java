package com.example.lining.easytour.tourist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.lining.easytour.R;
import com.example.lining.easytour.photo.ClipBaseActivity;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;
import java.io.File;

public class TouristSettingActivity extends ClipBaseActivity {
    private EditText et_username;
    private EditText et_phone;
    private EditText et_intro;
    private ImageView ivHeadImg;
    private String newPath;
    private String username;
    private String tel;
    private String path;
    private String intro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_setting);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        tel = intent.getStringExtra("tel");
        intro = intent.getStringExtra("intro");
        path = intent.getStringExtra("mPath");
        x.view().inject(this);
        init();

        //显示Bar的返回按钮
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(0);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
   when pushed the back button , go back
    */
    @Override
    public void onBackPressed() {
        setResult(0);
        finish();
    }

    @Override
    public void errorLoadImg() {

    }

    @Override
    public void setImg(Bitmap img, String path) {
        ivHeadImg.setImageBitmap(img);
        newPath = path;
    }

    private void init() {
        et_username = findViewById(R.id.et_setting_username);
        et_phone = findViewById(R.id.et_setting_tel);
        et_intro = findViewById(R.id.et_setting_intro);
        ivHeadImg = findViewById(R.id.t_setting_iv_icon);

        Picasso.with(TouristSettingActivity.this).load(path).into(ivHeadImg);   //获取头像
        et_username.setText(username);
        et_phone.setText(tel);
        et_intro.setText(intro);

        newPath = "without photo";
        ivHeadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(ivHeadImg);
            }
        });
        //显示Bar的返回按钮
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void btnUpdate(View view) {
        String newname = et_username.getText().toString().trim();
        String newtel = et_phone.getText().toString().trim();
        String newintro = et_intro.getText().toString().trim();
        if (newname.equals("")) {
            Toast.makeText(TouristSettingActivity.this, "Please input new user name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (newtel.equals("")) {
            Toast.makeText(TouristSettingActivity.this, "Please input new telephone", Toast.LENGTH_SHORT).show();
            return;
        }
        if(newPath.equals("without photo")){
            updateInformationWhitoutPhoto(username,newname,newtel,newintro);
        }else{
            updateInformationWithPhoto(username,newname,newtel,newintro,newPath);
        }
    }

    public void updateInformationWithPhoto(String oldname,String name,String tel,String intro,String path){
        String uri = "http://118.89.18.136/EasyTour/EasyTour-Img/updateTouristInfoWithPhoto.php/";
        File file = new File(path);
        if (!file.exists()) {
            Toast.makeText(TouristSettingActivity.this,"File doesn't exist",Toast.LENGTH_SHORT).show();
            return;
        }
        // 保存需上传文件信息
        RequestParams requestParams=new RequestParams(uri);
        requestParams.addBodyParameter("oldusername",oldname);
        requestParams.addBodyParameter("username",name);
        requestParams.addBodyParameter("telephone",tel);
        requestParams.addBodyParameter("introduce",intro);
        requestParams.addBodyParameter("file",file);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("result","------->"+result);
                String message=null;
                String newPathInServer = null;
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    message = jsonObject.getString("message");
                    newPathInServer = jsonObject.getString("data");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (message.equals("update success")){
                    Toast.makeText(TouristSettingActivity.this,"Update successful",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("newname",et_username.getText().toString().trim());
                    intent.putExtra("newtel",et_phone.getText().toString().trim());
                    intent.putExtra("newintro",et_intro.getText().toString().trim());
                    intent.putExtra("newServerPath",newPathInServer);
                    setResult(1,intent);
                    finish();
                }else if(message.equals("username exists")){
                    Toast.makeText(TouristSettingActivity.this,"Username exists",Toast.LENGTH_SHORT).show();
                    return;
                }else
                    Toast.makeText(TouristSettingActivity.this,"Update Fail, Server Error",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(TouristSettingActivity.this,"update Fail",Toast.LENGTH_SHORT).show();
                Log.i("result",ex.getMessage());
                ex.printStackTrace();
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });

    }

    public void updateInformationWhitoutPhoto(String oldname, final String name, String tel, String intro){
        String uri = "http://118.89.18.136/EasyTour/EasyTour-Img/updateTouristInfoWithoutPhoto.php/";
        // 保存需上传文件信息
        RequestParams requestParams=new RequestParams(uri);
        requestParams.addBodyParameter("oldusername",oldname);
        requestParams.addBodyParameter("username",name);
        requestParams.addBodyParameter("telephone",tel);
        requestParams.addBodyParameter("introduce",intro);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("result","------->"+result);
                if (result.equals("update success")){
                    Toast.makeText(TouristSettingActivity.this,"Update successful",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("newname",et_username.getText().toString().trim());
                    intent.putExtra("newtel",et_phone.getText().toString().trim());
                    intent.putExtra("newintro",et_intro.getText().toString().trim());
                    setResult(2,intent);
                    finish();
                }else if(result.equals("username exists")){
                    Toast.makeText(TouristSettingActivity.this,"Username exists",Toast.LENGTH_SHORT).show();
                    return;
                }else
                    Toast.makeText(TouristSettingActivity.this,"Update Fail, Server Error",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(TouristSettingActivity.this,"update Fail",Toast.LENGTH_SHORT).show();
                Log.i("result",ex.getMessage());
                ex.printStackTrace();
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }
}
