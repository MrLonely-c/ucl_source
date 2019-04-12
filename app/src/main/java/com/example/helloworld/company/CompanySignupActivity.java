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
import com.example.helloworld.producer.ProducerMessCompleteActivity;
import com.example.helloworld.producer.productionStateActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    //运输公司
    private EditText etxCompanyName4 = null;
    private EditText etxCorporate4 = null;
    private EditText etxCorporateIDCardNo4 = null;
    private EditText etxCompanyContactNo4 = null;
    private EditText etxTradeLocation4 = null;
    private EditText etxLoginName4 = null;
    private EditText etxLoginPassword4 = null;
    private EditText etxReLoginPassword4 = null;
    private ImageView ivTransporterPaper1 = null;
    private Uri TransporterPaper1Uri = null;
    private File TransporterPaper1File = null;
    private Button btnTransporterCamera1 = null;
    private Button btnTransporterAlbum1 = null;
    private ImageView ivTransporterPaper2 = null;
    private Uri TransporterPaper2Uri = null;
    private File TransporterPaper2File = null;
    private Button btnTransporterCamera2 = null;
    private Button btnTransporterAlbum2 = null;
    private ImageView ivTransporterPaper3 = null;
    private Uri TransporterPaper3Uri = null;
    private File TransporterPaper3File = null;
    private Button btnTransporterCamera3 = null;
    private Button btnTransporterAlbum3 = null;

    //销售
    private EditText etxCompanyName5 = null;
    private EditText etxCorporate5 = null;
    private EditText etxCorporateIDCardNo5 = null;
    private EditText etxCompanyContactNo5 = null;
    private EditText etxTradeLocation5 = null;
    private EditText etxLoginName5 = null;
    private EditText etxLoginPassword5 = null;
    private EditText etxReLoginPassword5 = null;
    private ImageView ivSellerPaper1 = null;
    private Uri SellerPaper1Uri = null;
    private File SellerPaper1File = null;
    private Button btnSellerCamera1 = null;
    private Button btnSellerAlbum1 = null;
    private ImageView ivSellerPaper2 = null;
    private Uri SellerPaper2Uri = null;
    private File SellerPaper2File = null;
    private Button btnSellerCamera2 = null;
    private Button btnSellerAlbum2 = null;
    private ImageView ivSellerPaper3 = null;
    private Uri SellerPaper3Uri = null;
    private File SellerPaper3File = null;
    private Button btnSellerCamera3 = null;
    private Button btnSellerAlbum3 = null;


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

        //运输公司
        etxCompanyName4 = findViewById(R.id.etxCompanyName4);
        etxCorporate4 = findViewById(R.id.etxCorporate4);
        etxCorporateIDCardNo4 = findViewById(R.id.etxCorporateIDCardNo4);
        etxCompanyContactNo4 = findViewById(R.id.etxCompanyContactNo4);
        etxTradeLocation4 = findViewById(R.id.etxTradeLocation4);
        etxLoginName4 = findViewById(R.id.etxLoginName4);
        etxLoginPassword4 = findViewById(R.id.etxLoginPassword4);
        etxReLoginPassword4 = findViewById(R.id.etxReLoginPassword4);
        ivTransporterPaper1 = findViewById(R.id.ivTransporterPaper1);
        btnTransporterCamera1 = findViewById(R.id.btnTransporterCamera1);
        btnTransporterCamera1.setOnClickListener(this);
        btnTransporterAlbum1 = findViewById(R.id.btnTransporterAlbum1);
        btnTransporterAlbum1.setOnClickListener(this);
        ivTransporterPaper2 = findViewById(R.id.ivTransporterPaper2);
        btnTransporterCamera2 = findViewById(R.id.btnTransporterCamera2);
        btnTransporterCamera2.setOnClickListener(this);
        btnTransporterAlbum2 = findViewById(R.id.btnTransporterAlbum2);
        btnTransporterAlbum2.setOnClickListener(this);
        ivTransporterPaper3 = findViewById(R.id.ivTransporterPaper3);
        btnTransporterCamera3 = findViewById(R.id.btnTransporterCamera3);
        btnTransporterCamera3.setOnClickListener(this);
        btnTransporterAlbum3 = findViewById(R.id.btnTransporterAlbum3);
        btnTransporterAlbum3.setOnClickListener(this);

        //销售
        etxCompanyName5 = findViewById(R.id.etxCompanyName5);
        etxCorporate5 = findViewById(R.id.etxCorporate5);
        etxCorporateIDCardNo5 = findViewById(R.id.etxCorporateIDCardNo5);
        etxCompanyContactNo5 = findViewById(R.id.etxCompanyContactNo5);
        etxTradeLocation5 = findViewById(R.id.etxTradeLocation5);
        etxLoginName5 = findViewById(R.id.etxLoginName5);
        etxLoginPassword5 = findViewById(R.id.etxLoginPassword5);
        etxReLoginPassword5 = findViewById(R.id.etxReLoginPassword5);
        ivSellerPaper1 = findViewById(R.id.ivSellerPaper1);
        btnSellerCamera1 = findViewById(R.id.btnSellerCamera1);
        btnSellerCamera1.setOnClickListener(this);
        btnSellerAlbum1 = findViewById(R.id.btnSellerAlbum1);
        btnSellerAlbum1.setOnClickListener(this);
        ivSellerPaper2 = findViewById(R.id.ivSellerPaper2);
        btnSellerCamera2 = findViewById(R.id.btnSellerCamera2);
        btnSellerCamera2.setOnClickListener(this);
        btnSellerAlbum2 = findViewById(R.id.btnSellerAlbum2);
        btnSellerAlbum2.setOnClickListener(this);
        ivSellerPaper3 = findViewById(R.id.ivSellerPaper3);
        btnSellerCamera3 = findViewById(R.id.btnSellerCamera3);
        btnSellerCamera3.setOnClickListener(this);
        btnSellerAlbum3 = findViewById(R.id.btnSellerAlbum3);
        btnSellerAlbum3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCompanySignUp:

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnCompanySignUp.setClickable(false);
                        btnCompanySignUp.setText("正在提交");
                        btnCompanySignUp.setBackgroundResource(R.drawable.btn_bg_unclicked);
                    }
                });

                String url = HttpUtil.BASEURL_SELL;
                MultipartBody multipartBody = null;

                switch (currCharacter) {
                    case 0:
                        url += "";
                        multipartBody = new MultipartBody.Builder("AaB03x")
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("Flag", "1")
                                .addFormDataPart("CompanyName1", etxCompanyName1.getText().toString())
                                .addFormDataPart("Corporate1", etxCorporate1.getText().toString())
                                .addFormDataPart("CoporateIDCardNo1", etxCoporateIDCardNo1.getText().toString())
                                .addFormDataPart("CompanyContactNo1", etxCompanyContactNo1.getText().toString())
                                .addFormDataPart("CompanySignUpTime1", etxCompanySignUpTime1.getText().toString())
                                .addFormDataPart("LoginName1", etxLoginName1.getText().toString())
                                .addFormDataPart("LoginPassword1", etxLoginPassword1.getText().toString())
                                .addFormDataPart("TradeLocation1", etxTradeLocation1.getText().toString())
                                .addFormDataPart("ProducerPaper1File", "ProducerPaper1File.jpg", RequestBody.create(
                                        MediaType.parse("image/*"),
                                        ProducerPaper1File))
                                .addFormDataPart("ProducerPaper2File", "ProducerPaper2File.jpg", RequestBody.create(
                                        MediaType.parse("image/*"),
                                        ProducerPaper2File))
                                .build();
                        break;
                    case 1:
                        url += "";
                        multipartBody = new MultipartBody.Builder("AaB03x")
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("Flag", "2")
                                .addFormDataPart("CompanyName2", etxCompanyName2.getText().toString())
                                .addFormDataPart("TradeLocation2", etxTradeLocation2.getText().toString())
                                .addFormDataPart("LoginName2", etxLoginName2.getText().toString())
                                .addFormDataPart("LoginPassword2", etxLoginPassword2.getText().toString())
                                .addFormDataPart("QuarantinerPaper1File", "QuarantinerPaper1File.jpg", RequestBody.create(
                                        MediaType.parse("image/*"),
                                        QuarantinerPaper1File))
                                .build();
                        break;
                    case 2:
                        url += "";
                        multipartBody = new MultipartBody.Builder("AaB03x")
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("Flag", "3")
                                .addFormDataPart("CompanyName3", etxCompanyName3.getText().toString())
                                .addFormDataPart("Corporate3", etxCorporate3.getText().toString())
                                .addFormDataPart("CorporateIDCardNo3", etxCorporateIDCardNo3.getText().toString())
                                .addFormDataPart("CompanyContactNo3", etxCompanyContactNo3.getText().toString())
                                .addFormDataPart("CompanySignUpTime3", etxCompanySignUpTime3.getText().toString())
                                .addFormDataPart("TradeLocation3", etxTradeLocation3.getText().toString())
                                .addFormDataPart("LoginName3", etxLoginName3.getText().toString())
                                .addFormDataPart("LoginPassword3", etxLoginPassword3.getText().toString())
                                .addFormDataPart("ProcesserPaper1File", "ProcesserPaper1File.jpg", RequestBody.create(
                                        MediaType.parse("image/*"),
                                        ProcesserPaper1File))
                                .addFormDataPart("ProcesserPaper2File", "ProcesserPaper2File.jpg", RequestBody.create(
                                        MediaType.parse("image/*"),
                                        ProcesserPaper2File))
                                .addFormDataPart("ProcesserPaper3File", "ProcesserPaper3File.jpg", RequestBody.create(
                                        MediaType.parse("image/*"),
                                        ProcesserPaper3File))
                                .addFormDataPart("ProcesserPaper4File", "ProcesserPaper4File.jpg", RequestBody.create(
                                        MediaType.parse("image/*"),
                                        ProcesserPaper4File))
                                .addFormDataPart("ProcesserPaper5File", "ProcesserPaper5File.jpg", RequestBody.create(
                                        MediaType.parse("image/*"),
                                        ProcesserPaper5File))
                                .addFormDataPart("ProcesserPaper6File", "ProcesserPaper6File.jpg", RequestBody.create(
                                        MediaType.parse("image/*"),
                                        ProcesserPaper6File))
                                .build();
                        break;
                    case 3:
                        url += "";
                        multipartBody = new MultipartBody.Builder("AaB03x")
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("Flag", "4")
                                .addFormDataPart("CompanyName4", etxCompanyName4.getText().toString())
                                .addFormDataPart("Corporate4", etxCorporate4.getText().toString())
                                .addFormDataPart("CorporateIDCardNo4", etxCorporateIDCardNo4.getText().toString())
                                .addFormDataPart("CompanyContactNo4", etxCompanyContactNo4.getText().toString())
                                .addFormDataPart("TradeLocation4", etxTradeLocation4.getText().toString())
                                .addFormDataPart("LoginName4", etxLoginName4.getText().toString())
                                .addFormDataPart("LoginPassword4", etxLoginPassword4.getText().toString())
                                .addFormDataPart("TransporterPaper1File", "TransporterPaper1File.jpg", RequestBody.create(
                                        MediaType.parse("image/*"),
                                        TransporterPaper1File))
                                .addFormDataPart("TransporterPaper2File", "TransporterPaper2File.jpg", RequestBody.create(
                                        MediaType.parse("image/*"),
                                        TransporterPaper2File))
                                .addFormDataPart("TransporterPaper3File", "TransporterPaper3File.jpg", RequestBody.create(
                                        MediaType.parse("image/*"),
                                        TransporterPaper3File))
                                .build();
                        break;
                    case 4:
                        url += "";
                        multipartBody = new MultipartBody.Builder("AaB03x")
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("Flag", "5")
                                .addFormDataPart("CompanyName5", etxCompanyName5.getText().toString())
                                .addFormDataPart("Corporate5", etxCorporate5.getText().toString())
                                .addFormDataPart("CorporateIDCardNo5", etxCorporateIDCardNo5.getText().toString())
                                .addFormDataPart("CompanyContactNo5", etxCompanyContactNo5.getText().toString())
                                .addFormDataPart("TradeLocation5", etxTradeLocation5.getText().toString())
                                .addFormDataPart("LoginName5", etxLoginName5.getText().toString())
                                .addFormDataPart("LoginPassword5", etxLoginPassword5.getText().toString())
                                .addFormDataPart("SellerPaper1File", "SellerPaper1File.jpg", RequestBody.create(
                                        MediaType.parse("image/*"),
                                        SellerPaper1File))
                                .addFormDataPart("SellerPaper2File", "SellerPaper2File.jpg", RequestBody.create(
                                        MediaType.parse("image/*"),
                                        SellerPaper2File))
                                .addFormDataPart("SellerPaper3File", "SellerPaper3File.jpg", RequestBody.create(
                                        MediaType.parse("image/*"),
                                        SellerPaper3File))
                                .build();
                        break;

                }

                HttpUtil.sendOKHttpMultipartRequestPOST(
                        url, multipartBody,
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

                                    intent = new Intent(CompanySignupActivity.this, LoginAndSignActivity.class);
                                    startActivity(intent);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            btnCompanySignUp.setClickable(true);
                                            btnCompanySignUp.setText("提交保存");
                                            btnCompanySignUp.setBackgroundResource(R.drawable.btn_selector);
                                        }
                                    });
                                    finish();
                                }
                            }
                        }
                );

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

            case R.id.btnTransporterCamera1:
                try {
                    BaseUtil.takeAPhoto(this, String.valueOf(getExternalCacheDir()), new TCCallbackListener() {
                        @Override
                        public void jump(Uri uri, File file, int requestCode) {
                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            TransporterPaper1Uri = uri;
                            TransporterPaper1File = file;
                            startActivityForResult(intent, requestCode);
                        }
                    }, TRANSPOTER_CAMERA_PAPER1);
                } catch (Exception e) {
                    Log.d(TAG, "onClick: " + e);
                }
                break;
            case R.id.btnTransporterAlbum1:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    startActivityForResult(intent, TRANSPOTER_ALBUM_PAPER1);
                }
                break;
            case R.id.btnTransporterCamera2:
                try {
                    BaseUtil.takeAPhoto(this, String.valueOf(getExternalCacheDir()), new TCCallbackListener() {
                        @Override
                        public void jump(Uri uri, File file, int requestCode) {
                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            TransporterPaper2Uri = uri;
                            TransporterPaper2File = file;
                            startActivityForResult(intent, requestCode);
                        }
                    }, TRANSPOTER_CAMERA_PAPER2);
                } catch (Exception e) {
                    Log.d(TAG, "onClick: " + e);
                }
                break;
            case R.id.btnTransporterAlbum2:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    startActivityForResult(intent, TRANSPOTER_ALBUM_PAPER2);
                }
                break;
            case R.id.btnTransporterCamera3:
                try {
                    BaseUtil.takeAPhoto(this, String.valueOf(getExternalCacheDir()), new TCCallbackListener() {
                        @Override
                        public void jump(Uri uri, File file, int requestCode) {
                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            TransporterPaper3Uri = uri;
                            TransporterPaper3File = file;
                            startActivityForResult(intent, requestCode);
                        }
                    }, TRANSPOTER_CAMERA_PAPER3);
                } catch (Exception e) {
                    Log.d(TAG, "onClick: " + e);
                }
                break;
            case R.id.btnTransporterAlbum3:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    startActivityForResult(intent, TRANSPOTER_ALBUM_PAPER3);
                }
                break;

            case R.id.btnSellerCamera1:
                try {
                    BaseUtil.takeAPhoto(this, String.valueOf(getExternalCacheDir()), new TCCallbackListener() {
                        @Override
                        public void jump(Uri uri, File file, int requestCode) {
                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            SellerPaper1Uri = uri;
                            SellerPaper1File = file;
                            startActivityForResult(intent, requestCode);
                        }
                    }, SELLER_CAMERA_PAPER1);
                } catch (Exception e) {
                    Log.d(TAG, "onClick: " + e);
                }
                break;
            case R.id.btnSellerAlbum1:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    startActivityForResult(intent, SELLER_ALBUM_PAPER1);
                }
                break;

            case R.id.btnSellerCamera2:
                try {
                    BaseUtil.takeAPhoto(this, String.valueOf(getExternalCacheDir()), new TCCallbackListener() {
                        @Override
                        public void jump(Uri uri, File file, int requestCode) {
                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            SellerPaper2Uri = uri;
                            SellerPaper2File = file;
                            startActivityForResult(intent, requestCode);
                        }
                    }, SELLER_CAMERA_PAPER2);
                } catch (Exception e) {
                    Log.d(TAG, "onClick: " + e);
                }
                break;
            case R.id.btnSellerAlbum2:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    startActivityForResult(intent, SELLER_ALBUM_PAPER2);
                }
                break;

            case R.id.btnSellerCamera3:
                try {
                    BaseUtil.takeAPhoto(this, String.valueOf(getExternalCacheDir()), new TCCallbackListener() {
                        @Override
                        public void jump(Uri uri, File file, int requestCode) {
                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            SellerPaper3Uri = uri;
                            SellerPaper3File = file;
                            startActivityForResult(intent, requestCode);
                        }
                    }, SELLER_CAMERA_PAPER3);
                } catch (Exception e) {
                    Log.d(TAG, "onClick: " + e);
                }
                break;
            case R.id.btnSellerAlbum3:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    startActivityForResult(intent, SELLER_ALBUM_PAPER3);
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

            case TRANSPOTER_CAMERA_PAPER1:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(TransporterPaper1Uri));
                        ivTransporterPaper1.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相机调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case TRANSPOTER_ALBUM_PAPER1:
                if (resultCode == RESULT_OK) {
                    try {
                        Log.d(TAG, "getAlbumImagePath: " + BaseUtil.getAlbumImagePath(this, data));
                        TransporterPaper1File = new File(BaseUtil.getAlbumImagePath(this, data));

                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(data.getData()));
                        ivTransporterPaper1.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相册调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;
            case TRANSPOTER_CAMERA_PAPER2:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(TransporterPaper2Uri));
                        ivTransporterPaper2.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相机调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case TRANSPOTER_ALBUM_PAPER2:
                if (resultCode == RESULT_OK) {
                    try {
                        Log.d(TAG, "getAlbumImagePath: " + BaseUtil.getAlbumImagePath(this, data));
                        TransporterPaper2File = new File(BaseUtil.getAlbumImagePath(this, data));

                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(data.getData()));
                        ivTransporterPaper2.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相册调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;
            case TRANSPOTER_CAMERA_PAPER3:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(TransporterPaper3Uri));
                        ivTransporterPaper3.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相机调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case TRANSPOTER_ALBUM_PAPER3:
                if (resultCode == RESULT_OK) {
                    try {
                        Log.d(TAG, "getAlbumImagePath: " + BaseUtil.getAlbumImagePath(this, data));
                        TransporterPaper3File = new File(BaseUtil.getAlbumImagePath(this, data));

                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(data.getData()));
                        ivTransporterPaper3.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相册调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;


            case SELLER_CAMERA_PAPER1:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(SellerPaper1Uri));
                        ivSellerPaper1.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相机调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case SELLER_ALBUM_PAPER1:
                if (resultCode == RESULT_OK) {
                    try {
                        Log.d(TAG, "getAlbumImagePath: " + BaseUtil.getAlbumImagePath(this, data));
                        SellerPaper1File = new File(BaseUtil.getAlbumImagePath(this, data));

                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(data.getData()));
                        ivSellerPaper1.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相册调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;
            case SELLER_CAMERA_PAPER2:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(SellerPaper2Uri));
                        ivSellerPaper2.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相机调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case SELLER_ALBUM_PAPER2:
                if (resultCode == RESULT_OK) {
                    try {
                        Log.d(TAG, "getAlbumImagePath: " + BaseUtil.getAlbumImagePath(this, data));
                        SellerPaper2File = new File(BaseUtil.getAlbumImagePath(this, data));

                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(data.getData()));
                        ivSellerPaper2.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相册调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;
            case SELLER_CAMERA_PAPER3:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(SellerPaper3Uri));
                        ivSellerPaper3.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相机调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;

            case SELLER_ALBUM_PAPER3:
                if (resultCode == RESULT_OK) {
                    try {
                        Log.d(TAG, "getAlbumImagePath: " + BaseUtil.getAlbumImagePath(this, data));
                        SellerPaper3File = new File(BaseUtil.getAlbumImagePath(this, data));

                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(data.getData()));
                        ivSellerPaper3.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, "相册调用: " + e);
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
