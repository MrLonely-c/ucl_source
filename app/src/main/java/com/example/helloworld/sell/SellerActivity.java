package com.example.helloworld.sell;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.R;

public class SellerActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.seller_layout);

        TextView btnback=findViewById(R.id.toolbar_left_tv);
        btnback.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                onBackPressed();
            }
        });
        TextView rightClick = findViewById(R.id.toolbar_right_tv);

        rightClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(SellerActivity.this,
                        "按钮被点击", Toast.LENGTH_SHORT).show();
            }
        });
        Button SellerClick = findViewById(R.id.bt_1);

        SellerClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(SellerActivity.this, sellerinf_changeActivity.class);
                startActivity(intent);

            }
        });
        Button SupClick = findViewById(R.id.bt_2);

        SupClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(SellerActivity.this, marketinf_changeActivity.class);
                startActivity(intent);

            }
        });
        Button ItemClick = findViewById(R.id.bt_3);

        ItemClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(SellerActivity.this, Item_in_Activity.class);
                startActivity(intent);

            }
        });
        Button Itemfind = findViewById(R.id.bt_4);

        Itemfind.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(SellerActivity.this, Iteminf_findActivity.class);
                startActivity(intent);

            }
        });

        Button Itemrecord= findViewById(R.id.bt_5);

        Itemrecord.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(SellerActivity.this, sellrecordActivity.class);
                startActivity(intent);

            }
        });
    }






}
