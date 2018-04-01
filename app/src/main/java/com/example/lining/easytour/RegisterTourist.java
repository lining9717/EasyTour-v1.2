package com.example.lining.easytour;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by lining on 2018/3/24.
 */

public class RegisterTourist extends Activity {
    private EditText et_r_t_user_name;
    private EditText et_r_t_psw;
    private EditText et_r_t_psw_for_sure;
    private Button btn_r_t_ok;
    private Button btn_r_t_cancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_tourist);

        init();

        btn_r_t_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterTourist.this,"Coding",Toast.LENGTH_SHORT).show();
            }
        });

        btn_r_t_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void init(){
        et_r_t_user_name = findViewById(R.id.et_r_t_user_name);
        et_r_t_psw = findViewById(R.id.et_r_t_psw);
        et_r_t_psw_for_sure = findViewById(R.id.et_r_t_psw_for_sure);
        btn_r_t_ok = findViewById(R.id.btn_r_t_ok);
        btn_r_t_cancel = findViewById(R.id.btn_r_t_cancel);
    }
}
