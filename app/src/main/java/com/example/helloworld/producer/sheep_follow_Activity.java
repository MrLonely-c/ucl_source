package com.example.helloworld.producer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.HttpUtil;
import com.example.helloworld.R;

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

public class sheep_follow_Activity extends AppCompatActivity  {
    private SearchView searchView;
    private static final String TAG = "tigercheng";
    private SearchListView list=null;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sheep_follow);
        TextView btnback=findViewById(R.id.toolbar_left_tv);
        btnback.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                onBackPressed();
            }
        });

        final TextView re=findViewById(R.id.result);
        searchView=findViewById(R.id.search_view);
        // 4. 设置点击键盘上的搜索按键后的操作（通过回调接口）
        // 参数 = 搜索框输入的内容
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {

                System.out.println("我收到了" + string);
                search_item(string);

                re.setText(id);
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

    private void search_item(String s){
        HttpUtil.sendOKHttp3RequestGET("http://223.3.79.119:8000/sell/sell_state/?GoodsName="+s,

                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: " + e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String resStr = response.body().string();
                        String [] strs = resStr.split("\\}");
                        for(int i=0;i<strs.length;i++){
                            strs[i]+="}";
                            Log.d(TAG, "onResponse: "+strs[i]);
                        }

                        try{
                            for(int i=0;i<strs.length;i++) {
                                JSONObject jsonObjec = new JSONObject(strs[i]);

                                id += jsonObjec.getString("ProductionID");

                            }
//                            jsonObjec.getString("SPReceiveTime");
//                            jsonObjec.getString("SPSelloutTime");
//                            jsonObjec.getString("Price");
//                            jsonObjec.getString("APApprovalRes");
//                            jsonObjec.getString("AccountabilityFlag");
//                            jsonObjec.getString("SellUCLLink");
//                            jsonObjec.getString("GoodsName");
//                            jsonObjec.getString("ConsumerID");

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
                                    Toast.makeText(sheep_follow_Activity.this, ""+resStr, Toast.LENGTH_SHORT).show();
                                }
                            }));
//                            e.printStackTrace();
                        }
                    }
                }




        );


    }



}
