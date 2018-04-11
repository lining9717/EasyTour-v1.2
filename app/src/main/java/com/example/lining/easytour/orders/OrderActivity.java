package com.example.lining.easytour.orders;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lining.easytour.R;
import com.example.lining.easytour.util.SerializableHashMap;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {
    private TextView tv_orderID;
    private TextView tv_touristname;
    private TextView tv_place;
    private TextView tv_start_date;
    private TextView tv_end_date;
    private TextView tv_place_instruction;
    private TextView tv_time_instruction;
    private TextView tv_numofPeople;
    private ImageView iv_Img;
    private Map<String,String> order;
    private String guidername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_accept_order);
        init();
        Bundle bundle = getIntent().getExtras();
        SerializableHashMap serializableHashMap = (SerializableHashMap) bundle.get("message");
        guidername = bundle.getString("guidername");
        order = serializableHashMap.getMap();
        tv_orderID.setText("0000000"+order.get("orderID"));
        tv_touristname.setText(order.get("username"));
        tv_place.setText(order.get("place"));
        tv_start_date.setText(order.get("begin_day"));
        tv_end_date.setText(order.get("end_day"));
        tv_place_instruction.setText(order.get("place_descript"));
        tv_time_instruction.setText(order.get("time_descript"));
        tv_numofPeople.setText(order.get("num_of_people")+" people");
        //显示Bar的返回按钮
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

    }



    public void init(){
        tv_orderID = findViewById(R.id.tv_order_detail_id);
        tv_touristname = findViewById(R.id.tv_order_detail_tourist_name);
        tv_place = findViewById(R.id.tv_order_detail_place);
        tv_start_date = findViewById(R.id.tv_order_detail_start_date);
        tv_end_date = findViewById(R.id.tv_order_detail_end_date);
        tv_place_instruction = findViewById(R.id.tv_order_detail_place_instruction);
        tv_time_instruction = findViewById(R.id.tv_order_detail_time_instruction);
        tv_numofPeople = findViewById(R.id.tv_order_detail_number_of_people);
        iv_Img = findViewById(R.id.iv_order_detail_img);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void btnAcceptOrder(View view) {
        String orderID = order.get("orderID");
        new AcceptOrder().execute(orderID,guidername);
    }


    private class AcceptOrder extends AsyncTask<String,Void,Integer>{
        @Override
        protected Integer doInBackground(String... strings) {
            String uri = "http://118.89.18.136/EasyTour-bk/guiderAcceptOrder.php/";
            StringBuilder result = new StringBuilder();
            HttpPost httpRequest = new HttpPost(uri);
            List params = new ArrayList();
            params.add(new BasicNameValuePair("orderid", strings[0]));
            params.add(new BasicNameValuePair("guidername", strings[1]));
            try {
                httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
                Log.i("AcceptOrder","getStatusCode():------>"+httpResponse.getStatusLine().getStatusCode());
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
                return  code;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return  0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if(integer == 0){
                Toast.makeText(OrderActivity.this,"Accept Fail",Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(OrderActivity.this,"Accept success",Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
