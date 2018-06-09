package com.melonltd.naberc.view.user.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.melonltd.naberc.R;

import java.util.List;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private static final String TAG = HistoryAdapter.class.getSimpleName();
    private List<String> listData;
    private View.OnClickListener ItemClickListener;

    public HistoryAdapter(List<String> listDate) {
        this.listData = listDate;
    }

    public void setListener(View.OnClickListener ItemClickListener){
        this.ItemClickListener =ItemClickListener;
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_history_item, parent, false);
        HistoryAdapter.ViewHolder vh = new HistoryAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {


        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(this.ItemClickListener);
        holder.restaurantNameText.setText("營業時間 10:00~ 11:0" + listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView restaurantNameText, totalAmountText, orderStatusText, getOrderTimeText;

        public ViewHolder(View v) {
            super(v);
            restaurantNameText = v.findViewById(R.id.restaurantNameText);
            totalAmountText = v.findViewById(R.id.totalAmountText);
            orderStatusText = v.findViewById(R.id.orderStatusText);
            getOrderTimeText = v.findViewById(R.id.getOrderTimeText);
        }
    }
}


