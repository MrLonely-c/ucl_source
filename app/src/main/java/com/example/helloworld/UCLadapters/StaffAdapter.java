package com.example.helloworld.UCLadapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.helloworld.R;
import com.example.helloworld.UCLclasses.Staff;

import java.util.List;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.ViewHolder> {
    private static final String TAG = "tigercheng";

    private List<Staff> mStaffList;

    private StaffAdapter.OnRecycleViewItemClickListener onRecycleViewItemClickListener;

    public StaffAdapter(List<Staff> mStaffList) {
        this.mStaffList = mStaffList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d(TAG, "onCreateViewHolder: -------------------------");
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.staff_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onRecycleViewItemClickListener.onItemClick(v);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onRecycleViewItemClickListener.onItemLongClick(v);
                return true;
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StaffAdapter.ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: --------------------------");
        //i--position
        Staff _s = mStaffList.get(i);
        viewHolder.tvStaffID.setText(String.valueOf(_s.getStaffID()));
        viewHolder.tvStaffName.setText(_s.getStaffName());
        viewHolder.tvIDCardNo.setText(String.valueOf(_s.getIDCardNo()));
        viewHolder.tvContactNo.setText((String.valueOf(_s.getContactNo())));

        //在tag中设置索引信息
        viewHolder.itemView.setTag(i);
    }

    @Override
    public int getItemCount() {
        return mStaffList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvStaffID;
        TextView tvStaffName;
        TextView tvIDCardNo;
        TextView tvContactNo;

        public ViewHolder(@NonNull View view) {
            super(view);
            tvStaffID = view.findViewById(R.id.tvStaffID);
            tvStaffName = view.findViewById(R.id.tvStaffName);
            tvIDCardNo = view.findViewById(R.id.tvIDCardNo);
            tvContactNo = view.findViewById(R.id.tvContactNo);
        }
    }

    //点击事件回调接口
    public interface OnRecycleViewItemClickListener {
        void onItemClick(View view);

        void onItemLongClick(View view);

//        void onItemClick(int position);
//
//        void onItemLongClick(int position);
    }

    //提供设置点击事件监听器
    public void setOnRecycleViewItemClickListener(
            StaffAdapter.OnRecycleViewItemClickListener itemClickListener) {
        onRecycleViewItemClickListener = itemClickListener;
    }
}
