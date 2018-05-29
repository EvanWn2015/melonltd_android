package com.melonltd.naberc.view.seller.adapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.melonltd.naberc.R;
import com.melonltd.naberc.view.customize.SwitchButton;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private List<String> listData;
    private SwitchButton.OnCheckedChangeListener switchListener;
    private View.OnClickListener deleteListener, editListener;
    private View.OnLongClickListener copyLongListener;

    public MenuAdapter(List<String> listData) {
        this.listData = listData;
    }

    public void setListener(SwitchButton.OnCheckedChangeListener switchListener, View.OnClickListener deleteListener, View.OnClickListener editListener, View.OnLongClickListener copyLongListener) {
        this.switchListener = switchListener;
        this.deleteListener = deleteListener;
        this.editListener = editListener;
        this.copyLongListener = copyLongListener;
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

        holder.itemIconImageView.setImageURI(Uri.parse("http://zipotesrestaurant.com/images-mexican-salvadorean-restaurant-redwood_city/slides/zipotes_mexican_salvadorean_20.jpg"));
        holder.nameText.setText("Menu Name " + position);
        holder.menuSwitch.setTag(listData.get(position));
        holder.deleteBtn.setTag(listData.get(position));
        holder.editBtn.setTag(listData.get(position));
        holder.itemIconImageView.setTag(listData.get(position));

        holder.itemIconImageView.setOnLongClickListener(this.copyLongListener);
        holder.menuSwitch.setOnCheckedChangeListener(this.switchListener);
        holder.deleteBtn.setOnClickListener(this.deleteListener);
        holder.editBtn.setOnClickListener(this.editListener);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView itemIconImageView;
        private TextView nameText, priceText;
        private Button deleteBtn, editBtn;
        private SwitchButton menuSwitch;

        ViewHolder(View v) {
            super(v);
            itemIconImageView = v.findViewById(R.id.ordersItemIconImageView);
            nameText = v.findViewById(R.id.ordersItemNameText);
            priceText = v.findViewById(R.id.itemPriceText);
            deleteBtn = v.findViewById(R.id.deleteBtn);
            deleteBtn.setVisibility(View.VISIBLE);
            editBtn = v.findViewById(R.id.editBtn);
            editBtn.setVisibility(View.VISIBLE);
            menuSwitch = v.findViewById(R.id.menuSwitch);
            menuSwitch.setVisibility(View.VISIBLE);
            View lineView = v.findViewById(R.id.lineView);
            lineView.setVisibility(View.VISIBLE);
        }

    }
}

