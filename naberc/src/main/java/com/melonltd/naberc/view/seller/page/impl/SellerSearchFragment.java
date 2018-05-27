package com.melonltd.naberc.view.seller.page.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.common.collect.Lists;
import com.melonltd.naberc.R;
import com.melonltd.naberc.model.helper.okhttp.ApiCallback;
import com.melonltd.naberc.model.helper.okhttp.ApiManager;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;
import com.melonltd.naberc.view.seller.adapter.SearchAdapter;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class SellerSearchFragment extends AbsPageFragment {
    private static final String TAG = SellerSearchFragment.class.getSimpleName();
    private static SellerSearchFragment FRAGMENT = null;
    private BGARefreshLayout searchRefreshLayout;
    private EditText phoneEditText;
    private Button phoneSearchBtn;

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
        phoneEditText = v.findViewById(R.id.phoneEditText);
        phoneSearchBtn = v.findViewById(R.id.phoneSearchBtn);
        searchRefreshLayout = v.findViewById(R.id.sellerSearchRefreshLayout);
        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(getContext(), true);
        refreshViewHolder.setPullDownRefreshText("Pull");
        refreshViewHolder.setRefreshingText("Pull to refresh");
        refreshViewHolder.setReleaseRefreshText("Pull to refresh");

        searchRefreshLayout.setRefreshViewHolder(refreshViewHolder);
        recyclerView = v.findViewById(R.id.searchRecyclerView);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setListener() {
        recyclerView.setAdapter(adapter);
        searchRefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                ApiManager.test(new ApiCallback(getActivity()) {
                    @Override
                    public void onSuccess(String responseBody) {
                        listData.clear();
                        adapter.notifyDataSetChanged();
                        for (int i = 0; i < 10; i++) {
                            listData.add("listData : " + i);
                        }
                        adapter.notifyDataSetChanged();
                        searchRefreshLayout.endRefreshing();
                    }

                    @Override
                    public void onFail(Exception error) {
                        searchRefreshLayout.endRefreshing();
                    }
                });

            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
//                searchRefreshLayout.endLoadingMore();
//                for (int i = 0; i < 10; i++) {
//                    listData.add("listData : " + i);
//                }
//                adapter.notifyDataSetChanged();
//                searchRefreshLayout.endRefreshing();
                ApiManager.test(new ApiCallback(getActivity()) {
                    @Override
                    public void onSuccess(String responseBody) {
                        for (int i = 0; i < 10; i++) {
                            listData.add("listData : " + i);
                        }
                        adapter.notifyDataSetChanged();
                        searchRefreshLayout.endRefreshing();
                    }

                    @Override
                    public void onFail(Exception error) {
                        searchRefreshLayout.endRefreshing();
                    }
                });
                return false;
            }
        });
    }

}
