package com.example.helloworld.transporter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import test.Test_Pack;

public class ProductionCheckActivity extends AppCompatActivity {
    private EditText p_id;
    private EditText passer_id;
    private static final String TAG = "tigercheng";
    private Button submit=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_production_check);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        p_id=findViewById(R.id.item_id);
        passer_id=findViewById(R.id.passer_id);

        TextView btnback=findViewById(R.id.toolbar_left_tv);
        btnback.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                onBackPressed();
            }
        });
        submit=findViewById(R.id.submit_item);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit_passer_item();
                p_id.setText("");
            }
        });

        Button code_photo=findViewById(R.id.photo_out);
        code_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductionCheckActivity.this, CaptureActivity.class);

                startActivityForResult(intent,0);

            }


        });

    }
    private void submit_passer_item() {
        String getid= passer_id.getText().toString();
        String getpid=p_id.getText().toString();


        Log.d(TAG, "productioncheck_submit: ");

        JSONObject json = new JSONObject();
        JSONObject cdpsJson = new JSONObject();
        JSONObject contentJson = new JSONObject();
        try {
            contentJson.put("ProductionID" ,getpid);
            contentJson.put("TransactionPersonID" , getid);
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
        Log.d(TAG, "productioncheck: "+re);
        //http://223.3.72.161/register??characterFlag=1
        HttpUtil.sendOKHttp3RequestPOST("http://223.3.82.173:8000/transport/product_enter/",
                JsonUtil.getJSON(


//                        "ProductionID",getpid,
//                "TransactionPersonID",getid
                        "ucl",re,
                        "productionId", "3000000",
                        "serialnumber", "40",
                        "flag","2"





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
                                    Toast.makeText(ProductionCheckActivity.this, ""+resStr, Toast.LENGTH_SHORT).show();
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
                p_id.setText(content);
                Toast.makeText(ProductionCheckActivity.this,"扫描结果为;"+content,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
