package com.melonltd.naberc.view.seller.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.melonltd.naberc.R;
import com.melonltd.naberc.view.customize.SwitchButton;

import java.util.List;

public class DateSelectAdapter extends RecyclerView.Adapter<DateSelectAdapter.ViewHolder> {
    //    private Context context;
    private List<String> list;
    private SwitchButton.OnCheckedChangeListener listener;

    public DateSelectAdapter(Context context, List<String> list, SwitchButton.OnCheckedChangeListener listener) {
//        this.context = context;
        this.list = list;
        this.listener = listener;
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
        holder.dateText.setText("00:00 ~ 00:0" + i);
        holder.aSwitch.setTag(list.get(i));
        holder.aSwitch.setOnCheckedChangeListener(this.listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView dateText;
        private SwitchButton aSwitch;

        public ViewHolder(View v) {
            super(v);
            dateText = v.findViewById(R.id.dateText);
            aSwitch = v.findViewById(R.id.sellerDateSelectSwitch);
        }
    }
}
