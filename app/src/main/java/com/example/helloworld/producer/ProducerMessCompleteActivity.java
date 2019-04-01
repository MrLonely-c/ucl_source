package com.example.helloworld.producer;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DialogTitle;
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
import com.example.helloworld.loginAndSign.LoginAndSignActivity;
import com.example.helloworld.loginAndSign.ResponsibleChooseActivity;
import com.example.helloworld.process.ProcesserMessCompleteActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProducerMessCompleteActivity extends AppCompatActivity
        implements View.OnClickListener {
    private static final String TAG = "tigercheng";

    private static final int PHOTO_IDCARD = 1;
    private static final int PHOTO_CERTIFICATES = 2;
    private static final int ALBUM_IDCARD = 3;
    private static final int ALBUM_CERTIFICATES = 4;

    private Button btnProducerSave = null;

    private Intent intent = null;
    private SharedPreferences pref = null;
    private SharedPreferences.Editor prefEditor = null;

    private int characterFlags = 0b000000;

    private Uri IDCardUri;
    private File IDCardFile;
    private Uri CertificatesUri;
    private File CertificatesFile;

    private EditText etxIDCardNo = null;
    private EditText etxEmployerName = null;

    private Button btnPhotoIDCard = null;
    private Button btnAlbumIDCard = null;
    private Button btnPhotoCertificates = null;
    private Button btnAlbumCertificates = null;
    private ImageView ivIDCard = null;
    private ImageView ivCertificates = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "ProducerMessCompleteActivity onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_producer_mess_complete);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initUI();

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        characterFlags = pref.getInt("characterFlags", 0b000000);
    }

    private void initUI() {
        btnProducerSave = findViewById(R.id.btnProducerSave);
        btnProducerSave.setOnClickListener(this);

        btnPhotoIDCard = findViewById(R.id.btnPhotoIDCard);
        btnPhotoIDCard.setOnClickListener(this);

        btnAlbumIDCard = findViewById(R.id.btnAlbumIDCard);
        btnAlbumIDCard.setOnClickListener(this);

        btnPhotoCertificates = findViewById(R.id.btnPhotoCertificates);
        btnPhotoCertificates.setOnClickListener(this);

        btnAlbumCertificates = findViewById(R.id.btnAlbumCertificates);
        btnAlbumCertificates.setOnClickListener(this);

        ivIDCard = findViewById(R.id.ivIDCard);
        ivCertificates = findViewById(R.id.ivCertificates);

        etxIDCardNo = findViewById(R.id.etxIDCardNo);
        etxEmployerName = findViewById(R.id.etxEmployerName);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPhotoIDCard:
//                Log.d(TAG, "onClick: " + getExternalCacheDir());
                try {
                    BaseUtil.takeAPhoto(this, String.valueOf(getExternalCacheDir()), new TCCallbackListener() {
                        @Override
                        public void jump(Uri uri, File file, int requestCode) {
                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            IDCardUri = uri;
                            IDCardFile = file;
                            startActivityForResult(intent, requestCode);
                        }
                    }, PHOTO_IDCARD);
                } catch (Exception e) {
                    Log.d(TAG, "onClick: " + e);
                }
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

            case R.id.btnPhotoCertificates:

                BaseUtil.takeAPhoto(this, String.valueOf(getExternalCacheDir()), new TCCallbackListener() {
                    @Override
                    public void jump(Uri uri, File file, int requestCode) {
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        CertificatesUri = uri;
                        CertificatesFile = file;
                        startActivityForResult(intent, requestCode);
                    }
                }, PHOTO_CERTIFICATES);
                break;

            case R.id.btnAlbumCertificates:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    startActivityForResult(intent, ALBUM_CERTIFICATES);
                }
                break;

            case R.id.btnProducerSave:
                characterFlags = characterFlags | 0b100000;
                Log.d(TAG, "characterFlags: " + characterFlags);

                Log.d(TAG, "id: " + pref.getString("id", "id"));


                HttpUtil.sendOKHttp3RequestPOST(
                        HttpUtil.BASEURL_LOGIN_SIGN_PRODUCE + "/user/fulfil?CharacterFlag=0",
                        JsonUtil.getJSON(
                                "ConsumerId", pref.getString("id", "id"),
                                "IDNo", etxIDCardNo.getText().toString(),
                                "CompanyName", etxEmployerName.getText().toString()
                        ), new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.d(TAG, "onFailure: " + e);
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                final String resStr = response.body().string();
                                Log.d(TAG, "response.code: " + response.code());
                                Log.d(TAG, "生产者信息完善resStr: " + resStr);
                                try {
                                    JSONObject resJson = null;
                                    resJson = new JSONObject(resStr);
                                    Log.d(TAG, "resJson: " + resJson.toString());
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            btnProducerSave.setClickable(false);
                                            btnProducerSave.setText("正在提交");
                                            btnProducerSave.setBackgroundResource(R.drawable.btn_bg_unclicked);
                                        }
                                    });
                                    HttpUtil.sendOKHttpMultipartRequestPOST(
                                            HttpUtil.BASEURL_LOGIN_SIGN_PRODUCE + "/user/img_fulfil",
                                            new MultipartBody.Builder("AaB03x")
                                                    .setType(MultipartBody.FORM)
                                                    .addFormDataPart(
                                                            "imgID", "imgID.jpg",
                                                            RequestBody.create(MediaType.parse("image/*"),
                                                                    IDCardFile))
                                                    .addFormDataPart(
                                                            "imgwork", "imgwork.jpg",
                                                            RequestBody.create(MediaType.parse("image/*"),
                                                                    CertificatesFile))
                                                    .addFormDataPart("ConsumerId", pref.getString("id", "id"))
                                                    .addFormDataPart("CharacterFlag", "0")
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

                                                        intent = new Intent(
                                                                ProducerMessCompleteActivity.this,
                                                                productionStateActivity.class);
                                                        startActivity(intent);
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                btnProducerSave.setClickable(true);
                                                                btnProducerSave.setText("提交保存");
                                                                btnProducerSave.setBackgroundResource(R.drawable.btn_selector);
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
                                            Toast.makeText(ProducerMessCompleteActivity.this, resStr, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    e.printStackTrace();
                                }
                            }
                        }
                );
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PHOTO_IDCARD:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(IDCardUri));
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
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(data.getData()));
                        ivIDCard.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相册调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case PHOTO_CERTIFICATES:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(CertificatesUri));
                        ivCertificates.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相机调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case ALBUM_CERTIFICATES:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(data.getData()));
                        ivCertificates.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相册调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;
        }

    }


}
