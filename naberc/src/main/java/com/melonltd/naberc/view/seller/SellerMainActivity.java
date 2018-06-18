package com.melonltd.naberc.view.seller;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.collect.Lists;
import com.melonltd.naberc.R;
import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.common.factory.PageFragmentFactory;
import com.melonltd.naberc.view.common.type.PageType;
import com.melonltd.naberc.view.customize.NaberTab;
import com.melonltd.naberc.view.customize.SwitchButton;
import com.melonltd.naberc.view.seller.adapter.DateSelectAdapter;
import com.melonltd.naberc.view.seller.page.impl.SellerCategoryListFragment;
import com.melonltd.naberc.view.seller.page.impl.SellerDetailFragment;
import com.melonltd.naberc.view.seller.page.impl.SellerMenuEditFragment;
import com.melonltd.naberc.view.seller.page.impl.SellerOrderLogsDetailFragment;
import com.melonltd.naberc.view.seller.page.impl.SellerOrdersFragment;
import com.melonltd.naberc.view.seller.page.impl.SellerOrdersLogsFragment;
import com.melonltd.naberc.view.seller.page.impl.SellerRestaurantFragment;
import com.melonltd.naberc.view.seller.page.impl.SellerSearchFragment;
import com.melonltd.naberc.view.seller.page.impl.SellerSetUpFragment;
import com.melonltd.naberc.view.seller.page.impl.SellerSimpleInformationFragment;
import com.melonltd.naberc.view.seller.page.impl.SellerStatFragment;

import java.util.List;

public class SellerMainActivity extends BaseCore implements TabLayout.OnTabSelectedListener, SwitchButton.OnCheckedChangeListener {
    private static final String TAG = SellerMainActivity.class.getSimpleName();
    private static Context context;
    public static Toolbar toolbar;


    private static Drawable defaultIcon;
    private static DrawerLayout drawer;
    private DateSelectAdapter adapter;
    private List<String> rangeSwitchList = Lists.<String>newArrayList();

    public static List<View> tabViews = Lists.<View>newArrayList();

    private static final List<PageType> SELLER_MAIN_PAGE = Lists.newArrayList(PageType.SELLER_SEARCH, PageType.SELLER_ORDERS, PageType.SELLER_STAT, PageType.SELLER_RESTAURANT, PageType.SELLER_SET_UP);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);
        context = this;
        getViews();

        BaseCore.FRAGMENT_TAG = PageType.SELLER_SEARCH.name();
        Fragment fragment = PageFragmentFactory.of(PageType.SELLER_SEARCH, null);
        getSupportFragmentManager().beginTransaction().replace(R.id.sellerFrameContainer, fragment).addToBackStack(PageType.SELLER_SEARCH.name()).commit();
    }

    private void getViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.seller_drawer_layout);

        RecyclerView recyclerView = findViewById(R.id.sellerRecyclerView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        TabLayout tabLayout = findViewById(R.id.sellerMenuTabLayout);
        tabLayout.addOnTabSelectedListener(this);
        tabLayout.removeAllTabs();
        View v0 = new NaberTab(context).Builder().setIcon(R.drawable.naber_tab_search_icon).setTitle(R.string.seller_menu_search_btn).build();
        View v1 = new NaberTab(context).Builder().setIcon(R.drawable.naber_tab_history_icon).setTitle(R.string.seller_menu_orders_btn).build();
        View v2 = new NaberTab(context).Builder().setIcon(R.drawable.naber_tab_stat_icon).setTitle(R.string.seller_menu_stat_btn).build();
        View v3 = new NaberTab(context).Builder().setIcon(R.drawable.naber_tab_restaurant_icon).setTitle(R.string.seller_menu_menu_btn).build();
        View v4 = new NaberTab(context).Builder().setIcon(R.drawable.naber_tab_set_up_icon).setTitle(R.string.seller_menu_set_up_btn).build();
        tabViews = Lists.<View>newArrayList(v0, v1, v2, v3, v4);
        tabLayout.addTab(tabLayout.newTab().setCustomView(v0).setTag(R.string.seller_menu_search_btn), false);
        tabLayout.addTab(tabLayout.newTab().setCustomView(v1).setTag(R.string.seller_menu_orders_btn), false);
        tabLayout.addTab(tabLayout.newTab().setCustomView(v2).setTag(R.string.seller_menu_stat_btn), false);
        tabLayout.addTab(tabLayout.newTab().setCustomView(v3).setTag(R.string.seller_menu_menu_btn), false);
        tabLayout.addTab(tabLayout.newTab().setCustomView(v4).setTag(R.string.seller_menu_set_up_btn), false);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        defaultIcon = toolbar.getNavigationIcon();

        adapter = new DateSelectAdapter(rangeSwitchList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        for(int i=0; i<15; i++){
            rangeSwitchList.add("00:00-00:0"+i);
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onStop() {
        super.onStop();
        SellerCategoryListFragment.FRAGMENT = null;
        SellerDetailFragment.FRAGMENT = null;
        SellerMenuEditFragment.FRAGMENT = null;
        SellerOrderLogsDetailFragment.FRAGMENT = null;
        SellerOrdersFragment.FRAGMENT = null;
        SellerOrdersLogsFragment.FRAGMENT = null;
        SellerRestaurantFragment.FRAGMENT = null;
        SellerSearchFragment.FRAGMENT = null;
        SellerSetUpFragment.FRAGMENT = null;
        SellerSimpleInformationFragment.FRAGMENT = null;
        SellerStatFragment.FRAGMENT = null;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int index = Integer.parseInt(tab.getTag().toString());
        if (SELLER_MAIN_PAGE.contains(PageType.ofId(index))) {
            Fragment fragment = PageFragmentFactory.of(PageType.ofId(Integer.parseInt(tab.getTag().toString())), null);
            BaseCore.FRAGMENT_TAG = PageType.ofId(Integer.parseInt(tab.getTag().toString())).name();
            getSupportFragmentManager().beginTransaction().addToBackStack(fragment.toString()).replace(R.id.sellerFrameContainer, fragment).commit();
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
        if (!BaseCore.FRAGMENT_TAG.equals(PageType.ofId(index).name())) {
            if (SELLER_MAIN_PAGE.contains(PageType.ofId(index))) {
                Fragment fragment = PageFragmentFactory.of(PageType.ofId(Integer.parseInt(tab.getTag().toString())), null);
                BaseCore.FRAGMENT_TAG = PageType.ofId(Integer.parseInt(tab.getTag().toString())).name();
                getSupportFragmentManager().beginTransaction().addToBackStack(fragment.toString()).replace(R.id.sellerFrameContainer, fragment).commit();
            }

            View v = tab.getCustomView();
            ImageView icon = v.findViewById(R.id.tabIcon);
            TextView text = v.findViewById(R.id.tabTitle);
            icon.setColorFilter(getResources().getColor(R.color.naber_basis));
            text.setTextColor(getResources().getColor(R.color.naber_basis));
        }
    }


    public static void changeTabAndToolbarStatus() {
        int position = PageType.equalsPositionByName(BaseCore.FRAGMENT_TAG);
        toolbar.setTitle(context.getResources().getString(PageType.equalsIdByName(FRAGMENT_TAG)));

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


    public static void navigationIconDisplay(boolean show, View.OnClickListener listener) {

        if (!show) {
            toolbar.setNavigationIcon(defaultIcon);
            toolbar.setNavigationOnClickListener(null);
            drawer.addDrawerListener(new ActionBarDrawerToggle((Activity) context, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close));
        } else {
            toolbar.setNavigationIcon(context.getResources().getDrawable(R.drawable.naber_back_icon));
            toolbar.setNavigationOnClickListener(listener);
        }
    }

    @Override
    public void onCheckedChanged(SwitchButton view, boolean isChecked) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    public static void lockDrawer(boolean lock) {
        if (lock) {
            SellerMainActivity.toolbar.setNavigationIcon(null);
            drawer.closeDrawers();
        }
        drawer.setDrawerLockMode(lock ? DrawerLayout.LOCK_MODE_LOCKED_CLOSED : DrawerLayout.LOCK_MODE_UNDEFINED);
    }



}
