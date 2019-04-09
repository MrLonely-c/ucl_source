package com.example.helloworld.producer;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.R;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import java.text.SimpleDateFormat;
import java.util.Date;

public class sheep_inActivity extends AppCompatActivity  {
    private EditText intime;
    private Button submit=null;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sheepin);

        TextView btnback = findViewById(R.id.toolbar_left_tv);
        btnback.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView rightClick = findViewById(R.id.toolbar_right_tv);
        submit=findViewById(R.id.arrivetime);
        rightClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(sheep_inActivity.this,
                        "按钮被点击", Toast.LENGTH_SHORT).show();
            }
        });

        intime=findViewById(R.id.in_time);
        submit=findViewById(R.id.submit_in);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
//获取当前时间
                Date date = new Date(System.currentTimeMillis());
                intime.setText(simpleDateFormat.format(date));


            }
        });
    }


}
