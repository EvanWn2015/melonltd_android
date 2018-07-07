package com.melonltd.naber.view.user.page;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.common.base.Strings;
import com.melonltd.naber.R;
import com.melonltd.naber.model.api.ApiManager;
import com.melonltd.naber.model.api.ThreadCallback;
import com.melonltd.naber.model.bean.Model;
import com.melonltd.naber.model.constant.NaberConstant;
import com.melonltd.naber.util.DistanceTools;
import com.melonltd.naber.util.Tools;
import com.melonltd.naber.view.common.BaseCore;
import com.melonltd.naber.view.factory.PageType;
import com.melonltd.naber.view.user.UserMainActivity;
import com.melonltd.naber.view.user.adapter.UserCategoryAdapter;
import com.melonltd.naber.vo.LocationVo;
import com.melonltd.naber.vo.RestaurantCategoryRelVo;
import com.melonltd.naber.vo.RestaurantInfoVo;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class UserRestaurantDetailFragment extends Fragment {
//    private static final String TAG = UserRestaurantDetailFragment.class.getSimpleName();
    public static UserRestaurantDetailFragment FRAGMENT = null;

    private UserCategoryAdapter adapter;
    private ViewHolder holder;
    public static int TO_CATEGORY_MENU_INDEX = -1;

    public UserRestaurantDetailFragment() {
    }

    public Fragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new UserRestaurantDetailFragment();
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
        adapter = new UserCategoryAdapter(Model.RESTAURANT_CATEGORY_REL_LIST);
        Fresco.initialize(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.user_restaurant_detail_page) == null) {
            View v = inflater.inflate(R.layout.fragment_user_restaurant_detail, container, false);
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

        if (!Strings.isNullOrEmpty(vo.photo)) {
            holder.restaurantIcon.setImageURI(Uri.parse(vo.photo));
        } else {
            ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithResourceId(R.drawable.naber_icon_logo_reverse).build();
            holder.restaurantIcon.setImageURI(imageRequest.getSourceUri());
        }
        if (!Strings.isNullOrEmpty(vo.background_photo)) {
            holder.restaurantBackgroundImage.setImageURI(Uri.parse(vo.background_photo));
        } else {
            ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithResourceId(R.drawable.naber_default_image).build();
            holder.restaurantBackgroundImage.setImageURI(imageRequest.getSourceUri());
        }
        holder.restaurantBulletinText.setText(vo.bulletin);
        holder.restaurantNameText.setText(vo.name);
        holder.businessTimeText.setText("接單時間: " + vo.store_start + "~" + vo.store_end);
        holder.addressText.setText(vo.address);



//        holder.distanceText.setText(result.equals("0.0") ? "0.1" : result + "公里");
        double distance = DistanceTools.getDistance(Model.LOCATION, LocationVo.of(Double.parseDouble(vo.latitude), Double.parseDouble(vo.longitude)));
        String result = Tools.FORMAT.decimal("0.0", distance);
        holder.distanceText.setText(result.equals("0.0") ? "0.1" : result + "公里");

    }

    private void getViews(View v) {
        // header view
        holder = new ViewHolder(v);
        Bundle bundle = getArguments();
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
        if (TO_CATEGORY_MENU_INDEX == -1) {
            doLoadData(true);
        }

        UserMainActivity.changeTabAndToolbarStatus();
        if (UserMainActivity.toolbar != null) {
            UserMainActivity.navigationIconDisplay(true, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UserRestaurantListFragment.TO_RESTAURANT_DETAIL_INDEX = -1;
                    UserMainActivity.removeAndReplaceWhere(FRAGMENT, PageType.USER_RESTAURANT_LIST, null);
                    UserMainActivity.navigationIconDisplay(false, null);
                }
            });
        }

        if (TO_CATEGORY_MENU_INDEX == -1) {
            RestaurantInfoVo vo = (RestaurantInfoVo) getArguments().get(NaberConstant.RESTAURANT_INFO);
            if (vo.isShowOne) {
                if (!Strings.isNullOrEmpty(vo.is_store_now_open)) {
                    holder.warningText.setVisibility(View.GONE);
                    if (vo.not_business.size() > 0) {
                        holder.warningText.setVisibility(View.VISIBLE);
                        new AlertView.Builder()
                                .setTitle("")
                                .setMessage("該商家今日不營業")
                                .setContext(getContext())
                                .setStyle(AlertView.Style.Alert)
                                .setOthers(new String[]{"我知道了"})
                                .build()
                                .setCancelable(true)
                                .show();
                    } else if (vo.is_store_now_open.toUpperCase().equals("FALSE")) {
                        new AlertView.Builder()
                                .setTitle("")
                                .setMessage("目前時間該商家尚未營業")
                                .setContext(getContext())
                                .setStyle(AlertView.Style.Alert)
                                .setOthers(new String[]{"我知道了"})
                                .build()
                                .setCancelable(true)
                                .show();
                    }
                    vo.isShowOne = false;
                }
            }
        }

        if (TO_CATEGORY_MENU_INDEX >= 0) {
            toCategoryMenuPage(TO_CATEGORY_MENU_INDEX);
        }
    }

    private void toCategoryMenuPage(int index) {

        TO_CATEGORY_MENU_INDEX = index;
        BaseCore.FRAGMENT_TAG = PageType.USER_FOOD_LIST.name();
        Bundle bundle = new Bundle();
//        RestaurantInfoVo vo = (RestaurantInfoVo) getArguments().getSerializable(NaberConstant.RESTAURANT_INFO);
        bundle.putSerializable(NaberConstant.RESTAURANT_INFO, (RestaurantInfoVo) getArguments().get(NaberConstant.RESTAURANT_INFO));
        bundle.putSerializable(NaberConstant.RESTAURANT_CATEGORY_REL, Model.RESTAURANT_CATEGORY_REL_LIST.get(index));
        UserMainActivity.removeAndReplaceWhere(FRAGMENT, PageType.USER_FOOD_LIST, bundle);
    }

    private void doLoadData(boolean isRefresh) {
        if (isRefresh) {
            Model.RESTAURANT_CATEGORY_REL_LIST.clear();
        }
        ApiManager.restaurantDetail(holder.uuid, new ThreadCallback(getContext()) {
            @Override
            public void onSuccess(String responseBody) {
                List<RestaurantCategoryRelVo> vos = Tools.JSONPARSE.fromJsonList(responseBody, RestaurantCategoryRelVo[].class);
                Model.RESTAURANT_CATEGORY_REL_LIST.addAll(vos);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(Exception error, String msg) {

            }
        });
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
            int index = (int) v.getTag();
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