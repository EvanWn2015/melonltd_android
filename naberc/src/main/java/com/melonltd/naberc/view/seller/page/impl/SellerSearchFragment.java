package com.melonltd.naberc.view.seller.page.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.collect.Lists;
import com.melonltd.naberc.R;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;
import com.melonltd.naberc.view.seller.adapter.SearchAdapter;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class SellerSearchFragment extends AbsPageFragment {
    private static final String TAG = SellerSearchFragment.class.getSimpleName();
    private static SellerSearchFragment FRAGMENT = null;
    private BGARefreshLayout refreshLayout;

    private SearchAdapter adapter;
    private RecyclerView recyclerView;
    private List<String> listData = Lists.newArrayList();

    public SellerSearchFragment() {
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new SellerSearchFragment();
        }
        FRAGMENT.setArguments(bundle);
        return FRAGMENT;
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new SellerSearchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i < 10; i++) {
            listData.add("listData : " + i);
        }
        adapter = new SearchAdapter(listData);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.seller_search_main_page) == null) {
            View v = inflater.inflate(R.layout.fragment_seller_search, container, false);
            getViews(v);
            setListener();
            container.setTag(R.id.seller_search_main_page, v);
        }
        return (View) container.getTag(R.id.seller_search_main_page);
    }

    private void getViews(View v) {
        refreshLayout = v.findViewById(R.id.sellerSearchRefreshLayout);
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getContext(), true));
        recyclerView = v.findViewById(R.id.searchRecyclerView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setListener() {
        recyclerView.setAdapter(adapter);
        refreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                refreshLayout.endRefreshing();
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                refreshLayout.endLoadingMore();
                return false;
            }
        });
    }

}
