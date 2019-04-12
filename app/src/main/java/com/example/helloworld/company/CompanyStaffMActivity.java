package com.example.helloworld.company;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.helloworld.JsonUtil;
import com.example.helloworld.R;
import com.example.helloworld.UCLadapters.StaffAdapter;
import com.example.helloworld.UCLclasses.Staff;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CompanyStaffMActivity extends AppCompatActivity
        implements View.OnClickListener, StaffAdapter.OnRecycleViewItemClickListener {
    private static final String TAG = "tigercheng";
    private Intent intent = null;

    private String staffJsonStr = "";
    private String staffesStr = "";

    private RecyclerView rv_staff = null;
    private List<Staff> staffList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_company_staff_m);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initUI();
    }

    private void initUI() {
        intent = getIntent();

        rv_staff = findViewById(R.id.rv_staff);

        staffJsonStr = intent.getStringExtra("staff");
        try {
            JSONObject staffJson = new JSONObject(staffJsonStr);
            staffesStr = staffJson.getString("json");
            ArrayList<JSONObject> staffes = JsonUtil.getJSONArray(
                    staffesStr.replace("[", "").replace("]", ""),
                    "\\},\\{");

            for (int i = 0; i < staffes.size(); i++) {
                Log.d(TAG, "staffes[" + i + "]: " + staffes.get(i));
                Staff _s = new Staff(
                        staffes.get(i).getString("id"),
                        staffes.get(i).getString("name"),
                        staffes.get(i).getString("INo"),
                        staffes.get(i).getString("CNo")
                );
                staffList.add(_s);
                Log.d(TAG, "_s.getStaffName: " + _s.getStaffName());
            }

            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            rv_staff.setLayoutManager(layoutManager);

            StaffAdapter adapter = new StaffAdapter(staffList);
            StaffAdapter adapter1 = new StaffAdapter(new ArrayList<Staff>());

            adapter.setOnRecycleViewItemClickListener(this);

            Log.d(TAG, "staffList: " + staffList.size() + "_______" + staffList);
            Log.d(TAG, "adapter: " + adapter);

            StaffAdapter adapter2 = new StaffAdapter(staffList);

            rv_staff.setAdapter(adapter);
            Log.d(TAG, "rv_staff.setAdapter(: ");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(View view) {

    }

    @Override
    public void onItemLongClick(View view) {

    }
}
