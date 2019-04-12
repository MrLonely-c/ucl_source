package com.example.helloworld.loginAndSign;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.BaseUtil;
import com.example.helloworld.R;
import com.example.helloworld.baseuser.BaseUserActivity;
import com.example.helloworld.checker.checkerActivity;
import com.example.helloworld.process.ProcessActivity;
import com.example.helloworld.process.ProcesserMessCompleteActivity;
import com.example.helloworld.producer.ProducerActivity;
import com.example.helloworld.producer.ProducerMessCompleteActivity;


import com.example.helloworld.checker.CheckerMessCompleteActivity;
import com.example.helloworld.sell.SellerActivity;
import com.example.helloworld.sell.SellerMessCompleteActivity;
import com.example.helloworld.transporter.TransporterActivity;
import com.example.helloworld.transporter.TransporterMessCompleteActivity;


public class ResponsibleChooseActivity extends AppCompatActivity
        implements View.OnClickListener {

    private static final String TAG = "tigercheng";

    //控件
    private ImageView ivProducer = null;
    private ImageView ivQuarantiner = null;
    private ImageView ivProcesser = null;
    private ImageView ivTransporter = null;
    private ImageView ivSeller = null;
    private ImageView ivBaseUser = null;

    private TextView txReLogin = null;

    private Intent intent = null;
    private SharedPreferences pref = null;
    private SharedPreferences.Editor prefEditor = null;

    private int characterFlags = 0b000000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_responsible_choose);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        TextView btnback=findViewById(R.id.toolbar_left_tv);
        btnback.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                onBackPressed();
            }
        });
        initUI();

        pref = PreferenceManager.getDefaultSharedPreferences(this);
//        Toast.makeText(this, "" + pref.getBoolean("rememberPwd", false), Toast.LENGTH_SHORT).show();
        prefEditor = pref.edit();
        prefEditor.apply();


        Toast.makeText(this, String.valueOf(pref.getInt("characterFlags", 0b000000)), Toast.LENGTH_SHORT).show();
        intent = getIntent();
//        characters = intent.get("characters");
    }

    private void initUI() {
        ivProducer = findViewById(R.id.iv_producer);
        ivProducer.setOnClickListener(this);
        ivQuarantiner = findViewById(R.id.iv_quarantiner);
        ivQuarantiner.setOnClickListener(this);
        ivProcesser = findViewById(R.id.iv_processer);
        ivProcesser.setOnClickListener(this);
        ivTransporter = findViewById(R.id.iv_transporter);
        ivTransporter.setOnClickListener(this);
        ivSeller = findViewById(R.id.iv_seller);
        ivSeller.setOnClickListener(this);
        ivBaseUser = findViewById(R.id.iv_baseUser);
        ivBaseUser.setOnClickListener(this);

        txReLogin = findViewById(R.id.tx_reLogin);
        txReLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tx_reLogin:
                prefEditor.putBoolean("rememberPwd", false);
                prefEditor.apply();
                intent = new Intent(ResponsibleChooseActivity.this, LoginAndSignActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_producer:
                if (BaseUtil.isCompleted(pref.getInt("characterFlags", 0b000000), 1)) {//生产者信息完善信息完善
                    intent = new Intent(ResponsibleChooseActivity.this, ProducerActivity.class);

                    startActivity(intent);
                    //跳转到生产者操作界面
                } else {
                    intent = new Intent(ResponsibleChooseActivity.this, ProducerMessCompleteActivity.class);
                    intent.putExtra("title", "生产者信息完善");
                    startActivity(intent);
                }
                break;
            case R.id.iv_quarantiner:
                if (BaseUtil.isCompleted(pref.getInt("characterFlags", 0b000000), 2)) {
                    intent = new Intent(ResponsibleChooseActivity.this, checkerActivity.class);
                    //跳转到检疫人员操作界面
                    startActivity(intent);
                } else {
                    intent = new Intent(ResponsibleChooseActivity.this, CheckerMessCompleteActivity.class);
                    intent.putExtra("title", "检疫员信息完善");
                    startActivity(intent);
                }
                break;
            case R.id.iv_processer:
                if (BaseUtil.isCompleted(pref.getInt("characterFlags", 0b000000), 3)) {
                    intent = new Intent(ResponsibleChooseActivity.this, ProcessActivity.class);
                    //跳转到加工人员操作界面
                    startActivity(intent);
                } else {
                    intent = new Intent(ResponsibleChooseActivity.this, ProcesserMessCompleteActivity.class);
                    intent.putExtra("title", "加工员信息完善");
                    startActivity(intent);
                }
                break;
            case R.id.iv_transporter:
                if (BaseUtil.isCompleted(pref.getInt("characterFlags", 0b000000), 4)) {
                    intent = new Intent(ResponsibleChooseActivity.this, TransporterActivity.class);
                    //跳转到运输人员操作界面
                    startActivity(intent);
                } else {
                    intent = new Intent(ResponsibleChooseActivity.this, TransporterMessCompleteActivity.class);
                    intent.putExtra("title", "运输员信息完善");
                    startActivity(intent);
                }
                break;
            case R.id.iv_seller:
                if (BaseUtil.isCompleted(pref.getInt("characterFlags", 0b000000), 5)) {
                    intent = new Intent(ResponsibleChooseActivity.this, SellerActivity.class);
                    //跳转到销售人员操作界面
                    startActivity(intent);
                } else {
                    intent = new Intent(ResponsibleChooseActivity.this, SellerMessCompleteActivity.class);
                    intent.putExtra("title", "销售员信息完善");
                    startActivity(intent);
                }
                break;
            case R.id.iv_baseUser:
                intent =new Intent(ResponsibleChooseActivity.this, BaseUserActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

//    //信息是否完善 完善返回true
//    private boolean isCompleted(int character) {
//        boolean result = false;
//        characterFlags = pref.getInt("characterFlags", 0b000000);
//        Log.d(TAG, "the characterFlags: " + Integer.toBinaryString(characterFlags));
//        switch (character) {
//            case 1:
//                result = characterFlags >>> 5 == 0b000001;
////                Log.d(TAG, "" + Integer.toBinaryString(characterFlags >>> 5));
//                Log.d(TAG, "characterFlags = 1 completed ? " + result);
//                break;
//            case 2:
////                Log.d(TAG, "first: " + Integer.toBinaryString(characterFlags));
////                Log.d(TAG, "second: " + Integer.toBinaryString(0b010000));
////                result = (characterFlags & 0b010000) >>> 4 == 0b000001;
//                result = (characterFlags & 0b010000) > 0;
////                Log.d(TAG, "characterFlags 2 and: " + Integer.toBinaryString(characterFlags & 0b010000));
//                Log.d(TAG, "characterFlags = 2 completed ? " + result);
//                break;
//            case 3:
////                result = (characterFlags & 0b001000) >>> 3 == 0b000001;
//                result = (characterFlags & 0b001000) > 0;
//                Log.d(TAG, "characterFlags = 3 completed ? " + result);
//                break;
//            case 4:
////                result = (characterFlags & 0b000100) >>> 2 == 0b000001;
//                result = (characterFlags & 0b000100) > 0;
//                Log.d(TAG, "characterFlags = 4 completed ? " + result);
//                break;
//            case 5:
////                result = (characterFlags & 0b000010) >>> 1 == 0b000001;
//                result = (characterFlags & 0b000010) > 0;
//                Log.d(TAG, "characterFlags = 5 completed ? " + result);
//                break;
//        }
//        Log.d(TAG, "isCompleted: " + result);
//        return result;
//    }
}
