package com.example.helloworld.UCLadapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.helloworld.R;
import com.example.helloworld.UCLclasses.ProductionState;
import com.yzq.zxinglibrary.view.ViewfinderView;

import java.util.List;

public class ProductionStateAdapter
        extends RecyclerView.Adapter<ProductionStateAdapter.ViewHolder> {

    private List<ProductionState> mProductionList;

    private OnRecycleViewItemClickListener onRecycleViewItemClickListener;

    public ProductionStateAdapter(List<ProductionState> mProductionList) {
        this.mProductionList = mProductionList;
    }

    @NonNull
    @Override
    public ProductionStateAdapter.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.production_state_item, viewGroup, false);
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
    public void onBindViewHolder(
            @NonNull ProductionStateAdapter.ViewHolder viewHolder, int i) {
        //i--position
        ProductionState _ps = mProductionList.get(i);
        viewHolder.tvProductionState1.setText(String.valueOf(_ps.getMonitorId()));
        viewHolder.tvProductionState2.setText(String.valueOf(_ps.getState()));
        viewHolder.tvProductionState3.setText(String.valueOf(_ps.getHealthState()));
        viewHolder.tvProductionState4.setText(String.valueOf(_ps.getGPSLocation()));
        viewHolder.tvProductionState5.setText(String.valueOf(_ps.getActiveDis() + " (m)"));
        viewHolder.tvProductionState6.setText(String.valueOf(_ps.getBodyTemperature() + " (℃)"));
        viewHolder.tvProductionState7.setText(String.valueOf(_ps.getWeight() + " (kg)"));
        viewHolder.time.setText(String.valueOf(
                _ps.getMonitorRecordTime()
                        .replace("T", " ")
                        .replaceAll("Z", "")));

        //在tag中设置索引信息
        viewHolder.itemView.setTag(i);
    }

    @Override
    public int getItemCount() {
        return mProductionList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductionState1;
        TextView tvProductionState2;
        TextView tvProductionState3;
        TextView tvProductionState4;
        TextView tvProductionState5;
        TextView tvProductionState6;
        TextView tvProductionState7;
        TextView time;

        public ViewHolder(@NonNull View view) {
            super(view);
            tvProductionState1 = view.findViewById(R.id.tvProductionState1);
            tvProductionState2 = view.findViewById(R.id.tvProductionState2);
            tvProductionState3 = view.findViewById(R.id.tvProductionState3);
            tvProductionState4 = view.findViewById(R.id.tvProductionState4);
            tvProductionState5 = view.findViewById(R.id.tvProductionState5);
            tvProductionState6 = view.findViewById(R.id.tvProductionState6);
            tvProductionState7 = view.findViewById(R.id.tvProductionState7);
            time = view.findViewById(R.id.label_time);
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
            OnRecycleViewItemClickListener itemClickListener) {
        onRecycleViewItemClickListener = itemClickListener;
    }
}
