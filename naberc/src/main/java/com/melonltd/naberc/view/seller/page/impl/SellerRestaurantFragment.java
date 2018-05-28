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
import android.widget.EditText;

import com.google.common.collect.Lists;
import com.melonltd.naberc.R;
import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;
import com.melonltd.naberc.view.common.factory.PageFragmentFactory;
import com.melonltd.naberc.view.common.type.PageType;
import com.melonltd.naberc.view.seller.adapter.CategoryAdapter;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class SellerRestaurantFragment extends AbsPageFragment {
    private static final String TAG = SellerRestaurantFragment.class.getSimpleName();
    private static SellerRestaurantFragment FRAGMENT = null;

    private EditText categoryEdit;
    private Button newCategoryBtn;
    private List<String> listData = Lists.newArrayList();
    private CategoryAdapter adapter;
    private BGARefreshLayout bagRefreshLayout;
    private RecyclerView recyclerView;
    public static int TO_CATEGORY_LIST_PAGE_INDEX = -1;

    public SellerRestaurantFragment() {
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new SellerRestaurantFragment();
        }
        FRAGMENT.setArguments(bundle);
        return FRAGMENT;
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new SellerRestaurantFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new CategoryAdapter(listData);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.seller_restaurant_main_page) == null) {
            View v = inflater.inflate(R.layout.fragment_seller_restaurant, container, false);
            getViews(v);
            setListener();
            container.setTag(R.id.seller_restaurant_main_page, v);
            return v;
        }
        return (View) container.getTag(R.id.seller_restaurant_main_page);
    }


    private void getViews(View v) {
        categoryEdit = v.findViewById(R.id.categoryEdit);
        newCategoryBtn = v.findViewById(R.id.newCategoryBtn);
        bagRefreshLayout = v.findViewById(R.id.categoryBGARefreshLayout);
        recyclerView = v.findViewById(R.id.categoryRecyclerView);

        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(getContext(), true);
        refreshViewHolder.setPullDownRefreshText("Pull");
        refreshViewHolder.setRefreshingText("Pull to refresh");
        refreshViewHolder.setReleaseRefreshText("Pull to refresh");
        refreshViewHolder.setLoadingMoreText("Loading more !");
        bagRefreshLayout.setRefreshViewHolder(refreshViewHolder);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setListener() {
        recyclerView.setAdapter(adapter);
        adapter.setListener(new SwitchListener(), new EditListener(), new DeleteListener());
    }

    @Override
    public void onResume() {
        super.onResume();
        for (int i = 0; i < 10; i++) {
            listData.add("test data : " + i);
        }
        adapter.notifyDataSetChanged();


        if (TO_CATEGORY_LIST_PAGE_INDEX >=0){
            toCategoryListPage(TO_CATEGORY_LIST_PAGE_INDEX);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    private void toCategoryListPage(int index) {
        TO_CATEGORY_LIST_PAGE_INDEX = index;
        BaseCore.FRAGMENT_TAG = PageType.SELLER_CATEGORY_LIST.name();
        AbsPageFragment f = PageFragmentFactory.of(PageType.SELLER_CATEGORY_LIST, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.sellerFrameContainer, f).commit();
    }

    class SwitchListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            Log.d(TAG, b + "");
            Log.d(TAG, compoundButton.getTag() + "");
        }
    }

    class EditListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int index = listData.indexOf(view.getTag());
            toCategoryListPage(index);
        }
    }

    class DeleteListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Log.d(TAG, view.getTag() + "");
        }
    }


}
