package com.example.lining.easytour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
    }

    public void btnOrdersInformation(View view) {
        Toast.makeText(this,"进入用户信息，进行交互",Toast.LENGTH_SHORT).show();
    }

    public void btnResiveOrder(View view) {

    }
}
