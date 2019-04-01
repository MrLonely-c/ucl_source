package com.example.helloworld.process;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
import com.example.helloworld.sell.marketinf_changeActivity;
import com.example.helloworld.transporter.transporterinf_changeActivity;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class process_inf_changeActivity extends AppCompatActivity {
    private static final String TAG = "tigercheng";
    private Intent intent = null;
    public static final int TAKE_PHOTO = 1;
    private ImageView picture_1;
    private ImageView picture_2;
    private Uri imageUri_1;
    private Uri imageUri_2;
    private Button btnQuarantinerSave = null;
    private SharedPreferences pref = null;
    private SharedPreferences.Editor prefEditor = null;
    private int characterFlags = 0b000000;
    private int flag_1=0;
    private int flag_2=0;

    private EditText worker_name=null;
    private EditText process_id=null;
    private EditText idno_processer=null;
    private EditText reg_time=null;
    private EditText contact_num=null;
    private EditText workplace=null;
    private EditText new_password=null;
    private EditText cur_password=null;
    private Button change_submit = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.processinf_change_layout);
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
        picture_1=(ImageView)findViewById(R.id.photo_1);
        picture_2=(ImageView)findViewById(R.id.photo_2);

//        Button photo_1=findViewById(R.id.photo_processinfchange_1);
//        photo_1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                flag_1=1;
//                File outputImage = new File(Environment.getExternalStorageDirectory(), "/DCIM/Camera/"+System.currentTimeMillis() + ".jpg");
//                if (!outputImage.getParentFile().exists()){
//                    outputImage.getParentFile().mkdirs();
//                }
//                if (Build.VERSION.SDK_INT >= 24) {
//                    imageUri_1 = FileProvider.getUriForFile(process_inf_changeActivity.this, "com.example.helloworld.sell", outputImage);
//                } else {
//                    imageUri_1 = Uri.fromFile(outputImage);
//                }
//                Intent intent = new Intent();
//                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri_1);
//                startActivityForResult(intent, TAKE_PHOTO);
//
//            }
////
////                Intent intent = new Intent(sellerinf_changeActivity.this, CaptureActivity.class);
////
////                startActivityForResult(intent,0);}
//
//
//        });

        Button photo_2=findViewById(R.id.photo_processinfchange_2);
        photo_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag_1=1;
                File outputImage = new File(Environment.getExternalStorageDirectory(), "/DCIM/Camera/"+System.currentTimeMillis() + ".jpg");
                if (!outputImage.getParentFile().exists()){
                    outputImage.getParentFile().mkdirs();
                }
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri_2 = FileProvider.getUriForFile(process_inf_changeActivity.this, "com.example.helloworld.sell", outputImage);
                } else {
                    imageUri_2 = Uri.fromFile(outputImage);
                }
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri_2);
                startActivityForResult(intent, TAKE_PHOTO);

            }
//
//                Intent intent = new Intent(sellerinf_changeActivity.this, CaptureActivity.class);
//
//                startActivityForResult(intent,0);}


        });
        change_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "提交修改");
                processinf_change();
            }
        });


    }


    private void initUI() {
        worker_name=findViewById(R.id.worker_name);
        process_id=findViewById(R.id.process_id);
        idno_processer=findViewById(R.id.idno_processer);
        reg_time=findViewById(R.id.reg_time);
        contact_num=findViewById(R.id.contact_num);
        workplace=findViewById(R.id.workplace_id);
        new_password=findViewById(R.id.new_password);
        change_submit = findViewById(R.id.submit_processerchange);
        cur_password=findViewById(R.id.cur_password);
        get_inf();


    }

    private void get_inf(){
        HttpUtil.sendOKHttp3RequestGET("http://223.3.74.248:8000/process/processor_inquiry?ConsumerID="+"2",

                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: " + e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String resStr = response.body().string();
                        worker_name=findViewById(R.id.worker_name);
                        process_id=findViewById(R.id.process_id);
                        idno_processer=findViewById(R.id.idno_processer);
                        reg_time=findViewById(R.id.reg_time);
                        contact_num=findViewById(R.id.contact_num);
                        workplace=findViewById(R.id.workplace_id);
                        cur_password=findViewById(R.id.cur_password);
                        try{
                            JSONObject jsonObjec=new JSONObject(resStr);
                            worker_name.setText(jsonObjec.getString("ConsumerName"));
                            process_id.setText(jsonObjec.getString("ConsumerID"));
                            idno_processer.setText(jsonObjec.getString("IDNo"));
                            reg_time.setText(jsonObjec.getString("RegisterTimeConsumer"));
                            contact_num.setText(jsonObjec.getString("ContactNo"));
                            workplace.setText(jsonObjec.getString("WorkPlaceID"));

                                cur_password.setText(jsonObjec.getString("Password"));

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
                                    Toast.makeText(process_inf_changeActivity.this, ""+resStr, Toast.LENGTH_SHORT).show();
                                }
                            }));
//                            e.printStackTrace();
                        }
                    }
                }




        );


    }

    private void processinf_change() {
        String getname = worker_name.getText().toString();
        String getworkid = process_id.getText().toString();
        String getidno = idno_processer.getText().toString();
        String getintime = reg_time.getText().toString();
        String getphone = contact_num.getText().toString();
        String getplace = workplace.getText().toString();

        String getnewpassword = new_password.getText().toString();

        Log.d(TAG, "processerinf_change: ");
        //http://223.3.72.161/register??characterFlag=1
        HttpUtil.sendOKHttp3RequestPOST("http://223.3.74.248:8000/process/processor_update/",
                JsonUtil.getJSON(

                        "ConsumerId", "2",
                "ConsumerName", getname,
                "ContactNo", getphone,
                "Password", getnewpassword,
                "IDNo", getidno,
                "WorkPlaceID", getplace,
                "PhotoSrc", "23132",
                "HC4foodCertificationNo", 1,
                "HC4foodCertificationSrc", "1"






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
                                    Toast.makeText(process_inf_changeActivity.this, ""+resStr, Toast.LENGTH_SHORT).show();
                                }
                            }));
//                            e.printStackTrace();
                        }
                    }
                }

        );


    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK){
                    try {//显示照片
                        if(flag_1==1) {
                            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri_1));
                            picture_1.setImageBitmap(bitmap);
                        }
                        if(flag_2==1) {
                            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri_2));
                            picture_2.setImageBitmap(bitmap);
                        }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }


    }


}
