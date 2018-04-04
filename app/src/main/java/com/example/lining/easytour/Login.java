package com.example.lining.easytour;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lining on 2018/3/24.
 */

public class Login extends Activity {
    private EditText et_account;
    private EditText et_psw;
    private RadioButton rbt_tourist;
    private RadioButton rbt_guider;
    private RadioGroup radio;
    private Button btn_sign_in;
    private Button btn_sign_up;
    private Button btn_exit;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbt_guider.isChecked()) {
                    tourGuideLogin();
                } else {
                    touristLogin();
                }
            }
        });

        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Login.this, Register.class));
            }
        });

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(1);
            }
        });

    }

    private void init() {
        et_account = findViewById(R.id.et_account);
        et_psw = findViewById(R.id.et_psw);
        rbt_guider = findViewById(R.id.rbt_guider);
        rbt_tourist = findViewById(R.id.rbt_tourist);
        radio = findViewById(R.id.radio);
        btn_sign_in = findViewById(R.id.btn_sign_in);
        btn_sign_up = findViewById(R.id.btn_sign_up);
        btn_exit = findViewById(R.id.btn_exit);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("提示");
        progressDialog.setMessage("正在连接，请稍后....");
    }


    public void touristLogin() {
        progressDialog.show();
        String url = "http://118.89.18.136/EasyTour-bk/touristlogin.php/";
        new TouristLogin().execute(url);
    }

    public void tourGuideLogin() {
        Intent intent = new Intent(Login.this, GuiderActivity.class);
        startActivity(intent);
        finish();
    }

    public void btnForgetKeyword(View view) {
        Toast.makeText(this, "Refind the word", Toast.LENGTH_SHORT).show();
    }


    private class TouristLogin extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            String account = et_account.getText().toString().trim();
            String psw = et_psw.getText().toString().trim();
            String result = null;
            HttpPost httpRequest = new HttpPost(strings[0]);
            List params = new ArrayList();
            params.add(new BasicNameValuePair("username", account));
            params.add(new BasicNameValuePair("password", psw));
            try {
                httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
                // 服务器发给客户端的相应，有一个相应码： 相应码为200，正常； 相应码为404，客户端错误； 相应码为505，服务器端错误。
                Log.i("getStatusCode():", "------>" + httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    result = EntityUtils.toString(httpResponse.getEntity());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (result.equals("Login succeed"))
                return true;
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (!aBoolean) {
                progressDialog.dismiss();
                Toast.makeText(Login.this, "密码或用户名错误", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(Login.this, TouristActivity.class);
                startActivity(intent);
                finish();
                progressDialog.dismiss();
            }
        }
    }

}
