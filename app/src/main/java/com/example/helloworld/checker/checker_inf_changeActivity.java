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
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class checker_inf_changeActivity extends AppCompatActivity {
    private static final String TAG = "tigercheng";
    private EditText quarantiner_name=null;
    private EditText quarantiner_id=null;
    private EditText quarantiner_idno=null;
    private EditText quarantiner_intime=null;
    private EditText quarantiner_contact=null;
    private EditText quarantiner_workplace=null;
    private EditText quarantiner_newpassword=null;
    private EditText quarantiner_curpassword=null;
    private Button change_submit = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.checkerinf_layout);
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

        Button photo_1=findViewById(R.id.photo_checker_1);
        photo_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(checker_inf_changeActivity.this, CaptureActivity.class);
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

        Button photo_2=findViewById(R.id.photo_checker_2);
        photo_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(checker_inf_changeActivity.this, CaptureActivity.class);
                startActivityForResult(intent,0);

            }


        });

        Button photo_3=findViewById(R.id.photo_checker_3);
        photo_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(checker_inf_changeActivity.this, CaptureActivity.class);
                startActivityForResult(intent,0);

            }


        });
        change_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "提交修改");
                checker_inf_change();
            }
        });

    }

    private void initUI() {
        quarantiner_name=findViewById(R.id.quarantiner_name);
        quarantiner_id=findViewById(R.id.quarantiner_id);
        quarantiner_idno=findViewById(R.id.quarantiner_idno);
        quarantiner_intime=findViewById(R.id.quarantiner_intime);
        quarantiner_contact=findViewById(R.id.quarantiner_contact);
        quarantiner_workplace=findViewById(R.id.quarantiner_workplace);
        quarantiner_newpassword=findViewById(R.id.quarantiner_newpassword);
        quarantiner_curpassword=findViewById(R.id.quarantiner_curpassword);
        change_submit = findViewById(R.id.checkerchange_submit);
        get_inf();


    }


    private void get_inf(){
        HttpUtil.sendOKHttp3RequestGET("http://223.3.95.218:8000/quarantineTest/quarantiner/inquiry?QuarantinePersonID="+"09091",

                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: " + e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String resStr = response.body().string();
                        quarantiner_name=findViewById(R.id.quarantiner_name);
                        quarantiner_id=findViewById(R.id.quarantiner_id);
                        quarantiner_idno=findViewById(R.id.quarantiner_idno);
                        quarantiner_intime=findViewById(R.id.quarantiner_intime);
                        quarantiner_contact=findViewById(R.id.quarantiner_contact);
                        quarantiner_workplace=findViewById(R.id.quarantiner_workplace);
                        quarantiner_newpassword=findViewById(R.id.quarantiner_newpassword);
                        quarantiner_curpassword=findViewById(R.id.quarantiner_curpassword);

                        try{
                            JSONObject jsonObjec=new JSONObject(resStr);
                            quarantiner_name.setText(jsonObjec.getString("QuarantinerName"));
                            quarantiner_id.setText(jsonObjec.getString("QuarantinePersonID"));
                            quarantiner_idno.setText(jsonObjec.getString("IDNo"));
//                            quarantiner_intime.setText(jsonObjec.getString("RegisterTime"));
                            quarantiner_contact.setText(jsonObjec.getString("ContactNo_Quar"));
                            quarantiner_workplace.setText(jsonObjec.getString("WorkPlaceID"));

                            quarantiner_curpassword.setText(jsonObjec.getString("Password"));
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
                                    Toast.makeText(checker_inf_changeActivity.this, ""+resStr, Toast.LENGTH_SHORT).show();
                                }
                            }));
//                            e.printStackTrace();
                        }
                    }
                }




        );


    }

    private void checker_inf_change() {
        String getname = quarantiner_name.getText().toString();
        String getworkid = quarantiner_id.getText().toString();
        String getidno = quarantiner_idno.getText().toString();
        String getintime = quarantiner_intime.getText().toString();
        String getphone = quarantiner_contact.getText().toString();
        String getplace = quarantiner_workplace.getText().toString();
        String getnewpassword = quarantiner_newpassword.getText().toString();
        String getcurpassword = quarantiner_curpassword.getText().toString();
        Log.d(TAG, "checkerinf_change: ");
        //http://223.3.72.161/register??characterFlag=1
        HttpUtil.sendOKHttp3RequestPOST("http://223.3.95.218:8000/quarantineTest/quarantiner/alter",
                JsonUtil.getJSON(

                        "QuarantinerName" , "**",
        "QuarantinePersonID", "09091",
        "ContactNo_Quar","1343343421414",
        "WorkPlaceID", "nanjing",
        "QuarantinerName","lin",
        "Password", getcurpassword,
        "newpassword", getnewpassword








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
                                    Toast.makeText(checker_inf_changeActivity.this, ""+resStr, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(checker_inf_changeActivity.this,"扫描结果为;"+content,Toast.LENGTH_SHORT).show();
            }
        }
    }

}
