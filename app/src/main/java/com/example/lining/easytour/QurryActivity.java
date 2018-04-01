package com.example.lining.easytour;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class QurryActivity extends AppCompatActivity {
    private ListView lv_to_be_finished;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qurry);
        lv_to_be_finished = (ListView) findViewById(R.id.listview);
        MyArrayAdapter adapter = new MyArrayAdapter(QurryActivity.this,0,getDataFinished());
        lv_to_be_finished.setAdapter(adapter);
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
