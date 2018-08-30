package com.melonltd.naber.view.user.page;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.bigkoo.alertview.OnItemClickListener;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.melonltd.naber.R;
import com.melonltd.naber.model.api.ApiManager;
import com.melonltd.naber.model.api.ThreadCallback;
import com.melonltd.naber.model.bean.Model;
import com.melonltd.naber.model.constant.NaberConstant;
import com.melonltd.naber.util.Tools;
import com.melonltd.naber.view.factory.PageType;
import com.melonltd.naber.view.user.UserMainActivity;
import com.melonltd.naber.view.user.adapter.UserRestaurantAdapter;
import com.melonltd.naber.vo.ReqData;
import com.melonltd.naber.vo.RestaurantInfoVo;
import com.melonltd.naber.vo.RestaurantTemplate;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

import static com.melonltd.naber.view.common.BaseCore.LOCATION_MG;

public class UserRestaurantListFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = UserRestaurantListFragment.class.getSimpleName();
    public static UserRestaurantListFragment FRAGMENT = null;
    private List<RestaurantTemplate> restaurantTemplates = Lists.<RestaurantTemplate>newArrayList();
    private List<List<RestaurantTemplate>> restaurantTemplatePages = Lists.<List<RestaurantTemplate>>newArrayList();
//    private TextView filterTypeText;
    private Button filterNameBtn, filterCategoryBtn, filterAreaBtn, filterDistanceBtn;
    private List<Button> filterBtns = Lists.newArrayList();
    private ReqData reqData = new ReqData();
    private UserRestaurantAdapter adapter;
    private Location location;

    public static int TO_RESTAURANT_DETAIL_INDEX = -1;
    public static int HOME_TO_RESTAURANT_DETAIL_INDEX = -1;

    public UserRestaurantListFragment() {
    }

    public Fragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new UserRestaurantListFragment();
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new UserRestaurantAdapter(Model.RESTAURANT_INFO_FILTER_LIST, new ItemOnClickListener());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.user_restaurant_page) == null) {
            View v = inflater.inflate(R.layout.fragment_user_restaurant, container, false);
            getViews(v);
            container.setTag(R.id.user_restaurant_page, v);
            return v;
        }
        return (View) container.getTag(R.id.user_restaurant_page);
    }

    private void getViews(View v) {
//        filterTypeText = v.findViewById(R.id.filterTypeText);
        filterNameBtn = v.findViewById(R.id.filterNameBtn);
        filterCategoryBtn = v.findViewById(R.id.filterCategoryBtn);
        filterAreaBtn = v.findViewById(R.id.filterAreaBtn);
        filterDistanceBtn = v.findViewById(R.id.filterDistanceBtn);
        filterBtns = Lists.newArrayList(filterNameBtn, filterCategoryBtn, filterAreaBtn, filterDistanceBtn);
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

        filterNameBtn.setOnClickListener(this);
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
                    filterDistanceBtn.callOnClick();
                }else {
                    doLoadData(true);
                }
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
                    reqData.uuids.addAll(getTemplate(reqData.page, false));
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
                reqData.loadingMore = list.size() % NaberConstant.PAGE == 0 && list.size() != 0;
                for (int i = 0; i < list.size(); i++) {
                    Location rl = new Location("newlocation");
                    rl.setLatitude(Double.parseDouble(list.get(i).latitude));
                    rl.setLongitude(Double.parseDouble(list.get(i).longitude));
                    list.get(i).distance = location.distanceTo(rl) / 1000;
//                    list.get(i).distance = DistanceTools.getDistance(location, LocationVo.of(list.get(i).latitude, list.get(i).longitude));
                }

                if (reqData.search_type.equals("DISTANCE")) {
                    reqData.loadingMore = restaurantTemplatePages.size() > reqData.page;
                    Ordering<RestaurantInfoVo> ordering = Ordering.natural()
                            .nullsFirst()
                            .onResultOf(new Function<RestaurantInfoVo, Double>() {
                                public Double apply(RestaurantInfoVo info) {
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
                Log.i(TAG, msg);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        UserMainActivity.changeTabAndToolbarStatus();
        if (TO_RESTAURANT_DETAIL_INDEX >= 0) {
            UserMainActivity.removeAndReplaceWhere(FRAGMENT, PageType.USER_RESTAURANT_DETAIL, null);
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
            case R.id.filterNameBtn:
                View extView = getLayoutInflater().inflate(R.layout.alert_edit_view, null);
                final EditText nameEdit = extView.findViewById(R.id.edit);
                nameEdit.setLines(1);
                nameEdit.setMaxLines(1);
                nameEdit.setHint("店家名稱");

                new AlertView.Builder()
                        .setContext(getContext())
                        .setStyle(AlertView.Style.Alert)
                        .setTitle("請輸入查詢店家名稱")
                        .setCancelText("取消")
                        .setOthers(new String[] {"確定"})
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                if (position != -1) {
                                    setFilterBtnsColor(v);
                                    if (!reqData.category.equals(NaberConstant.FILTER_CATEGORYS[position])) {
                                        reqData = new ReqData();
                                        reqData.search_type = "STORE_NAME";
                                        reqData.name = nameEdit.getText().toString();
                                        reqData.page = 1;
                                        doLoadData(true);
                                    }
                                }
                            }
                        })
                        .build()
                        .addExtView(extView)
                        .setCancelable(false)
                        .setOnDismissListener(new OnDismissListener() {
                            @Override
                            public void onDismiss(Object o) {
                                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                            }
                        })
                        .show();
                break;
            case R.id.filterCategoryBtn:
                new AlertView.Builder()
                        .setContext(getContext())
                        .setStyle(AlertView.Style.ActionSheet)
                        .setCancelText("取消")
                        .setOthers(NaberConstant.FILTER_CATEGORYS)
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                if (position != -1) {
                                    setFilterBtnsColor(v);
                                    if (!reqData.category.equals(NaberConstant.FILTER_CATEGORYS[position])) {
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
//                        .setTitle("請選擇區域")
                        .setOthers(NaberConstant.FILTER_AREAS)
                        .setCancelText("取消")
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                if (position != -1) {
                                    setFilterBtnsColor(v);
                                    if (!reqData.area.equals(NaberConstant.FILTER_AREAS[position])) {
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
                setFilterBtnsColor(v);
                restaurantTemplatePages.clear();
                ApiManager.restaurantTemplate(new ThreadCallback(getContext()) {
                    @Override
                    public void onSuccess(String responseBody) {
                        restaurantTemplates.clear();
                        restaurantTemplates.addAll(Tools.JSONPARSE.fromJsonList(responseBody, RestaurantTemplate[].class));
                        for (int i = 0; i < restaurantTemplates.size(); i++) {
                            Location rl = new Location("newlocation");
                            rl.setLatitude(restaurantTemplates.get(i).latitude);
                            rl.setLongitude(restaurantTemplates.get(i).longitude);
                            restaurantTemplates.get(i).distance = location.distanceTo(rl) / 1000;
//                            restaurantTemplates.get(i).distance = DistanceTools.getDistance(Model.LOCATION, LocationVo.of(restaurantTemplates.get(i).latitude, restaurantTemplates.get(i).longitude));
                        }
                        Ordering<RestaurantTemplate> ordering = Ordering.natural().nullsFirst().onResultOf(new Function<RestaurantTemplate, Double>() {
                            public Double apply(RestaurantTemplate template) {
                                return template.distance;
                            }
                        });
                        restaurantTemplatePages.addAll(Lists.partition(ordering.sortedCopy(restaurantTemplates), 10));

                        reqData = new ReqData();
                        reqData.search_type = "DISTANCE";
                        reqData.uuids = Lists.newArrayList();
                        reqData.page = 1;
                        reqData.uuids.addAll(getTemplate(reqData.page, true));
                        doLoadData(true);
                    }

                    @Override
                    public void onFail(Exception error, String msg) {

                    }
                });
                break;
        }
    }

    private  void setFilterBtnsColor(View v){
        for(Button b : filterBtns) {
            b.setBackground(getResources().getDrawable(R.drawable.naber_reverse_gary_button_style));
        }
        v.setBackground(getResources().getDrawable(R.drawable.naber_button_style));
    }

    private  List<String> getTemplate(int page, boolean isRefresh) {
        if(isRefresh){
            restaurantTemplatePages.clear();
            for (int i = 0; i < restaurantTemplates.size(); i++) {
                Location rl = new Location("newlocation");
                rl.setLatitude(restaurantTemplates.get(i).latitude);
                rl.setLongitude(restaurantTemplates.get(i).longitude);
                restaurantTemplates.get(i).distance = location.distanceTo(rl) / 1000;

//                restaurantTemplates.get(i).distance = DistanceTools.getDistance(Model.LOCATION, LocationVo.of(restaurantTemplates.get(i).latitude, restaurantTemplates.get(i).longitude));
            }
            Ordering<RestaurantTemplate> ordering = Ordering.natural().nullsFirst().onResultOf(new Function<RestaurantTemplate, Double>() {
                public Double apply(RestaurantTemplate template) {
                    return template.distance;
                }
            });
            restaurantTemplatePages.addAll(Lists.partition(ordering.sortedCopy(restaurantTemplates), 10));
        }
        List<String> uuids = Lists.<String>newArrayList();
        if (restaurantTemplatePages.size() <= page - 1) {
            return uuids;
        }
        if (!restaurantTemplatePages.isEmpty()) {
            for (int i = 0; i < restaurantTemplatePages.get(page - 1).size(); i++) {
                uuids.add(restaurantTemplatePages.get(page - 1).get(i).restaurant_uuid);
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
            UserMainActivity.removeAndReplaceWhere(FRAGMENT, PageType.USER_RESTAURANT_DETAIL, bundle);
        }
    }

}


