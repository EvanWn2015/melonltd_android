package com.melonltd.naberc.view.user.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.melonltd.naberc.R;

import java.util.ArrayList;

public class RestaurantAdapter extends BaseAdapter {

    private LayoutInflater inflater = null;
    private ArrayList<String> list;

    public RestaurantAdapter(Context context, ArrayList list) {
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        RestaurantItem item = null;
        if (view == null) {
            view = inflater.inflate(R.layout.user_restaurant_item, null);
            item = RestaurantItem.valueOf(view);
            view.setTag(item);
        } else {
            item = (RestaurantItem) view.getTag();
        }
        item.restaurantNameText.setText(list.get(i) + " XX 店");
        Uri uri = Uri.parse("https://sjhexpress.com/wp-content/uploads/2015/02/HannahRidoutFoodPhotography.jpg");
        item.restaurantIcon.setImageURI(uri);
        return view;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }

    public void setData(ArrayList<String> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }


    static class RestaurantItem {
        SimpleDraweeView restaurantIcon;
        TextView restaurantNameText, businessTimeText, addressText, distanceText;
        public static RestaurantItem valueOf(View view ) {
            RestaurantItem item =  new RestaurantItem();
            item.restaurantIcon = view.findViewById(R.id.restaurantImageView);
            item.restaurantNameText = view.findViewById(R.id.restaurantNameText);
            item.businessTimeText = view.findViewById(R.id.businessTimeText);
            item.addressText = view.findViewById(R.id.addressText);
            item.distanceText = view.findViewById(R.id.distanceText);
            return item;
        }
    }
}
