package com.example.helloworld.transporter;

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
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.helloworld.R;
import com.example.helloworld.sell.marketinf_changeActivity;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import java.io.File;
import java.io.FileNotFoundException;


public class transporterinf_changeActivity extends AppCompatActivity {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.transporter_inf_change);
        TextView btnback=findViewById(R.id.toolbar_left_tv);
        btnback.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                onBackPressed();
            }
        });
        picture_1=(ImageView)findViewById(R.id.photo_1);
        picture_2=(ImageView)findViewById(R.id.photo_2);

        Button photo_2=findViewById(R.id.photo_transporterchange_1);
        photo_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag_1=1;
                File outputImage = new File(Environment.getExternalStorageDirectory(), "/DCIM/Camera/"+System.currentTimeMillis() + ".jpg");
                if (!outputImage.getParentFile().exists()){
                    outputImage.getParentFile().mkdirs();
                }
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri_1 = FileProvider.getUriForFile(transporterinf_changeActivity.this, "com.example.helloworld.sell", outputImage);
                } else {
                    imageUri_1 = Uri.fromFile(outputImage);
                }
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri_1);
                startActivityForResult(intent, TAKE_PHOTO);

            }
//
//                Intent intent = new Intent(sellerinf_changeActivity.this, CaptureActivity.class);
//
//                startActivityForResult(intent,0);}


        });

        Button photo_3=findViewById(R.id.photo_transporterchange_2);
        photo_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag_2=1;
                File outputImage = new File(Environment.getExternalStorageDirectory(), "/DCIM/Camera/"+System.currentTimeMillis() + ".jpg");
                if (!outputImage.getParentFile().exists()){
                    outputImage.getParentFile().mkdirs();
                }
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri_2 = FileProvider.getUriForFile(transporterinf_changeActivity.this, "com.example.helloworld.sell", outputImage);
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


    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // 扫描二维码/条码回传
//        if (requestCode == 0 && resultCode == RESULT_OK) {
//            if (data != null) {
//
//                String content = data.getStringExtra(Constant.CODED_CONTENT);
//                Toast.makeText(sellerinf_changeActivity.this,"扫描结果为;"+content,Toast.LENGTH_SHORT).show();
//            }
//        }
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
