package com.example.helloworld.checker;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.HttpUtil;
import com.example.helloworld.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class checkerActivity extends AppCompatActivity  {
    private DrawerLayout mDrawerLayout;
    private static final String TAG = "tigercheng";
    private TextView information;
    private String name;
    private String phone;
    private int flag=0;
    public static final int UPDATE_TEXT=1;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch(msg.what){

                case UPDATE_TEXT:
                    getreact();
                    if(flag==1) {
                        information = findViewById(R.id.information);
                        information.append("运输人员姓名：" + name);
                        information.append("\n");
                        information.append("运输人员联系方式:" + phone);
                        information.append("\n");
                    }

                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.checker);
        information=findViewById(R.id.information);
        information.setText("人员申请信息：\n");
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

        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);

        Button menuB=findViewById(R.id.toolbar_right_btn);
        menuB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
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

        Button apply_trans=findViewById(R.id.bt_check_3);


        apply_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message=new Message();
                        message.what=UPDATE_TEXT;
                        handler.sendMessage(message);
                    }
                }).start();

            }
        });
    }

    private void getreact(){
        HttpUtil.sendOKHttp3RequestGET("http://223.3.67.245:8000/transport/transpoter/apply/",

                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: " + e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String resStr = response.body().string();
                        information=findViewById(R.id.information);



                        try{
                            JSONObject jsonObjec=new JSONObject(resStr);
//                            information.setText("此次分配人员信息如下：");
////                            information.append("/n");
////                            information.append("人员编号：");
////                            information.append(jsonObjec.getString("ConsumerId"));
////                            information.append("/n");
////                            information.append("运输人员姓名：");
////                            information.append(jsonObjec.getString("ConsumerName"));
////                            information.append("/n");
////                            information.append("联系电话：");
////                            information.append(jsonObjec.getString("ContactNo"));

                            flag=1;
                            phone=jsonObjec.getString("ContactNo");
                            name=jsonObjec.getString("ConsumerName");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d(TAG, "code: " + response.code());
                        Log.d(TAG, "resStr: " + resStr);
                        try {
                            JSONObject resJson = new JSONObject(resStr);
                            Log.d(TAG, "resJson: " + resJson.toString());

                        } catch (JSONException e) {
                            Log.d(TAG, "JSONException: " + e.toString());
                            runOnUiThread(new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(checkerActivity.this, ""+resStr, Toast.LENGTH_SHORT).show();
                                }
                            }));
//                            e.printStackTrace();
                        }
                    }
                }




        );


    }







}
