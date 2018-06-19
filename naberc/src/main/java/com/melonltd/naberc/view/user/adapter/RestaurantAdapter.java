package com.melonltd.naberc.view.user.adapter;

import android.location.Location;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.common.base.Strings;
import com.melonltd.naberc.R;
import com.melonltd.naberc.util.DistanceTools;
import com.melonltd.naberc.util.Tools;
import com.melonltd.naberc.vo.LocationVo;
import com.melonltd.naberc.vo.RestaurantInfoVo;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {
    private static final String TAG = RestaurantAdapter.class.getSimpleName();
    private List<RestaurantInfoVo> listData;
    private View.OnClickListener itemOnClickListener;
    private Location location;


    public RestaurantAdapter(List<RestaurantInfoVo> listData) {
        this.listData = listData;
    }

    public void setLocation(Location location){
        this.location = location;
    }

    public void setItemOnClickListener(View.OnClickListener itemOnClickListener) {
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
    public void onBindViewHolder(@NonNull final RestaurantAdapter.ViewHolder holder, int position) {
        holder.restaurantItem.setTag(position);
        holder.restaurantItem.setOnClickListener(this.itemOnClickListener);

        if (!Strings.isNullOrEmpty(listData.get(position).photo)){
            holder.restaurantIcon.setImageURI(Uri.parse(listData.get(position).photo));
        }else {
            ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithResourceId(R.drawable.naber_icon_logo_reverse).build();
            holder.restaurantIcon.setImageURI(imageRequest.getSourceUri());
        }

        holder.restaurantNameText.setText(listData.get(position).name);
        holder.businessTimeText.setText("接單時間: " + listData.get(position).store_start + "~" + listData.get(position).store_end);
        holder.addressText.setText(listData.get(position).address);
        String distance = DistanceTools.getGoogleDistance(this.location, LocationVo.of(Double.parseDouble(listData.get(position).latitude), Double.parseDouble(listData.get(position).longitude)));
        holder.distanceText.setText("" + distance);

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
