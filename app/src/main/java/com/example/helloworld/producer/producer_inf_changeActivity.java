package com.example.helloworld.producer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import android.widget.TextView;


import com.example.helloworld.R;



    public class producer_inf_changeActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.producerinfchange);
            TextView btnback=findViewById(R.id.toolbar_left_tv);
            btnback.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    onBackPressed();
                }
            });




        }


}
