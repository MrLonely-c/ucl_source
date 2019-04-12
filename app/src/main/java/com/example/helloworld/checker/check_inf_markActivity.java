package com.example.helloworld.checker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
import com.example.helloworld.sell.marketinf_inActivity;
import com.yzq.zxinglibrary.common.Constant;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import test.Test_Pack;

public class check_inf_markActivity extends AppCompatActivity {
    private static final String TAG = "tigercheng";
    private EditText check_roundid=null;
    private EditText checkerbatch=null;
    private EditText checkerid=null;
    private EditText check_place=null;
    private EditText applicant=null;
    private EditText checker_res=null;
    private EditText checker_name=null;
    private EditText productionid=null;
    private Button submit_check=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.check_inf_mark);
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
        initUI();
        submit_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "结果提交");
                check_result_in();
            }
        });
    }

    private void initUI() {
        submit_check=findViewById(R.id.submit_check);

        check_roundid=findViewById(R.id.check_round);
        checkerbatch=findViewById(R.id.checkbatch);
        checkerid=findViewById(R.id.checkerid);
        check_place=findViewById(R.id.workplace);
        applicant=findViewById(R.id.applicant);
//        checker_res=findViewById(); 检疫结果需要拍照上传
        checker_name=findViewById(R.id.checkername);
         productionid=findViewById(R.id.production_id);

    }
    private void check_result_in() {
        String getname = checker_name.getText().toString();
        String getapplicant = applicant.getText().toString();
        String getidno = checkerid.getText().toString();
        String getroundid = check_roundid.getText().toString();
        String getproducion = productionid.getText().toString();
        String getplace = check_place.getText().toString();

        String getbatch = checkerbatch.getText().toString();

        Log.d(TAG, "check_inf_markin: ");
        //http://223.3.72.161/register??characterFlag=1
        JSONObject json = new JSONObject();
        JSONObject cdpsJson = new JSONObject();
        JSONObject cgpsJson = new JSONObject();
        JSONObject contentJson = new JSONObject();
        String productionID="4000";
        String serialNumber="40";
        try {
            contentJson.put("QuarantineID" ,"acx0");
            contentJson.put("QuarantineBatch" , "axc023");
            contentJson.put("QuarantinePersonID", "09093");
            contentJson.put( "ProductionId" , "123");
            contentJson.put("QuarantineLocation", "nanjing");
            contentJson.put("Applicant", "wang");
            contentJson.put("QuarantinerName", "lin");
            contentJson.put("QuarantineRes" , "***");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            cdpsJson.put("content",contentJson);
            cdpsJson.put("tag",serialNumber);
            cdpsJson.put("relatedUCL", productionID + ";" + serialNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            cgpsJson.put("contentid",productionID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String key = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAwbb0r0rOMet2lz7k\nr7usGBGROIeFgjXJ2TPF9rH2LwMjFBUEBciVPG8FVej2DrXhdjGzyE6C0H+2w3GD\nLrtEhQIDAQABAkAywpNJX6u6Tv4LUdKw7deBkxDfpDtqzFdxD+z+4NEmrhG3zJPK\nLNIWpzempDymlcwIcR8EsWf92g3NX6en+tSBAiEA8CicGBsa1ozwcDh6vGbYXdGf\nURQqpzxRF9bcU5d1waECIQDOfhRZbZlNo0QcuiabZ4T8jQvMFbsM6uVqxc1x5zng\nZQIhAK6L1Wdvy8HEDbyCUDI+TWNix3gWQCnsHMRG1TusCVoBAiAQvKdpmDiU0mby\n7SOz9PASiFwsbpZ6tY9i2CWO1e8bAQIgHByogCSMuWtc8CZMU2L083NFjwytpzhi\nXZlwXqx5MEg=";
        try {
            json.put("cdps",cdpsJson);
            json.put("cgps",cgpsJson);
            json.put("privateKey", key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String uclStr = json.toString();

        String re=Test_Pack.JSONToUCL(uclStr);
        Log.d(TAG, "check_result_in: "+re);

        String back=Test_Pack.UCLToJSON(re);


        try {
            JSONObject unpack = new JSONObject(back);
            serialNumber = unpack.getJSONObject("cdps").getString("tag");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String s_num=Integer.toString(Integer.parseInt(serialNumber) + 1);
        Log.d(TAG, "check_result_in: "+back);
        HttpUtil.sendOKHttp3RequestPOST("http://223.3.95.218:8000/quarantine/quarantine/submit",
                JsonUtil.getJSON(

//                        "QuarantineID" , "acx0",
//                                 "QuarantineBatch" , "axc023",
//                                  "QuarantinePersonID", "09093",
//                                "ProductionId" , "123",
//                                "QuarantineLocation", "nanjing",
//                                "Applicant", "wang"   ,
//                                 "QuarantinerName", "lin" ,
//                                 "QuarantineRes" , "***"
                            "ucl",re,
                        "productionId", productionID,
                "serialnumber",s_num ,
                "flag","1"







//                        "password", passwordSS
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
                                    Toast.makeText(check_inf_markActivity.this, ""+resStr, Toast.LENGTH_SHORT).show();
                                }
                            }));
//                            e.printStackTrace();
                        }
                    }
                }

        );


    }
}

//2019_4_1 完成ucl基本测试以及 textview的添加工作
