package com.example.helloworld.company;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.helloworld.BaseUtil;
import com.example.helloworld.HttpUtil;
import com.example.helloworld.JsonUtil;
import com.example.helloworld.R;
import com.example.helloworld.loginAndSign.LoginAndSignActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.Response;

public class CompanyManagerActivity extends AppCompatActivity
        implements View.OnClickListener {

    private static final String TAG = "tigercheng";

    private Button btnCompanyMessage = null;
    private Button btnCompanyStaff = null;
    //    private Button btnOperateScale = null;
    private Button btnExit = null;

    private Intent intent = null;

    private SharedPreferences pref = null;
    private SharedPreferences.Editor prefEditor = null;

    private String companyName = "";
    private String companyCharacterFlag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_company_manager);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initUI();

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        prefEditor = pref.edit();
        companyName = pref.getString("companyName", "companyName");
        companyCharacterFlag = pref.getString("companyCharacterFlag", "error");
        Log.d(TAG, "companyName: " + companyName + "*companyCharacterFlag:" + companyCharacterFlag);

    }

    private void initUI() {
        btnCompanyMessage = findViewById(R.id.btnCompanyMessage);
        btnCompanyMessage.setOnClickListener(this);
        btnCompanyStaff = findViewById(R.id.btnCompanyStaff);
        btnCompanyStaff.setOnClickListener(this);
//        btnOperateScale = findViewById(R.id.btnOperateScale);
//        btnOperateScale.setOnClickListener(this);
        btnExit = findViewById(R.id.btnExit);
        btnExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCompanyMessage:
                break;
            case R.id.btnCompanyStaff:
                HttpUtil.sendOKHttpMultipartRequestPOST(
                        HttpUtil.BASEURL_COMPANY + "/employee.action",
                        new MultipartBody.Builder("AaB03x")
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("Flag", companyCharacterFlag)
                                .addFormDataPart("LoginName", companyName)
                                .build(),
                        new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.d(TAG, "onFailure: " + e.toString());
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String resStr = response.body().string();
                                Log.d(TAG, "response.code: " + response.code());
                                Log.d(TAG, "企业组织用户登录操作resStr: " + resStr);

                                intent = new Intent(CompanyManagerActivity.this, CompanyStaffMActivity.class);
                                intent.putExtra("title", "企业人员管理");
                                intent.putExtra("staff", resStr);
                                startActivity(intent);
                            }
                        }
                );
                break;
//            case R.id.btnOperateScale:
//                break;
            case R.id.btnExit:
                prefEditor.putBoolean("rememberPwd", false);
                prefEditor.apply();
                Log.d(TAG, "CompanyManagerActivity: " + pref.getBoolean("rememberPwd", true));

                intent = new Intent(CompanyManagerActivity.this, LoginAndSignActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}
