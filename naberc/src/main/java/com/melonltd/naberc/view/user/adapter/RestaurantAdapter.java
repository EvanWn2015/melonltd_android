package com.melonltd.naberc.view.user.adapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.drawee.view.SimpleDraweeView;
import com.melonltd.naberc.R;

import java.util.ArrayList;
import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {
    private static final  String TAG = RestaurantAdapter.class.getSimpleName();
    private List<String> listData;
    private View.OnClickListener itemOnClickListener;

    public RestaurantAdapter(List<String> listData) {
        this.listData = listData;
    }


    public void setItemOnClickListener(View.OnClickListener itemOnClickListener){
        this.itemOnClickListener = itemOnClickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_restaurant_item, parent, false);
        RestaurantAdapter.ViewHolder vh = new RestaurantAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantAdapter.ViewHolder holder, int position) {

        holder.restaurantNameText.setText(listData.get(position) + " XX 店");
        Uri uri = Uri.parse("https://sjhexpress.com/wp-content/uploads/2015/02/HannahRidoutFoodPhotography.jpg");
        holder.restaurantIcon.setImageURI(uri);
        holder.restaurantItem.setTag(position);
        holder.restaurantItem.setOnClickListener(this.itemOnClickListener );
    }


    @Override
    public int getItemCount() {
        return listData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView restaurantIcon;
        ConstraintLayout restaurantItem;
        TextView restaurantNameText, businessTimeText, addressText, distanceText;
        ViewHolder(View v) {
            super(v);
            restaurantItem = v.findViewById(R.id.restaurantItem);
            restaurantIcon = v.findViewById(R.id.restaurantImageView);
            restaurantNameText = v.findViewById(R.id.restaurantNameText);
            businessTimeText = v.findViewById(R.id.businessTimeText);
            addressText = v.findViewById(R.id.addressText);
            distanceText = v.findViewById(R.id.distanceText);
        }
    }
}
