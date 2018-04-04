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

public class RegisterGuider extends Activity {
    private EditText et_r_g_user_name;
    private EditText et_r_g_psw;
    private EditText et_r_g_real_name;
    private EditText et_r_g_ID;
    private EditText et_r_g_tel;
    private Button btn_r_g_ok;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_guider);
        init();
        btn_r_g_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterGuider.this, "Coding...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
    when pushed the back button , go back
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void init() {
        et_r_g_user_name = findViewById(R.id.et_r_g_user_name);
        et_r_g_psw = findViewById(R.id.et_r_g_psw);
        et_r_g_real_name = findViewById(R.id.et_r_g_real_name);
        et_r_g_ID = findViewById(R.id.et_r_g_ID);
        et_r_g_tel = findViewById(R.id.et_r_g_tel);
        btn_r_g_ok = findViewById(R.id.btn_r_g_ok);

    }
}
