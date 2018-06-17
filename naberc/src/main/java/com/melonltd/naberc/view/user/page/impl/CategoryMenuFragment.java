package com.melonltd.naberc.view.user.page.impl;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.common.collect.Lists;
import com.melonltd.naberc.R;
import com.melonltd.naberc.model.helper.okhttp.ApiCallback;
import com.melonltd.naberc.model.helper.okhttp.ApiManager;
import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;
import com.melonltd.naberc.view.common.factory.PageFragmentFactory;
import com.melonltd.naberc.view.common.type.PageType;
import com.melonltd.naberc.view.user.UserMainActivity;
import com.melonltd.naberc.view.user.adapter.MenuAdapter;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class CategoryMenuFragment extends AbsPageFragment {
    private static final String TAG = CategoryMenuFragment.class.getSimpleName();
    public static CategoryMenuFragment FRAGMENT = null;

    private TextView categoryNameText;
//    private BGARefreshLayout bgaRefreshLayout;
    private RecyclerView recyclerView;
    private MenuAdapter adapter;

    private List<String> list = Lists.newArrayList();
    public static int TO_MENU_DETAIL_INDEX = -1;


    public CategoryMenuFragment() {
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new CategoryMenuFragment();
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new CategoryMenuFragment();
            TO_MENU_DETAIL_INDEX = -1;
        }
        FRAGMENT.setArguments(null);
        FRAGMENT.setArguments(bundle);
        return FRAGMENT;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new MenuAdapter(list);
        Fresco.initialize(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.user_category_menu_page) == null) {
            View v = inflater.inflate(R.layout.fragment_category_menu, container, false);
            getViews(v);
            container.setTag(R.id.user_category_menu_page, v);
            return v;
        }
        return (View) container.getTag(R.id.user_category_menu_page);
    }

    private void getViews(View v) {
        categoryNameText = v.findViewById(R.id.categoryNameText);

        final BGARefreshLayout bgaRefreshLayout = v.findViewById(R.id.menuBGARefreshLayout);
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

        // setListener

        adapter.setItemClickListener(new ItemClickListener());
        recyclerView.setAdapter(adapter);

        bgaRefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                bgaRefreshLayout.endRefreshing();
                ApiManager.test(new ApiCallback(getActivity()) {
                    @Override
                    public void onSuccess(String responseBody) {
                        list.clear();
                        for (int i = 0; i < 30; i++) {
                            list.add("" + i);
                        }
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFail(Exception error) {
                        bgaRefreshLayout.endRefreshing();
                    }
                });
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                bgaRefreshLayout.endLoadingMore();
                ApiManager.test(new ApiCallback(getActivity()) {
                    @Override
                    public void onSuccess(String responseBody) {
                        for (int i = 0; i < 30; i++) {
                            list.add("" + i);
                        }
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFail(Exception error) {
                        bgaRefreshLayout.endLoadingMore();
                    }
                });
                return false;
            }
        });
    }

    private void doLoadData(boolean isRefresh) {
        if (isRefresh) {
            list.clear();
        }

        ApiManager.test(new ApiCallback(getActivity()) {
            @Override
            public void onSuccess(String responseBody) {
                for (int i = 0; i < 10; i++) {
                    list.add("" + i);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(Exception error) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        UserMainActivity.changeTabAndToolbarStatus();
        if (list.size() == 0) {
            doLoadData(true);
            String categoryName = getArguments().getString("categoryName");
            categoryNameText.setText(categoryName);
        }

        if (UserMainActivity.toolbar != null) {
            UserMainActivity.navigationIconDisplay(true, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    backToRestaurantDetail();
                    UserMainActivity.navigationIconDisplay(false, null);
                }
            });
        }
        if (TO_MENU_DETAIL_INDEX >= 0) {
            toMenuDetailPage(TO_MENU_DETAIL_INDEX);
        }
    }

    private void toMenuDetailPage(int index) {
        TO_MENU_DETAIL_INDEX = index;
        BaseCore.FRAGMENT_TAG = PageType.MENU_DETAIL.name();
        AbsPageFragment f = PageFragmentFactory.of(PageType.MENU_DETAIL, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.frameContainer, f).commit();
    }

    private void backToRestaurantDetail() {
        BaseCore.FRAGMENT_TAG = PageType.RESTAURANT_DETAIL.name();
        RestaurantDetailFragment.TO_CATEGORY_MENU_INDEX = -1;
        AbsPageFragment f = PageFragmentFactory.of(PageType.RESTAURANT_DETAIL, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.frameContainer, f).commit();
    }

    @Override
    public void onStop() {
        super.onStop();
        UserMainActivity.navigationIconDisplay(false, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    class ItemClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            toMenuDetailPage((int) v.getTag());
        }
    }
}
