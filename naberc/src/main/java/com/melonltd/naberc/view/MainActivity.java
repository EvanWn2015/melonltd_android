package com.melonltd.naberc.view;


import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.google.common.collect.Lists;
import com.melonltd.naberc.R;
import com.melonltd.naberc.view.page.abs.AbsPageFragment;
import com.melonltd.naberc.view.page.factory.PageFragmentFactory;
import com.melonltd.naberc.view.page.type.PageType;

import java.util.List;

public class MainActivity extends BaseCore implements View.OnClickListener, TabLayout.OnTabSelectedListener, View.OnLayoutChangeListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Context context;
    private Toolbar toolbar;
    public static TabLayout bottomMenuTabLayout;
    private FrameLayout frameContainer;

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        getView();
        setSupportActionBar(toolbar);
        serTab();
        bottomMenuTabLayout.setVisibility(View.GONE);

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
            List<TabLayout.Tab> userTabs = Lists.newArrayList(
                    bottomMenuTabLayout.newTab().setText(R.string.menu_home_btn).setIcon(R.drawable.ic_launcher_background).setTag(R.string.menu_home_btn),
                    bottomMenuTabLayout.newTab().setText(R.string.menu_restaurant_btn).setIcon(R.drawable.ic_launcher_background).setTag(R.string.menu_restaurant_btn),
                    bottomMenuTabLayout.newTab().setText(R.string.menu_shopping_cart_btn).setIcon(R.drawable.ic_launcher_background).setTag(R.string.menu_shopping_cart_btn),
                    bottomMenuTabLayout.newTab().setText(R.string.menu_history_btn).setIcon(R.drawable.ic_launcher_background).setTag(R.string.menu_history_btn),
                    bottomMenuTabLayout.newTab().setText(R.string.menu_set_up_btn).setIcon(R.drawable.ic_launcher_background).setTag(R.string.menu_set_up_btn));
            for (TabLayout.Tab t : userTabs) {
                bottomMenuTabLayout.addTab(t, true);
            }
        } else {
            bottomMenuTabLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        AbsPageFragment fragment = null;
        bottomMenuTabLayout.setVisibility(View.GONE);
        fragment = PageFragmentFactory.of(PageType.LOGIN, null);
        fragmentManager.beginTransaction().replace(R.id.frameContainer, fragment).commit();

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
        TabLayout.Tab tab = bottomMenuTabLayout.getTabAt(PageType.equalsPositionByName(FRAGMENT_TAG, true));
        if (tab != null) {
            FRAGMENT_TAG = PageType.ofName(FRAGMENT_TAG).name();
            tab.select();
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        tab.getIcon().setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        AbsPageFragment fragment = PageFragmentFactory.of(PageType.ofId(Integer.parseInt(tab.getTag().toString())), null);
        FRAGMENT_TAG = PageType.ofId(Integer.parseInt(tab.getTag().toString())).name();
        fragmentManager.beginTransaction().replace(R.id.frameContainer, fragment).commit();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        tab.getIcon().setColorFilter(ContextCompat.getColor(context, R.color.colorAccent), PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }
}
