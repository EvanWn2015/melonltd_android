package com.melonltd.naber.view.user.page;

import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
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
import com.google.common.collect.Lists;
import com.melonltd.naber.R;
import com.melonltd.naber.model.api.ApiManager;
import com.melonltd.naber.model.api.ThreadCallback;
import com.melonltd.naber.model.bean.Model;
import com.melonltd.naber.model.constant.NaberConstant;
import com.melonltd.naber.util.IntegerTools;
import com.melonltd.naber.util.Tools;
import com.melonltd.naber.view.common.BaseCore;
import com.melonltd.naber.view.customize.CoverFlowLayoutManger;
import com.melonltd.naber.view.customize.RecyclerCoverFlow;
import com.melonltd.naber.view.factory.PageType;
import com.melonltd.naber.view.user.UserMainActivity;
import com.melonltd.naber.view.user.adapter.UserCategoryAdapter;
import com.melonltd.naber.view.user.adapter.UserStorePhotoAdapter;
import com.melonltd.naber.vo.CategoryRelVo;
import com.melonltd.naber.vo.FoodVo;
import com.melonltd.naber.vo.ReqData;
import com.melonltd.naber.vo.RestaurantInfoVo;
import com.melonltd.naber.vo.RestaurantPhotoVo;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class UserRestaurantDetailFragment extends Fragment {
    private static final String TAG = UserRestaurantDetailFragment.class.getSimpleName();
    public static UserRestaurantDetailFragment FRAGMENT = null;
    public static List<CategoryRelVo> restaurantCategoryRelVos = Lists.newArrayList();
    private UserCategoryAdapter adapter;
    private ViewHolder holder;
    public static int TO_CATEGORY_MENU_INDEX = -1;

    private UserStorePhotoAdapter photoAdapter;
    private Handler handler = new Handler();
    private PhotoRun photoRun;
    private List<RestaurantPhotoVo> photos = Lists.newArrayList();
    private CoverFlowLayoutManger coverFlowLayoutManger;
    private int photoAutoPlayInterval = 3000;

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
        adapter = new UserCategoryAdapter(restaurantCategoryRelVos);
        photoAdapter = new UserStorePhotoAdapter(photos, new PhotoClick());
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

        if (Model.LOCATION != null) {
            Location rl = new Location("newlocation");
            rl.setLatitude(Double.parseDouble(vo.latitude));
            rl.setLongitude(Double.parseDouble(vo.longitude));
            double distance = Model.LOCATION.distanceTo(rl) / 1000;
            String result = Tools.FORMAT.decimal("0.0", distance);
            holder.distanceText.setText(result.equals("0.0") ? "0.1" : result + "公里");
        } else {
            holder.distanceText.setText("");
        }

    }

    private void getViews(View v) {

        final BGARefreshLayout bgaRefreshLayout = v.findViewById(R.id.restaurantBGARefreshLayout);

        View header = LayoutInflater.from(getContext()).inflate(R.layout.fragment_user_restaurant_detail_header, null, true);
        bgaRefreshLayout.setCustomHeaderView(header, true);
        holder = new ViewHolder(header);
        Bundle bundle = getArguments();
        serHeaderValue(bundle);

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


        final RecyclerCoverFlow photoRecycler = header.findViewById(R.id.recyclerCoverFlow);
        coverFlowLayoutManger = new CoverFlowLayoutManger.Builder()
                .setFlat(false)
                .setGreyItem(true)
                .setAlphaItem(true)
                .setIntervalRatio(-1.0f)
                .build();
        photoRecycler.setLayoutManager(coverFlowLayoutManger);
        photoRecycler.setHasFixedSize(true);
        photoRecycler.setAdapter(photoAdapter);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(photoRecycler);
        photoRecycler.scrollToPosition(this.photos.size() / 2);

        photoRecycler.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
            }
        });

        photoRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }
        });

        this.photoRun = new PhotoRun(photoRecycler);
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
                                .setMessage("該商家今日已結束接單")
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
        bundle.putSerializable(NaberConstant.RESTAURANT_CATEGORY_REL, restaurantCategoryRelVos.get(index));
        UserMainActivity.removeAndReplaceWhere(FRAGMENT, PageType.USER_FOOD_LIST, bundle);
    }

    private void doLoadData(boolean isRefresh) {
        if (isRefresh) {
            restaurantCategoryRelVos.clear();
            handler.removeCallbacks(this.photoRun);
            coverFlowLayoutManger.scrollToPosition(0);
            photos.clear();
            photoAdapter.notifyDataSetChanged();
        }
        ApiManager.restaurantDetail(holder.uuid, new ThreadCallback(getContext()) {
            @Override
            public void onSuccess(String responseBody) {
                List<CategoryRelVo> vos = Tools.JSONPARSE.fromJsonList(responseBody, CategoryRelVo[].class);
                Collections.sort(vos, new Comparator<CategoryRelVo>() {
                    public int compare(CategoryRelVo o1, CategoryRelVo o2) {
                        return IntegerTools.parseInt(o1.top, 0) - IntegerTools.parseInt(o2.top, 0);
                    }
                });
                restaurantCategoryRelVos.addAll(vos);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(Exception error, String msg) {

            }
        });

        ReqData req = new ReqData();
        req.uuid = holder.uuid;
        ApiManager.getRestaurantPhotoList(req, new ThreadCallback(getContext()) {
            @Override
            public void onSuccess(String responseBody) {
                List<RestaurantPhotoVo> vos = Tools.JSONPARSE.fromJsonList(responseBody, RestaurantPhotoVo[].class);
                photos.addAll(vos);
                photoAdapter.notifyDataSetChanged();
                if (!photos.isEmpty() || photos.size() > 1) {
                    if (photos.size() > 2){
                        coverFlowLayoutManger.scrollToPosition(photos.size() / 2);
                    }
                    handler.post(photoRun);
                }
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
        handler.removeCallbacks(this.photoRun);
        coverFlowLayoutManger.scrollToPosition(0);
//        photos.clear();
//        photoAdapter.notifyDataSetChanged();
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

    // 輪播圖片線程
    class PhotoRun implements  Runnable {
        RecyclerCoverFlow mList;
        PhotoRun (RecyclerCoverFlow mList){
            this.mList = mList;
        }
        @Override
        public void run() {
            mList.smoothScrollToPosition(mList.getSelectedPos() + 1);
            handler.postDelayed(this, photoAutoPlayInterval);
        }
    }

    class PhotoClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            RestaurantPhotoVo vo = (RestaurantPhotoVo)view.getTag();

            ApiManager.restaurantFoodDetail(vo.uuid, new ThreadCallback(getContext()) {
                @Override
                public void onSuccess(String responseBody) {
                    FoodVo food = Tools.JSONPARSE.fromJson(responseBody, FoodVo.class);
                    String catName = "";
                    int index = 0;
                    for(CategoryRelVo cat : restaurantCategoryRelVos){
                        if (food.category_uuid.equals(cat.category_uuid)){
                            catName = cat.category_name;
                            index = restaurantCategoryRelVos.indexOf(cat);
                        }
                    }
                    UserFoodListFragment.TO_REST_DETAIL_INDEX = 1;
                    UserRestaurantListFragment.TO_RESTAURANT_DETAIL_INDEX = index;
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(NaberConstant.RESTAURANT_CATEGORY_REL, restaurantCategoryRelVos.get(index));
                    bundle.putSerializable(NaberConstant.RESTAURANT_INFO, getArguments().getSerializable(NaberConstant.RESTAURANT_INFO));
                    bundle.putString(NaberConstant.CATEGORY_NAME, catName);
                    bundle.putSerializable(NaberConstant.FOOD_INFO, food);
                    UserMainActivity.removeAndReplaceWhere(FRAGMENT, PageType.USER_FOOD_DETAIL, bundle);

                }

                @Override
                public void onFail(Exception error, String msg) {

                }
            });
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
