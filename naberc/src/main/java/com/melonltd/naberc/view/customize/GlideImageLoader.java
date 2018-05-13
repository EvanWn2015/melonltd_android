package com.melonltd.naberc.view.customize;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Uri uri = Uri.parse((String) path);
        imageView.setImageURI(uri);
    }

    public GlideImageLoader() {
        super();
    }

    @Override
    public ImageView createImageView(Context context) {
        SimpleDraweeView simpleDraweeView =new SimpleDraweeView(context);
        return simpleDraweeView;
    }
}
