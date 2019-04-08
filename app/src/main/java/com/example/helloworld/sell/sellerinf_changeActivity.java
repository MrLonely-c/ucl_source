package com.example.helloworld.sell;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
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
import com.example.helloworld.producer.OutActivity;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class sellerinf_changeActivity extends AppCompatActivity  {
    private static final String TAG = "tigercheng";
    public static final int TAKE_PHOTO = 1;
    private EditText seller_name=null;
    private EditText seller_id=null;
    private EditText idno=null;
    private EditText in_time=null;
    private EditText contact_num=null;
    private EditText workplace=null;
    private EditText new_password=null;
    private EditText cur_password=null;
    private Button change_submit = null;
    private ImageView picture;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sellerinf_change_layout);
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
        Button photo=findViewById(R.id.photo_seller_2);
        picture=(ImageView)findViewById(R.id.work_id);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File outputImage = new File(Environment.getExternalStorageDirectory(), "/DCIM/Camera/"+System.currentTimeMillis() + ".jpg");
                if (!outputImage.getParentFile().exists()){
                    outputImage.getParentFile().mkdirs();
                }
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(sellerinf_changeActivity.this, "com.example.helloworld.sell", outputImage);
                } else {
                    imageUri = Uri.fromFile(outputImage);
                }
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);
            }
//
//                Intent intent = new Intent(sellerinf_changeActivity.this, CaptureActivity.class);
//
//                startActivityForResult(intent,0);

        });

        change_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "提交修改");
                sellerinf_change();
            }
        });

    }
    private void initUI() {
         seller_name=findViewById(R.id.sell_name);
        seller_id=findViewById(R.id.sell_id);
        idno=findViewById(R.id.idno_seller);
        in_time=findViewById(R.id.intime);
       contact_num=findViewById(R.id.phonenum);
        workplace=findViewById(R.id.place);
         new_password=findViewById(R.id.new_pass);
        change_submit = findViewById(R.id.submit_sellerchange);
        get_inf();


    }

    private void get_inf(){
        HttpUtil.sendOKHttp3RequestGET("http://223.3.79.119:8000/sell/inquiry_seller/?ConsumerId=3200000002",

                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: " + e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String resStr = response.body().string();
                        seller_name=findViewById(R.id.sell_name);
                        seller_id=findViewById(R.id.sell_id);
                        idno=findViewById(R.id.idno_seller);
                        in_time=findViewById(R.id.intime);
                        contact_num=findViewById(R.id.phonenum);
                        workplace=findViewById(R.id.place);
                        new_password=findViewById(R.id.new_pass);
                        cur_password=findViewById(R.id.cur_password);

                        try{
                            JSONObject jsonObjec=new JSONObject(resStr);
                            seller_name.setText(jsonObjec.getString("ConsumerName"));
                            seller_id.setText(jsonObjec.getString("ConsumerId"));
                            idno.setText(jsonObjec.getString("IDNo"));
                            in_time.setText(jsonObjec.getString("RegisterTimeConsumer"));
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
                                    Toast.makeText(sellerinf_changeActivity.this, ""+resStr, Toast.LENGTH_SHORT).show();
                                }
                            }));
//                            e.printStackTrace();
                        }
                    }
                }




        );


    }
    private void sellerinf_change() {
        String getname = seller_name.getText().toString();
        String getworkid = seller_id.getText().toString();
        String getidno = idno.getText().toString();
        String getintime = in_time.getText().toString();
        String getphone = contact_num.getText().toString();
        String getplace = workplace.getText().toString();
        String getnewpassword = new_password.getText().toString();
        Log.d(TAG, "sellerinf_change: ");
        //http://223.3.72.161/register??characterFlag=1
        HttpUtil.sendOKHttp3RequestPOST("http://223.3.79.119:8000/sell/alter_seller/",
                JsonUtil.getJSON(

                        "ConsumerId", "3200000002",
                "ConsumerName", "销售员01",
                "ContactNo","186XXXXXXXX",
                "RegisterTimeConsumer", "2019-01-01",
                "SearchCounts", 0,
                "VIP", 0,
                "Password", "123456",
                "CharacterFlag", 1,
                "IDNo", "3210XXXXXXXXXXXXXX",
                "RegisterTime", "2019-01-01",
                "WorkPlaceID", "123XXXXXXXX",
                "PhotoSrc", "XXXXXXXXX"









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
                                    Toast.makeText(sellerinf_changeActivity.this, ""+resStr, Toast.LENGTH_SHORT).show();
                                }
                            }));
//                            e.printStackTrace();
                        }
                    }
                }

        );


    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
        switch (requestCode){
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK){
                    try {//显示照片
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(bitmap);
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
