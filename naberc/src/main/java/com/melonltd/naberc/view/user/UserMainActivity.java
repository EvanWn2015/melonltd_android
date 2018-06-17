package com.melonltd.naberc.view.user;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.multidex.MultiDex;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.collect.Lists;
import com.melonltd.naberc.R;
import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;
import com.melonltd.naberc.view.common.factory.PageFragmentFactory;
import com.melonltd.naberc.view.common.type.PageType;
import com.melonltd.naberc.view.customize.NaberTab;
import com.melonltd.naberc.view.user.page.impl.AccountDetailFragment;
import com.melonltd.naberc.view.user.page.impl.CategoryMenuFragment;
import com.melonltd.naberc.view.user.page.impl.HistoryFragment;
import com.melonltd.naberc.view.user.page.impl.HomeFragment;
import com.melonltd.naberc.view.user.page.impl.MenuDetailFragment;
import com.melonltd.naberc.view.user.page.impl.OrderDetailFragment;
import com.melonltd.naberc.view.user.page.impl.ResetPasswordFragment;
import com.melonltd.naberc.view.user.page.impl.RestaurantDetailFragment;
import com.melonltd.naberc.view.user.page.impl.RestaurantFragment;
import com.melonltd.naberc.view.user.page.impl.SetUpFragment;
import com.melonltd.naberc.view.user.page.impl.ShoppingCartFragment;
import com.melonltd.naberc.view.user.page.impl.SimpleInformationFragment;
import com.melonltd.naberc.view.user.page.impl.SubmitOrdersFragment;

import java.util.List;


public class UserMainActivity extends BaseCore implements View.OnClickListener, TabLayout.OnTabSelectedListener {
    private static final String TAG = UserMainActivity.class.getSimpleName();
    private static Context context;
    public static Toolbar toolbar;
    public static List<View> tabViews = Lists.<View>newArrayList();
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
        getView();
        FRAGMENT_TAG = PageType.HOME.name();
        AbsPageFragment fragment = PageFragmentFactory.of(PageType.HOME, null);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, fragment).addToBackStack(fragment.toString()).commit();
    }

    private void getView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TabLayout bottomMenuTabLayout = findViewById(R.id.bottomMenuTabLayout);
        bottomMenuTabLayout.addOnTabSelectedListener(this);

        bottomMenuTabLayout.removeAllTabs();
        View v0 = new NaberTab(context).Builder().setIcon(R.drawable.naber_tab_home_icon).setTitle(R.string.menu_home_btn).build();
        View v1 = new NaberTab(context).Builder().setIcon(R.drawable.naber_tab_restaurant_icon).setTitle(R.string.menu_restaurant_btn).build();
        View v2 = new NaberTab(context).Builder().setIcon(R.drawable.naber_tab_shopping_cart_icon).setTitle(R.string.menu_shopping_cart_btn).build();
        View v3 = new NaberTab(context).Builder().setIcon(R.drawable.naber_tab_history_icon).setTitle(R.string.menu_history_btn).build();
        View v4 = new NaberTab(context).Builder().setIcon(R.drawable.naber_tab_set_up_icon).setTitle(R.string.menu_set_up_btn).build();
        tabViews = Lists.<View>newArrayList(v0, v1, v2, v3, v4);
        bottomMenuTabLayout.addTab(bottomMenuTabLayout.newTab().setCustomView(v0).setTag(R.string.menu_home_btn), false);
        bottomMenuTabLayout.addTab(bottomMenuTabLayout.newTab().setCustomView(v1).setTag(R.string.menu_restaurant_btn), false);
        bottomMenuTabLayout.addTab(bottomMenuTabLayout.newTab().setCustomView(v2).setTag(R.string.menu_shopping_cart_btn), false);
        bottomMenuTabLayout.addTab(bottomMenuTabLayout.newTab().setCustomView(v3).setTag(R.string.menu_history_btn), false);
        bottomMenuTabLayout.addTab(bottomMenuTabLayout.newTab().setCustomView(v4).setTag(R.string.menu_set_up_btn), false);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onStop() {
        super.onStop();
        AccountDetailFragment.FRAGMENT = null;
        CategoryMenuFragment.FRAGMENT = null;
        HistoryFragment.FRAGMENT = null;
        HomeFragment.FRAGMENT = null;
        MenuDetailFragment.FRAGMENT = null;
        OrderDetailFragment.FRAGMENT = null;
        ResetPasswordFragment.FRAGMENT = null;
        RestaurantDetailFragment.FRAGMENT = null;
        RestaurantFragment.FRAGMENT = null;
        SetUpFragment.FRAGMENT = null;
        ShoppingCartFragment.FRAGMENT = null;
        SimpleInformationFragment.FRAGMENT = null;
        SubmitOrdersFragment.FRAGMENT = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
    }

    public static void changeTabAndToolbarStatus() {
        int position = PageType.equalsPositionByName(FRAGMENT_TAG);
        if (PageType.HOME.name().equals(FRAGMENT_TAG)) {
            toolbar.setTitle("NABER");
        } else {
            toolbar.setTitle(context.getResources().getString(PageType.equalsIdByName(FRAGMENT_TAG)));
        }

        for (View tab : tabViews) {
            ImageView icon = tab.findViewById(R.id.tabIcon);
            TextView text = tab.findViewById(R.id.tabTitle);
            icon.setColorFilter(context.getResources().getColor(R.color.naber_tab_default_color));
            text.setTextColor(context.getResources().getColor(R.color.naber_tab_default_color));
            if (tabViews.indexOf(tab) == position) {
                icon.setColorFilter(context.getResources().getColor(R.color.naber_basis));
                text.setTextColor(context.getResources().getColor(R.color.naber_basis));
            }
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        int index = Integer.parseInt(tab.getTag().toString());
        if (MAIN_PAGE.contains(PageType.ofId(index))) {
            AbsPageFragment fragment = PageFragmentFactory.of(PageType.ofId(Integer.parseInt(tab.getTag().toString())), null);
            FRAGMENT_TAG = PageType.ofId(Integer.parseInt(tab.getTag().toString())).name();
            getSupportFragmentManager().beginTransaction().addToBackStack(fragment.toString()).replace(R.id.frameContainer, fragment).commit();
        }

        View v = tab.getCustomView();
        ImageView icon = v.findViewById(R.id.tabIcon);
        TextView text = v.findViewById(R.id.tabTitle);
        icon.setColorFilter(getResources().getColor(R.color.naber_basis));
        text.setTextColor(getResources().getColor(R.color.naber_basis));
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        View v = tab.getCustomView();
        ImageView icon = v.findViewById(R.id.tabIcon);
        TextView text = v.findViewById(R.id.tabTitle);
        icon.setColorFilter(getResources().getColor(R.color.naber_tab_default_color));
        text.setTextColor(getResources().getColor(R.color.naber_tab_default_color));
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

        int index = Integer.parseInt(tab.getTag().toString());
        if (!FRAGMENT_TAG.equals(PageType.ofId(index).name())) {
            if (MAIN_PAGE.contains(PageType.ofId(index))) {
                AbsPageFragment fragment = PageFragmentFactory.of(PageType.ofId(Integer.parseInt(tab.getTag().toString())), null);
                FRAGMENT_TAG = PageType.ofId(Integer.parseInt(tab.getTag().toString())).name();
                getSupportFragmentManager().beginTransaction().addToBackStack(fragment.toString()).replace(R.id.frameContainer, fragment).commit();
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
            toolbar.setNavigationIcon(context.getResources().getDrawable(R.drawable.naber_back_icon));
        }
        toolbar.setNavigationOnClickListener(listener);

    }

}
