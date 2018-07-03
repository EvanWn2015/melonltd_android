package com.melonltd.naber.view.seller.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.melonltd.naber.R;
import com.melonltd.naber.model.bean.Model;
import com.melonltd.naber.model.type.SwitchStatus;
import com.melonltd.naber.view.customize.SwitchButton;

public class DateSelectAdapter extends RecyclerView.Adapter<DateSelectAdapter.ViewHolder> {

    private SwitchButton.OnCheckedChangeListener listener;

    public DateSelectAdapter(SwitchButton.OnCheckedChangeListener listener) {
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
        holder.aSwitch.setTag(i);
        holder.dateText.setText(Model.SELLER_BUSINESS_TIME_RANGE.get(i).date);
        SwitchStatus switchStatus = SwitchStatus.of(Model.SELLER_BUSINESS_TIME_RANGE.get(i).status);
        holder.aSwitch.setChecked(switchStatus.getStatus());
        holder.aSwitch.setOnCheckedChangeListener(this.listener);
    }

    @Override
    public int getItemCount() {
        return Model.SELLER_BUSINESS_TIME_RANGE.size();
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
