package com.example.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.helloworld.checker.CheckerActivity;
import com.example.helloworld.loginAndSign.LoginAndSignActivity;
import com.example.helloworld.process.ProcessActivity;
import com.example.helloworld.producer.ProducerActivity;
import com.example.helloworld.sell.SellerActivity;
import com.example.helloworld.transporter.TransporterActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button button1=(Button)findViewById(R.id.bt_1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent=new Intent(MainActivity.this, ProducerActivity.class);
                    startActivity(intent);
                    //finish();
            }
        });

        Button button2=(Button)findViewById(R.id.bt_2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, TransporterActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        Button button3=(Button)findViewById(R.id.bt_3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, CheckerActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        Button button4=(Button)findViewById(R.id.bt_4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, ProcessActivity.class);
                startActivity(intent);
               // finish();
            }
        });

        Button button5=(Button)findViewById(R.id.bt_5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, SellerActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        Button realstart=(Button)findViewById(R.id.bt_6);
        realstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, LoginAndSignActivity.class);
                startActivity(intent);
                //finish();
            }
        });


    }
}
