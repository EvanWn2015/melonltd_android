package com.melonltd.naberc.view.user.page.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.common.collect.Lists;
import com.melonltd.naberc.R;
import com.melonltd.naberc.model.helper.okhttp.ApiCallback;
import com.melonltd.naberc.model.helper.okhttp.ApiManager;
import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;
import com.melonltd.naberc.view.common.factory.PageFragmentFactory;
import com.melonltd.naberc.view.common.type.PageType;
import com.melonltd.naberc.view.customize.GlideImageLoader;
import com.melonltd.naberc.view.customize.OnLoadLayout;
import com.melonltd.naberc.view.user.adapter.RestaurantAdapter;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;


public class HomeFragment extends AbsPageFragment {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private static HomeFragment FRAGMENT = null;
    private OnLoadLayout contentLoadLayout;
    private ListView top30RestaurantListView;
    private RestaurantAdapter adapter;
    private ArrayList<String> list = Lists.newArrayList();
    private List<String> images = Lists.newArrayList();
    private Banner banner;

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
        // 圖片異步加載初始
        Fresco.initialize(getContext());
        adapter = new RestaurantAdapter(getContext(), list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.user_home_page) == null) {
            View v = inflater.inflate(R.layout.fragment_home, container, false);
            getViews(v);
            setListener();
            container.setTag(R.id.user_home_page, v);
            return v;
        } else {
            return (View) container.getTag(R.id.user_home_page);
        }
    }


    private void getViews(View v) {
        contentLoadLayout = v.findViewById(R.id.contentLoadLayout);
        top30RestaurantListView = v.findViewById(R.id.top30ListView);
        top30RestaurantListView.setAdapter(adapter);
        top30RestaurantListView.addHeaderView(LayoutInflater.from(getContext()).inflate(R.layout.fragment_user_home_banner, null), null, false);
        banner = v.findViewById(R.id.banner);
    }


    private void setListener() {
        contentLoadLayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                doLoadData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return ((OnLoadLayout) frame).isTop();
            }
        });

        top30RestaurantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RestaurantFragment.TO_RESTAURANT_DETAIL_INDEX = i;
                BaseCore.FRAGMENT_TAG = PageType.RESTAURANT_DETAIL.name();
                Bundle b = new Bundle();
                b.putString("where", "HOME");
                AbsPageFragment f = PageFragmentFactory.of(PageType.RESTAURANT_DETAIL, b);
                getFragmentManager().beginTransaction().replace(R.id.frameContainer, f).commit();
            }
        });

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Log.d(TAG, "");
            }
        });
    }


    private void doLoadData() {
        images.clear();
        adapter.notifyDataSetChanged();
        for (int i = 1; i < 5; i++) {
            images.add("http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg");
        }
        banner.setImages(images).setImageLoader(new GlideImageLoader()).start();

        list.clear();
        ApiManager.test(new ApiCallback(getActivity()) {
            @Override
            public void onSuccess(String responseBody) {

                for (int i = 0; i < 30; i++) {
                    list.add("" + i);
                }
                adapter.notifyDataSetChanged();
                contentLoadLayout.refreshComplete();
            }

            @Override
            public void onFail(Exception error) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

//        if ( RestaurantFragment.TO_RESTAURANT_DETAIL_INDEX >= 0) {
//            Bundle b = new Bundle();
//            b.putString("where", "RESTAURANT");
//            BaseCore.FRAGMENT_TAG = PageType.RESTAURANT_DETAIL.name();
//            AbsPageFragment f = PageFragmentFactory.of(PageType.RESTAURANT_DETAIL, b);
//            getFragmentManager().beginTransaction().replace(R.id.frameContainer, f).commit();
//        }else {
        doLoadData();
//        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
