package com.melonltd.naber.view.user.page;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.melonltd.naber.R;
import com.melonltd.naber.model.api.ApiManager;
import com.melonltd.naber.model.api.ThreadCallback;
import com.melonltd.naber.model.bean.Model;
import com.melonltd.naber.model.constant.NaberConstant;
import com.melonltd.naber.util.DistanceTools;
import com.melonltd.naber.util.Tools;
import com.melonltd.naber.view.common.BaseCore;
import com.melonltd.naber.view.factory.PageFragmentFactory;
import com.melonltd.naber.view.factory.PageType;
import com.melonltd.naber.view.user.UserMainActivity;
import com.melonltd.naber.view.user.adapter.RestaurantAdapter;
import com.melonltd.naber.vo.LocationVo;
import com.melonltd.naber.vo.ReqData;
import com.melonltd.naber.vo.RestaurantInfoVo;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

import static com.melonltd.naber.view.common.BaseCore.LOCATION_MG;

public class RestaurantFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = RestaurantFragment.class.getSimpleName();
    public static RestaurantFragment FRAGMENT = null;

    private TextView filterTypeText;
    private Button filterCategoryBtn, filterAreaBtn, filterDistanceBtn;
    private ReqData reqData = new ReqData();
    private RestaurantAdapter adapter;
    private Location location;

    public static int TO_RESTAURANT_DETAIL_INDEX = -1;
    public static int HOME_TO_RESTAURANT_DETAIL_INDEX = -1;

    public RestaurantFragment() {
    }

    public Fragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new RestaurantFragment();
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new RestaurantAdapter(Model.RESTAURANT_INFO_FILTER_LIST, new ItemOnClickListener());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.user_restaurant_page) == null) {
            View v = inflater.inflate(R.layout.fragment_restaurant, container, false);
            getViews(v);
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

        final BGARefreshLayout bgaRefreshLayout = v.findViewById(R.id.restaurantBGARefreshLayout);
        RecyclerView recyclerView = v.findViewById(R.id.restaurantRecyclerView);

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
        recyclerView.setAdapter(adapter);
        filterCategoryBtn.setOnClickListener(this);
        filterAreaBtn.setOnClickListener(this);
        filterDistanceBtn.setOnClickListener(this);

        bgaRefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                bgaRefreshLayout.endRefreshing();
                getLocation();
                reqData.loadingMore = true;
                reqData.page = 1;
                if (reqData.search_type.equals("DISTANCE")) {
                    reqData.uuids.clear();
                    reqData.uuids.addAll(getTemplate(reqData.page));
                }
                doLoadData(true);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                bgaRefreshLayout.endLoadingMore();
                if (!reqData.loadingMore) {
                    return false;
                }

                reqData.page++;
                if (reqData.search_type.equals("DISTANCE")) {
                    reqData.uuids.clear();
                    reqData.uuids.addAll(getTemplate(reqData.page));
                }
                doLoadData(false);
                return false;
            }
        });
    }

    private void doLoadData(boolean isRefresh) {
        if (isRefresh) {
            Model.RESTAURANT_INFO_FILTER_LIST.clear();
            adapter.notifyDataSetChanged();
        }

        ApiManager.restaurantList(reqData, new ThreadCallback(getContext()) {
            @Override
            public void onSuccess(String responseBody) {
                List<RestaurantInfoVo> list = Tools.JSONPARSE.fromJsonList(responseBody, RestaurantInfoVo[].class);
                reqData.loadingMore = list.size() % 10 == 0 && list.size() != 0;
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).distance = DistanceTools.getGoogleDistance(location, LocationVo.of(list.get(i).latitude, list.get(i).longitude));
                }

                if (reqData.search_type.equals("DISTANCE")) {

                    reqData.loadingMore = Model.RESTAURANT_TEMPLATE.size() > reqData.page;
                    Ordering<RestaurantInfoVo> ordering = Ordering.natural()
                            .nullsFirst()
                            .onResultOf(new Function<RestaurantInfoVo, String>() {
                                public String apply(RestaurantInfoVo info) {
                                    return info.distance;
                                }
                            });
                    Model.RESTAURANT_INFO_FILTER_LIST.addAll(ordering.sortedCopy(list));
                } else {
                    Model.RESTAURANT_INFO_FILTER_LIST.addAll(list);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(Exception error, String msg) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        UserMainActivity.changeTabAndToolbarStatus();
        if (TO_RESTAURANT_DETAIL_INDEX >= 0) {
            BaseCore.FRAGMENT_TAG = PageType.RESTAURANT_DETAIL.name();
            Fragment f = PageFragmentFactory.of(PageType.RESTAURANT_DETAIL, null);
            getFragmentManager().beginTransaction().replace(R.id.frameContainer, f).addToBackStack(f.toString()).commit();
        } else {
            getLocation();
            if (Model.RESTAURANT_INFO_FILTER_LIST.size() == 0) {
                filterDistanceBtn.callOnClick();
            }
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (Model.LOCATION == null) {
                location = LOCATION_MG.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            } else {
                location = Model.LOCATION;
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
    public void onClick(final View v) {

        switch (v.getId()) {
            case R.id.filterCategoryBtn:
                new AlertView.Builder()

                        .setContext(getContext())
                        .setStyle(AlertView.Style.ActionSheet)
                        .setTitle("請選擇種類")
                        .setCancelText("取消")
                        .setOthers(NaberConstant.FILTER_CATEGORYS)
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                if (position != -1) {
                                    filterAreaBtn.setBackground(getResources().getDrawable(R.drawable.naber_reverse_gary_button_style));
                                    filterDistanceBtn.setBackground(getResources().getDrawable(R.drawable.naber_reverse_gary_button_style));
                                    v.setBackgroundColor(getResources().getColor(R.color.naber_basis));
                                    if (!reqData.category.equals(NaberConstant.FILTER_CATEGORYS[position])) {
                                        filterTypeText.setText(NaberConstant.FILTER_CATEGORYS[position]);
                                        reqData = new ReqData();
                                        reqData.search_type = "CATEGORY";
                                        reqData.category = NaberConstant.FILTER_CATEGORYS[position];
                                        reqData.page = 1;
                                        doLoadData(true);
                                    }
                                }
                            }
                        })
                        .build()
                        .setCancelable(true)
                        .show();
                break;
            case R.id.filterAreaBtn:
                new AlertView.Builder()
                        .setContext(getContext())
                        .setStyle(AlertView.Style.ActionSheet)
                        .setTitle("請選擇區域")
                        .setOthers(NaberConstant.FILTER_AREAS)
                        .setCancelText("取消")
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                if (position != -1) {
                                    filterCategoryBtn.setBackground(getResources().getDrawable(R.drawable.naber_reverse_gary_button_style));
                                    filterDistanceBtn.setBackground(getResources().getDrawable(R.drawable.naber_reverse_gary_button_style));
                                    v.setBackgroundColor(getResources().getColor(R.color.naber_basis));
                                    if (!reqData.area.equals(NaberConstant.FILTER_AREAS[position])) {
                                        filterTypeText.setText(NaberConstant.FILTER_AREAS[position]);
                                        reqData = new ReqData();
                                        reqData.search_type = "AREA";
                                        reqData.area = NaberConstant.FILTER_AREAS[position];
                                        reqData.page = 1;
                                        doLoadData(true);
                                    }
                                }
                            }
                        })
                        .build()
                        .setCancelable(true)
                        .show();
                break;
            case R.id.filterDistanceBtn:
                filterCategoryBtn.setBackground(getResources().getDrawable(R.drawable.naber_reverse_gary_button_style));
                filterAreaBtn.setBackground(getResources().getDrawable(R.drawable.naber_reverse_gary_button_style));
                v.setBackgroundColor(getResources().getColor(R.color.naber_basis));
                if (reqData.search_type == null || !reqData.search_type.equals("DISTANCE")) {
                    filterTypeText.setText("離我最近");
                    reqData = new ReqData();
                    reqData.search_type = "DISTANCE";
                    reqData.uuids = Lists.newArrayList();
                    reqData.page = 1;
                    reqData.uuids.addAll(getTemplate(reqData.page));
                    doLoadData(true);
                }
                break;
        }
    }

    private static List<String> getTemplate(int page) {
        List<String> uuids = Lists.<String>newArrayList();

        if (Model.RESTAURANT_TEMPLATE.size() <= page - 1) {
            return uuids;
        }
        if (!Model.RESTAURANT_TEMPLATE.isEmpty()) {
            for (int i = 0; i < Model.RESTAURANT_TEMPLATE.get(page - 1).size(); i++) {
                uuids.add(Model.RESTAURANT_TEMPLATE.get(page - 1).get(i).restaurant_uuid);
            }
        }

        return uuids;
    }

    class ItemOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int index = (int) view.getTag();
            Bundle bundle = new Bundle();
            bundle.putSerializable(NaberConstant.RESTAURANT_INFO, Model.RESTAURANT_INFO_FILTER_LIST.get(index));
            TO_RESTAURANT_DETAIL_INDEX = index;
            BaseCore.FRAGMENT_TAG = PageType.RESTAURANT_DETAIL.name();
            Fragment f = PageFragmentFactory.of(PageType.RESTAURANT_DETAIL, bundle);
            getFragmentManager().beginTransaction().replace(R.id.frameContainer, f).addToBackStack(f.toString()).commit();
        }
    }

}


