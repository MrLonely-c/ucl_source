package com.example.helloworld.process;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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
                String a="2018201800000000";
                String b=a.replaceFirst("00","01");
                Log.d(TAG, "onClick: "+b);
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

        Log.d(TAG, "processerinf_change: ");
        //http://223.3.72.161/register??characterFlag=1
        HttpUtil.sendOKHttp3RequestPOST("http://223.3.85.206:8000/process/processtion_add/",
                JsonUtil.getJSON(

                        "ProcessID", "20181455",
                "ProductionID", getproid,
                "ConsumerId",getworkid,
                "ProcessLocation", getlocation,
                "ProductionKind",getkind,
                "ReproductionID1", "121238273 ",
                "ReproductionID2", "1354651351",
                "ProcessUCLLink","121315112"






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
                                    Toast.makeText(process_inf_loginActivity.this, ""+resStr, Toast.LENGTH_SHORT).show();
                                }
                            }));
//                            e.printStackTrace();
                        }
                    }
                }

        );


    }

}
