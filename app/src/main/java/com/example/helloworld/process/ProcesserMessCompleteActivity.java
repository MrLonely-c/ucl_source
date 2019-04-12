package com.example.helloworld.process;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.BaseUtil;
import com.example.helloworld.HttpUtil;
import com.example.helloworld.JsonUtil;
import com.example.helloworld.R;
import com.example.helloworld.TCCallbackListener;
import com.example.helloworld.process.ProcessActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProcesserMessCompleteActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "tigercheng";

    private static final int PHOTO_IDCARD = 1;
    private static final int ALBUM_IDCARD = 2;
    private static final int PHOTO_CERTIFICATE1 = 3;
    private static final int ALBUM_CERTIFICATE1 = 4;
    private static final int PHOTO_CERTIFICATE2 = 5;
    private static final int ALBUM_CERTIFICATE2 = 6;

    private Intent intent = null;

    private Button btnProcesserSave = null;
    private SharedPreferences pref = null;
    private SharedPreferences.Editor prefEditor = null;

    private int characterFlags = 0b000000;

    private EditText etxIDCardNo = null;
    private EditText etxCompanyName = null;

    private ImageView ivIDCard = null;
    private Uri idCardUri = null;
    private File idCardFile = null;
    private Button btnPhotoIDCard = null;
    private Button btnAlbumIDCard = null;

    private ImageView ivCertificate1 = null;
    private Uri certificateUri1 = null;
    private File certificateFile1 = null;
    private Button btnPhotoCertificate1 = null;
    private Button btnAlbumCertificate1 = null;

    private ImageView ivCertificate2 = null;
    private Uri certificateUri2 = null;
    private File certificateFile2 = null;
    private Button btnPhotoCertificate2 = null;
    private Button btnAlbumCertificate2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_processer_mess_complete);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initUI();

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        characterFlags = pref.getInt("characterFlags", 0b000000);
    }

    private void initUI() {

        btnProcesserSave = findViewById(R.id.btn_processerSave);
        btnProcesserSave.setOnClickListener(this);

        etxIDCardNo = findViewById(R.id.etxIDCardNo);
        etxCompanyName = findViewById(R.id.etxCompanyName);

        ivIDCard = findViewById(R.id.ivIDCard);
        btnPhotoIDCard = findViewById(R.id.btnPhotoIDCard);
        btnPhotoIDCard.setOnClickListener(this);
        btnAlbumIDCard = findViewById(R.id.btnAlbumIDCard);
        btnAlbumIDCard.setOnClickListener(this);

        ivCertificate1 = findViewById(R.id.ivCertificate1);
        btnPhotoCertificate1 = findViewById(R.id.btnPhotoCertificate1);
        btnPhotoCertificate1.setOnClickListener(this);
        btnAlbumCertificate1 = findViewById(R.id.btnAlbumCertificate1);
        btnAlbumCertificate1.setOnClickListener(this);

        ivCertificate2 = findViewById(R.id.ivCertificate2);
        btnPhotoCertificate2 = findViewById(R.id.btnPhotoCertificate2);
        btnPhotoCertificate2.setOnClickListener(this);
        btnAlbumCertificate2 = findViewById(R.id.btnAlbumCertificate2);
        btnAlbumCertificate2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_processerSave:
                HttpUtil.sendOKHttp3RequestPOST(
                        HttpUtil.BASEURL_LOGIN_SIGN_PRODUCE + "/user/fulfil?CharacterFlag=2",
                        JsonUtil.getJSON(
                                "ConsumerId", pref.getString("id", "id"),
                                "IDNo", etxIDCardNo.getText().toString(),
                                "CompanyName", etxCompanyName.getText().toString()
                        ), new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.d(TAG, "onFailure: " + e);
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                final String resStr = response.body().string();
                                Log.d(TAG, "response.code: " + response.code());
                                Log.d(TAG, "processer信息完善resStr: " + resStr);
                                try {
                                    JSONObject resJson = null;
                                    resJson = new JSONObject(resStr);
                                    Log.d(TAG, "resJson: " + resJson.toString());
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            btnProcesserSave.setClickable(false);
                                            btnProcesserSave.setText("正在提交");
                                            btnProcesserSave.setBackgroundResource(R.drawable.btn_bg_unclicked);
                                        }
                                    });
                                    HttpUtil.sendOKHttpMultipartRequestPOST(
                                            HttpUtil.BASEURL_LOGIN_SIGN_PRODUCE + "/user/img_fulfil",
                                            new MultipartBody.Builder("AaB03x")
//                                    MultipartBody body = new MultipartBody.Builder("")
                                                    .setType(MultipartBody.FORM)
                                                    .addFormDataPart("imgID", "idcardFile.jpg", RequestBody.create(
                                                            MediaType.parse("image/*"),
                                                            idCardFile))
                                                    .addFormDataPart("imgwork", "certificateFile1.jpg", RequestBody.create(
                                                            MediaType.parse("image/*"),
                                                            certificateFile1))
                                                    .addFormDataPart("imgquality", "certificateFile2.jpg", RequestBody.create(
                                                            MediaType.parse("image/*"),
                                                            certificateFile2))
                                                    .addFormDataPart("ConsumerId", pref.getString("id", "id"))
                                                    .addFormDataPart("CharacterFlag", "2")
                                                    .build(),
                                            new Callback() {
                                                @Override
                                                public void onFailure(Call call, IOException e) {
                                                    Log.d(TAG, "onFailure: " + e.getMessage());
                                                }

                                                @Override
                                                public void onResponse(Call call, Response response) throws IOException {
                                                    String resStr = response.body().string();
                                                    Log.d(TAG, "protocol:" + response.protocol() + " code:" + response.code() + " message:" + response.message());

                                                    Log.d(TAG, "onResponse: " + resStr);
                                                    if (response.code() == 200) {

                                                        prefEditor = pref.edit();
                                                        characterFlags = characterFlags | 0b001000;
                                                        prefEditor.putInt("characterFlags", characterFlags);
                                                        prefEditor.apply();

                                                        intent = new Intent(ProcesserMessCompleteActivity.this, ProcessActivity.class);
                                                        intent.putExtra("title", "检疫结果录入");
                                                        startActivity(intent);
                                                        finish();
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                btnProcesserSave.setClickable(true);
                                                                btnProcesserSave.setText("提交保存");
                                                                btnProcesserSave.setBackgroundResource(R.drawable.btn_selector);
                                                            }
                                                        });
                                                        finish();
                                                    }
                                                }
                                            }
                                    );
                                } catch (JSONException e) {
                                    Log.d(TAG, "JSONException: " + e.toString());
//                                etxContact.setText("");
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ProcesserMessCompleteActivity.this, resStr, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    e.printStackTrace();
                                }
                            }
                        }
                );

                break;

            case R.id.btnPhotoIDCard:
                BaseUtil.takeAPhoto(this, String.valueOf(getExternalCacheDir()), new TCCallbackListener() {
                    @Override
                    public void jump(Uri uri, File file, int requestCode) {
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        idCardUri = uri;
                        idCardFile = file;
                        startActivityForResult(intent, requestCode);
                    }
                }, PHOTO_IDCARD);
                break;

            case R.id.btnAlbumIDCard:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    startActivityForResult(intent, ALBUM_IDCARD);
                }
                break;

            case R.id.btnPhotoCertificate1:
                BaseUtil.takeAPhoto(this, String.valueOf(getExternalCacheDir()), new TCCallbackListener() {
                    @Override
                    public void jump(Uri uri, File file, int requestCode) {
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        certificateUri1 = uri;
                        certificateFile1 = file;
                        startActivityForResult(intent, requestCode);
                    }
                }, PHOTO_CERTIFICATE1);
                break;

            case R.id.btnAlbumCertificate1:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    startActivityForResult(intent, ALBUM_CERTIFICATE1);
                }
                break;

            case R.id.btnPhotoCertificate2:
                BaseUtil.takeAPhoto(this, String.valueOf(getExternalCacheDir()), new TCCallbackListener() {
                    @Override
                    public void jump(Uri uri, File file, int requestCode) {
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        certificateUri2 = uri;
                        certificateFile2 = file;
                        startActivityForResult(intent, requestCode);
                    }
                }, PHOTO_CERTIFICATE2);
                break;
            case R.id.btnAlbumCertificate2:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    startActivityForResult(intent, ALBUM_CERTIFICATE2);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case PHOTO_IDCARD:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(idCardUri));
                        ivIDCard.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相机调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case ALBUM_IDCARD:
                if (resultCode == RESULT_OK) {
                    try {
                        idCardFile = new File(BaseUtil.getAlbumImagePath(this, data));
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(data.getData()));
                        ivIDCard.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相册调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case PHOTO_CERTIFICATE1:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(certificateUri1));
                        ivCertificate1.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相机调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case ALBUM_CERTIFICATE1:
                if (resultCode == RESULT_OK) {
                    try {
                        certificateFile1 = new File(BaseUtil.getAlbumImagePath(this, data));
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(data.getData()));
                        ivCertificate1.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相册调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case PHOTO_CERTIFICATE2:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(certificateUri2));
                        ivCertificate2.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相机调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case ALBUM_CERTIFICATE2:
                if (resultCode == RESULT_OK) {
                    try {
                        certificateFile2 = new File(BaseUtil.getAlbumImagePath(this, data));
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(data.getData()));
                        ivCertificate2.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相册调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
