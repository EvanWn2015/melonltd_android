package com.melonltd.naber.view.user.adapter;

import android.net.Uri;
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
import com.melonltd.naber.R;
import com.melonltd.naber.vo.RestaurantInfoVo;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {
    private static final String TAG = RestaurantAdapter.class.getSimpleName();
    private View.OnClickListener itemOnClickListener;
    private List<RestaurantInfoVo> list;
//    private Location location;

//    public void setLocation(Location location){
//        this.location = location;
//    }

    public RestaurantAdapter(List<RestaurantInfoVo> list, View.OnClickListener itemOnClickListener) {
        this.list = list;
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

        if (!Strings.isNullOrEmpty(list.get(position).photo)){
            holder.restaurantIcon.setImageURI(Uri.parse(list.get(position).photo));
        }else {
            ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithResourceId(R.drawable.naber_icon_logo_reverse).build();
            holder.restaurantIcon.setImageURI(imageRequest.getSourceUri());
        }

        holder.restaurantNameText.setText(list.get(position).name);
        holder.businessTimeText.setText("接單時間: " +
                list.get(position).store_start + "~" +
                list.get(position).store_end);

        holder.addressText.setText(list.get(position).address);
//        String distance = DistanceTools.getGoogleDistance(
//                this.location,
//                LocationVo.of(
//                        Double.parseDouble(list.get(position).latitude),
//                        Double.parseDouble(list.get(position).longitude)
//                )
//        );
        holder.distanceText.setText("" + list.get(position).distance);
    }

    @Override
    public int getItemCount() {
        return list.size();
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
