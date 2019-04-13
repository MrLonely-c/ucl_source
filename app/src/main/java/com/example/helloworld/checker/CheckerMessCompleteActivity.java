package com.example.helloworld.checker;

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

public class CheckerMessCompleteActivity extends AppCompatActivity
        implements View.OnClickListener {

    private static final String TAG = "tigercheng";

    private static final int PHOTO_IDCARD = 1;
    private static final int PHOTO_CERTIFICATES = 2;
    private static final int ALBUM_IDCARD = 3;
    private static final int ALBUM_CERTIFICATES = 4;
    private static final int PHOTO_SIGNINGVETERINARYCERTIFICATE = 5;
    private static final int ALBUM_SIGNINGVETERINARYCERTIFICATE = 6;
    private static final int PHOTO_LICENSEDVETERINARYQC = 7;
    private static final int ALBUM_LICENSEDVETERINARYQC = 8;

    private Intent intent = null;

    private Button btn_photo_idcard2 = null;
    private Button btn_album_idcard2 = null;
    private Button btn_photo_certificates2 = null;
    private Button btn_album_certificates2 = null;
    private Button btn_photo_signingveterinarycertificate2 = null;
    private Button btn_album_signingveterinarycertificate2 = null;
    private Button btn_photo_licensedveterinaryqc2 = null;
    private Button btn_album_licensedveterinaryqc2 = null;
    private Button btnQuarantinerSave = null;

    private ImageView iv_idcard2 = null;
    private ImageView iv_certificates2 = null;
    private ImageView iv_signingveterinarycertificate2 = null;
    private ImageView iv_licensedveterinaryqc2 = null;

    private Uri idcardUri = null;
    private Uri certificatesUri = null;
    private Uri signingveterinarycertificateUri = null;
    private Uri licensedveterinaryqcUri = null;

    private File idcardFile = null;
    private File certificatesFile = null;
    private File signingveterinarycertificateFile = null;
    private File licensedveterinaryqcFile = null;

    private EditText etxIDCardNo = null;
    private EditText etxCompanyName = null;

    private SharedPreferences pref = null;
    private SharedPreferences.Editor prefEditor = null;
    private int characterFlags = 0b000000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_quarantiner_mess_complete);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initUI();

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        characterFlags = pref.getInt("characterFlags", 0b000000);
    }

    private void initUI() {
        Log.d(TAG, "initUI: CheckerMessCompleteActivity");
        intent = getIntent();

        iv_idcard2 = findViewById(R.id.iv_idcard2);
        iv_certificates2 = findViewById(R.id.iv_certificates2);
        iv_signingveterinarycertificate2 = findViewById(R.id.iv_signingveterinarycertificate2);
        iv_licensedveterinaryqc2 = findViewById(R.id.iv_licensedveterinaryqc2);

        btn_photo_idcard2 = findViewById(R.id.btn_photo_idcard2);
        btn_photo_idcard2.setOnClickListener(this);
        btn_album_idcard2 = findViewById(R.id.btn_album_idcard2);
        btn_album_idcard2.setOnClickListener(this);
        btn_photo_certificates2 = findViewById(R.id.btn_photo_certificates2);
        btn_photo_certificates2.setOnClickListener(this);
        btn_album_certificates2 = findViewById(R.id.btn_album_certificates2);
        btn_album_certificates2.setOnClickListener(this);
        btn_photo_signingveterinarycertificate2 = findViewById(R.id.btn_photo_signingveterinarycertificate2);
        btn_photo_signingveterinarycertificate2.setOnClickListener(this);
        btn_album_signingveterinarycertificate2 = findViewById(R.id.btn_album_signingveterinarycertificate2);
        btn_album_signingveterinarycertificate2.setOnClickListener(this);
        btn_photo_licensedveterinaryqc2 = findViewById(R.id.btn_photo_licensedveterinaryqc2);
        btn_photo_licensedveterinaryqc2.setOnClickListener(this);
        btn_album_licensedveterinaryqc2 = findViewById(R.id.btn_album_licensedveterinaryqc2);
        btn_album_licensedveterinaryqc2.setOnClickListener(this);

        btnQuarantinerSave = findViewById(R.id.btnQuarantinerSave);
        btnQuarantinerSave.setOnClickListener(this);

        etxIDCardNo = findViewById(R.id.etxIDCardNo);
        etxCompanyName = findViewById(R.id.etxCompanyName);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_photo_idcard2:
                Log.d(TAG, "btn_photo_idcard2: "+btn_photo_idcard2);
                BaseUtil.takeAPhoto(this, String.valueOf(getExternalCacheDir()), new TCCallbackListener() {
                    @Override
                    public void jump(Uri uri, File file, int requestCode) {
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        idcardUri = uri;
                        idcardFile = file;
                        startActivityForResult(intent, requestCode);
                    }
                }, PHOTO_IDCARD);
                break;

            case R.id.btn_photo_certificates2:
                BaseUtil.takeAPhoto(this, String.valueOf(getExternalCacheDir()), new TCCallbackListener() {
                    @Override
                    public void jump(Uri uri, File file, int requestCode) {
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        certificatesUri = uri;
                        certificatesFile = file;
                        startActivityForResult(intent, requestCode);
                    }
                }, PHOTO_CERTIFICATES);
                break;

            case R.id.btn_photo_signingveterinarycertificate2:
                BaseUtil.takeAPhoto(this, String.valueOf(getExternalCacheDir()), new TCCallbackListener() {
                    @Override
                    public void jump(Uri uri, File file, int requestCode) {
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        signingveterinarycertificateUri = uri;
                        signingveterinarycertificateFile = file;
                        startActivityForResult(intent, requestCode);
                    }
                }, PHOTO_SIGNINGVETERINARYCERTIFICATE);
                break;

            case R.id.btn_photo_licensedveterinaryqc2:
                BaseUtil.takeAPhoto(this, String.valueOf(getExternalCacheDir()), new TCCallbackListener() {
                    @Override
                    public void jump(Uri uri, File file, int requestCode) {
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        licensedveterinaryqcUri = uri;

                        licensedveterinaryqcFile = file;
                        startActivityForResult(intent, requestCode);
                    }
                }, PHOTO_LICENSEDVETERINARYQC);
                break;

            case R.id.btn_album_idcard2:
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

            case R.id.btn_album_certificates2:
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

            case R.id.btn_album_signingveterinarycertificate2:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    startActivityForResult(intent, ALBUM_SIGNINGVETERINARYCERTIFICATE);
                }
                break;

            case R.id.btn_album_licensedveterinaryqc2:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    startActivityForResult(intent, ALBUM_LICENSEDVETERINARYQC);
                }
                break;

            case R.id.btnQuarantinerSave:
                HttpUtil.sendOKHttp3RequestPOST(
                        HttpUtil.BASEURL_LOGIN_SIGN_PRODUCE + "/user/fulfil?CharacterFlag=1",
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
                                Log.d(TAG, "Quarantiner信息完善resStr: " + resStr);
                                try {
                                    JSONObject resJson = null;
                                    resJson = new JSONObject(resStr);
                                    Log.d(TAG, "resJson: " + resJson.toString());
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            btnQuarantinerSave.setClickable(false);
                                            btnQuarantinerSave.setText("正在提交");
                                            btnQuarantinerSave.setBackgroundResource(R.drawable.btn_bg_unclicked);
                                        }
                                    });
                                    HttpUtil.sendOKHttpMultipartRequestPOST(
                                            HttpUtil.BASEURL_LOGIN_SIGN_PRODUCE + "/user/img_fulfil",
                                            new MultipartBody.Builder("AaB03x")
//                                    MultipartBody body = new MultipartBody.Builder("")
                                                    .setType(MultipartBody.FORM)
                                                    .addFormDataPart("imgID", "idcardFile.jpg", RequestBody.create(
                                                            MediaType.parse("image/*"),
                                                            idcardFile))
                                                    .addFormDataPart("imgwork", "certificatesFile.jpg", RequestBody.create(
                                                            MediaType.parse("image/*"),
                                                            certificatesFile))
                                                    .addFormDataPart("imgquality1", "signingveterinarycertificateFile.jpg", RequestBody.create(
                                                            MediaType.parse("image/*"),
                                                            signingveterinarycertificateFile))
                                                    .addFormDataPart("imgquality2", "licensedveterinaryqcFile.jpg", RequestBody.create(
                                                            MediaType.parse("image/*"),
                                                            licensedveterinaryqcFile))
                                                    .addFormDataPart("ConsumerId", pref.getString("id", "id"))
                                                    .addFormDataPart("CharacterFlag", "1")
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
                                                        characterFlags = characterFlags | 0b010000;
                                                        prefEditor.putInt("characterFlags", characterFlags);
                                                        prefEditor.apply();

                                                        intent = new Intent(CheckerMessCompleteActivity.this, checkerActivity.class);
                                                        intent.putExtra("title", "检疫结果录入");
                                                        startActivity(intent);
                                                        finish();
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                btnQuarantinerSave.setClickable(true);
                                                                btnQuarantinerSave.setText("提交保存");
                                                                btnQuarantinerSave.setBackgroundResource(R.drawable.btn_selector);
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
                                            Toast.makeText(CheckerMessCompleteActivity.this, resStr, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    e.printStackTrace();
                                }
                            }
                        }
                );

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
                                getContentResolver().openInputStream(idcardUri));
                        iv_idcard2.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相机调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case PHOTO_CERTIFICATES:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(certificatesUri));
                        iv_certificates2.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相机调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case PHOTO_SIGNINGVETERINARYCERTIFICATE:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(signingveterinarycertificateUri));
                        iv_signingveterinarycertificate2.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相机调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case PHOTO_LICENSEDVETERINARYQC:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(licensedveterinaryqcUri));
                        iv_licensedveterinaryqc2.setImageBitmap(bitmap);
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
                        iv_idcard2.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相册调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case ALBUM_CERTIFICATES:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(data.getData()));
                        iv_certificates2.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相册调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case ALBUM_SIGNINGVETERINARYCERTIFICATE:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(data.getData()));
                        iv_signingveterinarycertificate2.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相册调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case ALBUM_LICENSEDVETERINARYQC:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(data.getData()));
                        iv_licensedveterinaryqc2.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相册调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;
        }

    }

}
