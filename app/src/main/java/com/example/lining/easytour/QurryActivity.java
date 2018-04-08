package com.example.lining.easytour;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class QurryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ListView lv_to_be_finished;
    private Button TBDbutton,Fbutton;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qurry);
        lv_to_be_finished = (ListView) findViewById(R.id.orders_listview);/*changed the name of the orders ListView*/
        MyArrayAdapter adapter = new MyArrayAdapter(QurryActivity.this,0,getDataFinished());
        lv_to_be_finished.setAdapter(adapter);
        lv_to_be_finished.setOnItemClickListener(this);
        TBDbutton = findViewById(R.id.btn_to_be_finished);
        Fbutton = findViewById(R.id.btn_finished_order);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(QurryActivity.this,OrderDetailActivity.class);
        startActivity(intent);
    }
    private List<Order> getDataFinished() {
        // TODO Auto-generated method stub
        List<Order> list= new ArrayList<Order>();
        for(int i=0;i<20;i++)
        {
            Order p = new Order(R.drawable.logotemp,i+"","虎溪", "2018/3/" +i,i+"天");
            list.add(p);
        }
        return list;
    }
    private List<Order> getDataToBeFinished() {
        // TODO Auto-generated method stub
        List<Order> list= new ArrayList<Order>();
        for(int i=0;i<20;i++)
        {
            Order p = new Order(R.drawable.logotemp,i+"","沙坪坝", "2018/4/" +i,i+"天");
            list.add(p);
        }
        return list;
    }


    public void btnToBeFinished(View view) {
        MyArrayAdapter adapter = new MyArrayAdapter(QurryActivity.this,0,getDataFinished());
        lv_to_be_finished.setAdapter(adapter);

    }

    public void btnFinishedOrder(View view) {
        MyArrayAdapter adapter = new MyArrayAdapter(QurryActivity.this,0,getDataToBeFinished());
        lv_to_be_finished.setAdapter(adapter);

    }
}
