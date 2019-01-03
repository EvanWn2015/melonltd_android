package com.melonltd.naber.view.user.adapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.common.base.Strings;
import com.melonltd.naber.R;
import com.melonltd.naber.vo.RestaurantPhotoVo;

import java.util.List;

public class UserStorePhotoAdapter extends RecyclerView.Adapter<UserStorePhotoAdapter.ViewHolder> {
    private static final String TAG = UserStorePhotoAdapter.class.getSimpleName();
    private List<RestaurantPhotoVo> photos;
    private View.OnClickListener photoClick;

    public UserStorePhotoAdapter(List<RestaurantPhotoVo> photos, View.OnClickListener photoClick) {
        this.photos = photos;
        this.photoClick = photoClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_store_photo_item, parent, false);
        UserStorePhotoAdapter.ViewHolder vh = new UserStorePhotoAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        RestaurantPhotoVo vo = this.photos.get(position  % photos.size());
        h.itemView.setTag(vo);
        if (vo.type.equals("FOOD")) {
            h.itemView.setOnClickListener(this.photoClick);
        }
        if (!Strings.isNullOrEmpty(vo.photo)) {
            h.storePhoto.setImageURI(Uri.parse(vo.photo));
        } else {
            ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithResourceId(R.drawable.naber_icon_logo_reverse).build();
            h.storePhoto.setImageURI(imageRequest.getSourceUri());
        }
    }

    @Override
    public int getItemCount() {
        return this.photos.size() < 2 ? this.photos.size() : Integer.MAX_VALUE;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView storePhoto;

        public ViewHolder(View v) {
            super(v);
            storePhoto = v.findViewById(R.id.storePhoto);
        }
    }
}
