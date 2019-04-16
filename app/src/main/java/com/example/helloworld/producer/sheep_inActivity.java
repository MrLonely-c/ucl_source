package com.example.helloworld.producer;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class sheep_inActivity extends AppCompatActivity  {
    private EditText intime;
    private static final String TAG = "tigercheng";
    private EditText uid=null;
    private EditText manager=null;
    private Button submit=null;

    private ImageView imView;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sheepin);

        imView = (ImageView) findViewById(R.id.code_show);

        StrictMode.setThreadPolicy(new
                StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(
                new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());


        TextView btnback = findViewById(R.id.toolbar_left_tv);
        btnback.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView rightClick = findViewById(R.id.toolbar_right_tv);
        submit=findViewById(R.id.arrivetime);
        rightClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(sheep_inActivity.this,
                        "按钮被点击", Toast.LENGTH_SHORT).show();
            }
        });
        uid=findViewById(R.id.u_id);
        manager=findViewById(R.id.manger_id);
        intime=findViewById(R.id.in_time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        intime.setText(simpleDateFormat.format(date));
        submit=findViewById(R.id.submit_in);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
//获取当前时间
                Date date = new Date(System.currentTimeMillis());
                String getuid=uid.getText().toString();
                if(getuid.trim().length()==0){
                    Toast.makeText(sheep_inActivity.this, ""+"项圈ID不能为空，请重新输入！", Toast.LENGTH_SHORT).show();
                }
                else {
                    sheepin();
                    intime.setText(simpleDateFormat.format(date));
                    uid.setText("");
                }
            }
        });
    }

    public Bitmap getBitmap(String path) throws IOException {
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                InputStream inputStream = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }



    private void sheepin() {
        String getuid=uid.getText().toString();
        String getmid=manager.getText().toString();
        Log.d(TAG, "sheepin: ");
        //http://223.3.72.161/register??characterFlag=1
        HttpUtil.sendOKHttp3RequestPOST("http://223.3.79.211:8000/produce/input_sheep",
                JsonUtil.getJSON(
                        "UUID",getuid,
                        "ConsumerId",getmid

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
                                    Toast.makeText(sheep_inActivity.this, ""+resStr, Toast.LENGTH_SHORT).show();


                                    try {
                                        Bitmap bitmap = getBitmap(resStr);
                                        imView.setImageBitmap(bitmap);
                                    } catch (IOException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }

                                }
                            }));
//                            e.printStackTrace();
                        }
                    }
                }

        );


    }

}
