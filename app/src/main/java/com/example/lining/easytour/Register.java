package com.example.lining.easytour;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

/**
 * Created by lining on 2018/3/24.
 */

public class Register extends Activity {
    private Button btn_become_tourist;
    private Button btn_become_guider;
    private Button btn_go_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        init();

        btn_become_tourist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Register.this, RegisterTourist.class));

            }
        });

        btn_become_guider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, RegisterGuider.class));

            }
        });

        btn_go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void init() {
        btn_become_guider = findViewById(R.id.btn_become_guider);
        btn_become_tourist = findViewById(R.id.btn_become_tourist);
        btn_go_back = findViewById(R.id.btn_goback);
    }
}
