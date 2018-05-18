package com.melonltd.naberc.view.user;


import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.google.common.collect.Lists;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.melonltd.naberc.R;
import com.melonltd.naberc.view.user.page.abs.AbsPageFragment;
import com.melonltd.naberc.view.user.page.factory.PageFragmentFactory;
import com.melonltd.naberc.view.user.page.type.PageType;
//import com.youth.banner.loader.ImageLoader;

import java.util.List;

public class MainActivity extends BaseCore implements View.OnClickListener, TabLayout.OnTabSelectedListener, View.OnLayoutChangeListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Context context;
    public static Toolbar toolbar;
    public static TabLayout bottomMenuTabLayout;
    private FrameLayout frameContainer;
    private static Drawable navigationIcon;
    private static final List<PageType> MAIN_PAGE = Lists.newArrayList(PageType.HOME, PageType.RESTAURANT, PageType.SHOPPING_CART, PageType.HISTORY, PageType.SET_UP);

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        navigationIcon = getResources().getDrawable(R.drawable.naber_back_icon);
        getView();
        setSupportActionBar(toolbar);
        serTab();

        if (currentUser != null) {
            Log.d(TAG, currentUser.getEmail());
        }
    }

    private void getView() {
        toolbar = findViewById(R.id.toolbar);
        frameContainer = findViewById(R.id.frameContainer);
        bottomMenuTabLayout = findViewById(R.id.bottomMenuTabLayout);
        bottomMenuTabLayout.addOnTabSelectedListener(this);
        frameContainer.addOnLayoutChangeListener(this);
    }

    private static void serTab() {
        bottomMenuTabLayout.removeAllTabs();
        if (IS_USER) {
            bottomMenuTabLayout.addTab(bottomMenuTabLayout.newTab().setText(R.string.menu_home_btn).setIcon(R.drawable.ic_launcher_background).setTag(R.string.menu_home_btn), false);
            bottomMenuTabLayout.addTab(bottomMenuTabLayout.newTab().setText(R.string.menu_restaurant_btn).setIcon(R.drawable.ic_launcher_background).setTag(R.string.menu_restaurant_btn), false);
            bottomMenuTabLayout.addTab(bottomMenuTabLayout.newTab().setText(R.string.menu_shopping_cart_btn).setIcon(R.drawable.ic_launcher_background).setTag(R.string.menu_shopping_cart_btn), false);
            bottomMenuTabLayout.addTab(bottomMenuTabLayout.newTab().setText(R.string.menu_history_btn).setIcon(R.drawable.ic_launcher_background).setTag(R.string.menu_history_btn), false);
            bottomMenuTabLayout.addTab(bottomMenuTabLayout.newTab().setText(R.string.menu_set_up_btn).setIcon(R.drawable.ic_launcher_background).setTag(R.string.menu_set_up_btn), false);
//            bottomMenuTabLayout.getTabAt(0).select();
        } else {
            bottomMenuTabLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        AbsPageFragment fragment = null;
        bottomMenuTabLayout.setVisibility(View.GONE);
        fragment = PageFragmentFactory.of(PageType.REGISTERED, null);
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
        Log.d(TAG, "LayoutChange ->> " + FRAGMENT_TAG);
        int position = PageType.equalsPositionByName(FRAGMENT_TAG);
        if (position <= 4) {
            bottomMenuTabLayout.getTabAt(position).select();
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int index = Integer.parseInt(tab.getTag().toString());
        if (MAIN_PAGE.contains(PageType.ofId(index))) {
            tab.getIcon().setColorFilter(ContextCompat.getColor(context, R.color.naber_basis), PorterDuff.Mode.SRC_IN);
            AbsPageFragment fragment = PageFragmentFactory.of(PageType.ofId(Integer.parseInt(tab.getTag().toString())), null);
            FRAGMENT_TAG = PageType.ofId(Integer.parseInt(tab.getTag().toString())).name();
            fragmentManager.beginTransaction().addToBackStack(fragment.toString()).replace(R.id.frameContainer, fragment).commit();
        }
//        tab.getIcon().setColorFilter(ContextCompat.getColor(context, R.color.naber_basis), PorterDuff.Mode.SRC_IN);
//        AbsPageFragment fragment = PageFragmentFactory.of(PageType.ofId(Integer.parseInt(tab.getTag().toString())), null);
//        FRAGMENT_TAG = PageType.ofId(Integer.parseInt(tab.getTag().toString())).name();
//        fragmentManager.beginTransaction().addToBackStack(fragment.toString()).replace(R.id.frameContainer, fragment).commit();
//        fragmentManager.beginTransaction().replace(R.id.frameContainer, fragment).commit();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        tab.getIcon().setColorFilter(ContextCompat.getColor(context, R.color.cardview_dark_background), PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
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
