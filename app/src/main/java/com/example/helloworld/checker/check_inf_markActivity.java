package com.example.helloworld.checker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

    private EditText checker_name=null;
    private EditText productionid=null;
    private Button submit_check=null;
    private Button next=null;
    private String str;
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
        String[] ctype = new String[]{"产地检疫", "异地运输检疫", "入厂检疫", "加工后检疫"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ctype);  //创建一个数组适配器
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式

        final Spinner spinner = super.findViewById(R.id.check_round);
        spinner.setAdapter(adapter);

        str = (String) spinner.getSelectedItem();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                //拿到被选择项的值
                str = (String) spinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        Button code_photo=findViewById(R.id.photo_checkid);
        code_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(check_inf_markActivity.this, CaptureActivity.class);

                startActivityForResult(intent,0);

            }


        });

        submit_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "结果提交");
                check_result_in();
            }
        });

        next=findViewById(R.id.button_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(check_inf_markActivity.this,check_inf_markActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initUI() {
        submit_check=findViewById(R.id.submit_check);



        checkerid=findViewById(R.id.checkerid);
        check_place=findViewById(R.id.workplace);

//        checker_res=findViewById(); 检疫结果需要拍照上传
        checker_name=findViewById(R.id.checkername);
         productionid=findViewById(R.id.production_id);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == 0 && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(Constant.CODED_CONTENT);
                Toast.makeText(check_inf_markActivity.this,"扫描结果为;"+content,Toast.LENGTH_SHORT).show();

                JSONObject jsonObject= null;
                productionid.setText(content);
                try {
                    jsonObject = new JSONObject(content);
                    String p_id=jsonObject.getString("ProductionID");
                    productionid.setText(p_id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        }
    }
    private void check_result_in() {
        String getname = checker_name.getText().toString();

        String getidno = checkerid.getText().toString();
        String getroundid = str;
        String getproducion = productionid.getText().toString();
        String getplace = check_place.getText().toString();

//        String getbatch = checkerbatch.getText().toString();

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
            contentJson.put( "ProductionId" , "3400000000000000");
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
//            cdpsJson.put("relatedUCL", productionID + ";" + serialNumber);
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
        HttpUtil.sendOKHttp3RequestPOST("http://223.3.90.52:8000/quarantine/quarantine/submit",
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
