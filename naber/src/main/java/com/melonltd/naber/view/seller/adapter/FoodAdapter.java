package com.melonltd.naber.view.seller.adapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.common.base.Strings;
import com.melonltd.naber.R;
import com.melonltd.naber.model.bean.Model;
import com.melonltd.naber.view.customize.SwitchButton;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    private SwitchButton.OnCheckedChangeListener switchListener;
    private View.OnClickListener deleteListener, editListener;
    private View.OnLongClickListener copyLongListener;

    public FoodAdapter(SwitchButton.OnCheckedChangeListener switchListener, View.OnClickListener deleteListener, View.OnClickListener editListener, View.OnLongClickListener copyLongListener) {
        this.switchListener = switchListener;
        this.deleteListener = deleteListener;
        this.editListener = editListener;
        this.copyLongListener = copyLongListener;
    }

    @NonNull
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_menu_item, parent, false);
        FoodAdapter.ViewHolder vh = new FoodAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.ViewHolder holder, int position) {

        holder.setTag(position);
        if (!Strings.isNullOrEmpty(Model.SELLER_FOOD_LIST.get(position).photo)){
            holder.itemIconImageView.setImageURI(Uri.parse(Model.SELLER_FOOD_LIST.get(position).photo));
        }else {
            ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithResourceId(R.drawable.naber_icon_logo_reverse).build();
            holder.itemIconImageView.setImageURI(imageRequest.getSourceUri());
        }

        holder.nameText.setText(Model.SELLER_FOOD_LIST.get(position).food_name);
        holder.priceText.setText(Model.SELLER_FOOD_LIST.get(position).default_price);
        holder.menuSwitch.setChecked(Model.SELLER_FOOD_LIST.get(position).status.getStatus());

        holder.itemIconImageView.setOnLongClickListener(this.copyLongListener);
        holder.menuSwitch.setOnCheckedChangeListener(this.switchListener);
        holder.deleteBtn.setOnClickListener(this.deleteListener);
        holder.editBtn.setOnClickListener(this.editListener);
    }

    @Override
    public int getItemCount() {
        return Model.SELLER_FOOD_LIST.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView itemIconImageView;
        private TextView nameText, priceText;
        private Button deleteBtn, editBtn;
        private SwitchButton menuSwitch;

        ViewHolder(View v) {
            super(v);
            this.itemIconImageView = v.findViewById(R.id.ordersItemIconImageView);
            this.nameText = v.findViewById(R.id.ordersItemNameText);
            this.priceText = v.findViewById(R.id.itemPriceText);
            this.deleteBtn = v.findViewById(R.id.deleteBtn);
            this.deleteBtn.setVisibility(View.VISIBLE);
            this.editBtn = v.findViewById(R.id.editBtn);
            this.editBtn.setVisibility(View.VISIBLE);
            this.menuSwitch = v.findViewById(R.id.menuSwitch);
            this.menuSwitch.setVisibility(View.VISIBLE);
            View lineView = v.findViewById(R.id.lineView);
            lineView.setVisibility(View.VISIBLE);
        }

        public void setTag(int position){
            this.nameText.setTag(position);
            this.menuSwitch.setTag(position);
            this.deleteBtn.setTag(position);
            this.editBtn.setTag(position);
            this.itemIconImageView.setTag(position);
        }
    }
}

