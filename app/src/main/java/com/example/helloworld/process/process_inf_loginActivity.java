package com.example.helloworld.process;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import test.Test_Pack;

public class process_inf_loginActivity extends AppCompatActivity {
    private int kind;
    private static final String TAG = "tigercheng";
    private String str;
//    private EditText process_round=null;
    private TextView processer=null;
    private EditText processer_num=null;
    private EditText process_location=null;
    private EditText process_old_id=null;
    private  EditText process_kind_total=null;
    private EditText kind_num=null;
    private EditText process_new_id=null;
    private Button submit=null;
    private TextView res=null;
    public static final int UPDATE_TEXT=1;
    private String resStr;
    private int flag=0;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch(msg.what){

                case UPDATE_TEXT:

                    res.setText(resStr);

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
        setContentView(R.layout.process_inf_login);
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
        String[] ctype = new String[]{"屠宰", "清洗", "去皮", "包装"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ctype);  //创建一个数组适配器
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式

        final Spinner spinner = super.findViewById(R.id.spinner_1);
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


        Button next=findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent inent=new Intent(process_inf_loginActivity.this,process_inf_loginActivity.class);
                startActivity(inent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                in_processinf();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        if(flag==1){
                            Message message=new Message();
                            message.what=UPDATE_TEXT;
                            handler.sendMessage(message);
                        }}
                    }).start();

            }
        });

        Button code_photo=findViewById(R.id.code_getid);
        code_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(process_inf_loginActivity.this, CaptureActivity.class);
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
        res=findViewById(R.id.code_res);
        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(resStr));

                startActivity(intent);
            }
        });
    }
    private void initUI() {


         processer=findViewById(R.id.processer);
         processer_num=findViewById(R.id.processer_num);
        process_location=findViewById(R.id.location);
       process_old_id=findViewById(R.id.old_id);
        process_kind_total=findViewById(R.id.total_kind);
        kind_num=findViewById(R.id.kind_num);
        process_new_id=findViewById(R.id.new_id);
        submit=findViewById(R.id.submit);
//        get_inf();


    }
    private void in_processinf() {
        String getname = processer.getText().toString();
        String getworkid = processer_num.getText().toString();
        String getlocation = process_location.getText().toString();
        String getproid=process_old_id.getText().toString();
        String getkind=process_kind_total.getText().toString();
        String getkindnum=kind_num.getText().toString();
        String newid=getproid.replaceFirst("00",getkindnum);
        process_new_id.setText(newid);
        String getnewid=process_new_id.getText().toString();

        flag=1;
        Log.d(TAG, "processerinf_login: ");
        JSONObject json = new JSONObject();
        JSONObject cdpsJson = new JSONObject();
        JSONObject contentJson = new JSONObject();
        try {
            contentJson.put("ProcessID","2018040311");
            contentJson.put("ProductionID", getproid);
            contentJson.put("ConsumerId",getworkid);
            contentJson.put("ProcessLocation", getlocation);
            contentJson.put("ProductionKind",getkindnum);
            contentJson.put("ReproductionID",getnewid);
            contentJson.put( "Step","2");
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
        Log.d(TAG, "process_inf_login: "+re);
        //http://223.3.72.161/register??characterFlag=1
        HttpUtil.sendOKHttp3RequestPOST(HttpUtil.BASEURL_PROCESSAND_SOURCE+"/process/processtion_add/",
                JsonUtil.getJSON(

                        "ucl",re,
                        "productionId", "3000000",
                        "serialnumber", "42",
                        "flag","3"









//                        "password", passwordSS
                ),
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: " + e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        resStr = response.body().string();

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
                                    Toast.makeText(process_inf_loginActivity.this, ""+resStr, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(process_inf_loginActivity.this,"扫描结果为;"+content,Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject=new JSONObject(content);
                    String p_id=jsonObject.getString("ProductionID");
                    process_old_id.setText(p_id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
