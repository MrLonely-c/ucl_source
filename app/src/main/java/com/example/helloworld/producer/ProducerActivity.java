package com.example.helloworld.producer;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.R;

public class ProducerActivity extends AppCompatActivity  {
    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.producer_layout);
        TextView btnback=findViewById(R.id.toolbar_left_tv);
            btnback.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    onBackPressed();
                }
            });
            mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        TextView rightClick = findViewById(R.id.toolbar_right_tv);

        rightClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(ProducerActivity.this,
                        "按钮被点击", Toast.LENGTH_SHORT).show();
            }
        });

        Button menuB=findViewById(R.id.toolbar_right_btn);
        menuB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        TextView proinfchange = findViewById(R.id.bt_producer_1);

        proinfchange.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(ProducerActivity.this, producer_inf_changeActivity.class);
                startActivity(intent);
            }
        });

        TextView farminfchange = findViewById(R.id.bt_producer_2);

        farminfchange.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(ProducerActivity.this, farm_inf_changeActivity.class);
                startActivity(intent);
            }
        });

        TextView state_inf = findViewById(R.id.bt_producer_3);

        state_inf.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(ProducerActivity.this, productionStateActivity.class);
                startActivity(intent);
            }
        });

        TextView OutClick = findViewById(R.id.bt_producer_4);

        OutClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(ProducerActivity.this, OutActivity.class);
                startActivity(intent);
            }
        });
    }






}
