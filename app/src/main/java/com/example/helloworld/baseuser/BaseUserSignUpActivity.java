package com.example.helloworld.baseuser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.CodeUtil;
import com.example.helloworld.JsonUtil;
import com.example.helloworld.HttpUtil;
import com.example.helloworld.R;
import com.example.helloworld.loginAndSign.LoginAndSignActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BaseUserSignUpActivity extends AppCompatActivity
        implements View.OnClickListener {
    private static final String TAG = "tigercheng";

    private EditText etxUserName = null;
    private EditText etxContact = null;
    private EditText etxPassword = null;
    private EditText etxRePassword = null;
    private EditText etxVerificationCode = null;

    private Button btnGetVerificationCode = null;
    private ImageView ivVerification = null;
    private Button btnBaseUserSignUp = null;

    private Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_base_user_sign_up);

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
    }

    private void initUI() {
        Intent intent = getIntent();


        etxUserName = findViewById(R.id.etxUserName);
        etxContact = findViewById(R.id.etxContact);
        etxPassword = findViewById(R.id.etxPassword);
        etxRePassword = findViewById(R.id.etxRePassword);
        etxVerificationCode = findViewById(R.id.etxVerificationCode);

//        btnGetVerificationCode = findViewById(R.id.btn_getVerificationCode);
        ivVerification = findViewById(R.id.iv_verification);
        ivVerification.setOnClickListener(this);
        btnBaseUserSignUp = findViewById(R.id.btn_baseUserSignUp);
        btnBaseUserSignUp.setOnClickListener(this);

        ivVerification.setImageBitmap(CodeUtil.getInstance().createBitmap());
        Toast.makeText(this, CodeUtil.getInstance().getCode(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.btn_getVerificationCode:
//                Log.d(TAG, "获取验证码");
//                break;

            case R.id.iv_verification:
                ivVerification.setImageBitmap(CodeUtil.getInstance().createBitmap());
                Toast.makeText(this, CodeUtil.getInstance().getCode(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_baseUserSignUp:
                Log.d(TAG, "提交注册");
                if (baseUserSignUp()) {
                    intent = new Intent(BaseUserSignUpActivity.this, LoginAndSignActivity.class);
                    startActivity(intent);
                }
                break;

        }
    }

    private boolean baseUserSignUp() {
        boolean access = false;

        String userName = etxUserName.getText().toString();
        String contact = etxContact.getText().toString();
        String password = etxPassword.getText().toString();
        String rePassword = etxRePassword.getText().toString();
        String verificationCode = etxVerificationCode.getText().toString();

        Log.d(TAG, "baseUserSignUp: ");
        //http://223.3.72.161/register??characterFlag=1
        HttpUtil.sendOKHttp3RequestPOST("http://223.3.79.211:8000/register?CharacterFlag=1",
                JsonUtil.getJSON(
                        "ConsumerName","fan",
                "ContactNo","13333337899",
                        "Password","123456"





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

                        Log.d(TAG, "resStr: " + resStr);
                        try {
                            JSONObject resJson = new JSONObject(resStr);
                            Log.d(TAG, "resJson: " + resJson.toString());

                            if (true) {
                                intent = new Intent(BaseUserSignUpActivity.this, LoginAndSignActivity.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            Log.d(TAG, "JSONException: " + e.toString());
                            runOnUiThread(new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(BaseUserSignUpActivity.this, ""+resStr, Toast.LENGTH_SHORT).show();
                                }
                            }));
//                            e.printStackTrace();
                        }
                    }
                }

        );

        return access;
    }
}
