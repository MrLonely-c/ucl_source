package com.example.helloworld.sell;

import android.content.Intent;
import android.support.v7.app.ActionBar;
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
import com.example.helloworld.JsonUtil;
import com.example.helloworld.R;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import test.Test_Pack;

public class Item_in_Activity extends AppCompatActivity  {
    private static final String TAG = "tigercheng";
    private EditText productionid=null;
    private EditText selllocation=null;
    private EditText receivetime=null;
    private EditText sellouttime=null;
    private EditText price=null;
    private EditText name=null;
    private Button change_submit = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.item_in_layout);
        TextView btnback=findViewById(R.id.toolbar_left_tv);
        btnback.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                onBackPressed();
            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Button photo=findViewById(R.id.item_photo);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Item_in_Activity.this, CaptureActivity.class);
                /*ZxingConfig是配置类  可以设置是否显示底部布局，闪光灯，相册，是否播放提示音  震动等动能
                 * 也可以不传这个参数
                 * 不传的话  默认都为默认不震动  其他都为true
                 * */

                //ZxingConfig config = new ZxingConfig();
                //config.setShowbottomLayout(true);//底部布局（包括闪光灯和相册）
                //config.setPlayBeep(true);//是否播放提示音
                //config.setShake(true);//是否震动
                //config.setShowAlbum(true);//是否显示相册
                //config.setShowFlashLight(true);//是否显示闪光灯
                //intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                startActivityForResult(intent,0);

            }


        });
        productionid=findViewById(R.id.productionid);
        selllocation=findViewById(R.id.selllocation);

        receivetime=findViewById(R.id.receivetime);
        price=findViewById(R.id.price);
        name=findViewById(R.id.name);
        change_submit = findViewById(R.id.item_submit);
        change_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "信息提交");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
//获取当前时间
                Date date = new Date(System.currentTimeMillis());
                receivetime.setText(simpleDateFormat.format(date));
                item_in();
            }
        });

    }
    private void item_in() {
        String getitemid = productionid.getText().toString();
        String getlocation = selllocation.getText().toString();
        String getintime = receivetime.getText().toString();

        String getprice = price.getText().toString();
        String getname=name.getText().toString();
        Log.d(TAG, "item_in: ");
        JSONObject json = new JSONObject();
        JSONObject cdpsJson = new JSONObject();
        JSONObject contentJson = new JSONObject();
        try {
            contentJson.put("ProductionID",getitemid);
            contentJson.put("SPReceiveTime",getintime);

            contentJson.put( "Price",getprice);
            contentJson.put("APApprovalRes",0);
            contentJson.put("AccountabilityFlag",0);
            contentJson.put("GoodsName",getname);
            contentJson.put("ConsumerID","3200000002");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            cdpsJson.put("content",contentJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("cdps",cdpsJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String uclStr = json.toString();

        String re= Test_Pack.JSONToUCL(uclStr);
        Log.d(TAG, "check_result_in: "+re);

        //http://223.3.72.161/register??characterFlag=1
        HttpUtil.sendOKHttp3RequestPOST("http://223.3.79.119:8000/sell/register_commodity/",
                JsonUtil.getJSON(
                        "ucl",re,
                        "productionId", "3000000",
                        "serialnumber", "40",
                        "flag","4"
),
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: " + e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String resStr = response.body().string();

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
                                    Toast.makeText(Item_in_Activity.this, ""+resStr, Toast.LENGTH_SHORT).show();
                                }
                            }));
//                            e.printStackTrace();
                        }
                    }
                }

        );


    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == 0 && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(Constant.CODED_CONTENT);
                Toast.makeText(Item_in_Activity.this,"扫描结果为;"+content,Toast.LENGTH_SHORT).show();
            }
        }
    }





}
