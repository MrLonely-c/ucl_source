package com.example.helloworld.process;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.R;

public class ProcessActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.process_layout);
        TextView btnback=findViewById(R.id.toolbar_left_tv);
        btnback.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                onBackPressed();

            }
        });
        TextView rightClick = findViewById(R.id.toolbar_right_tv);

        rightClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(ProcessActivity.this,
                        "按钮被点击", Toast.LENGTH_SHORT).show();
            }
        });


        Button process_inf_change=findViewById(R.id.bt_process_1);
        process_inf_change.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent=new Intent(ProcessActivity.this,process_inf_changeActivity.class);
                startActivity(intent);
            }
        });


        Button process_inf_login=findViewById(R.id.bt_process_2);
        process_inf_login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent=new Intent(ProcessActivity.this,process_inf_loginActivity.class);
                startActivity(intent);
            }
        });

        Button process_record=findViewById(R.id.bt_process_3);
        process_record.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent=new Intent(ProcessActivity.this,process_recordActivity.class);
                startActivity(intent);
            }
        });
    }


}
