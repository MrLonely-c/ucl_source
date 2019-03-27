package com.example.helloworld.checker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.BaseUtil;
import com.example.helloworld.R;
import com.example.helloworld.sell.sellerinf_changeActivity;

import java.io.File;
import java.io.FileNotFoundException;

public class CheckerMessCompleteActivity extends AppCompatActivity
        implements View.OnClickListener {
    private Intent intent = null;
    public static final int TAKE_PHOTO = 1;
    private ImageView picture;
    private Uri imageUri;
    private Button btnQuarantinerSave = null;
    private SharedPreferences pref = null;
    private SharedPreferences.Editor prefEditor = null;
    private int characterFlags = 0b000000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_quarantiner_mess_complete);
        TextView btnback=findViewById(R.id.toolbar_left_tv);
        btnback.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                onBackPressed();
            }
        });
        Button photo=findViewById(R.id.button14);
        picture=(ImageView)findViewById(R.id.work_id);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File outputImage = new File(Environment.getExternalStorageDirectory(), "/DCIM/Camera/"+System.currentTimeMillis() + ".jpg");
                if (!outputImage.getParentFile().exists()){
                    outputImage.getParentFile().mkdirs();
                }
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(CheckerMessCompleteActivity.this, "com.example.helloworld.sell", outputImage);
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
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        characterFlags = pref.getInt("characterFlags", 0b000000);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initUI();





    }

    private void initUI() {
        intent = getIntent();



        btnQuarantinerSave = findViewById(R.id.btnQuarantinerSave);
        btnQuarantinerSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnQuarantinerSave:

                prefEditor = pref.edit();
                characterFlags = characterFlags | 0b010000;
                prefEditor.putInt("characterFlags", characterFlags);
                prefEditor.apply();

                if (BaseUtil.isCompleted(pref.getInt("characterFlags", 0b000000), 2)) {
                    Toast.makeText(this, "信息注册成功", Toast.LENGTH_SHORT).show();
                    intent = new Intent(CheckerMessCompleteActivity.this, CheckerActivity.class);
                    intent.putExtra("title", "检疫结果录入");
                    startActivity(intent);
                    finish();
                }
                break;

        }
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
