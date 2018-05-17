package com.melonltd.naberc.view.user.page.impl;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.common.collect.Lists;
import com.melonltd.naberc.R;
import com.melonltd.naberc.view.customize.GlideImageLoader;
import com.melonltd.naberc.view.user.BaseCore;
import com.melonltd.naberc.view.user.page.abs.AbsPageFragment;
import com.melonltd.naberc.view.user.page.type.PageType;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * getArguments(); get this Bundle
 */
public class HomeFragment extends AbsPageFragment {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private static HomeFragment FRAGMENT = null;
    private Banner banner;
    private ArrayList<String> list = Lists.newArrayList();
    private ScrollView scrollView;
    private ListView top30ListView;
    private BaseAdapter top30Adapter;
    private static final int KEY = 1122447;

    public HomeFragment() {
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new HomeFragment();
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new HomeFragment();
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(getContext());
        top30Adapter = new Top30Adapter(getContext(), list);
        for(int i=0; i<10; i++){
            list.add("" + i);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        if (container.getTag(R.string.menu_home_btn) == null){
//            View v = inflater.inflate(R.layout.fragment_home, container, false);
//            getView(v);
//            top30ListView.setAdapter(top30Adapter);
//            container.setTag(R.string.menu_home_btn,v);
//            return v;
//        }else {
//            return (View)container.getTag(R.string.menu_home_btn);
//        }
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        getView(v);
        top30ListView.setAdapter(top30Adapter);
        return v;
    }

    private void getView(View v) {
        banner = v.findViewById(R.id.banner);
        top30ListView = v.findViewById(R.id.top30ListView);
        scrollView = v.findViewById(R.id.scrollView);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        List<String> images = Lists.newArrayList();
        for (int i = 1; i < 5; i++) {
            images.add("http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg");
        }
        banner.setImages(images).setImageLoader(new GlideImageLoader()).start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Log.d(TAG, "");
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    class Top30Adapter extends BaseAdapter {
        private LayoutInflater inflater = null;
        private ArrayList<String> list;

        public Top30Adapter(Context context, ArrayList list) {
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
                item = RestaurantItem.valueOf();
                view = inflater.inflate(R.layout.restaurant_item, null);
                item.title = view.findViewById(R.id.addressText);
                view.setTag(item);
            } else {
                item = (RestaurantItem) view.getTag();
            }

            // TODO item view
            item.title.setText("營業時間 10:00~ 11:0" + list.get(i));
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
    }

    static class RestaurantItem {
        TextView title;
        public static RestaurantItem valueOf() {
            return new RestaurantItem();
        }
    }


}
