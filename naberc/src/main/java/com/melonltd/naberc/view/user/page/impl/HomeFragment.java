package com.melonltd.naberc.view.user.page.impl;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.common.collect.Lists;
import com.melonltd.naberc.R;
import com.melonltd.naberc.view.customize.BannerFragment;
import com.melonltd.naberc.view.customize.GlideImageLoader;
import com.melonltd.naberc.view.customize.adapter.BannerFragmentAdapter;
import com.melonltd.naberc.view.user.page.abs.AbsPageFragment;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.List;

/**
 * getArguments(); get this Bundle
 */
public class HomeFragment extends AbsPageFragment {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private static HomeFragment FRAGMENT = null;

    //    private ViewPager bannerViewPage;
    private BannerFragmentAdapter bannerFragmentAdapter;
    private List<BannerFragment> bannerFragmentList = Lists.newArrayList();
    private ViewPager bannerViewPage;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int currentItem = bannerViewPage.getCurrentItem();
            if(currentItem == 4){
                currentItem =0;
            }else {
                currentItem++;
            }
            bannerViewPage.setCurrentItem(currentItem);
            sendEmptyMessageDelayed(0, 2000);
        }
    };

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
        bannerFragmentAdapter = new BannerFragmentAdapter(getFragmentManager(), bannerFragmentList);
        for (int i = 0; i < 5; i++) {
            BannerFragment f = BannerFragment.getInstance()
                    .Builder()
                    .setBundle(null)
                    .setImageURI("http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg")
                    .setTitle("0" + (i + 1) + "/ 05")
                    .setContent(i + "今天是母親節，明天星期一，多數人要上班上課，緊接來的周六周日(19、20日)還有國中會考，未來一周天氣如何，中央氣象局稍早在臉書報天氣 - 中央氣象局貼了一張本周天氣圖，讓民眾看了馬上懂。" + i)
                    .build();
            bannerFragmentList.add(f);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        getView(v);

        return v;
    }

    private void getView(View v) {
        bannerViewPage = v.findViewById(R.id.bannerViewPage);
        bannerViewPage.setAdapter(bannerFragmentAdapter);
        Log.d(TAG, bannerViewPage + "");
        Log.d(TAG, bannerViewPage + "");
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        bannerFragmentAdapter.notifyDataSetChanged();
        handler.sendEmptyMessageDelayed(0, 2000);
    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeCallbacksAndMessages(null);
    }


}
