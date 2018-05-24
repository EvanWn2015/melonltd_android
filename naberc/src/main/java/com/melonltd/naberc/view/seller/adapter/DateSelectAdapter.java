package com.melonltd.naberc.view.seller.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.melonltd.naberc.R;

import java.util.List;

public class DateSelectAdapter extends RecyclerView.Adapter<DateSelectAdapter.ViewHolder> {
    private Context context;
    private List<String> list;

    public DateSelectAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DateSelectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_select_date_switch, parent, false);
        DateSelectAdapter.ViewHolder vh = new DateSelectAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull DateSelectAdapter.ViewHolder holder, int i) {
        holder.aSwitch.setText(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private Switch aSwitch;

        public ViewHolder(View v) {
            super(v);
            aSwitch = v.findViewById(R.id.sellerDateSelectSwitch);
        }
    }
}
