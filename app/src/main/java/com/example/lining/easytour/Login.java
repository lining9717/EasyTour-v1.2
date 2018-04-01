package com.example.lining.easytour;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by lining on 2018/3/24.
 */

public class Login extends Activity {
    private EditText et_account;
    private EditText et_psw;
    private RadioButton rbt_tourist;
    private RadioButton rbt_guider;
    private RadioGroup radio;
    private Button btn_sign_in;
    private Button btn_sign_up;
    private Button btn_exit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbt_guider.isChecked()){
                Intent intent = new Intent(Login.this,MainActivity.class);
                startActivity(intent);
                finish();
                }else{
                    Intent intent = new Intent(Login.this,Main2Activity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);

            }
        });

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(1);
            }
        });

    }
    private void init(){
        et_account = findViewById(R.id.et_account);
        et_psw = findViewById(R.id.et_psw);
        rbt_guider = findViewById(R.id.rbt_guider);
        rbt_tourist = findViewById(R.id.rbt_tourist);
        radio = findViewById(R.id.radio);
        btn_sign_in = findViewById(R.id.btn_sign_in);
        btn_sign_up = findViewById(R.id.btn_sign_up);
        btn_exit = findViewById(R.id.btn_exit);
    }


}
