package com.example.helloworld.loginAndSign;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.UiThread;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.HttpUtil;
import com.example.helloworld.JsonUtil;
import com.example.helloworld.R;
import com.example.helloworld.baseuser.BaseUserSignUpActivity;
import com.example.helloworld.company.CompanyManagerActivity;
import com.example.helloworld.company.CompanySignupActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginAndSignActivity extends AppCompatActivity
        implements View.OnClickListener {

    private static final String TAG = "tigercheng";

    //控件
    private TextView etxContactNo = null;
    private TextView etxPassword = null;

    private Button btnSignUp = null;
    private Button btnLogIn = null;

    private RadioGroup rgChooseLogKind = null;

    private RadioButton rbtnCompany = null;
    private RadioButton rbtnPersonal = null;

    private CheckBox cboxRemeberPwd = null;

    //标志

    private boolean companyFlag = true;
    private boolean rememberPwdFlag = false;
    //连接服务器判断用户是否登录成功
    private boolean loginAccess = false;
    //连接服务器判断责任人角色是否需要完善信息 0为需要 1为不需要
//    private int characterFlags = getCharacterFlags();
    private int characterFlags = 0b000000;

    private Intent intent = null;
    private SharedPreferences pref = null;
    private SharedPreferences.Editor prefEditor = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login_and_sign);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initUI();

        /**  测试 **/

        Log.d(TAG, "JsonUtil.isJSON: " + JsonUtil.isJSON("{\"hh\":231}"));
        Log.d(TAG, "JsonUtil.isJSON: " + JsonUtil.isJSON("{hh:231}"));
        try {
            //java中json的键会自动加上双引号，从而正常的解析
            JSONObject jsonObject = new JSONObject("{hh:\"231\"}");
            Log.d(TAG, "jsonObject: " + jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "JsonUtil.isJSON: " + JsonUtil.isJSON("123hhh"));

        /**  测试 **/


        //以当前应用程序包名来命名sharedpreferences文件，当前应用程序共享
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        rememberPwdFlag = pref.getBoolean("rememberPwd", false);
        Log.d(TAG, "rememberPwdFlag: " + rememberPwdFlag);
        if (rememberPwdFlag) {
            companyFlag = pref.getBoolean("companyFlag", false);
            rbtnCompany.setChecked(companyFlag);
            rbtnPersonal.setChecked(!companyFlag);

            etxContactNo.setText(pref.getString("userName", ""));
            etxPassword.setText(pref.getString("password", ""));
            cboxRemeberPwd.setChecked(true);
            btnLogIn.callOnClick();
        }

        //选择按钮 企业组织 或 个人用户
        rgChooseLogKind.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbtn_company:
                        Toast.makeText(LoginAndSignActivity.this, "企业用户", Toast.LENGTH_SHORT).show();
                        companyFlag = true;
                        break;

                    case R.id.rbtn_personal:
                        Toast.makeText(LoginAndSignActivity.this, "个人用户", Toast.LENGTH_SHORT).show();
                        companyFlag = false;
                        break;
                }
            }
        });

    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: ");
        super.onStart();
//        rememberPwdFlag = pref.getBoolean("rememberPwd", false);
//        if (!rememberPwdFlag) {
//            companyFlag = pref.getBoolean("companyFlag", false);
//            rbtnCompany.setChecked(companyFlag);
//            rbtnPersonal.setChecked(!companyFlag);
//
//            etxUserName.setText("");
//            etxPassword.setText("");
//            cboxRemeberPwd.setChecked(false);
//        }
    }

    private void initUI() {
        etxContactNo = findViewById(R.id.etx_contactNo);
        etxPassword = findViewById(R.id.etx_password);

        btnSignUp = findViewById(R.id.btn_signup);
        btnSignUp.setOnClickListener(this);
        btnLogIn = findViewById(R.id.btn_login);
        btnLogIn.setOnClickListener(this);

        rgChooseLogKind = findViewById(R.id.rg_choselogkind);

        rbtnCompany = findViewById(R.id.rbtn_company);
        rbtnPersonal = findViewById(R.id.rbtn_personal);

        cboxRemeberPwd = findViewById(R.id.cbox_remeberPwd);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_signup:
                Log.d(TAG, "企业登录操作");
                signUp();
                break;

            case R.id.btn_login:
                Log.d(TAG, "用户登录操作");
                logIn();
                break;

            default:
                break;
        }
    }

    private void signUp() {
        if (companyFlag) {
            //企业组织注册操作
            Log.d(TAG, "企业组织用户注册操作");
            intent = new Intent(LoginAndSignActivity.this, CompanySignupActivity.class);
            intent.putExtra("title", "企业组织注册");
            startActivity(intent);
        } else {
            //个人用户注册操作
            Log.d(TAG, "个人用户登录操作");
            intent = new Intent(LoginAndSignActivity.this, BaseUserSignUpActivity.class);
            intent.putExtra("title", "个人用户注册");
            startActivity(intent);
        }

    }

    private void logIn() {
        Log.d(TAG, "companyFlag: " + companyFlag);
        final String contactNo = etxContactNo.getText().toString();
        final String password = etxPassword.getText().toString();
        Log.d(TAG, "logIn: " + contactNo + "::::" + password);
        Log.d(TAG, "JsonUtil.getJSON: " + JsonUtil.getJSON(
                "name", contactNo,
                "password", password
        ));

        if (companyFlag) {
            //企业组织登录操作
            Log.d(TAG, "企业组织用户登录操作");

            HttpUtil.sendOKHttpMultipartRequestPOST(HttpUtil.BASEURL_COMPANY + "/login.action",
                    new MultipartBody.Builder("AaB03x")
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("name", contactNo)
                            .addFormDataPart("password", password)
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
                            try {
                                JSONObject resJson = new JSONObject(resStr);
                                if (!resJson.getString("status").equals("error")) {
                                    intent = new Intent(LoginAndSignActivity.this, CompanyManagerActivity.class);
                                    intent.putExtra("title", "企业组织管理");
                                    handlePreference(companyFlag, contactNo, password, characterFlags, "");
                                    prefEditor = pref.edit();
                                    prefEditor.putString("companyName", contactNo);
                                    prefEditor.putString("companyCharacterFlag", resJson.getString("status"));
                                    prefEditor.apply();
                                    startActivity(intent);
                                    finish();
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(LoginAndSignActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            } catch (JSONException e) {
//                                e.printStackTrace();
                            }
                        }
                    });
        } else {
            //个人用户登录操作
            Log.d(TAG, "个人用户登录操作");

            HttpUtil.sendOKHttp3RequestPOST(HttpUtil.BASEURL_LOGIN_SIGN_PRODUCE + "/user/login?CharacterFlag=1",
                    JsonUtil.getJSON(
                            "ContactNo", Integer.parseInt(contactNo),
                            "Password", password
                    ),
                    new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d(TAG, "onFailure: " + e.toString());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String resStr = response.body().string();
                            Log.d(TAG, "response.code: " + response.code());
                            Log.d(TAG, "个人用户登录操作resStr: " + resStr);

                            try {
                                JSONObject resJson = null;
                                resJson = new JSONObject(resStr);
                                Log.d(TAG, "resJson: " + resJson.toString());

                                characterFlags = Integer.valueOf(resJson.getString("CharacterFlag"));

                                intent = new Intent(LoginAndSignActivity.this, ResponsibleChooseActivity.class);
                                handlePreference(
                                        companyFlag, contactNo, password, characterFlags,
                                        resJson.getString("ConsumerId")
                                );

                                startActivity(intent);
                                finish();
                            } catch (JSONException e) {
                                Log.d(TAG, "JSONException: " + e.toString());
//                                etxContact.setText("");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginAndSignActivity.this, resStr, Toast.LENGTH_SHORT).show();
                                    }
                                });
                                e.printStackTrace();
                            }
                        }
                    });
        }

    }

    private void handlePreference(
            boolean companyFlag, String username,
            String password, int characterFlags,
            String id) {
        prefEditor = pref.edit();
        if (cboxRemeberPwd.isChecked()) {
            prefEditor.putBoolean("rememberPwd", true);
            prefEditor.putBoolean("companyFlag", companyFlag);
            prefEditor.putString("userName", username);
            prefEditor.putString("password", password);
            prefEditor.putInt("characterFlags", characterFlags);

        } else {
            prefEditor.putBoolean("rememberPwd", false);
            prefEditor.remove("userName");
            prefEditor.remove("password");
            prefEditor.remove("id");
            etxContactNo.setText("");
            etxPassword.setText("");
            prefEditor.putInt("characterFlags", 0);
        }

        prefEditor.putString("id", id);

        prefEditor.apply();
    }
}
