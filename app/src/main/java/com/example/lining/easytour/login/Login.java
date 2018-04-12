package com.example.lining.easytour.login;

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
import android.widget.Toast;

import com.example.lining.easytour.guide.GuideActivity;
import com.example.lining.easytour.R;
import com.example.lining.easytour.tourist.TouristActivity;
import com.example.lining.easytour.register.Register;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lining on 2018/3/24.
 */

public class Login extends Activity {
    private EditText et_account;
    private EditText et_psw;
    private RadioButton rbt_guider;
    private Button btn_sign_in;
    private Button btn_sign_up;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbt_guider.isChecked()) {
                    guiderlogin();
                } else {
                    touristlogin();
                }
            }
        });

        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        et_account = findViewById(R.id.et_account);
        et_psw = findViewById(R.id.et_psw);
        rbt_guider = findViewById(R.id.rbt_guider);
        btn_sign_in = findViewById(R.id.btn_sign_in);
        btn_sign_up = findViewById(R.id.btn_sign_up);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Prompt");
        progressDialog.setMessage("Connecting...");
    }

    public void touristlogin(){
        progressDialog.show();
//        new TouristLogin().execute();
        Intent intent = new Intent(Login.this, TouristActivity.class);
        intent.putExtra("username","test");
        intent.putExtra("introduce","test");
        intent.putExtra("tel","test");
        startActivity(intent);
        finish();
    }

    public void guiderlogin(){
        progressDialog.show();
        new GuiderLogin().execute();
    }

    private class TouristLogin extends AsyncTask<String, Void, String[]> {
        @Override
        protected String[] doInBackground(String... strings) {
            String uri = "http://118.89.18.136/EasyTour/EasyTour-bk/touristlogin.php/";
            String account = et_account.getText().toString().trim();
            String psw = et_psw.getText().toString().trim();
            StringBuilder result = new StringBuilder();
            HttpPost httpRequest = new HttpPost(uri);
            List params = new ArrayList();
            params.add(new BasicNameValuePair("username", account));
            params.add(new BasicNameValuePair("password", psw));
            try {
                httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
                // 服务器发给客户端的相应，有一个相应码： 相应码为200，正常； 相应码为404，客户端错误； 相应码为505，服务器端错误。
                Log.i("getStatusCode():","------>"+httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                    for(String s=bufferedReader.readLine();s!=null;s=bufferedReader.readLine()){
                        result.append(s);
                    }
                }
                Log.i("result","------->"+result.toString());
                //从json中取出数据
                JSONObject mObject = new JSONObject(result.toString());
                int code = mObject.getInt("code");
                String message = mObject.getString("message");
                JSONObject mData = mObject.getJSONObject("data");
                if(code == 0){
                    Log.i("fail message","------>"+message);
                    return new String[]{"false"};
                }
                String[] returndata = new String[5];
                returndata[0] = "success";
                returndata[1] = mData.getString("username");
                returndata[2] = mData.getString("introduce");
                returndata[3] = mData.getString("tel");
                returndata[4] = mData.getString("photo");

                if(code == 1 && message.equals("Login success"))
                    return returndata;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new String[]{"false"};
        }

        /**
         * * 这个strings就是返回的用户数据,与上面相对应
         * strings[0]->返回的状态信息
         * strings[1]->username
         * strings[2]->introduce:个人介绍
         * strings[3]->tel
         * strings[4]->用户头像，现在还没有头像，先不用管
         * @param strings
         */
        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
            if(strings[0].equals("false")) {
                progressDialog.dismiss();
                Toast.makeText(Login.this, "Password or username wrong", Toast.LENGTH_SHORT).show();
            }else{
                /*
                * 从这里可以用Intent传递到下一个activity
                */
                Intent intent = new Intent(Login.this, TouristActivity.class);
                intent.putExtra("username",strings[1]);
                intent.putExtra("introduce",strings[2]);
                intent.putExtra("tel",strings[3]);
                intent.putExtra("photo",strings[4]);
                startActivity(intent);
                finish();
                progressDialog.dismiss();
            }
        }
    }

    private class GuiderLogin extends AsyncTask<String, Void, String[]> {
        @Override
        protected String[] doInBackground(String... strings) {
            String uri = "http://118.89.18.136/EasyTour/EasyTour-bk/guiderlogin.php/";
            String account = et_account.getText().toString().trim();
            String psw = et_psw.getText().toString().trim();
            StringBuilder result = new StringBuilder();
            HttpPost httpRequest = new HttpPost(uri);
            List params = new ArrayList();
            params.add(new BasicNameValuePair("username", account));
            params.add(new BasicNameValuePair("password", psw));
            try {
                httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
                // 服务器发给客户端的相应，有一个相应码： 相应码为200，正常； 相应码为404，客户端错误； 相应码为505，服务器端错误。
                Log.i("getStatusCode():","------>"+httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                    for(String s=bufferedReader.readLine();s!=null;s=bufferedReader.readLine()){
                        result.append(s);
                    }
                }
                Log.i("result","------->"+result.toString());
                //从json中取出数据
                JSONObject mObject = new JSONObject(result.toString());
                int code = mObject.getInt("code");
                String message = mObject.getString("message");
                JSONObject mData = mObject.getJSONObject("data");
                if(code == 0){
                    Log.i("fail message","------>"+message);
                    return new String[]{"false"};
                }
                String[] returndata = new String[9];
                returndata[0] = "success";
                returndata[1] = mData.getString("guidername");
                returndata[2] = mData.getString("introduce");
                returndata[3] = mData.getString("realname");
                returndata[4] = mData.getString("tel");
                returndata[5] = mData.getString("photo");
                returndata[6] = mData.getString("place");
                returndata[7] = mData.getString("star");
                returndata[8] = mData.getString("IDnumber");
                if(code == 1 && message.equals("Login success"))
                    return returndata;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new String[]{"false"};
        }


        /**
         * @param strings
         * 与tourist类似，一共有8个信息，对用doInBackground中的returndata
         */
        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
            if(strings[0].equals("false")){
                progressDialog.dismiss();
                Toast.makeText(Login.this, "Password or username wrong", Toast.LENGTH_SHORT).show();
            }else{
                //从这里传递
                Intent intent = new Intent(Login.this, GuideActivity.class);
                intent.putExtra("guidername",strings[1]);
                intent.putExtra("introduce",strings[2]);
                intent.putExtra("tel",strings[4]);
                intent.putExtra("photo",strings[5]);
                intent.putExtra("place",strings[6]);
                startActivity(intent);
                finish();
                progressDialog.dismiss();
            }
        }
    }

}
