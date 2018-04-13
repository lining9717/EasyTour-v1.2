package com.example.lining.easytour.tourist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lining.easytour.R;
import com.example.lining.easytour.util.SerializableHashMap;

import java.util.Map;

public class TouristUnfinishiedOrdersActivity extends AppCompatActivity {
    private TextView tv_orderID;
    private TextView tv_touristname;
    private TextView tv_guidename;
    private TextView tv_place;
    private TextView tv_start_date;
    private TextView tv_end_date;
    private TextView tv_place_instruction;
    private TextView tv_time_instruction;
    private TextView tv_numofPeople;
    private ImageView iv_Img;
    private Map<String, String> order;
    private String guidername;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_unfinished);
        init();
        Bundle bundle = getIntent().getExtras();
        SerializableHashMap serializableHashMap = (SerializableHashMap) bundle.get("message");
        guidername = bundle.getString("touristname");
        order = serializableHashMap.getMap();
        tv_orderID.setText("0000000" + order.get("orderID"));
        tv_touristname.setText(order.get("username"));
        tv_guidename.setText(guidername);
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
        tv_orderID = findViewById(R.id.tv_order_detail_id);
        tv_touristname = findViewById(R.id.tv_order_detail_tourist_name);
        tv_guidename = findViewById(R.id.tv_order_detail_guider_name);
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
    /*
    when pushed the back button , go back
     */
    @Override
    public void onBackPressed() {
        finish();
    }

}
