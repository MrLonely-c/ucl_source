package com.example.helloworld.producer;

import android.content.Intent;
import android.content.SharedPreferences;
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

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class productionStateActivity extends AppCompatActivity
        implements ProductionStateAdapter.OnRecycleViewItemClickListener {
    private static final String TAG = "tigercheng";

    private List<ProductionState> productionStateList = new ArrayList<>();

    private SharedPreferences pref = null;
    private SharedPreferences.Editor prefEditor = null;

    private Intent intent = null;

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
        initProductionState();
        RecyclerView recyclerView = findViewById(R.id.recycle_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ProductionStateAdapter adapter = new ProductionStateAdapter(productionStateList);
        adapter.setOnRecycleViewItemClickListener(this);

        recyclerView.setAdapter(adapter);
    }

    private void initProductionState() {
        for (int i = 0; i < 20; i++) {
//            int _id = Integer.parseInt("2019030100");
            ProductionState _ps = new ProductionState(
                    2019030100 + i,
                    "健康",
                    107,
                    37);
            productionStateList.add(_ps);
        }

        HttpUtil.sendOKHttp3RequestGET(
                HttpUtil.BASEURL_LOGIN_SIGN_PRODUCE + "/produce/sheep_state",
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String resStr = response.body().string();
                        Log.d(TAG, "code: " + response.code() + "resStr: " + resStr);
                        ArrayList<JSONObject> js = JsonUtil.getJSONArray(resStr);
                        Log.d(TAG, "js.toString: " + js.toString());
                    }
                }
        );
    }

    @Override
    public void onItemClick(View view) {
        Toast.makeText(this, "click:" + view.getTag(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view) {
        Toast.makeText(this, "longclick:" + view.getTag(), Toast.LENGTH_SHORT).show();
    }
}
