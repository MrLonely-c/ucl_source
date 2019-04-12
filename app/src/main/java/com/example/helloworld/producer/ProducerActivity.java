package com.example.helloworld.producer;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.HttpUtil;
import com.example.helloworld.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ProducerActivity extends AppCompatActivity  {
    private DrawerLayout mDrawerLayout;
    private static final String TAG = "tigercheng";
        private TextView information;
   private String name;
   private String phone;
   private String check_name;
   private String checker_phone;
    public static final int UPDATE_TEXT=1;
    public static final int UPDATE_TEXT_1=2;
    private int flag=0;
    private Handler handler=new Handler(){
    public void handleMessage(Message msg){
        switch(msg.what){
            case UPDATE_TEXT_1:
                check_apply();
                information=findViewById(R.id.information);
                if(flag==1) {
                    information.append("检疫人员姓名：" + check_name);
                    information.append("\n");
                    information.append("检疫人员联系方式:" + checker_phone);
                    information.append("\n");
                }

                break;
            case UPDATE_TEXT:
                getreact();
                information=findViewById(R.id.information);
                information.append("运输人员姓名："+name);
                information.append("\n");
                information.append("运输人员联系方式:"+phone);
                information.append("\n");


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
        setContentView(R.layout.producer_layout);
        TextView btnback=findViewById(R.id.toolbar_left_tv);
            btnback.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    onBackPressed();
                }
            });
            mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        TextView rightClick = findViewById(R.id.toolbar_right_tv);
        information=findViewById(R.id.information);
        information.setText("人员申请信息：\n");
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
                Intent intent=new Intent(ProducerActivity.this, sheep_inActivity.class);
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

        Button apply_trans=findViewById(R.id.bt_producer_6);


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

        Button apply_check=findViewById(R.id.bt_producer_5);


        apply_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message=new Message();
                        message.what=UPDATE_TEXT_1;
                        handler.sendMessage(message);
                    }
                }).start();

            }
        });

        Button sheepfollow=findViewById(R.id.bt_producer_7);
        sheepfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProducerActivity.this,sheep_follow_Activity.class);
                startActivity(intent);
            }
        });
    }


    private void getreact(){

        HttpUtil.sendOKHttp3RequestGET("http://223.3.82.173:8000/transport/transpoter/apply/",

                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: " + e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String resStr = response.body().string();

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


                            phone=jsonObjec.getString("ContactNo");
                            name=jsonObjec.getString("ConsumerName");

//                            information.append(name+phone);

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
                                    Toast.makeText(ProducerActivity.this, ""+resStr, Toast.LENGTH_SHORT).show();
                                }
                            }));
//                            e.printStackTrace();
                        }
                    }
                }




        );


    }

    private void check_apply(){

        HttpUtil.sendOKHttp3RequestGET("http://223.3.95.218:8000/quarantine/quarantiner/application",

                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: " + e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String resStr = response.body().string();

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


                            checker_phone=jsonObjec.getString("ContactNo");
                            check_name=jsonObjec.getString("QuarantinerName");
//                            information.append("申请所得检疫人员信息如下："+"\n");
                            flag=1;
//                            information.append(name+phone);

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
                                    Toast.makeText(ProducerActivity.this, ""+resStr, Toast.LENGTH_SHORT).show();
                                }
                            }));
//                            e.printStackTrace();
                        }
                    }
                }




        );


    }


}
