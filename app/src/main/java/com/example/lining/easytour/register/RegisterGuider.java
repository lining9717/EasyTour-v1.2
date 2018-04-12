package com.example.lining.easytour.register;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lining.easytour.R;
import com.example.lining.easytour.photo.ClipBaseActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lining on 2018/3/24.
 */

public class RegisterGuider extends ClipBaseActivity {
    private EditText et_r_g_user_name;
    private EditText et_r_g_psw;
    private EditText et_r_g_confirm_psw;
    private EditText et_r_g_real_name;
    private EditText et_r_g_ID;
    private EditText et_r_g_tel;
    private ImageView ivHeadImg;
    private Button btn_r_g_ok;
    private String username;
    private String psw;
    private String confirm_psw;
    private String realname;
    private String tel;
    private String IDnumber;
    private String mPath;
    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";
    public static final String REGEX_ID = "^(\\d{6})(19|20)(\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)?$";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_guider);
        init();
        btn_r_g_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = et_r_g_user_name.getText().toString().trim();
                psw = et_r_g_psw.getText().toString().trim();
                realname = et_r_g_real_name.getText().toString().trim();
                tel = et_r_g_tel.getText().toString().trim();
                IDnumber = et_r_g_ID.getText().toString().trim();
                confirm_psw = et_r_g_confirm_psw.getText().toString().trim();
                if(username.equals("")){
                    Toast.makeText(RegisterGuider.this,"Please input user name",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(psw.equals("")){
                    Toast.makeText(RegisterGuider.this,"Please input password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(confirm_psw.equals("")){
                    Toast.makeText(RegisterGuider.this,"Please input confirm password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(realname.equals("")){
                    Toast.makeText(RegisterGuider.this,"Please input your real name",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(tel.equals("")){
                    Toast.makeText(RegisterGuider.this,"Please input telephone",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(IDnumber.equals("")){
                    Toast.makeText(RegisterGuider.this,"Please input ID number",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!psw.equals(confirm_psw)){
                    Toast.makeText(RegisterGuider.this,"Password isn't same with confirm password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!isMobile(tel)){
                    Toast.makeText(RegisterGuider.this,"Telephone's format is wrong",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!is18ByteIdCard(IDnumber)){
                    Toast.makeText(RegisterGuider.this,"ID number's format is wrong",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(mPath == null){
                    Toast.makeText(RegisterGuider.this, "Please choose your photo", Toast.LENGTH_SHORT).show();
                    return;
                }
                guideregister();
            }
        });
    }

    @Override
    public void errorLoadImg() {

    }

    @Override
    public void setImg(Bitmap img, String path) {
        ivHeadImg.setImageBitmap(img);
        mPath = path;
    }

    /*
    when pushed the back button , go back
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void init(){
        et_r_g_user_name = findViewById(R.id.et_r_g_user_name);
        et_r_g_psw = findViewById(R.id.et_r_g_psw);
        et_r_g_confirm_psw = findViewById(R.id.et_r_g_confirm_psw);
        et_r_g_real_name = findViewById(R.id.et_r_g_real_name);
        et_r_g_ID = findViewById(R.id.et_r_g_ID);
        et_r_g_tel = findViewById(R.id.et_r_g_tel);
        btn_r_g_ok = findViewById(R.id.btn_r_g_ok);
        ivHeadImg = findViewById(R.id.register_giv_usericon);
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * 校验手机号
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 18位身份证校验,粗略的校验
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean is18ByteIdCard(String idCard){
        Pattern pattern1 = Pattern.compile(REGEX_ID); //粗略的校验
        Matcher matcher = pattern1.matcher(idCard);
        if(matcher.matches()){
            return true;
        }
        return false;
    }

    public void guideregister(){
        String uri = "http://118.89.18.136/EasyTour/EasyTour-Img/guiderregister.php/";
        File file = new File(mPath);
        if (!file.exists()) {
            Toast.makeText(RegisterGuider.this,"File doesn't exist",Toast.LENGTH_SHORT).show();
            return;
        }
        // 保存需上传文件信息
        RequestParams requestParams=new RequestParams(uri);
        requestParams.addBodyParameter("username",username);
        requestParams.addBodyParameter("password",psw);
        requestParams.addBodyParameter("telephone",tel);
        requestParams.addBodyParameter("realname",realname);
        requestParams.addBodyParameter("IDnumber",IDnumber);
        requestParams.addBodyParameter("file",file);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Log.i("guideregister","------->"+result);
                if (result.equals("register successful")){
                    Toast.makeText(RegisterGuider.this,"Sign up successful",Toast.LENGTH_SHORT).show();
                    finish();
                }else if(result.equals("username exists")){
                    Toast.makeText(RegisterGuider.this,"Username exists",Toast.LENGTH_SHORT).show();
                    return;
                }else
                    Toast.makeText(RegisterGuider.this,"Sign up Fail, Server Error",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(RegisterGuider.this,"Sign up Fail",Toast.LENGTH_SHORT).show();
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
