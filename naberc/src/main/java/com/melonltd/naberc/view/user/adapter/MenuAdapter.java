package com.melonltd.naberc.view.user.adapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.melonltd.naberc.R;

import java.util.List;


public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private static final String TAG = MenuAdapter.class.getSimpleName();
    private List<String> listData;
    private View.OnClickListener itemClickListener;

    public MenuAdapter(List<String> listData) {
        this.listData = listData;
    }

    public void setItemClickListener(View.OnClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_menu_item, parent, false);
        MenuAdapter.ViewHolder vh = new MenuAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.ViewHolder h, int position) {
        h.itemView.setTag(position);
        h.itemView.setOnClickListener(this.itemClickListener);
        h.itemPriceText.setText("3" + position);
        h.itemIconImageView.setImageURI(Uri.parse("http://zipotesrestaurant.com/images-mexican-salvadorean-restaurant-redwood_city/slides/zipotes_mexican_salvadorean_20.jpg"));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView itemIconImageView;
        private TextView itemNameText, itemPriceText;

        public ViewHolder(View v) {
            super(v);
            itemIconImageView = v.findViewById(R.id.ordersItemIconImageView);
            itemNameText = v.findViewById(R.id.ordersItemNameText);
            itemPriceText = v.findViewById(R.id.itemPriceText);
        }
    }
}

