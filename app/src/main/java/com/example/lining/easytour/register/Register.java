package com.example.lining.easytour.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.lining.easytour.R;

/**
 * Created by lining on 2018/3/24.
 */

public class Register extends AppCompatActivity {
    private Button btn_become_tourist;
    private Button btn_become_guider;
    private Button btn_goback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        init();
        //显示Bar的返回按钮
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        btn_become_tourist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this,RegisterTourist.class);
                startActivity(intent);
                finish();

            }
        });

        btn_become_guider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this,RegisterGuider.class);
                startActivity(intent);
                finish();

            }
        });
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

    public void init(){
        btn_become_guider = findViewById(R.id.btn_become_guider);
        btn_become_tourist = findViewById(R.id.btn_become_tourist);

    }
}
