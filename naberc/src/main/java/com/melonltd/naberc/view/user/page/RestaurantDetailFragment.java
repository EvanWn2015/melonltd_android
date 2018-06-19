package com.melonltd.naberc.view.user.page;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.melonltd.naberc.R;
import com.melonltd.naberc.model.api.ApiManager;
import com.melonltd.naberc.model.api.ThreadCallback;
import com.melonltd.naberc.model.bean.CommonData;
import com.melonltd.naberc.model.constant.NaberConstant;
import com.melonltd.naberc.util.DistanceTools;
import com.melonltd.naberc.util.Tools;
import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.factory.PageFragmentFactory;
import com.melonltd.naberc.view.factory.PageType;
import com.melonltd.naberc.view.user.UserMainActivity;
import com.melonltd.naberc.view.user.adapter.CategoryAdapter;
import com.melonltd.naberc.vo.LocationVo;
import com.melonltd.naberc.vo.RestaurantCategoryRelVo;
import com.melonltd.naberc.vo.RestaurantInfoVo;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class RestaurantDetailFragment extends Fragment {
    private static final String TAG = RestaurantDetailFragment.class.getSimpleName();
    public static RestaurantDetailFragment FRAGMENT = null;

    private CategoryAdapter adapter;
//    private List<RestaurantCategoryRelVo> list = Lists.newArrayList();
    private ViewHolder holder;
    public static int TO_CATEGORY_MENU_INDEX = -1;

    public RestaurantDetailFragment() {
    }

    public Fragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new RestaurantDetailFragment();
            TO_CATEGORY_MENU_INDEX = -1;
        }
        if (bundle != null) {
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new CategoryAdapter(CommonData.RESTAURANT_CATEGORY_REL_LIST);
        Fresco.initialize(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.user_restaurant_detail_page) == null) {
            View v = inflater.inflate(R.layout.fragment_restaurant_detail, container, false);
            getViews(v);
            container.setTag(R.id.user_restaurant_detail_page, v);
            return v;
        }
        serHeaderValue(getArguments());
        return (View) container.getTag(R.id.user_restaurant_detail_page);
    }


    private void serHeaderValue(Bundle bundle) {
        final RestaurantInfoVo vo = (RestaurantInfoVo) bundle.getSerializable(NaberConstant.RESTAURANT_INFO);
        holder.uuid = vo.restaurant_uuid;

        Log.i(TAG ,"is_store_now_open" + vo.is_store_now_open);
        if (!Strings.isNullOrEmpty(vo.is_store_now_open)){
            if (vo.is_store_now_open.toUpperCase().equals("FALSE")){
                holder.warningText.setVisibility(View.VISIBLE);
                // show
                String msg = "該店家今天已經結束接單，請明天再嘗試！";
                new AlertView.Builder()
                        .setTitle("")
                        .setMessage(msg)
                        .setContext(getContext())
                        .setStyle(AlertView.Style.Alert)
                        .setOthers(new String[]{"我知道了"})
                        .build()
                        .setCancelable(true)
                        .show();
            }else {
                holder.warningText.setVisibility(View.GONE);
            }
        }

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (!Strings.isNullOrEmpty(vo.photo)) {
                    holder.restaurantIcon.setImageURI(Uri.parse(vo.photo));
                } else {
                    holder.restaurantIcon.setImageURI(Uri.parse(""));
                }
                if (!Strings.isNullOrEmpty(vo.background_photo)) {
                    holder.restaurantBackgroundImage.setImageURI(Uri.parse(vo.background_photo));
                } else {
                    holder.restaurantBackgroundImage.setImageURI(Uri.parse(""));
                }
                holder.restaurantBulletinText.setText(vo.bulletin);
                holder.restaurantNameText.setText(vo.name);
                holder.businessTimeText.setText("接單時間: " + vo.store_start + "~" + vo.store_end);
                holder.addressText.setText(vo.address);
                String distance = DistanceTools.getGoogleDistance(CommonData.LOCATION, LocationVo.of(Double.parseDouble(vo.latitude), Double.parseDouble(vo.longitude)));
                holder.distanceText.setText(distance);
            }
        });

    }

    private void getViews(View v) {
        // header view
        holder = new ViewHolder(v);
        Bundle bundle = getArguments();
//        final RestaurantInfoVo vo = (RestaurantInfoVo) bundle.getSerializable("restaurantInfo");
        serHeaderValue(bundle);

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
        adapter.setItemClickListener(new ItemOnClickListener());

        bgaRefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                bgaRefreshLayout.endRefreshing();
                doLoadData(true);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // 回到此畫面即時更新種類列表
        if (TO_CATEGORY_MENU_INDEX == -1){
            doLoadData(true);
        }

        UserMainActivity.changeTabAndToolbarStatus();
        if (UserMainActivity.toolbar != null) {
            UserMainActivity.navigationIconDisplay(true, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    backToRegisteredPage();
                    UserMainActivity.navigationIconDisplay(false, null);
                }
            });
        }

        if (TO_CATEGORY_MENU_INDEX >= 0 ) {
            toCategoryMenuPage(TO_CATEGORY_MENU_INDEX);
        }
    }

    private void toCategoryMenuPage(int index) {

        TO_CATEGORY_MENU_INDEX = index;
        BaseCore.FRAGMENT_TAG = PageType.CATEGORY_MENU.name();
        Bundle bundle = new Bundle();
        bundle.putSerializable(NaberConstant.RESTAURANT_CATEGORY_REL, CommonData.RESTAURANT_CATEGORY_REL_LIST.get(index));
        Fragment f = PageFragmentFactory.of(PageType.CATEGORY_MENU, bundle);
        getFragmentManager().beginTransaction().replace(R.id.frameContainer, f).addToBackStack(f.toString()).commit();
    }

    private void doLoadData(boolean isRefresh) {
        if (isRefresh) {
            CommonData.RESTAURANT_CATEGORY_REL_LIST.clear();
        }
        ApiManager.restaurantDetail(holder.uuid, new ThreadCallback(getContext()) {
            @Override
            public void onSuccess(String responseBody) {
                List<RestaurantCategoryRelVo> vos = Tools.JSONPARSE.fromJsonList(responseBody, RestaurantCategoryRelVo[].class);
                CommonData.RESTAURANT_CATEGORY_REL_LIST.addAll(vos);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(Exception error, String msg) {

            }
        });
    }

    private void backToRegisteredPage() {
        BaseCore.FRAGMENT_TAG = PageType.RESTAURANT.name();
        RestaurantFragment.TO_RESTAURANT_DETAIL_INDEX = -1;
        Fragment f = PageFragmentFactory.of(PageType.RESTAURANT, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.frameContainer, f).addToBackStack(f.toString()).commit();
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

    class ItemOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int index = (int)v.getTag();
            toCategoryMenuPage(index);
        }
    }


    private class ViewHolder {
        public String uuid;
        public SimpleDraweeView restaurantBackgroundImage;
        public SimpleDraweeView restaurantIcon;
        public TextView restaurantBulletinText;
        public TextView restaurantNameText;
        public TextView businessTimeText;
        public TextView addressText;
        public TextView distanceText;
        public TextView warningText;

        ViewHolder(View v) {
            this.restaurantBackgroundImage = v.findViewById(R.id.restaurantDetailImage);
            this.restaurantIcon = v.findViewById(R.id.restaurantImageView);
            this.restaurantBulletinText = v.findViewById(R.id.restaurantDetailBulletinText);
            this.restaurantNameText = v.findViewById(R.id.restaurantNameText);
            this.businessTimeText = v.findViewById(R.id.businessTimeText);
            this.addressText = v.findViewById(R.id.addressText);
            this.distanceText = v.findViewById(R.id.distanceText);
            this.warningText = v.findViewById(R.id.warningText);
        }
    }

}
