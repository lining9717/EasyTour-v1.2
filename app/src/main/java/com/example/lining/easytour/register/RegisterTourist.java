package com.example.lining.easytour.register;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lining.easytour.R;
import com.example.lining.easytour.photo.ClipBaseActivity;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.regex.Pattern;

/**
 * Created by lining on 2018/3/24.
 */

public class RegisterTourist extends ClipBaseActivity {
    private EditText et_r_t_user_name;
    private EditText et_r_t_psw;
    private EditText et_r_t_conform_psw;
    private EditText et_r_t_phone;
    private Button btn_r_t_ok;
    private ImageView ivHeadImg;
    private String username;
    private String psw;
    private String confirm_psw;
    private String tel;
    private String mPath;

    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_tourist);
        x.view().inject(this);
        init();

        btn_r_t_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(RegisterTourist.this,"Coding",Toast.LENGTH_SHORT).show();
                username = et_r_t_user_name.getText().toString().trim();
                psw = et_r_t_psw.getText().toString().trim();
                confirm_psw = et_r_t_conform_psw.getText().toString().trim();
                tel = et_r_t_phone.getText().toString().trim();

                if (username.equals("")) {
                    Toast.makeText(RegisterTourist.this, "Please input user name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (psw.equals("")) {
                    Toast.makeText(RegisterTourist.this, "Please input password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (confirm_psw.equals("")) {
                    Toast.makeText(RegisterTourist.this, "Please input confirm password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (tel.equals("")) {
                    Toast.makeText(RegisterTourist.this, "Please input telephone", Toast.LENGTH_SHORT).show();
                    return;
                }
                //测试时先注释
                if (!isMobile(tel)) {
                    Toast.makeText(RegisterTourist.this, "Telephone format is wrong", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mPath == null){
                    Toast.makeText(RegisterTourist.this, "Please choose your photo", Toast.LENGTH_SHORT).show();
                    return;
                }

                touristregister();
            }
        });

    }

    @Override
    public void errorLoadImg() {
        Toast.makeText(RegisterTourist.this,"Load images error",Toast.LENGTH_SHORT).show();
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
    /*
    changed the layout delete the cancel button and rename the id of views
     */
    public void init(){
        et_r_t_user_name = findViewById(R.id.et_tourist_user_name);
        et_r_t_psw = findViewById(R.id.et_tourist__psw);
        et_r_t_conform_psw = findViewById(R.id.et_tourist_confirm_psw);
        et_r_t_phone = findViewById(R.id.et_tourist_tel);
        btn_r_t_ok = findViewById(R.id.btn_tourist_ok);
        ivHeadImg = findViewById(R.id.register_tiv_usericon);
        ivHeadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(ivHeadImg);
            }
        });
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }


    public void touristregister(){
        String uri = "http://118.89.18.136/EasyTour/EasyTour-Img/touristregister.php/";
        File file = new File(mPath);
        if (!file.exists()) {
            Toast.makeText(RegisterTourist.this,"File doesn't exist",Toast.LENGTH_SHORT).show();
            return;
        }
        // 保存需上传文件信息
        RequestParams requestParams=new RequestParams(uri);
        requestParams.addBodyParameter("username",username);
        requestParams.addBodyParameter("password",psw);
        requestParams.addBodyParameter("telephone",tel);
        requestParams.addBodyParameter("file",file);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result.equals("register success")){
                    Toast.makeText(RegisterTourist.this,"Sign up successful",Toast.LENGTH_SHORT).show();
                    finish();
                }else if(result.equals("username exists")){
                    Toast.makeText(RegisterTourist.this,"Username exists",Toast.LENGTH_SHORT).show();
                    return;
                }else
                    Toast.makeText(RegisterTourist.this,"Sign up Fail, Server Error",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(RegisterTourist.this,"Sign up Fail",Toast.LENGTH_SHORT).show();
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
