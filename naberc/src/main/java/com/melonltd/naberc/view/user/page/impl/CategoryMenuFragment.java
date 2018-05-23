package com.melonltd.naberc.view.user.page.impl;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.common.collect.Lists;
import com.melonltd.naberc.R;
import com.melonltd.naberc.model.helper.okhttp.ApiCallback;
import com.melonltd.naberc.model.helper.okhttp.ApiManager;
import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;
import com.melonltd.naberc.view.common.factory.PageFragmentFactory;
import com.melonltd.naberc.view.common.type.PageType;
import com.melonltd.naberc.view.customize.OnLoadLayout;
import com.melonltd.naberc.view.user.UserMainActivity;
import com.melonltd.naberc.view.user.adapter.MenuAdapter;

import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class CategoryMenuFragment extends AbsPageFragment {
    private static final String TAG = CategoryMenuFragment.class.getSimpleName();
    private static CategoryMenuFragment FRAGMENT = null;

    private TextView categoryNameText;
    private OnLoadLayout menuOnLoadLayout;
    private MenuAdapter adapter;
    private ListView menuListView;
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
        }
        FRAGMENT.setArguments(null);
        FRAGMENT.setArguments(bundle);
        return FRAGMENT;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new MenuAdapter(getContext(), list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.user_category_menu__page) == null) {
            View v = inflater.inflate(R.layout.fragment_category_menu, container, false);
            getViews(v);
            setListener();
            container.setTag(R.id.user_category_menu__page, v);
            return v;
        }
        return (View) container.getTag(R.id.user_category_menu__page);
    }

    private void getViews(View v) {
        categoryNameText = v.findViewById(R.id.categoryNameText);
        menuOnLoadLayout = v.findViewById(R.id.menuOnLoadLayout);
        menuListView = v.findViewById(R.id.menuListView);
        menuListView.setAdapter(adapter);
    }

    private void setListener() {
        menuOnLoadLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                doLoadData(true);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return ((OnLoadLayout) frame).isTop();
            }
        });

        menuOnLoadLayout.setOnLoadListener(new OnLoadLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                doLoadData(false);
            }
        });

        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                toMenuDetailPage(i);
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
                menuOnLoadLayout.refreshComplete();
            }

            @Override
            public void onFail(Exception error) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
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
        if (TO_MENU_DETAIL_INDEX >=0 ){
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
}
