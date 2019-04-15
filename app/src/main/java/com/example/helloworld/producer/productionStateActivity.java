package com.example.helloworld.producer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.HttpUtil;
import com.example.helloworld.JsonUtil;
import com.example.helloworld.R;
import com.example.helloworld.UCLadapters.ProductionStateAdapter;
import com.example.helloworld.UCLclasses.ProductionState;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.spec.PSSParameterSpec;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.SearchView;
import scut.carson_ho.searchview.bCallBack;

public class productionStateActivity extends AppCompatActivity {
    private static final String TAG = "tigercheng";
    public static final int UPDATE_TEXT = 1;

    private SharedPreferences pref = null;
    private SharedPreferences.Editor prefEditor = null;

    private Intent intent = null;

    private SearchView searchView;

    private ArrayList<ProductionState> PSes = new ArrayList<>();

    private RecyclerView rv_production = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_production_state);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }


        initUI();

        pref = PreferenceManager.getDefaultSharedPreferences(this);
//        Toast.makeText(this, "" + pref.getBoolean("rememberPwd", false), Toast.LENGTH_SHORT).show();
        prefEditor = pref.edit();
        prefEditor.apply();

        Log.d(TAG, "SharedPreferences: " + String.valueOf(pref.getInt("characterFlags", 0b000000))
                + "--" + pref.getString("id", "id"));
        Toast.makeText(this,
                String.valueOf(pref.getInt("characterFlags", 0b000000))
                        + pref.getString("id", "id"),
                Toast.LENGTH_SHORT).show();


    }

    private void initUI() {
        rv_production = findViewById(R.id.rv_production);
        searchView = findViewById(R.id.search_view);
        // 4. 设置点击键盘上的搜索按键后的操作（通过回调接口）
        // 参数 = 搜索框输入的内容
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                Log.d(TAG, "SearchAciton: " + string);

                HttpUtil.sendOKHttp3RequestPOST(
                        HttpUtil.BASEURL_LOGIN_SIGN_PRODUCE + "/produce/sheep_state",
                        JsonUtil.getJSON(
                                "RecordID", "3400000000000000",
                                "ConsumerId", "0400000003"
                        ),
                        new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.d(TAG, "onFailure: " + e);
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                final String resStr = response.body().string();
                                Log.d(TAG, "response.code: " + response.code());
                                Log.d(TAG, "SearchAciton resStr: " + resStr);

                                ArrayList<JSONObject> jsonObjects = JsonUtil.getJSONArray(
                                        resStr.replace("[", "").replace("]", ""),
                                        "\\}, \\{");
                                Log.d(TAG, "jsonObjects: " + jsonObjects);
                                for (int i = 0; i < jsonObjects.size(); i++) {
                                    Log.d(TAG, "json[" + i + "]: " + jsonObjects.get(i));
                                    try {
                                        JSONObject jsonObject = jsonObjects.get(i).getJSONObject("fields");
                                        ProductionState _ps = new ProductionState(
                                                jsonObject.getString("RecordID"),
                                                jsonObject.getString("MonitorId"),
                                                jsonObject.getString("State"),
                                                jsonObject.getString("HealthState"),
                                                jsonObject.getString("GPSLocation"),
                                                jsonObject.getString("ActiveDis"),
                                                jsonObject.getString("Weight"),
                                                jsonObject.getString("BodyTemperature"),
                                                jsonObject.getString("UCLLink"),
                                                jsonObject.getString("MonitorRecordTime"),
                                                jsonObject.getString("Flag")
                                        );
                                        PSes.add(_ps);


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        LinearLayoutManager layoutManager = new LinearLayoutManager(productionStateActivity.this);
                                        rv_production.setLayoutManager(layoutManager);

                                        ProductionStateAdapter adapter = new ProductionStateAdapter(PSes);
//                                        adapter.setOnRecycleViewItemClickListener(productionStateActivity.this);

                                        rv_production.setAdapter(adapter);
                                    }
                                });


                            }
                        }
                );
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
}
