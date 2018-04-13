package com.example.lining.easytour.guide;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
import java.util.List;
import java.util.Map;

public class GuideUnfinishedOrdersActivity extends AppCompatActivity {
    private TextView tv_orderID;
    private TextView tv_touristname;
    private TextView tv_place;
    private TextView tv_start_date;
    private TextView tv_end_date;
    private TextView tv_place_instruction;
    private TextView tv_time_instruction;
    private TextView tv_numofPeople;
    private ImageView iv_Img;
    private Map<String, String> order;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_unfinished);
        init();
        Bundle bundle = getIntent().getExtras();
        SerializableHashMap serializableHashMap = (SerializableHashMap) bundle.get("message");
        order = serializableHashMap.getMap();
        tv_orderID.setText("0000000" + order.get("orderID"));
        tv_touristname.setText(order.get("username"));
        tv_place.setText(order.get("place"));
        tv_start_date.setText(order.get("begin_day"));
        tv_end_date.setText(order.get("end_day"));
        tv_place_instruction.setText(order.get("place_descript"));
        tv_time_instruction.setText(order.get("time_descript"));
        tv_numofPeople.setText(order.get("num_of_people") + " people");
        //显示Bar的返回按钮
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void init() {
        tv_orderID = findViewById(R.id.tv_guide_unfinished_detail_id);
        tv_touristname = findViewById(R.id.tv_guide_unfinished_detail_tourist_name);
        tv_place = findViewById(R.id.tv_guide_unfinished_detail_place);
        tv_start_date = findViewById(R.id.tv_guide_unfinished_detail_start_date);
        tv_end_date = findViewById(R.id.tv_guide_unfinished_detail_end_date);
        tv_place_instruction = findViewById(R.id.tv_guide_unfinished_detail_place_instruction);
        tv_time_instruction = findViewById(R.id.tv_guide_unfinished_detail_time_instruction);
        tv_numofPeople = findViewById(R.id.tv_guide_unfinished_detail_number_of_people);
        iv_Img = findViewById(R.id.iv_guide_unfinished_detail_img);
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

    public void btnFininshedOrder(View view) {
        String orderID = order.get("orderID");
        new FinishedOrder().execute(orderID);
    }

    private class FinishedOrder extends AsyncTask<String, Void, Integer>{
        @Override
        protected Integer doInBackground(String... strings) {
            String uri = "http://118.89.18.136/EasyTour/EasyTour-bk/guidefinisheorder.php/";
            StringBuilder result = new StringBuilder();
            HttpPost httpRequest = new HttpPost(uri);
            List params = new ArrayList();
            params.add(new BasicNameValuePair("orderid", strings[0]));
            try {
                httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
                Log.i("FinishedOrder", "getStatusCode():------>" + httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                    for (String s = bufferedReader.readLine(); s != null; s = bufferedReader.readLine()) {
                        result.append(s);
                    }
                }
                Log.i("FinishedOrder result", "------->" + result.toString());
                //从json中取出数据
                JSONObject mObject = new JSONObject(result.toString());
                int code = mObject.getInt("code");
                return code;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }
        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if (integer == 0) {
                Toast.makeText(GuideUnfinishedOrdersActivity.this, "Server Fail", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(GuideUnfinishedOrdersActivity.this, "Finish success", Toast.LENGTH_SHORT).show();
            setResult(1);
            finish();
        }

    }

}
