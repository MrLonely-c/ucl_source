package com.example.helloworld.company;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.BaseUtil;
import com.example.helloworld.HttpUtil;
import com.example.helloworld.JsonUtil;
import com.example.helloworld.R;
import com.example.helloworld.TCCallbackListener;
import com.example.helloworld.loginAndSign.LoginAndSignActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.util.Arrays;

public class CompanySignupActivity extends AppCompatActivity
        implements View.OnClickListener {
    private static final String TAG = "tigercheng";

    private ArrayList<String> characterList = null;
    private Spinner spinner = null;
    private int currCharacter = 0;

    private LinearLayout characterBox = null;
    private Button btnCompanySignUp = null;

    private Intent intent = null;

    private static final int PRODUCER_CAMERA_PAPER1 = 1;
    private static final int PRODUCER_ALBUM_PAPER1 = 2;

    private static final int PRODUCER_CAMERA_PAPER2 = 3;
    private static final int PRODUCER_ALBUM_PAPER2 = 4;

    private static final int QUARANTINER_CAMERA_PAPER1 = 5;
    private static final int QUARANTINER_ALBUM_PAPER1 = 6;

    private static final int PROCESSER_CAMERA_PAPER1 = 7;
    private static final int PROCESSER_ALBUM_PAPER1 = 8;

    private static final int PROCESSER_CAMERA_PAPER2 = 9;
    private static final int PROCESSER_ALBUM_PAPER2 = 10;

    private static final int PROCESSER_CAMERA_PAPER3 = 11;
    private static final int PROCESSER_ALBUM_PAPER3 = 12;

    private static final int PROCESSER_CAMERA_PAPER4 = 13;
    private static final int PROCESSER_ALBUM_PAPER4 = 14;

    private static final int PROCESSER_CAMERA_PAPER5 = 15;
    private static final int PROCESSER_ALBUM_PAPER5 = 16;

    private static final int PROCESSER_CAMERA_PAPER6 = 17;
    private static final int PROCESSER_ALBUM_PAPER6 = 18;

    private static final int TRANSPOTER_CAMERA_PAPER1 = 19;
    private static final int TRANSPOTER_ALBUM_PAPER1 = 20;

    private static final int TRANSPOTER_CAMERA_PAPER2 = 21;
    private static final int TRANSPOTER_ALBUM_PAPER2 = 22;

    private static final int TRANSPOTER_CAMERA_PAPER3 = 23;
    private static final int TRANSPOTER_ALBUM_PAPER3 = 24;

    private static final int SELLER_CAMERA_PAPER1 = 25;
    private static final int SELLER_ALBUM_PAPER1 = 26;

    private static final int SELLER_CAMERA_PAPER2 = 27;
    private static final int SELLER_ALBUM_PAPER2 = 28;

    private static final int SELLER_CAMERA_PAPER3 = 29;
    private static final int SELLER_ALBUM_PAPER3 = 30;

    //    农场组件
    private EditText etxCompanyName1 = null;
    private EditText etxCorporate1 = null;
    private EditText etxCoporateIDCardNo1 = null;
    private EditText etxCompanyContactNo1 = null;
    private EditText etxCompanySignUpTime1 = null;
    private EditText etxLoginName1 = null;
    private EditText etxLoginPassword1 = null;
    private EditText etxReLoginPassword1 = null;
    private EditText etxTradeLocation1 = null;
    private ImageView ivProducerPaper1 = null;
    private Uri ProducerPaper1Uri = null;
    private File ProducerPaper1File = null;
    private Button btnProducerCamera1 = null;
    private Button btnProducerAlbum1 = null;
    private ImageView ivProducerPaper2 = null;
    private Uri ProducerPaper2Uri = null;
    private File ProducerPaper2File = null;
    private Button btnProducerCamera2 = null;
    private Button btnProducerAlbum2 = null;

    //检疫局
    private EditText etxCompanyName2 = null;
    private EditText etxTradeLocation2 = null;
    private EditText etxLoginName2 = null;
    private EditText etxLoginPassword2 = null;
    private EditText etxReLoginPassword2 = null;
    private ImageView ivQuarantinerPaper1 = null;
    private Uri QuarantinerPaper1Uri = null;
    private File QuarantinerPaper1File = null;
    private Button btnQuarantinerCamera1 = null;
    private Button btnQuarantinerAlbum1 = null;

    //加工厂
    private EditText etxCompanyName3 = null;
    private EditText etxCorporate3 = null;
    private EditText etxCorporateIDCardNo3 = null;
    private EditText etxCompanyContactNo3 = null;
    private EditText etxCompanySignUpTime3 = null;
    private EditText etxTradeLocation3 = null;
    private EditText etxLoginName3 = null;
    private EditText etxLoginPassword3 = null;
    private EditText etxReLoginPassword3 = null;
    private ImageView ivProcesserPaper1 = null;
    private Uri ProcesserPaper1Uri = null;
    private File ProcesserPaper1File = null;
    private Button btnProcesserCamera1 = null;
    private Button btnProcesserAlbum1 = null;
    private ImageView ivProcesserPaper2 = null;
    private Uri ProcesserPaper2Uri = null;
    private File ProcesserPaper2File = null;
    private Button btnProcesserCamera2 = null;
    private Button btnProcesserAlbum2 = null;
    private ImageView ivProcesserPaper3 = null;
    private Uri ProcesserPaper3Uri = null;
    private File ProcesserPaper3File = null;
    private Button btnProcesserCamera3 = null;
    private Button btnProcesserAlbum3 = null;
    private ImageView ivProcesserPaper4 = null;
    private Uri ProcesserPaper4Uri = null;
    private File ProcesserPaper4File = null;
    private Button btnProcesserCamera4 = null;
    private Button btnProcesserAlbum4 = null;
    private ImageView ivProcesserPaper5 = null;
    private Uri ProcesserPaper5Uri = null;
    private File ProcesserPaper5File = null;
    private Button btnProcesserCamera5 = null;
    private Button btnProcesserAlbum5 = null;
    private ImageView ivProcesserPaper6 = null;
    private Uri ProcesserPaper6Uri = null;
    private File ProcesserPaper6File = null;
    private Button btnProcesserCamera6 = null;
    private Button btnProcesserAlbum6 = null;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_company_signup);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initUI();


        characterList = new ArrayList<String>(
                Arrays.asList("农场", "检疫局", "加工厂", "运输公司 ", "销售点"));
//        list.add("1.苹果");
        /*
         * 第二个参数是显示的布局
         * 第三个参数是在布局显示的位置id
         * 第四个参数是将要显示的数据
         */
        ArrayAdapter spinnerAdapter =
                new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, characterList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CompanySignupActivity.this, characterList.get(position), Toast.LENGTH_SHORT).show();

                for (int _i = 0; _i < 5; _i++) {
                    characterBox.getChildAt(_i).setVisibility(View.GONE);
                }

                characterBox.getChildAt(position).setVisibility(View.VISIBLE);

                currCharacter = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initUI() {
        spinner = findViewById(R.id.spinner);
        characterBox = findViewById(R.id.ll_characterBox);
        btnCompanySignUp = findViewById(R.id.btnCompanySignUp);
        btnCompanySignUp.setOnClickListener(this);

//        农场
        etxCompanyName1 = findViewById(R.id.etxCompanyName1);
        etxCorporate1 = findViewById(R.id.etxCorporate1);
        etxCoporateIDCardNo1 = findViewById(R.id.etxCoporateIDCardNo1);
        etxCompanyContactNo1 = findViewById(R.id.etxCompanyContactNo1);
        etxCompanySignUpTime1 = findViewById(R.id.etxCompanySignUpTime1);
        etxLoginName1 = findViewById(R.id.etxLoginName1);
        etxLoginPassword1 = findViewById(R.id.etxLoginPassword1);
        etxReLoginPassword1 = findViewById(R.id.etxReLoginPassword1);
        etxTradeLocation1 = findViewById(R.id.etxTradeLocation1);
        ivProducerPaper1 = findViewById(R.id.ivProducerPaper1);
        btnProducerCamera1 = findViewById(R.id.btnProducerCamera1);
        btnProducerCamera1.setOnClickListener(this);
        btnProducerAlbum1 = findViewById(R.id.btnProducerAlbum1);
        btnProducerAlbum1.setOnClickListener(this);
        ivProducerPaper2 = findViewById(R.id.ivProducerPaper2);
        btnProducerCamera2 = findViewById(R.id.btnProducerCamera2);
        btnProducerCamera2.setOnClickListener(this);
        btnProducerAlbum2 = findViewById(R.id.btnProducerAlbum2);
        btnProducerAlbum2.setOnClickListener(this);

        //检疫局
        etxCompanyName2 = findViewById(R.id.etxCompanyName2);
        etxTradeLocation2 = findViewById(R.id.etxTradeLocation2);
        etxLoginName2 = findViewById(R.id.etxLoginName2);
        etxLoginPassword2 = findViewById(R.id.etxLoginPassword2);
        etxReLoginPassword2 = findViewById(R.id.etxReLoginPassword2);
        ivQuarantinerPaper1 = findViewById(R.id.ivQuarantinerPaper1);
        btnQuarantinerCamera1 = findViewById(R.id.btnQuarantinerCamera1);
        btnQuarantinerCamera1.setOnClickListener(this);
        btnQuarantinerAlbum1 = findViewById(R.id.btnQuarantinerAlbum1);
        btnQuarantinerAlbum1.setOnClickListener(this);

        //加工厂
        etxCompanyName3 = findViewById(R.id.etxCompanyName3);
        etxCorporate3 = findViewById(R.id.etxCorporate3);
        etxCorporateIDCardNo3 = findViewById(R.id.etxCorporateIDCardNo3);
        etxCompanyContactNo3 = findViewById(R.id.etxCompanyContactNo3);
        etxCompanySignUpTime3 = findViewById(R.id.etxCompanySignUpTime3);
        etxTradeLocation3 = findViewById(R.id.etxTradeLocation3);
        etxLoginName3 = findViewById(R.id.etxLoginName3);
        etxLoginPassword3 = findViewById(R.id.etxLoginPassword3);
        etxReLoginPassword3 = findViewById(R.id.etxReLoginPassword3);
        ivProcesserPaper1 = findViewById(R.id.ivProcesserPaper1);
        btnProcesserCamera1 = findViewById(R.id.btnProcesserCamera1);
        btnProcesserCamera1.setOnClickListener(this);
        btnProcesserAlbum1 = findViewById(R.id.btnProcesserAlbum1);
        btnProcesserAlbum1.setOnClickListener(this);
        ivProcesserPaper2 = findViewById(R.id.ivProcesserPaper2);
        btnProcesserCamera2 = findViewById(R.id.btnProcesserCamera2);
        btnProcesserCamera2.setOnClickListener(this);
        btnProcesserAlbum2 = findViewById(R.id.btnProcesserAlbum2);
        btnProcesserAlbum2.setOnClickListener(this);
        ivProcesserPaper3 = findViewById(R.id.ivProcesserPaper3);
        btnProcesserCamera3 = findViewById(R.id.btnProcesserCamera3);
        btnProcesserCamera3.setOnClickListener(this);
        btnProcesserAlbum3 = findViewById(R.id.btnProcesserAlbum3);
        btnProcesserAlbum3.setOnClickListener(this);
        ivProcesserPaper4 = findViewById(R.id.ivProcesserPaper4);
        btnProcesserCamera4 = findViewById(R.id.btnProcesserCamera4);
        btnProcesserCamera4.setOnClickListener(this);
        btnProcesserAlbum4 = findViewById(R.id.btnProcesserAlbum4);
        btnProcesserAlbum4.setOnClickListener(this);
        ivProcesserPaper5 = findViewById(R.id.ivProcesserPaper5);
        btnProcesserCamera5 = findViewById(R.id.btnProcesserCamera5);
        btnProcesserCamera5.setOnClickListener(this);
        btnProcesserAlbum5 = findViewById(R.id.btnProcesserAlbum5);
        btnProcesserAlbum5.setOnClickListener(this);
        ivProcesserPaper6 = findViewById(R.id.ivProcesserPaper6);
        btnProcesserCamera6 = findViewById(R.id.btnProcesserCamera6);
        btnProcesserCamera6.setOnClickListener(this);
        btnProcesserAlbum6 = findViewById(R.id.btnProcesserAlbum6);
        btnProcesserAlbum6.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCompanySignUp:
                Toast.makeText(this,
                        characterList.get(currCharacter) + " 注册成功", Toast.LENGTH_SHORT).show();
//                intent = new Intent(CompanySignupActivity.this, LoginAndSignActivity.class);
//                startActivity(intent);

                String url = HttpUtil.BASEURL_SELL;
                String postData = "";

                switch (currCharacter) {
                    case 0:
                        url += "";
                        postData = JsonUtil.getJSON(
                                "ConsumerId", "123",
                                "ConsumerName", "Name"
                        );
                        break;
                }

                HttpUtil.sendOKHttp3RequestPOST(url, postData, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: " + e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String resStr = response.body().string();
                        Log.d(TAG, "response.code: " + response.code());
                        Log.d(TAG, "resStr: " + resStr);
                    }
                });

                break;
            case R.id.btnProducerCamera1:
                try {
                    BaseUtil.takeAPhoto(this, String.valueOf(getExternalCacheDir()), new TCCallbackListener() {
                        @Override
                        public void jump(Uri uri, File file, int requestCode) {
                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            ProducerPaper1Uri = uri;
                            ProducerPaper1File = file;
                            startActivityForResult(intent, requestCode);
                        }
                    }, PRODUCER_CAMERA_PAPER1);
                } catch (Exception e) {
                    Log.d(TAG, "onClick: " + e);
                }
                break;
            case R.id.btnProducerAlbum1:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    startActivityForResult(intent, PRODUCER_ALBUM_PAPER1);
                }
                break;

            case R.id.btnProducerCamera2:
                try {
                    BaseUtil.takeAPhoto(this, String.valueOf(getExternalCacheDir()), new TCCallbackListener() {
                        @Override
                        public void jump(Uri uri, File file, int requestCode) {
                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            ProducerPaper2Uri = uri;
                            ProducerPaper2File = file;
                            startActivityForResult(intent, requestCode);
                        }
                    }, PRODUCER_CAMERA_PAPER2);
                } catch (Exception e) {
                    Log.d(TAG, "onClick: " + e);
                }
                break;
            case R.id.btnProducerAlbum2:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    startActivityForResult(intent, PRODUCER_ALBUM_PAPER2);
                }
                break;

            case R.id.btnQuarantinerCamera1:
                try {
                    BaseUtil.takeAPhoto(this, String.valueOf(getExternalCacheDir()), new TCCallbackListener() {
                        @Override
                        public void jump(Uri uri, File file, int requestCode) {
                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            QuarantinerPaper1Uri = uri;
                            QuarantinerPaper1File = file;
                            startActivityForResult(intent, requestCode);
                        }
                    }, QUARANTINER_CAMERA_PAPER1);
                } catch (Exception e) {
                    Log.d(TAG, "onClick: " + e);
                }
                break;
            case R.id.btnQuarantinerAlbum1:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    startActivityForResult(intent, QUARANTINER_ALBUM_PAPER1);
                }
                break;

            case R.id.btnProcesserCamera1:
                try {
                    BaseUtil.takeAPhoto(this, String.valueOf(getExternalCacheDir()), new TCCallbackListener() {
                        @Override
                        public void jump(Uri uri, File file, int requestCode) {
                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            ProcesserPaper1Uri = uri;
                            ProcesserPaper1File = file;
                            startActivityForResult(intent, requestCode);
                        }
                    }, PROCESSER_CAMERA_PAPER1);
                } catch (Exception e) {
                    Log.d(TAG, "onClick: " + e);
                }
                break;
            case R.id.btnProcesserAlbum1:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    startActivityForResult(intent, PROCESSER_ALBUM_PAPER1);
                }
                break;
            case R.id.btnProcesserCamera2:
                try {
                    BaseUtil.takeAPhoto(this, String.valueOf(getExternalCacheDir()), new TCCallbackListener() {
                        @Override
                        public void jump(Uri uri, File file, int requestCode) {
                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            ProcesserPaper2Uri = uri;
                            ProcesserPaper2File = file;
                            startActivityForResult(intent, requestCode);
                        }
                    }, PROCESSER_CAMERA_PAPER2);
                } catch (Exception e) {
                    Log.d(TAG, "onClick: " + e);
                }
                break;
            case R.id.btnProcesserAlbum2:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    startActivityForResult(intent, PROCESSER_ALBUM_PAPER2);
                }
                break;
            case R.id.btnProcesserCamera3:
                try {
                    BaseUtil.takeAPhoto(this, String.valueOf(getExternalCacheDir()), new TCCallbackListener() {
                        @Override
                        public void jump(Uri uri, File file, int requestCode) {
                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            ProcesserPaper3Uri = uri;
                            ProcesserPaper3File = file;
                            startActivityForResult(intent, requestCode);
                        }
                    }, PROCESSER_CAMERA_PAPER3);
                } catch (Exception e) {
                    Log.d(TAG, "onClick: " + e);
                }
                break;
            case R.id.btnProcesserAlbum3:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    startActivityForResult(intent, PROCESSER_ALBUM_PAPER3);
                }
                break;
            case R.id.btnProcesserCamera4:
                try {
                    BaseUtil.takeAPhoto(this, String.valueOf(getExternalCacheDir()), new TCCallbackListener() {
                        @Override
                        public void jump(Uri uri, File file, int requestCode) {
                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            ProcesserPaper4Uri = uri;
                            ProcesserPaper4File = file;
                            startActivityForResult(intent, requestCode);
                        }
                    }, PROCESSER_CAMERA_PAPER4);
                } catch (Exception e) {
                    Log.d(TAG, "onClick: " + e);
                }
                break;
            case R.id.btnProcesserAlbum4:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    startActivityForResult(intent, PROCESSER_ALBUM_PAPER4);
                }
                break;
            case R.id.btnProcesserCamera5:
                try {
                    BaseUtil.takeAPhoto(this, String.valueOf(getExternalCacheDir()), new TCCallbackListener() {
                        @Override
                        public void jump(Uri uri, File file, int requestCode) {
                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            ProcesserPaper5Uri = uri;
                            ProcesserPaper5File = file;
                            startActivityForResult(intent, requestCode);
                        }
                    }, PROCESSER_CAMERA_PAPER5);
                } catch (Exception e) {
                    Log.d(TAG, "onClick: " + e);
                }
                break;
            case R.id.btnProcesserAlbum5:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    startActivityForResult(intent, PROCESSER_ALBUM_PAPER5);
                }
                break;
            case R.id.btnProcesserCamera6:
                try {
                    BaseUtil.takeAPhoto(this, String.valueOf(getExternalCacheDir()), new TCCallbackListener() {
                        @Override
                        public void jump(Uri uri, File file, int requestCode) {
                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            ProcesserPaper6Uri = uri;
                            ProcesserPaper6File = file;
                            startActivityForResult(intent, requestCode);
                        }
                    }, PROCESSER_CAMERA_PAPER6);
                } catch (Exception e) {
                    Log.d(TAG, "onClick: " + e);
                }
                break;
            case R.id.btnProcesserAlbum6:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    startActivityForResult(intent, PROCESSER_ALBUM_PAPER6);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PRODUCER_CAMERA_PAPER1:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(ProducerPaper1Uri));
                        ivProducerPaper1.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相机调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case PRODUCER_ALBUM_PAPER1:
                if (resultCode == RESULT_OK) {
                    try {
                        Log.d(TAG, "getAlbumImagePath: " + BaseUtil.getAlbumImagePath(this, data));
                        ProducerPaper1File = new File(BaseUtil.getAlbumImagePath(this, data));

                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(data.getData()));
                        ivProducerPaper1.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相册调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case PRODUCER_CAMERA_PAPER2:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(ProducerPaper2Uri));
                        ivProducerPaper2.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相机调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case PRODUCER_ALBUM_PAPER2:
                if (resultCode == RESULT_OK) {
                    try {
                        Log.d(TAG, "getAlbumImagePath: " + BaseUtil.getAlbumImagePath(this, data));
                        ProducerPaper2File = new File(BaseUtil.getAlbumImagePath(this, data));

                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(data.getData()));
                        ivProducerPaper2.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相册调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case QUARANTINER_CAMERA_PAPER1:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(QuarantinerPaper1Uri));
                        ivQuarantinerPaper1.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相机调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case QUARANTINER_ALBUM_PAPER1:
                if (resultCode == RESULT_OK) {
                    try {
                        Log.d(TAG, "getAlbumImagePath: " + BaseUtil.getAlbumImagePath(this, data));
                        QuarantinerPaper1File = new File(BaseUtil.getAlbumImagePath(this, data));

                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(data.getData()));
                        ivQuarantinerPaper1.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相册调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case PROCESSER_CAMERA_PAPER1:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(ProcesserPaper1Uri));
                        ivProcesserPaper1.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相机调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case PROCESSER_ALBUM_PAPER1:
                if (resultCode == RESULT_OK) {
                    try {
                        Log.d(TAG, "getAlbumImagePath: " + BaseUtil.getAlbumImagePath(this, data));
                        ProcesserPaper1File = new File(BaseUtil.getAlbumImagePath(this, data));

                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(data.getData()));
                        ivProcesserPaper1.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相册调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;
            case PROCESSER_CAMERA_PAPER2:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(ProcesserPaper2Uri));
                        ivProcesserPaper2.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相机调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case PROCESSER_ALBUM_PAPER2:
                if (resultCode == RESULT_OK) {
                    try {
                        Log.d(TAG, "getAlbumImagePath: " + BaseUtil.getAlbumImagePath(this, data));
                        ProcesserPaper2File = new File(BaseUtil.getAlbumImagePath(this, data));

                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(data.getData()));
                        ivProcesserPaper2.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相册调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;
            case PROCESSER_CAMERA_PAPER3:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(ProcesserPaper3Uri));
                        ivProcesserPaper3.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相机调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case PROCESSER_ALBUM_PAPER3:
                if (resultCode == RESULT_OK) {
                    try {
                        Log.d(TAG, "getAlbumImagePath: " + BaseUtil.getAlbumImagePath(this, data));
                        ProcesserPaper3File = new File(BaseUtil.getAlbumImagePath(this, data));

                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(data.getData()));
                        ivProcesserPaper3.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相册调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;
            case PROCESSER_CAMERA_PAPER4:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(ProcesserPaper4Uri));
                        ivProcesserPaper4.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相机调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case PROCESSER_ALBUM_PAPER4:
                if (resultCode == RESULT_OK) {
                    try {
                        Log.d(TAG, "getAlbumImagePath: " + BaseUtil.getAlbumImagePath(this, data));
                        ProcesserPaper4File = new File(BaseUtil.getAlbumImagePath(this, data));

                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(data.getData()));
                        ivProcesserPaper4.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相册调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case PROCESSER_CAMERA_PAPER5:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(ProcesserPaper5Uri));
                        ivProcesserPaper5.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相机调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case PROCESSER_ALBUM_PAPER5:
                if (resultCode == RESULT_OK) {
                    try {
                        Log.d(TAG, "getAlbumImagePath: " + BaseUtil.getAlbumImagePath(this, data));
                        ProcesserPaper5File = new File(BaseUtil.getAlbumImagePath(this, data));

                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(data.getData()));
                        ivProcesserPaper5.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相册调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case PROCESSER_CAMERA_PAPER6:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(ProcesserPaper6Uri));
                        ivProcesserPaper6.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相机调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case PROCESSER_ALBUM_PAPER6:
                if (resultCode == RESULT_OK) {
                    try {
                        Log.d(TAG, "getAlbumImagePath: " + BaseUtil.getAlbumImagePath(this, data));
                        ProcesserPaper6File = new File(BaseUtil.getAlbumImagePath(this, data));

                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(data.getData()));
                        ivProcesserPaper6.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相册调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
