package com.example.helloworld.checker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.R;

public class checkerActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.checker);

        TextView btnback=findViewById(R.id.toolbar_left_tv);
        btnback.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                onBackPressed();
            }
        });
        TextView rightClick = findViewById(R.id.toolbar_right_tv);

        rightClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(checkerActivity.this,
                        "按钮被点击", Toast.LENGTH_SHORT).show();
            }
        });

        Button checker_inf_change=findViewById(R.id.bt_check_1);
        checker_inf_change.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent=new Intent(checkerActivity.this,checker_inf_changeActivity.class);
                startActivity(intent);
            }
        });

        Button checke_inf_mark=findViewById(R.id.bt_check_2);
        checke_inf_mark.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent=new Intent(checkerActivity.this,check_inf_markActivity.class);
                startActivity(intent);
            }
        });

        Button checke_record=findViewById(R.id.bt_check_3);
        checke_record.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent=new Intent(checkerActivity.this, checker_outActivity.class);
                startActivity(intent);
            }
        });
    }






}
