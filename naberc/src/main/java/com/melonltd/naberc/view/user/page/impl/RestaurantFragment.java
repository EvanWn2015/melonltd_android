package com.melonltd.naberc.view.user.page.impl;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.google.common.collect.Lists;
import com.melonltd.naberc.R;
import com.melonltd.naberc.model.helper.okhttp.ApiCallback;
import com.melonltd.naberc.model.helper.okhttp.ApiManager;
import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;
import com.melonltd.naberc.view.common.factory.PageFragmentFactory;
import com.melonltd.naberc.view.common.type.PageType;
import com.melonltd.naberc.view.user.adapter.RestaurantAdapter;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class RestaurantFragment extends AbsPageFragment implements View.OnClickListener {
    private static final String TAG = RestaurantFragment.class.getSimpleName();
    public static RestaurantFragment FRAGMENT = null;

    private TextView filterTypeText;
    private Button filterCategoryBtn, filterAreaBtn, filterDistanceBtn;

    private BGARefreshLayout bgaRefreshLayout;
    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;
    private List<String> list = Lists.newArrayList();

    public static int TO_RESTAURANT_DETAIL_INDEX = -1;

    public static int HOME_TO_RESTAURANT_DETAIL_INDEX = -1;

    public RestaurantFragment() {
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new RestaurantFragment();
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new RestaurantFragment();
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new RestaurantAdapter(list);
        adapter.setItemOnClickListener(new ItemOnClickListener());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.user_restaurant_page) == null) {
            View v = inflater.inflate(R.layout.fragment_restaurant, container, false);
            getViews(v);
            setListener();
            container.setTag(R.id.user_restaurant_page, v);
            return v;
        }
        return (View) container.getTag(R.id.user_restaurant_page);
    }

    private void getViews(View v) {
        filterTypeText = v.findViewById(R.id.filterTypeText);
        filterCategoryBtn = v.findViewById(R.id.filterCategoryBtn);
        filterAreaBtn = v.findViewById(R.id.filterAreaBtn);
        filterDistanceBtn = v.findViewById(R.id.filterDistanceBtn);

        bgaRefreshLayout = v.findViewById(R.id.restaurantBGARefreshLayout);
        recyclerView = v.findViewById(R.id.restaurantRecyclerView);

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
        recyclerView.setAdapter(adapter);
        filterCategoryBtn.setOnClickListener(this);
        filterAreaBtn.setOnClickListener(this);
        filterDistanceBtn.setOnClickListener(this);

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
                ApiManager.test(new ApiCallback(getActivity()) {
                    @Override
                    public void onSuccess(String responseBody) {
                        for (int i = 0; i < 30; i++) {
                            list.add("" + i);
                        }
                        adapter.notifyDataSetChanged();
                        bgaRefreshLayout.endRefreshing();
                    }

                    @Override
                    public void onFail(Exception error) {
                        bgaRefreshLayout.endRefreshing();
                    }
                });
                return false;
            }
        });

    }

    private void doLoadData(boolean isRefresh) {
        if (isRefresh) {
            list.clear();
            adapter.notifyDataSetChanged();
        }
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

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // TODO Bundle check where to detail page HOME or this
        if (TO_RESTAURANT_DETAIL_INDEX >= 0) {
            Bundle b = new Bundle();
//            b.putString("where", "RESTAURANT");
            BaseCore.FRAGMENT_TAG = PageType.RESTAURANT_DETAIL.name();
            AbsPageFragment f = PageFragmentFactory.of(PageType.RESTAURANT_DETAIL, b);
            getFragmentManager().beginTransaction().replace(R.id.baseContainer, f).commit();
//        } else if (HOME_TO_RESTAURANT_DETAIL_INDEX >=0){
//            Log.d(TAG, "home to this ");
        } else {
            if (list.size() == 0) {
                doLoadData(true);
            }
        }

    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.filterCategoryBtn:
                final String[] categorys = new String[]{"火鍋", "燒烤/居酒屋", "鐵板燒", "素蔬食", "早午餐", "下午茶", "西式/牛排", "中式", "港式", "日式", "韓式", "異國", "美式", "義式", "熱炒", "小吃", "泰式", "咖啡輕食", "甜點", "冰飲"};
                new AlertView.Builder()
                        .setContext(getContext())
                        .setStyle(AlertView.Style.ActionSheet)
                        .setTitle("請選擇種類")
                        .setOthers(categorys)
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                filterTypeText.setText(categorys[position]);
                                doLoadData(true);
                            }
                        })
                        .build()
                        .setCancelable(true)
                        .show();
                break;
            case R.id.filterAreaBtn:
                final String[] areas = new String[]{"桃園區", "中壢區", "平鎮區", "龍潭區", "楊梅區", "新屋區", "觀音區", "龜山區", "八德區", "大溪區", "大園區", "蘆竹區", "復興區"};
                new AlertView.Builder()
                        .setContext(getContext())
                        .setStyle(AlertView.Style.ActionSheet)
                        .setTitle("請選擇區域")
//                        .setDestructive(areas)
                        .setOthers(areas)
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                filterTypeText.setText(areas[position]);
                                doLoadData(true);
                            }
                        })
                        .build()
                        .setCancelable(true)
                        .show();
                break;
            case R.id.filterDistanceBtn:
                filterTypeText.setText("離我最近");
                doLoadData(true);

//                restaurantListView.setSelection(10);

                break;
        }
    }

    class ItemOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Log.d(TAG, view.getTag() + "");
            Log.d(TAG, "item on :" + view.getTag());
            TO_RESTAURANT_DETAIL_INDEX = (int)view.getTag();
            Bundle b = new Bundle();
            b.putString("where", "RESTAURANT");
            BaseCore.FRAGMENT_TAG = PageType.RESTAURANT_DETAIL.name();
            AbsPageFragment f = PageFragmentFactory.of(PageType.RESTAURANT_DETAIL, b);
            getFragmentManager().beginTransaction().replace(R.id.frameContainer, f).commit();

        }
    }

}


