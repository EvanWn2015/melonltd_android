package com.melonltd.naberc.view.seller;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.google.common.collect.Lists;
import com.melonltd.naberc.R;
import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.customize.NaberTab;

import java.util.List;

public class SellerMainActivity extends BaseCore {
    private static final String TAG = SellerMainActivity.class.getSimpleName();
    private FragmentManager fm;
    private DrawerLayout drawer;
    public static TabLayout tabLayout;
    private DateSelectAdapter adapter;
    private RecyclerView sellerRecyclerView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);
        getViews();
        getManager();
        serTab();
        List<String> list = Lists.newArrayList();


        for (int i = 0; i < 20; i++) {
            list.add((i + 1) * 30 + "~" + (1 + i) * 10);
        }
        adapter = new DateSelectAdapter(this, list);
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
        sellerRecyclerView = findViewById(R.id.sellerRecyclerView);
        tabLayout = findViewById(R.id.sellerMenuTabLayout);
    }

    private void getManager() {
        fm = getFragmentManager();
    }

    private void serTab() {
        View v0 = new NaberTab(context).Builder().setIcon(R.drawable.naber_tab_history_icon).setTitle(R.string.seller_menu_orders_btn).build();
        View v1 = new NaberTab(context).Builder().setIcon(R.drawable.naber_tab_stat_icon).setTitle(R.string.seller_menu_stat_btn).build();
        View v2 = new NaberTab(context).Builder().setIcon(R.drawable.naber_tab_restaurant_icon).setTitle(R.string.seller_menu_menu_btn).build();
        View v3 = new NaberTab(context).Builder().setIcon(R.drawable.naber_tab_set_up_icon).setTitle(R.string.seller_menu_set_up_btn).build();

        tabLayout.addTab(tabLayout.newTab().setCustomView(v0).setTag(R.string.seller_menu_orders_btn), false);
        tabLayout.addTab(tabLayout.newTab().setCustomView(v1).setTag(R.string.seller_menu_stat_btn), false);
        tabLayout.addTab(tabLayout.newTab().setCustomView(v2).setTag(R.string.seller_menu_menu_btn), false);
        tabLayout.addTab(tabLayout.newTab().setCustomView(v3).setTag(R.string.seller_menu_set_up_btn), false);
    }


    class DateSelectAdapter extends RecyclerView.Adapter<DateSelectAdapter.ViewHolder> {
        private Context context;
        private List<String> list;

        DateSelectAdapter(Context context, List<String> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_select_date_switch, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
            holder.aSwitch.setText(list.get(i));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private Switch aSwitch;

            public ViewHolder(View v) {
                super(v);
                aSwitch = v.findViewById(R.id.sellerDateSelectSwitch);
            }
        }
    }


}
