package com.example.lining.easytour.guide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.lining.easytour.R;

public class GuiderSettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guider_setting);
        init();
    }

    private void init() {

    }

    public void btnUpdate(View view) {
        Toast.makeText(GuiderSettingActivity.this,"Updating the new information",Toast.LENGTH_SHORT).show();
    }

}
