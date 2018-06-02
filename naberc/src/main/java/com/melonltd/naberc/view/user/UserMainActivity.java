package com.melonltd.naberc.view.user;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.multidex.MultiDex;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.collect.Lists;
import com.melonltd.naberc.R;
import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.customize.NaberTab;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;
import com.melonltd.naberc.view.common.factory.PageFragmentFactory;
import com.melonltd.naberc.view.common.type.PageType;
import com.melonltd.naberc.view.seller.SellerMainActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

//import com.youth.banner.loader.ImageLoader;

public class UserMainActivity extends BaseCore implements View.OnClickListener, TabLayout.OnTabSelectedListener, View.OnLayoutChangeListener {
    private static final String TAG = UserMainActivity.class.getSimpleName();
    private Context context;
    public static Toolbar toolbar;
    public static TabLayout bottomMenuTabLayout;
    private FrameLayout frameContainer;
    private static Drawable navigationIcon;
    private static final List<PageType> MAIN_PAGE = Lists.newArrayList(PageType.HOME, PageType.RESTAURANT, PageType.SHOPPING_CART, PageType.HISTORY, PageType.SET_UP);

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        context = this;
        navigationIcon = getResources().getDrawable(R.drawable.naber_back_icon);
        getView();
        setSupportActionBar(toolbar);
        serTab();

//        if (currentUser != null) {
////            Log.d(TAG, currentUser.getEmail());
//        }
    }

    private void getView() {
        toolbar = findViewById(R.id.toolbar);
        frameContainer = findViewById(R.id.frameContainer);
        bottomMenuTabLayout = findViewById(R.id.bottomMenuTabLayout);
        bottomMenuTabLayout.addOnTabSelectedListener(this);
        frameContainer.addOnLayoutChangeListener(this);
    }

    private void serTab() {
        bottomMenuTabLayout.removeAllTabs();
        // TODO icon size & text size
        if (IS_USER) {
            View v0 = new NaberTab(context).Builder().setIcon(R.drawable.naber_tab_home_icon).setTitle(R.string.menu_home_btn).build();
            View v1 = new NaberTab(context).Builder().setIcon(R.drawable.naber_tab_restaurant_icon).setTitle(R.string.menu_restaurant_btn).build();
            View v2 = new NaberTab(context).Builder().setIcon(R.drawable.naber_tab_shopping_cart_icon).setTitle(R.string.menu_shopping_cart_btn).build();
            View v3 = new NaberTab(context).Builder().setIcon(R.drawable.naber_tab_history_icon).setTitle(R.string.menu_history_btn).build();
            View v4 = new NaberTab(context).Builder().setIcon(R.drawable.naber_tab_set_up_icon).setTitle(R.string.menu_set_up_btn).build();
            bottomMenuTabLayout.addTab(bottomMenuTabLayout.newTab().setCustomView(v0).setTag(R.string.menu_home_btn), false);
            bottomMenuTabLayout.addTab(bottomMenuTabLayout.newTab().setCustomView(v1).setTag(R.string.menu_restaurant_btn), false);
            bottomMenuTabLayout.addTab(bottomMenuTabLayout.newTab().setCustomView(v2).setTag(R.string.menu_shopping_cart_btn), false);
            bottomMenuTabLayout.addTab(bottomMenuTabLayout.newTab().setCustomView(v3).setTag(R.string.menu_history_btn), false);
            bottomMenuTabLayout.addTab(bottomMenuTabLayout.newTab().setCustomView(v4).setTag(R.string.menu_set_up_btn), false);
        } else {
            bottomMenuTabLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Date currentTime = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:SS.sss'Z'");
////        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
//
//        Log.d(TAG, "UTC time: " + sdf.format(currentTime));
        // TODO Seller page
//        startActivity(new Intent(context, SellerMainActivity.class));
        // TODO user page
        AbsPageFragment fragment = null;
        bottomMenuTabLayout.setVisibility(View.GONE);
        fragment = PageFragmentFactory.of(PageType.LOGIN, null);
        fragmentManager.beginTransaction().replace(R.id.frameContainer, fragment).addToBackStack(fragment.toString()).commit();

//        if (SharedPreferencesService.isFirstUse()) {
//            fragmentManager.beginTransaction().replace(R.id.frameContainer, new IntroFragment()).commit();
////        }else if (BaseCore.currentUser == null){
////            bottomMenuTabLayout.setVisibility(View.GONE);
////            fragment = PageFragmentFactory.of(PageType.LOGIN, null);
////            fragmentManager.beginTransaction().replace(R.id.frameContainer, fragment).commit();
//        } else {
//            fragment = PageFragmentFactory.of(PageType.equalsName(FRAGMENT_TAG), null);
//            if (fragment == null) {
//                fragment = PageFragmentFactory.of(PageType.HOME, null);
//            }
//            fragmentManager.beginTransaction().replace(R.id.frameContainer, fragment).commit();
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
        int position = PageType.equalsPositionByName(FRAGMENT_TAG);

        // TODO toolbar title
        if (position == 0) {
            toolbar.setTitle("NABER");
        } else {
            toolbar.setTitle(getResources().getString(PageType.equalsIdByName(FRAGMENT_TAG)));
        }

        for (int index = 0; index < bottomMenuTabLayout.getTabCount(); index++) {
            onTabUnselected(bottomMenuTabLayout.getTabAt(index));
        }

        if (bottomMenuTabLayout.getTabAt(position) != null) {
            View v = bottomMenuTabLayout.getTabAt(position).getCustomView();
            ImageView icon = v.findViewById(R.id.tabIcon);
            TextView text = v.findViewById(R.id.tabTitle);
            icon.setColorFilter(getResources().getColor(R.color.naber_basis));
            text.setTextColor(getResources().getColor(R.color.naber_basis));
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        int index = Integer.parseInt(tab.getTag().toString());
//&& RestaurantFragment.HOME_TO_RESTAURANT_DETAIL_INDEX < 0
        if (MAIN_PAGE.contains(PageType.ofId(index))) {
//            tab.getIcon().setColorFilter(ContextCompat.getColor(context, R.color.naber_basis), PorterDuff.Mode.SRC_IN);
            AbsPageFragment fragment = PageFragmentFactory.of(PageType.ofId(Integer.parseInt(tab.getTag().toString())), null);
            FRAGMENT_TAG = PageType.ofId(Integer.parseInt(tab.getTag().toString())).name();
            fragmentManager.beginTransaction().addToBackStack(fragment.toString()).replace(R.id.frameContainer, fragment).commit();
        }

        View v = tab.getCustomView();
        ImageView icon = v.findViewById(R.id.tabIcon);
        TextView text = v.findViewById(R.id.tabTitle);
        icon.setColorFilter(getResources().getColor(R.color.naber_basis));
        text.setTextColor(getResources().getColor(R.color.naber_basis));

//        for (int i = 0; i < bottomMenuTabLayout.getTabCount(); i++) {
//            View view = bottomMenuTabLayout.getTabAt(i).getCustomView();
//            ImageView icon = view.findViewById(R.id.tabIcon);
//            TextView text =  view.findViewById(R.id.tabTitle);
//            if(i == tab.getPosition()){ // 选中状态
//                icon.setColorFilter(getResources().getColor(R.color.naber_basis));
//                text.setTextColor(getResources().getColor(R.color.naber_basis));
//            }else{
//                icon.setColorFilter(getResources().getColor(android.R.color.black));
//                text.setTextColor(getResources().getColor(android.R.color.black));
//            }
//
//        }
//        tab.getIcon().setColorFilter(ContextCompat.getColor(context, R.color.naber_basis), PorterDuff.Mode.SRC_IN);
//        AbsPageFragment fragment = PageFragmentFactory.of(PageType.ofId(Integer.parseInt(tab.getTag().toString())), null);
//        FRAGMENT_TAG = PageType.ofId(Integer.parseInt(tab.getTag().toString())).name();
//        fragmentManager.beginTransaction().addToBackStack(fragment.toString()).replace(R.id.frameContainer, fragment).commit();
//        fragmentManager.beginTransaction().replace(R.id.frameContainer, fragment).commit();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        View v = tab.getCustomView();
        ImageView icon = v.findViewById(R.id.tabIcon);
        TextView text = v.findViewById(R.id.tabTitle);
        icon.setColorFilter(getResources().getColor(R.color.naber_tab_default_color));
        text.setTextColor(getResources().getColor(R.color.naber_tab_default_color));
//        tab.getIcon().setColorFilter(ContextCompat.getColor(context, R.color.cardview_dark_background), PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

        int index = Integer.parseInt(tab.getTag().toString());
        if (!FRAGMENT_TAG.equals(PageType.ofId(index).name())) {
            if (MAIN_PAGE.contains(PageType.ofId(index))) {
//            tab.getIcon().setColorFilter(ContextCompat.getColor(context, R.color.naber_basis), PorterDuff.Mode.SRC_IN);
                AbsPageFragment fragment = PageFragmentFactory.of(PageType.ofId(Integer.parseInt(tab.getTag().toString())), null);
                FRAGMENT_TAG = PageType.ofId(Integer.parseInt(tab.getTag().toString())).name();
                fragmentManager.beginTransaction().addToBackStack(fragment.toString()).replace(R.id.frameContainer, fragment).commit();
            }
            View v = tab.getCustomView();
            ImageView icon = v.findViewById(R.id.tabIcon);
            TextView text = v.findViewById(R.id.tabTitle);
            icon.setColorFilter(getResources().getColor(R.color.naber_basis));
            text.setTextColor(getResources().getColor(R.color.naber_basis));
        }
    }


    public static void navigationIconDisplay(boolean show, View.OnClickListener listener) {
        if (!show) {
            toolbar.setNavigationIcon(null);
        } else {
            toolbar.setNavigationIcon(navigationIcon);
        }
        toolbar.setNavigationOnClickListener(listener);
    }
}
