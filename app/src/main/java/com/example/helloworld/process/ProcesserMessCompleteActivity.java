package com.example.helloworld.process;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.BaseUtil;
import com.example.helloworld.R;
import com.example.helloworld.checker.check_inf_markActivity;

public class ProcesserMessCompleteActivity extends AppCompatActivity implements View.OnClickListener {

    private Intent intent = null;

    private Button btnProcesserSave = null;
    private SharedPreferences pref = null;
    private SharedPreferences.Editor prefEditor = null;

    private int characterFlags = 0b000000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_processer_mess_complete);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initUI();
        TextView btnback=findViewById(R.id.toolbar_left_tv);
        btnback.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                onBackPressed();
            }
        });
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        characterFlags = pref.getInt("characterFlags", 0b000000);
    }

    private void initUI() {


        btnProcesserSave = findViewById(R.id.btn_processerSave);
        btnProcesserSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_processerSave:
                prefEditor = pref.edit();
                characterFlags = characterFlags | 0b001000;
                prefEditor.putInt("characterFlags", characterFlags);
                prefEditor.apply();

                if (BaseUtil.isCompleted(pref.getInt("characterFlags", 0b000000), 3)) {
                    Toast.makeText(this, "信息注册成功", Toast.LENGTH_SHORT).show();
                    intent = new Intent(ProcesserMessCompleteActivity.this, ProcessActivity.class);
                    intent.putExtra("title", "检疫结果录入");
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }
}
