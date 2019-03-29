package com.example.helloworld.company;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.HttpUtil;
import com.example.helloworld.JsonUtil;
import com.example.helloworld.R;
import com.example.helloworld.loginAndSign.LoginAndSignActivity;

import java.io.IOException;
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
        }
    }
}
