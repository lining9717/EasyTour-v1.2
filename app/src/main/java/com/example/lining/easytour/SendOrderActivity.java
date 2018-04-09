package com.example.lining.easytour;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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

public class SendOrderActivity extends AppCompatActivity {
    private static final String[] Province={"Chongqing","Sichuan","USA"};
    private static final String[] City={"Shapingba","Chengdu","San Francisco"};
    private String username;
    private String place;
    private String place_description;
    private String time_description;
    private String numofpeople;
    private String starttime;
    private String endtime;
    private com.example.lining.easytour.TimePicker startday;
    private com.example.lining.easytour.TimePicker endday;
    private Spinner spinner_province;
    private Spinner spinner_city;
    private Spinner spinner_number;
    private EditText et_location_des;
    private EditText et_time_des;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_order);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        startday = findViewById(R.id.start_time);
        endday = findViewById(R.id.end_time);
        spinner_province = findViewById(R.id.spin_provence);
        spinner_city =  findViewById(R.id.spin_city);
        spinner_number = findViewById(R.id.spin_number);
        et_location_des = findViewById(R.id.descript_location);
        et_time_des = findViewById(R.id.descript_time);

        List<String> prolist = new ArrayList<String>();
        for(String str : Province){
            prolist.add(str);
        }
        ArrayAdapter<String> adapter_pro=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,prolist);
        spinner_province.setAdapter(adapter_pro);

        List<String> citylist = new ArrayList<String>();
        for(String str : City){
            citylist.add(str);
        }
        ArrayAdapter<String> adapter_city=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,citylist);
        spinner_city.setAdapter(adapter_city);

        List<Integer> number_list = new ArrayList<Integer>();
        for(int i=0;i<20;i++){
            number_list.add(i);
        }
        ArrayAdapter<Integer> adapter_number=new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item,number_list);
        spinner_number.setAdapter(adapter_number);

    }

    public void btnSendOrder(View view) {
         place = spinner_province.getSelectedItem().toString()+spinner_city.getSelectedItem().toString();
         place_description = et_location_des.getText().toString();
         time_description = et_time_des.getText().toString();
         numofpeople = spinner_number.getSelectedItem().toString();
         starttime = startday.toString();
         endtime = endday.toString();
         if(place_description.equals("")){
             Toast.makeText(SendOrderActivity.this,"Please input place description",Toast.LENGTH_SHORT).show();
             return;
         }
        if(time_description.equals("")){
            Toast.makeText(SendOrderActivity.this,"Please input time description",Toast.LENGTH_SHORT).show();
            return;
        }
        new ReleaseOrder().execute();
    }

    private class ReleaseOrder extends AsyncTask<String,Void,Integer>{
        @Override
        protected Integer doInBackground(String... strings) {
            String uri = "http://118.89.18.136/EasyTour-bk/releaseorders.php/";
            String result = null;
            HttpPost httpRequest = new HttpPost(uri);
            List params = new ArrayList();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("place", place));
            params.add(new BasicNameValuePair("place_description",place_description));
            params.add(new BasicNameValuePair("time_description",time_description));
            params.add(new BasicNameValuePair("start_time",starttime));
            params.add(new BasicNameValuePair("end_time",endtime));
            params.add(new BasicNameValuePair("number",numofpeople));
            try {
                httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
                Log.i("getStatusCode():","------>"+httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    result = EntityUtils.toString(httpResponse.getEntity());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.i("result:", "------------>"+result);
            if (result.equals("release success"))
                return 1;
            return 0;
        }

        @Override
        protected void onPostExecute(Integer a) {
            super.onPostExecute(a);
            if(a==1){
                Toast.makeText(SendOrderActivity.this,"Release successful",Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(SendOrderActivity.this,"Release failed, server error",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
