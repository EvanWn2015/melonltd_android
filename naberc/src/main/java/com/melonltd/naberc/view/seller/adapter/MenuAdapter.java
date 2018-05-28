package com.melonltd.naberc.view.seller.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.melonltd.naberc.R;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private List<String> listData;
    private CompoundButton.OnCheckedChangeListener switchListener;
    private View.OnClickListener deleteListener;

    public MenuAdapter(List<String> listData) {
        this.listData = listData;
    }

    public void setListener(CompoundButton.OnCheckedChangeListener switchListener, View.OnClickListener deleteListener) {
        this.switchListener = switchListener;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_menu_item, parent, false);
        MenuAdapter.ViewHolder vh = new MenuAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.ViewHolder holder, int position) {
        holder.nameText.setText("Menu Name " + position);
        holder.menuSwitch.setTag(listData.get(position));
        holder.deleteBtn.setTag(listData.get(position));
        holder.menuSwitch.setOnCheckedChangeListener(this.switchListener);
        holder.deleteBtn.setOnClickListener(this.deleteListener);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameText, priceText;
        private Button deleteBtn;
        private Switch menuSwitch;

        ViewHolder(View v) {
            super(v);
            nameText = v.findViewById(R.id.ordersItemNameText);
            priceText = v.findViewById(R.id.itemPriceText);
            deleteBtn = v.findViewById(R.id.deleteBtn);
            deleteBtn.setVisibility(View.VISIBLE);
            menuSwitch = v.findViewById(R.id.menuSwitch);
            menuSwitch.setVisibility(View.VISIBLE);
            View lineView = v.findViewById(R.id.lineView);
            lineView.setVisibility(View.VISIBLE);
        }

    }
}

