package com.example.helloworld.sell;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.R;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

public class sellrecordActivity extends AppCompatActivity  {
    private int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sellrecord);
        TextView btnback=findViewById(R.id.toolbar_left_tv);
        btnback.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                onBackPressed();
            }
        });

        Button button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag++;
                LayoutInflater inflater = LayoutInflater.from(sellrecordActivity.this);
                // 获取需要被添加控件的布局
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.sellrecord_add);
                // 获取需要添加的布局
                LinearLayout layout = (LinearLayout) inflater.inflate(
                        R.layout.itemrecord, null).findViewById(R.id.LinearLayout_item);
                // 将布局加入到当前布局中

                linearLayout.addView(layout);
                EditText item_id=(EditText)findViewById(R.id.item_id);
                switch (flag){
                    case 1:
                        //为获取到的实例设置新的id
                        item_id.setId(R.id.etName_1);
                        //为获取到的实例设置文本
                        item_id.setText(flag+"");
                        break;
                    case 2:
                        item_id.setId(R.id.etName_2);
                        item_id.setText(flag+"");
                        break;
                    case 3:
                        item_id.setId(R.id.etName_3);
                        item_id.setText(flag+"");
                        break;
                    case 4:
                        item_id.setId(R.id.etName_4);
                        item_id.setText(flag+"");
                        break;
                }

            }
        });
        LayoutInflater inflater = LayoutInflater.from(sellrecordActivity.this);
        // 获取需要被添加控件的布局
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.sellrecord_add);
        // 获取需要添加的布局
        LinearLayout layout = (LinearLayout) inflater.inflate(
                R.layout.itemrecord, null).findViewById(R.id.LinearLayout_item);
        // 将布局加入到当前布局中
        linearLayout.addView(layout);




    }





}
