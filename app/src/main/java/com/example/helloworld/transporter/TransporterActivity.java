package com.example.helloworld.transporter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.MainActivity;
import com.example.helloworld.R;

public class TransporterActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.passer_layout);
        TextView btnback=findViewById(R.id.toolbar_left_tv);
        btnback.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                onBackPressed();
            }
        });
        TextView rightClick = findViewById(R.id.toolbar_right_tv);

        rightClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(TransporterActivity.this,
                        "按钮被点击", Toast.LENGTH_SHORT).show();
            }
        });

        Button trans_inf_change=findViewById(R.id.bt_transport_1);
        trans_inf_change.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent=new Intent(TransporterActivity.this,transporterinf_changeActivity.class);
                startActivity(intent);
            }
        });

        Button items_inf_check=findViewById(R.id.bt_transport_2);
        items_inf_check.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent=new Intent(TransporterActivity.this,ProductionCheckActivity.class);
                startActivity(intent);
            }
        });

        Button trans_inf_in=findViewById(R.id.bt_transport_3);
        trans_inf_in.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent=new Intent(TransporterActivity.this,TransportDataActivity.class);
                startActivity(intent);
            }
        });
    }






}
