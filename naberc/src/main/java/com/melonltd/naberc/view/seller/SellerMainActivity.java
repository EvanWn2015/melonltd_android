package com.melonltd.naberc.view.seller;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.collect.Lists;
import com.melonltd.naberc.R;
import com.melonltd.naberc.model.helper.okhttp.ApiCallback;
import com.melonltd.naberc.model.helper.okhttp.ApiManager;
import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;
import com.melonltd.naberc.view.common.factory.PageFragmentFactory;
import com.melonltd.naberc.view.common.type.PageType;
import com.melonltd.naberc.view.customize.NaberTab;
import com.melonltd.naberc.view.seller.adapter.DateSelectAdapter;

import java.util.List;

public class SellerMainActivity extends BaseCore implements TabLayout.OnTabSelectedListener, View.OnLayoutChangeListener, CompoundButton.OnCheckedChangeListener {
    private static final String TAG = SellerMainActivity.class.getSimpleName();
    private static Context context;
    private static DrawerLayout drawer;
    public static TabLayout tabLayout;
    private DateSelectAdapter adapter;
    private RecyclerView sellerRecyclerView;

    private static View.OnClickListener toolbarNavigationClickListener;
    public static Toolbar toolbar;
    private static Drawable backIcon, defualtIcon;
    private FrameLayout frameLayout;
    private static final List<PageType> SELLER_MAIN_PAGE = Lists.newArrayList(PageType.SELLER_SEARCH, PageType.SELLER_ORDERS, PageType.SELLER_STAT, PageType.SELLER_RESTAURANT, PageType.SELLER_SET_UP);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);
        context = this;
        getViews();
        serTab();
        List<String> list = Lists.newArrayList();

        for (int i = 0; i < 20; i++) {
            list.add((i + 1) * 30 + "~" + (1 + i) * 10);
        }
        adapter = new DateSelectAdapter(this, list, this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        sellerRecyclerView.setLayoutManager(layoutManager);
        sellerRecyclerView.setAdapter(adapter);
    }

    private void getViews() {
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.seller_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        backIcon = getResources().getDrawable(R.drawable.naber_back_icon);
        defualtIcon = toolbar.getNavigationIcon();
        toolbarNavigationClickListener = toggle.getToolbarNavigationClickListener();

        sellerRecyclerView = findViewById(R.id.sellerRecyclerView);
        tabLayout = findViewById(R.id.sellerMenuTabLayout);
        tabLayout.addOnTabSelectedListener(this);
        frameLayout = findViewById(R.id.sellerFrameContainer);
        frameLayout.addOnLayoutChangeListener(this);
    }

    private void serTab() {
        tabLayout.removeAllTabs();
        View v = new NaberTab(context).Builder().setIcon(R.drawable.naber_tab_search_icon).setTitle(R.string.seller_menu_search_btn).build();
        View v0 = new NaberTab(context).Builder().setIcon(R.drawable.naber_tab_history_icon).setTitle(R.string.seller_menu_orders_btn).build();
        View v1 = new NaberTab(context).Builder().setIcon(R.drawable.naber_tab_stat_icon).setTitle(R.string.seller_menu_stat_btn).build();
        View v2 = new NaberTab(context).Builder().setIcon(R.drawable.naber_tab_restaurant_icon).setTitle(R.string.seller_menu_menu_btn).build();
        View v3 = new NaberTab(context).Builder().setIcon(R.drawable.naber_tab_set_up_icon).setTitle(R.string.seller_menu_set_up_btn).build();
        tabLayout.addTab(tabLayout.newTab().setCustomView(v).setTag(R.string.seller_menu_search_btn), false);
        tabLayout.addTab(tabLayout.newTab().setCustomView(v0).setTag(R.string.seller_menu_orders_btn), false);
        tabLayout.addTab(tabLayout.newTab().setCustomView(v1).setTag(R.string.seller_menu_stat_btn), false);
        tabLayout.addTab(tabLayout.newTab().setCustomView(v2).setTag(R.string.seller_menu_menu_btn), false);
        tabLayout.addTab(tabLayout.newTab().setCustomView(v3).setTag(R.string.seller_menu_set_up_btn), false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AbsPageFragment fragment = null;
        FRAGMENT_TAG = PageType.SELLER_SEARCH.name();
        fragment = PageFragmentFactory.of(PageType.SELLER_SEARCH, null);
        fragmentManager.beginTransaction().replace(R.id.sellerFrameContainer, fragment).addToBackStack(fragment.toString()).commit();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int index = Integer.parseInt(tab.getTag().toString());
        if (SELLER_MAIN_PAGE.contains(PageType.ofId(index))) {
            AbsPageFragment fragment = PageFragmentFactory.of(PageType.ofId(Integer.parseInt(tab.getTag().toString())), null);
            FRAGMENT_TAG = PageType.ofId(Integer.parseInt(tab.getTag().toString())).name();
            fragmentManager.beginTransaction().addToBackStack(fragment.toString()).replace(R.id.sellerFrameContainer, fragment).commit();
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
        icon.setColorFilter(getResources().getColor(android.R.color.black));
        text.setTextColor(getResources().getColor(android.R.color.black));
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        int index = Integer.parseInt(tab.getTag().toString());
        if (!FRAGMENT_TAG.equals(PageType.ofId(index).name())) {
            if (SELLER_MAIN_PAGE.contains(PageType.ofId(index))) {
                AbsPageFragment fragment = PageFragmentFactory.of(PageType.ofId(Integer.parseInt(tab.getTag().toString())), null);
                FRAGMENT_TAG = PageType.ofId(Integer.parseInt(tab.getTag().toString())).name();
                fragmentManager.beginTransaction().addToBackStack(fragment.toString()).replace(R.id.sellerFrameContainer, fragment).commit();
            }

            View v = tab.getCustomView();
            ImageView icon = v.findViewById(R.id.tabIcon);
            TextView text = v.findViewById(R.id.tabTitle);
            icon.setColorFilter(getResources().getColor(R.color.naber_basis));
            text.setTextColor(getResources().getColor(R.color.naber_basis));
        }
    }

    @Override
    public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        int position = PageType.equalsPositionByName(FRAGMENT_TAG);
        for (int index = 0; index < tabLayout.getTabCount(); index++) {
            onTabUnselected(tabLayout.getTabAt(index));
        }

        if (tabLayout.getTabAt(position) != null) {
            View v = tabLayout.getTabAt(position).getCustomView();
            ImageView icon = v.findViewById(R.id.tabIcon);
            TextView text = v.findViewById(R.id.tabTitle);
            icon.setColorFilter(getResources().getColor(R.color.naber_basis));
            text.setTextColor(getResources().getColor(R.color.naber_basis));
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        ApiManager.test(new ApiCallback(context) {
            @Override
            public void onSuccess(String responseBody) {

            }

            @Override
            public void onFail(Exception error) {

            }
        });
    }

    public static void navigationIconDisplay(boolean show, View.OnClickListener listener) {

        if (!show) {
            toolbar.setNavigationIcon(defualtIcon);
            toolbar.setNavigationOnClickListener(null);
            drawer.addDrawerListener(new ActionBarDrawerToggle((Activity) context, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close));
        } else {
            toolbar.setNavigationIcon(backIcon);
            toolbar.setNavigationOnClickListener(listener);
        }
    }
}
