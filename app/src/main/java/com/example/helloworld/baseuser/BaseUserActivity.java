package com.example.helloworld.baseuser;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.annotation.JSONPOJOBuilder;
import com.example.helloworld.HttpUtil;
import com.example.helloworld.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BaseUserActivity extends AppCompatActivity {
    private Button search=null;
    private static final String TAG = "tigercheng";
    public static final int UPDATE_TEXT=1;
    private TextView information;
    private String market_name="  ";
    private String passer_name="  ";
    private String checker_name="  ";
    private String process_nam="  ";
    private String check_time="  ";
    private String check_application="  ";
    private String check_place="  ";
    private String in_time="  ";
    private String process_place;
    private int count=0;
private String process_time="  ";
private String recieve_time="  ";
private String process_id="  ";
private int flag=0;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            show_source();
            switch(msg.what){
                case UPDATE_TEXT:
                    if(flag==1) {
                        information = findViewById(R.id.information_all);
                        information.setText("这头羊出生于胜利牧场\n");
                        information.append("在" + check_time);

                        information.append("于");
                        information.append(check_place + "\n");
                        information.append("通过了检疫员");
                        information.append(checker_name);
                        information.append("的检疫，进入了加工厂\n");
                        information.append("");
                        information.append(process_time);
                        information.append(",");
                        information.append("在" + process_place);
                        information.append("完成了加工操作" + "\n");
                        information.append(recieve_time + "进入了超市");

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
        setContentView(R.layout.layout_base_user);
        TextView btnback=findViewById(R.id.toolbar_left_tv);
        btnback.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                onBackPressed();
            }
        });
        search=findViewById(R.id.source);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            Message message = new Message();
                            message.what = UPDATE_TEXT;
                            handler.sendMessage(message);
                        }
                    }).start();
                }

        });
    }
    private void show_source(){
        HttpUtil.sendOKHttp3RequestGET("http://223.3.85.206:8000/user/origin/?ProductionID=123",

                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: " + e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String resStr = response.body().string();
                        String [] strs = resStr.split("\\}");
                        for(int i=0;i<strs.length;i++){
                            strs[i]+="}";
                            Log.d(TAG, "onResponse: "+strs[i]);
                        }

                        try{
                                flag=1;
                                JSONObject jsonObjec = new JSONObject(strs[0]);
                                checker_name=jsonObjec.getString("QuarantinerName");
                                        Log.d(TAG, "onResponse: "+checker_name);
                                        check_time=jsonObjec.getString("QuarantineTime");
                                        Log.d(TAG, "onResponse: "+check_time);
                                        check_place=jsonObjec.getString("QuarantineLocation");
                                        Log.d(TAG, "onResponse: "+check_place);
                                        JSONObject jsonObjec1 = new JSONObject(strs[2]);
                                        process_place= jsonObjec1.getString("ProcessLocation");
                                        process_time=jsonObjec1.getString("ProcessTime");
                                        process_id=jsonObjec1.getString("ConsumerId");
                                        JSONObject jsonObjec2 = new JSONObject(strs[4]);

//                                        recieve_time=jsonObjec2.getString("SPReceiveTime");


//

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
                                    Toast.makeText(BaseUserActivity.this, ""+resStr, Toast.LENGTH_SHORT).show();
                                }
                            }));
//                            e.printStackTrace();
                        }
                    }
                }




        );


    }

}
