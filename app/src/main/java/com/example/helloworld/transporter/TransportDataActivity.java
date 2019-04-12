package com.example.helloworld.transporter;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.HttpUtil;
import com.example.helloworld.JsonUtil;
import com.example.helloworld.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TransportDataActivity extends AppCompatActivity {
    private Button start=null;
    private Button arrive=null;
    private Button submit=null;
    private EditText passer_id;
    private EditText from;
    private EditText to;
    private EditText start_show;
    private EditText arrive_show;
    private static final String TAG = "tigercheng";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_transport_data);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        TextView btnback=findViewById(R.id.toolbar_left_tv);
        btnback.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                onBackPressed();
            }
        });

        start=findViewById(R.id.starttime);
        arrive=findViewById(R.id.arrivetime);
        submit=findViewById(R.id.arrivetime);
        start_show=findViewById(R.id.begin_show);
        arrive_show=findViewById(R.id.arrive_show);
        from=findViewById(R.id.from);
        to=findViewById(R.id.to);
        passer_id=findViewById(R.id.passer_id);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
//获取当前时间
                Date date = new Date(System.currentTimeMillis());
                start_show.setText(simpleDateFormat.format(date));
                start_submit();

            }
        });

        arrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
//获取当前时间
                Date date = new Date(System.currentTimeMillis());
                arrive_show.setText(simpleDateFormat.format(date));
                arrive_submit();
            }
        });
    }

    private void start_submit() {
        String getid= passer_id.getText().toString();
        String getfrom = from.getText().toString();
        String getto = to.getText().toString();
        String getstime=start_show.getText().toString();
        String getatime=arrive_show.getText().toString();


        Log.d(TAG, "transportdata_submit: ");
        //http://223.3.72.161/register??characterFlag=1
        HttpUtil.sendOKHttp3RequestPOST("http://223.3.82.173:8000/transport/start/",
                JsonUtil.getJSON(


                        "TransactionPersonID",getid,
                "From",getfrom,
                "To",getto,
                "TransactionStartTime",getstime






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
                                    Toast.makeText(TransportDataActivity.this, ""+resStr, Toast.LENGTH_SHORT).show();
                                }
                            }));
//                            e.printStackTrace();
                        }
                    }
                }

        );



    }
    private void arrive_submit() {
        String getid= passer_id.getText().toString();
        String getfrom = from.getText().toString();
        String getto = to.getText().toString();
        String getstime=start_show.getText().toString();
        String getatime=arrive_show.getText().toString();

        Log.d(TAG, "transportdata_submit: ");
        //http://223.3.72.161/register??characterFlag=1
        HttpUtil.sendOKHttp3RequestPOST("http://223.3.82.173:8000/transport/end/",
                JsonUtil.getJSON(

                        "TransactionPersonID",getid,
                "TransactionEndTime",getatime






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
                                    Toast.makeText(TransportDataActivity.this, ""+resStr, Toast.LENGTH_SHORT).show();
                                }
                            }));
//                            e.printStackTrace();
                        }
                    }
                }

        );



    }


}