package com.melonltd.naberc.view.seller.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.melonltd.naberc.R;

import java.util.List;

public class OrdersLogsAdapter extends RecyclerView.Adapter<OrdersLogsAdapter.ViewHolder> {
    private List<String> listData;
    private Context context;
    private View.OnClickListener btnListener, itemListener;

    public OrdersLogsAdapter(List<String> listData) {
        this.listData = listData;
    }

    public void setClickListener(View.OnClickListener btnOnClickListener, View.OnClickListener itemOnClickListener) {
        this.btnListener = btnOnClickListener;
        this.itemListener = itemOnClickListener;
    }

    @NonNull
    @Override
    public OrdersLogsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_orders_logs_items, parent, false);
        OrdersLogsAdapter.ViewHolder vh = new OrdersLogsAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersLogsAdapter.ViewHolder holder, int position) {
        holder.statusBtn.setText("test:" + position);
        if (position % 2 == 0) {
            holder.statusBtn.setBackgroundColor(this.context.getResources().getColor(R.color.naber_basis_green));
        }

        holder.amountText.setText("test:" + position);
        holder.statusBtn.setTag(listData.get(position));
        holder.statusBtn.setOnClickListener(this.btnListener);
        holder.logsItemText.setTag(listData.get(position));
        holder.logsItemText.setOnClickListener(this.itemListener);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private Button statusBtn;
        private TextView amountText, logsItemText;

        ViewHolder(View v) {
            super(v);
            statusBtn = v.findViewById(R.id.statusBtn);
            amountText = v.findViewById(R.id.amountText);
            logsItemText = v.findViewById(R.id.logsItemText);
        }
    }
}

