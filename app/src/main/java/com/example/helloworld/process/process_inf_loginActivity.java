package com.example.helloworld.process;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.R;
import com.example.helloworld.sell.marketinf_inActivity;
import com.yzq.zxinglibrary.common.Constant;

public class process_inf_loginActivity extends AppCompatActivity {
    private int kind;
    private static final String TAG = "tigercheng";
    private String str;
//    private EditText process_round=null;
    private TextView processer=null;
    private TextView processer_num=null;
    private TextView process_location=null;
    private EditText process_old_id=null;
    private  EditText process_kind_total=null;
    private EditText kind_num=null;
    private EditText process_new_id=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.process_inf_login);
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
        String[] ctype = new String[]{"屠宰", "清洗", "去皮", "包装"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ctype);  //创建一个数组适配器
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式

        final Spinner spinner = super.findViewById(R.id.spinner_1);
        spinner.setAdapter(adapter);

        str = (String) spinner.getSelectedItem();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                //拿到被选择项的值
                str = (String) spinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        Button submit=findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(process_inf_loginActivity.this,
                      str, Toast.LENGTH_SHORT).show();
            }
        });

        Button next=findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent inent=new Intent(process_inf_loginActivity.this,process_inf_loginActivity.class);
                startActivity(inent);
            }
        });

    }
    private void initUI() {


         processer=findViewById(R.id.processer);
         processer_num=findViewById(R.id.processer_num);
        process_location=findViewById(R.id.location);
       process_old_id=findViewById(R.id.old_id);
        process_kind_total=findViewById(R.id.total_kind);
        kind_num=findViewById(R.id.kind_num);
        process_new_id=findViewById(R.id.new_id);
//        get_inf();


    }


}
