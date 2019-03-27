package com.example.helloworld.producer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.R;

public class ProducerMessCompleteActivity extends AppCompatActivity
        implements View.OnClickListener {
    private static final String TAG = "tigercheng";

    private Button btnProducerSave = null;

    private Intent intent = null;
    private SharedPreferences pref = null;
    private SharedPreferences.Editor prefEditor = null;

    private int characterFlags = 0b000000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "ProducerMessCompleteActivity onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_producer_mess_complete);
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
        intent = getIntent();


        btnProducerSave = findViewById(R.id.btnProducerSave);
        btnProducerSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnProducerSave:

                prefEditor = pref.edit();
                characterFlags = characterFlags | 0b100000;
                prefEditor.putInt("characterFlags", characterFlags);
                prefEditor.apply();

                Log.d(TAG, "characterFlags:" + Integer.toBinaryString(pref.getInt("characterFlags", 0b111111)));

                if (characterFlags >>> 5 == 0b000001) {
                    Toast.makeText(this, "信息注册成功", Toast.LENGTH_SHORT).show();
                    intent = new Intent(ProducerMessCompleteActivity.this, ProducerActivity.class);

                    startActivity(intent);
                    finish();
                }

                break;
            default:
                break;
        }
    }

}
