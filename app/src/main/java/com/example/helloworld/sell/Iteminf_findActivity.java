package com.example.helloworld.sell;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.HttpUtil;
import com.example.helloworld.JsonUtil;
import com.example.helloworld.R;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.SearchListView;
import scut.carson_ho.searchview.SearchView;
import scut.carson_ho.searchview.bCallBack;

public class Iteminf_findActivity extends AppCompatActivity  {
    private SearchView searchView;
    private static final String TAG = "tigercheng";
    private SearchListView list=null;

    private EditText i_name;
    private EditText i_n;
    public static final int UPDATE_TEXT=1;
private String[] id=new String[100];
private int flag=0;

    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            LayoutInflater inflater = LayoutInflater.from(Iteminf_findActivity.this);
            // 获取需要被添加控件的布局
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.item_find);
            // 获取需要添加的布局
            LinearLayout layout = (LinearLayout) inflater.inflate(
                    R.layout.itemrecord, null).findViewById(R.id.LinearLayout_item);
            // 将布局加入到当前布局中
            linearLayout.addView(layout);
            flag++;
            i_name=(EditText)findViewById(R.id.item_id);

            switch(flag){
                case 1:
                    i_name.setId(R.id.etName_1);
                    i_name.append(id[flag-1]);
                    break;
                case 2:
                    i_name.setId(R.id.etName_2);
                    i_name.append(id[flag-1]);
                    break;
                case 3:
                    i_name.setId(R.id.etName_3);
                    i_name.append(id[flag-1]);
                    break;
                case 4:
                    i_name.setId(R.id.etName_4);
                    i_name.append(id[flag-1]);
                    break;
                case 5:
                    i_name.setId(R.id.etName_5);
                    i_name.append(id[flag-1]);
                    break;
                case 6:
                    i_name.setId(R.id.etName_6);
                    i_name.append(id[flag-1]);
                    break;
                case 7:
                    i_name.setId(R.id.etName_7);
                    i_name.append(id[flag-1]);
                    break;
                case 8:
                    i_name.setId(R.id.etName_8);
                    i_name.append(id[flag-1]);
                    break;
                case 9:
                    i_name.setId(R.id.etName_9);
                    i_name.append(id[flag-1]);
                    break;
                case 10:
                    i_name.setId(R.id.etName_10);
                    i_name.append(id[flag-1]);
                    break;
                case 11:
                    i_name.setId(R.id.etName_11);
                    i_name.append(id[flag-1]);
                    break;
                case 12:
                    i_name.setId(R.id.etName_12);
                    i_name.append(id[flag-1]);
                    break;
                case 13:
                    i_name.setId(R.id.etName_13);
                    i_name.append(id[flag-1]);
                    break;
                case 14:
                    i_name.setId(R.id.etName_14);
                    i_name.append(id[flag-1]);
                    break;
default:break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.iteminf_find);
        TextView btnback=findViewById(R.id.toolbar_left_tv);
        btnback.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                onBackPressed();
            }
        });


        searchView=findViewById(R.id.search_view);
        // 4. 设置点击键盘上的搜索按键后的操作（通过回调接口）
        // 参数 = 搜索框输入的内容
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {

                System.out.println("我收到了" + string);
                search_item(string);

            }
        });

        // 5. 设置点击返回按键后的操作（通过回调接口）
        searchView.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                finish();
            }
        });


    }

    private void search_item(String s) {

        HttpUtil.sendOKHttp3RequestGET("http://223.3.73.85:8000/sell/sell_state/?GoodsName=" + s,

                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: " + e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String resStr = response.body().string();
                        String[] strs = resStr.split("\\}");
                        for (int i = 0; i < strs.length; i++) {

                            strs[i] += "}";
                            Log.d(TAG, "onResponse: " + strs[i]);
                        }

                        try {
                            for (int i = 0; i < strs.length; i++) {
                                JSONObject jsonObjec = new JSONObject(strs[i]);

                                id[i] = jsonObjec.getString("ProductionID");
                                Log.d(TAG, "id[i]: " + id[i]);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {

                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }


                                            Message message = new Message();
                                            message.what = UPDATE_TEXT;
                                            handler.sendMessage(message);

                                    }
                                }).start();



                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d(TAG, "code: " + response.code());
                        Log.d(TAG, "resStr: " + resStr);
                        try {
                            JSONObject resJson = new JSONObject(resStr);
                            Log.d(TAG, "resJson: " + resJson.toString());

                        } catch (JSONException e) {
                            Log.d(TAG, "JSONException: " + e.toString());
                            runOnUiThread(new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Iteminf_findActivity.this, "" + resStr, Toast.LENGTH_SHORT).show();
                                }
                            }));
//                            e.printStackTrace();
                        }
                    }
                }


        );


    }



}
