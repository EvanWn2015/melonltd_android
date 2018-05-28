package com.melonltd.naberc.view.seller.page.impl;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.common.collect.Lists;
import com.melonltd.naberc.R;
import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;
import com.melonltd.naberc.view.common.factory.PageFragmentFactory;
import com.melonltd.naberc.view.common.type.PageType;
import com.melonltd.naberc.view.seller.SellerMainActivity;
import com.melonltd.naberc.view.seller.adapter.MenuAdapter;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class SellerCategoryListFragment extends AbsPageFragment implements View.OnClickListener {
    private static final String TAG = SellerCategoryListFragment.class.getSimpleName();
    private static SellerCategoryListFragment FRAGMENT = null;

    private TextView categoryNameText;
    private Button newMenuBtn;

    private BGARefreshLayout bgaRefreshLayout;
    private RecyclerView recyclerView;
    private MenuAdapter adapter;
    private List<String> listData = Lists.newArrayList();

    public static int TO_MENU_EDIT_PAGE_INDEX = -1;

    public SellerCategoryListFragment() {
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new SellerCategoryListFragment();
        }
        FRAGMENT.setArguments(bundle);
        return FRAGMENT;
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new SellerCategoryListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new MenuAdapter(listData);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.seller_restaurant_category_list_page) == null) {
            View v = inflater.inflate(R.layout.fragment_seller_category_list, container, false);
            getViews(v);
            setListener();
            container.setTag(R.id.seller_restaurant_category_list_page, v);
            return v;
        }
        return (View) container.getTag(R.id.seller_restaurant_category_list_page);
    }


    private void getViews(View v) {
        categoryNameText = v.findViewById(R.id.categoryNameText);
        newMenuBtn = v.findViewById(R.id.newMenuBtn);
        bgaRefreshLayout = v.findViewById(R.id.menuBGARefreshLayout);
        recyclerView = v.findViewById(R.id.menuRecyclerView);

        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(getContext(), true);
        refreshViewHolder.setPullDownRefreshText("Pull");
        refreshViewHolder.setRefreshingText("Pull to refresh");
        refreshViewHolder.setReleaseRefreshText("Pull to refresh");
        refreshViewHolder.setLoadingMoreText("Loading more !");

        bgaRefreshLayout.setRefreshViewHolder(refreshViewHolder);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setListener() {
        adapter.setListener(new SwitchListener(), new DeleteListener());
        recyclerView.setAdapter(adapter);
        newMenuBtn.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        for (int i = 0; i < 10; i++) {
            listData.add("menu :: " + i);
        }
        adapter.notifyDataSetChanged();

        if (TO_MENU_EDIT_PAGE_INDEX >= 0) {
            toMenuEditPage(TO_MENU_EDIT_PAGE_INDEX);
        }

        if (SellerMainActivity.toolbar != null) {
            SellerMainActivity.navigationIconDisplay(true, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    backToSellerRestaurantPage();
                    SellerMainActivity.navigationIconDisplay(false, null);
                }
            });
        }
    }

    private void backToSellerRestaurantPage() {
        BaseCore.FRAGMENT_TAG = PageType.SELLER_RESTAURANT.name();
        SellerRestaurantFragment.TO_CATEGORY_LIST_PAGE_INDEX = -1;
        AbsPageFragment f = PageFragmentFactory.of(PageType.SELLER_RESTAURANT, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.sellerFrameContainer, f).commit();
    }

    private void toMenuEditPage(int index) {
        BaseCore.FRAGMENT_TAG = PageType.SELLER_MENU_EDIT.name();
        TO_MENU_EDIT_PAGE_INDEX = index;
        AbsPageFragment f = PageFragmentFactory.of(PageType.SELLER_MENU_EDIT, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.sellerFrameContainer, f).commit();
    }

    @Override
    public void onStop() {
        super.onStop();
        SellerMainActivity.navigationIconDisplay(false, null);
    }

    @Override
    public void onClick(View view) {
        toMenuEditPage(0);
    }

    class SwitchListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            Log.d(TAG, b + "");
            Log.d(TAG, compoundButton.getTag() + "");
        }
    }

    class DeleteListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Log.d(TAG, view.getTag() + "");
        }
    }

}
