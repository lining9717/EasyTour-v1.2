package com.example.lining.easytour.orders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lining.easytour.R;
import com.example.lining.easytour.pickerview.GetJsonDataUtil;
import com.example.lining.easytour.pickerview.JsonBean;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class SendOrderActivity extends AppCompatActivity {
    //省市联动
    private String[] Province;
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private boolean isLoaded = false;
    private String[] City;
    private String username;
    private String place;
    private String place_description;
    private String time_description;
    private String numofpeople;
    private String starttime;
    private String endtime;
    private TimePicker startday;
    private TimePicker endday;
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

        //Load DATA
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);

        List<Integer> number_list = new ArrayList<Integer>();
        for(int i=0;i<20;i++){
            number_list.add(i);
        }
        ArrayAdapter<Integer> adapter_number=new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item,number_list);
        spinner_number.setAdapter(adapter_number);

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
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了

                        Toast.makeText(SendOrderActivity.this, "Begin Parse Data", Toast.LENGTH_SHORT).show();
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    Toast.makeText(SendOrderActivity.this, "Parse Succeed", Toast.LENGTH_SHORT).show();
                    isLoaded = true;
                    dataLoadToSpiner();
                    break;

                case MSG_LOAD_FAILED:
                    Toast.makeText(SendOrderActivity.this, "Parse Failed", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void dataLoadToSpiner() {
        List<String> prolist = new ArrayList<String>();
//        for(String str : Province){
//            prolist.add(str);
//        }
        ArrayAdapter<String> adapter_pro=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,prolist);
        spinner_province.setAdapter(adapter_pro);

        List<String> citylist = new ArrayList<String>();
//        for(String str : City){
//            citylist.add(str);
//        }
        ArrayAdapter<String> adapter_city=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,citylist);
        spinner_city.setAdapter(adapter_city);
    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
//            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

//            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
//                String CityName = jsonBean.get(i).getCityList().get(c).getName();
//                CityList.add(CityName);//添加城市
//                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表
//
//                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
//                if (jsonBean.get(i).getCityList().get(c).getArea() == null
//                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
//                    City_AreaList.add("");
//                } else {
//                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
//                }
//                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
//            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
//            options3Items.add(Province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }
    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    public void btnSendOrder(View view) {
         place = spinner_province.getSelectedItem().toString()+" "+spinner_city.getSelectedItem().toString();
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
            String uri = "http://118.89.18.136/EasyTour/EasyTour-bk/releaseorders.php/";
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
