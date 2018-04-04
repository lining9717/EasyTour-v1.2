package com.example.lining.easytour;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class TouristSettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_setting);
    }

    public void btnUpdate(View view) {
        Toast.makeText(TouristSettingActivity.this, "Updating the new information", Toast.LENGTH_SHORT).show();
    }
}
