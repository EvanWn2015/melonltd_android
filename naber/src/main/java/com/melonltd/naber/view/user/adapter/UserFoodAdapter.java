package com.melonltd.naber.view.user.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.common.base.Strings;
import com.melonltd.naber.R;
import com.melonltd.naber.vo.FoodVo;

import java.util.List;


public class UserFoodAdapter extends RecyclerView.Adapter<UserFoodAdapter.ViewHolder> {
    private static final String TAG = UserFoodAdapter.class.getSimpleName();
    private List<FoodVo> listData;
    private View.OnClickListener itemClickListener;

    public UserFoodAdapter(List<FoodVo> listData) {
        this.listData = listData;
    }

    public void setItemClickListener(View.OnClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public UserFoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_food_item, parent, false);
        UserFoodAdapter.ViewHolder vh = new UserFoodAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull UserFoodAdapter.ViewHolder h, int position) {

        h.itemView.setTag(position);
        h.itemView.setOnClickListener(this.itemClickListener);

        if (Strings.isNullOrEmpty(listData.get(position).food_content)){
            h.foodContentText.setText("暫無介紹！");
        } else {
            h.foodContentText.setText(listData.get(position).food_content);
        }

        h.itemNameText.setText(listData.get(position).food_name);
        h.itemPriceText.setText(listData.get(position).default_price);

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemNameText, itemPriceText, foodContentText;

        public ViewHolder(View v) {
            super(v);
            itemNameText = v.findViewById(R.id.ordersItemNameText);
            itemPriceText = v.findViewById(R.id.itemPriceText);
            foodContentText = v.findViewById(R.id.foodContentText);
        }
    }
}

