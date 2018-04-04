package com.example.lining.easytour;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SendOrderActivity extends AppCompatActivity {
    private static final String[] Province = {"重庆", "四川", "内蒙古"};
    private static final String[] City = {"重庆", "成都", "呼和浩特"};
    private static final String[] Year = {"2018", "2017", "2016"};
    private static final String[] Month = {"11", "12", "1"};
    private static final String[] Day = {"18", "17", "16"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_order);
        Spinner spinner = (Spinner) findViewById(R.id.spin_provence);
        List<String> Prolist = new ArrayList<String>();
        for (String str : Province) {
            Prolist.add(str);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Prolist);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void btnSendOrder(View view) {
        Toast.makeText(SendOrderActivity.this, "Send order", Toast.LENGTH_SHORT).show();
    }
}
