package com.example.helloworld.producer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
    public static final int UPDATE_TEXT=1;
    private String market_name="  ";
    private String passer_name="  ";
    private String checker_name="  ";
    private String process_nam="  ";
    private String check_time="  ";
    private String check_application="  ";
    private String check_place="  ";
    private String in_time="  ";
    private String process_place;
    private int count=0;
    private String process_time="  ";
    private String recieve_time="  ";
    private String process_id="  ";
    private int flag=0;
    private TextView re;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){

            switch(msg.what){
                case UPDATE_TEXT:
                    if(flag==1) {
                        re=findViewById(R.id.result);
                        re.setText("这头羊出生于胜利牧场\n");
                        re.append("在" + check_time);

                        re.append("于");
                        re.append(check_place + "\n");
                        re.append("通过了检疫员");
                        re.append(checker_name);
                        re.append("的检疫，进入了加工厂\n");
                        re.append("");
                        re.append(process_time);
                        re.append(",");
                        re.append("在" + process_place);
                        re.append("完成了加工操作" + "\n");
                        re.append(recieve_time + "进入了超市");

                    }


                    break;
                default:
                    break;
            }
        }
    };
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


        searchView=findViewById(R.id.search_view);
        // 4. 设置点击键盘上的搜索按键后的操作（通过回调接口）
        // 参数 = 搜索框输入的内容
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {

                System.out.println("我收到了" + string);
                sheep_f(string);

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Message message = new Message();
                        message.what = UPDATE_TEXT;
                        handler.sendMessage(message);
                    }
                }).start();
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

    private void sheep_f(String s){
        HttpUtil.sendOKHttp3RequestGET("http://223.3.90.242:8000/user/origin/?ProductionID="+s,

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
                            flag=1;
                            JSONObject jsonObjec = new JSONObject(strs[0]);
                            checker_name=jsonObjec.getString("QuarantinerName");
                            Log.d(TAG, "onResponse: "+checker_name);
                            check_time=jsonObjec.getString("QuarantineTime");
                            Log.d(TAG, "onResponse: "+check_time);
                            check_place=jsonObjec.getString("QuarantineLocation");
                            Log.d(TAG, "onResponse: "+check_place);
                            JSONObject jsonObjec1 = new JSONObject(strs[2]);
                            process_place= jsonObjec1.getString("ProcessLocation");
                            process_time=jsonObjec1.getString("ProcessTime");
                            process_id=jsonObjec1.getString("ConsumerId");
                            JSONObject jsonObjec2 = new JSONObject(strs[4]);
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
