package com.example.helloworld.baseuser;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.annotation.JSONPOJOBuilder;
import com.example.helloworld.HttpUtil;
import com.example.helloworld.R;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

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
    private String from=" ";
    private String to="  ";
    private String start_id="  ";
    private int sheep_count=0;
    private String market_name="  ";
    private String passer_id="  ";
    private String pass_start="  ";
    private String pass_arrive="  ";
    private String checker_name="  ";
    private String process_name="  ";
    private String check_time="  ";
    private String check_place="  ";
    private String in_time="  ";
    private String old_id="  ";
    private String new_id="  ";
    private String process_place;
    private int count=0;
    private String process_time="  ";
    private String record_time="  ";
    private String process_id="  ";
    private int process_num;
    private String r_flag;
    private String id=" ";
    private String weight="  ";
    private String dis="  ";
    private String tem="  ";
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            information=findViewById(R.id.information_all);
            information.setMovementMethod(ScrollingMovementMethod.getInstance());
            information.setText("溯源结果如下：\n");
            switch(msg.what){
                case UPDATE_TEXT:
                    if(count==1) {
                        process_num = 0;
                        HttpUtil.sendOKHttp3RequestGET("http://223.3.90.242:8000/user/origin/?ProductionID=" + id,
                                new okhttp3.Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                        Log.d(TAG, "onFailure: " + e.toString());
                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        final String resStr = response.body().string();
                                        String[] strs = resStr.split("\\}");
                                        for (int i = 0; i < strs.length; i++) {
                                            strs[i] += "}";
                                            Log.d(TAG, "onResponse: " + strs[i]);
                                        }
                                        try {
                                            for (int i = 0; i < strs.length; i++) {
                                                JSONObject jsonObject = new JSONObject(strs[i]);
                                                r_flag = jsonObject.getString("Flag");
                                                switch (r_flag) {
                                                    case "0":
                                                        sheep_count++;
                                                        tem=jsonObject.getString("BodyTemperature");
                                                        dis=jsonObject.getString("ActiveDis");
                                                        weight=jsonObject.getString("Weight");
                                                        record_time=jsonObject.getString("MonitorRecordTime");
                                                        start_id=jsonObject.getString("RecordID");
                                                        information.append("\n"+record_time);
                                                        information.append("第"+sheep_count);
                                                        information.append("次监测原羊\n");
                                                        information.append("体重："+weight);
                                                        information.append("\n");
                                                        information.append("活动距离："+dis);
                                                        information.append("\n");
                                                        information.append("体温："+tem);
                                                        information.append("\n");
                                                        break;
                                                    case "1":
                                                        check_place = jsonObject.getString("QuarantineLocation");
                                                        checker_name = jsonObject.getString("QuarantinerName");
                                                        check_time = jsonObject.getString("QuarantineTime");
                                                        information.append("\n");
                                                        information.append(check_time + "，");
                                                        information.append("在" + check_place);
                                                        information.append("，由" + checker_name);
                                                        information.append("完成了检疫工作" + "O（∩_∩）0");
                                                        information.append("\n");
                                                        break;
                                                    case "2":
                                                        passer_id = jsonObject.getString("TransactionPersonID");
                                                        pass_arrive = jsonObject.getString("TransactionEndTime");
                                                        pass_start = jsonObject.getString("TransactionStartTime");
                                                        from = jsonObject.getString("From");
                                                        to = jsonObject.getString("To");
                                                        information.append("\n");
                                                        information.append(pass_start + "从");
                                                        information.append(from + "发货");
                                                        information.append("\n" + "经由");
                                                        information.append(passer_id + "运输");
                                                        information.append("\n");
                                                        information.append("于" + pass_arrive);
                                                        information.append("到达" + to);
                                                        information.append("\n");
                                                        break;
                                                    case "3":
                                                        process_num++;
                                                        process_name = jsonObject.getString("ConsumerId");
                                                        process_time = jsonObject.getString("ProcessTime");
                                                        process_place = jsonObject.getString("ProcessLocation");
                                                        old_id = jsonObject.getString("ProductionID");
                                                        new_id = jsonObject.getString("ReproductionID");
                                                        information.append("\n");
                                                        information.append(process_time + "，");
                                                        information.append("商品在" + process_place);
                                                        information.append("\n");
                                                        information.append("经由加工员" + process_name);
                                                        information.append("完成了第" + process_num);
                                                        information.append("次加工分块" + "\n");
                                                        information.append("原产品ID为：" + old_id);
                                                        information.append("   加工后id为" + new_id);
                                                        information.append("\n");
                                                        break;
                                                    case "4":
                                                        in_time = jsonObject.getString("SPReceiveTime");
                                                        information.append("\n");
                                                        information.append("商品于" + in_time);
                                                        information.append("到达了超市，最终来到您的手里^_^！");
                                                        information.append("\n");
                                                        break;
                                                    default:
                                                        break;
                                                }
                                            }

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
                                                    Toast.makeText(BaseUserActivity.this, "" + resStr, Toast.LENGTH_SHORT).show();
                                                }
                                            }));
//                            e.printStackTrace();
                                        }
                                    }
                                }
                        );

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

                Intent intent = new Intent(BaseUserActivity.this, CaptureActivity.class);

                startActivityForResult(intent,0);
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(count==1) {
                            Message message = new Message();
                            message.what = UPDATE_TEXT;
                            handler.sendMessage(message);
                        }
                    }
                }).start();

            }


        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == 0 && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(Constant.CODED_CONTENT);
                Toast.makeText(BaseUserActivity.this,"扫描结果为;"+content,Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject=new JSONObject(content);
                    id=jsonObject.getString("ProductionID");
                    count=1;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}
