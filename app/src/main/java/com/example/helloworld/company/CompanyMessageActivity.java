package com.example.helloworld.company;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.helloworld.R;

import org.json.JSONException;
import org.json.JSONObject;

public class CompanyMessageActivity extends AppCompatActivity {
    private static final String TAG = "tigercheng";
    private Intent intent = null;

    private String companyMess = "";
    private int flag = 0;
    private JSONObject mess = null;

    private ScrollView sv_producer = null;
    private ScrollView sv_quarantiner = null;
    private ScrollView sv_processer = null;
    private ScrollView sv_transporter = null;
    private ScrollView sv_seller = null;

    //农场
    private EditText etxCompanyName1 = null;
    private EditText etxCorporate1 = null;
    private EditText etxCorporateIDCardNo1 = null;
    private EditText etxCompanyContactNo1 = null;
    private EditText etxCompanySignUpTime1 = null;
    private EditText etxLoginName1 = null;
    private EditText etxLoginPassword1 = null;
    private EditText etxTradeLocation1 = null;

    //检疫局
    private EditText etxCompanyName2 = null;
    private EditText etxTradeLocation2 = null;
    private EditText etxLoginName2 = null;
    private EditText etxLoginPassword2 = null;

    //加工厂
    private EditText etxCompanyName3 = null;
    private EditText etxCorporate3 = null;
    private EditText etxCorporateIDCardNo3 = null;
    private EditText etxCompanyContactNo3 = null;
    private EditText etxCompanySignUpTime3 = null;
    private EditText etxTradeLocation3 = null;
    private EditText etxLoginName3 = null;
    private EditText etxLoginPassword3 = null;

    //运输公司
    private EditText etxCompanyName4 = null;
    private EditText etxCorporate4 = null;
    private EditText etxCorporateIDCardNo4 = null;
    private EditText etxCompanyContactNo4 = null;
    private EditText etxTradeLocation4 = null;
    private EditText etxLoginName4 = null;
    private EditText etxLoginPassword4 = null;

    //销售
    private EditText etxCompanyName5 = null;
    private EditText etxCorporate5 = null;
    private EditText etxCorporateIDCardNo5 = null;
    private EditText etxCompanyContactNo5 = null;
    private EditText etxTradeLocation5 = null;
    private EditText etxLoginName5 = null;
    private EditText etxLoginPassword5 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_company_message);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initUI();
    }

    private void initUI() {
        intent = getIntent();

        sv_producer = findViewById(R.id.sv_producer);
        sv_quarantiner = findViewById(R.id.sv_quarantiner);
        sv_processer = findViewById(R.id.sv_processer);
        sv_transporter = findViewById(R.id.sv_transporter);
        sv_seller = findViewById(R.id.sv_seller);

        //农场
        etxCompanyName1 = findViewById(R.id.etxCompanyName1);
        etxCorporate1 = findViewById(R.id.etxCorporate1);
        etxCorporateIDCardNo1 = findViewById(R.id.etxCorporateIDCardNo1);
        etxCompanyContactNo1 = findViewById(R.id.etxCompanyContactNo1);
        etxCompanySignUpTime1 = findViewById(R.id.etxCompanySignUpTime1);
        etxLoginName1 = findViewById(R.id.etxLoginName1);
        etxLoginPassword1 = findViewById(R.id.etxLoginPassword1);
        etxTradeLocation1 = findViewById(R.id.etxTradeLocation1);

        //检疫局
        etxCompanyName2 = findViewById(R.id.etxCompanyName2);
        etxTradeLocation2 = findViewById(R.id.etxTradeLocation2);
        etxLoginName2 = findViewById(R.id.etxLoginName2);
        etxLoginPassword2 = findViewById(R.id.etxLoginPassword2);

        //加工厂
        etxCompanyName3 = findViewById(R.id.etxCompanyName3);
        etxCorporate3 = findViewById(R.id.etxCorporate3);
        etxCorporateIDCardNo3 = findViewById(R.id.etxCorporateIDCardNo3);
        etxCompanyContactNo3 = findViewById(R.id.etxCompanyContactNo3);
        etxCompanySignUpTime3 = findViewById(R.id.etxCompanySignUpTime3);
        etxTradeLocation3 = findViewById(R.id.etxTradeLocation3);
        etxLoginName3 = findViewById(R.id.etxLoginName3);
        etxLoginPassword3 = findViewById(R.id.etxLoginPassword3);

        //运输公司
        etxCompanyName4 = findViewById(R.id.etxCompanyName4);
        etxCorporate4 = findViewById(R.id.etxCorporate4);
        etxCorporateIDCardNo4 = findViewById(R.id.etxCorporateIDCardNo4);
        etxCompanyContactNo4 = findViewById(R.id.etxCompanyContactNo4);
        etxTradeLocation4 = findViewById(R.id.etxTradeLocation4);
        etxLoginName4 = findViewById(R.id.etxLoginName4);
        etxLoginPassword4 = findViewById(R.id.etxLoginPassword4);

        //销售
        etxCompanyName5 = findViewById(R.id.etxCompanyName5);
        etxCorporate5 = findViewById(R.id.etxCorporate5);
        etxCorporateIDCardNo5 = findViewById(R.id.etxCorporateIDCardNo5);
        etxCompanyContactNo5 = findViewById(R.id.etxCompanyContactNo5);
        etxTradeLocation5 = findViewById(R.id.etxTradeLocation5);
        etxLoginName5 = findViewById(R.id.etxLoginName5);
        etxLoginPassword5 = findViewById(R.id.etxLoginPassword5);

        companyMess = intent.getStringExtra("companyMess");
        try {
            mess = new JSONObject(companyMess);
            companyMess = mess.getString("jsonObject");
            mess = new JSONObject(companyMess);
            flag = Integer.valueOf(mess.getString("Flag"));
            setVisible(flag);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setVisible(int flag) throws JSONException {
        switch (flag) {
            case 1:
                setInvisible();
                sv_producer.setVisibility(View.VISIBLE);
                edit(etxCompanyName1, "CompanyName");
                edit(etxCorporate1, "CorporateName");
                edit(etxCompanySignUpTime1, "RegisterTime");
                edit(etxCorporateIDCardNo1, "CorporateIDNo");
                edit(etxTradeLocation1, "OperatingPlace");
                edit(etxLoginName1, "LoginName");
                edit(etxLoginPassword1, "PASSWORD");
                break;

            case 2:
                setInvisible();
                sv_quarantiner.setVisibility(View.VISIBLE);
                edit(etxCompanyName2, "CompanyName");
                edit(etxTradeLocation2, "OperatingPlace");
                edit(etxLoginName2, "LoginName");
                edit(etxLoginPassword2, "PASSWORD");
                break;

            case 3:
                setInvisible();
                sv_processer.setVisibility(View.VISIBLE);
                edit(etxCompanyName3, "CompanyName");
                edit(etxCorporate3, "CorporateName");
                edit(etxCorporateIDCardNo3, "CorporateIDNo");
                edit(etxCompanyContactNo3, "CorporateContactNo");
                edit(etxCompanySignUpTime3, "RegisterTime");
                edit(etxTradeLocation3, "OperatingPlace");
                edit(etxLoginName3, "LoginName");
                edit(etxLoginPassword3, "PASSWORD");
                break;

            case 4:
                setInvisible();
                sv_transporter.setVisibility(View.VISIBLE);

                edit(etxCompanyName4, "CompanyName");
                edit(etxCorporate4, "CorporateName");
                edit(etxCorporateIDCardNo4, "CorporateIDNo");
                edit(etxCompanyContactNo4, "CorporateContactNo");
                edit(etxTradeLocation4, "OperatingPlace");
                edit(etxLoginName4, "LoginName");
                edit(etxLoginPassword4, "PASSWORD");
                break;

            case 5:
                setInvisible();
                sv_seller.setVisibility(View.VISIBLE);

                edit(etxCompanyName5, "CompanyName");
                edit(etxCorporate5, "CorporateName");
                edit(etxCorporateIDCardNo5, "CorporateIDNo");
                edit(etxCompanyContactNo5, "CorporateContactNo");
                edit(etxTradeLocation5, "OperatingPlace");
                edit(etxLoginName5, "LoginName");
                edit(etxLoginPassword5, "PASSWORD");
                break;
        }
    }

    private void setInvisible() {
        sv_producer.setVisibility(View.INVISIBLE);
        sv_quarantiner.setVisibility(View.INVISIBLE);
        sv_processer.setVisibility(View.INVISIBLE);
        sv_transporter.setVisibility(View.INVISIBLE);
        sv_seller.setVisibility(View.INVISIBLE);
    }

    private void edit(EditText editText, String str) throws JSONException {
        editText.setEnabled(true);
        Log.d(TAG, "edit: " + mess.getString(str));
        editText.setText(mess.getString(str));
        editText.setEnabled(false);
    }
}
