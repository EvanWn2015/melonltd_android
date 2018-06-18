package com.melonltd.naberc.view.user.page.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.common.collect.Lists;
import com.melonltd.naberc.R;
import com.melonltd.naberc.model.helper.okhttp.ApiCallback;
import com.melonltd.naberc.model.helper.okhttp.ApiManager;
import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.common.factory.PageFragmentFactory;
import com.melonltd.naberc.view.common.type.PageType;
import com.melonltd.naberc.view.user.UserMainActivity;
import com.melonltd.naberc.view.user.adapter.CategoryAdapter;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class RestaurantDetailFragment extends Fragment {
    private static final String TAG = RestaurantDetailFragment.class.getSimpleName();
    public static RestaurantDetailFragment FRAGMENT = null;
//    private SimpleDraweeView  restaurantIcon;
    private TextView restaurantBulletinText, restaurantNameText, businessTimeText, addressText, distanceText;

    private BGARefreshLayout bgaRefreshLayout;
    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private List<String> list = Lists.newArrayList();

    public static int TO_CATEGORY_MENU_INDEX = -1;


    public RestaurantDetailFragment() {
    }

    public Fragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new RestaurantDetailFragment();
            TO_CATEGORY_MENU_INDEX = -1;
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new CategoryAdapter(list);
        Fresco.initialize(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.user_restaurant_detail_page) == null) {
            View v = inflater.inflate(R.layout.fragment_restaurant_detail, container, false);
            getViews(v);
            setListener();
            container.setTag(R.id.user_restaurant_detail_page, v);
            return v;
        }
        return (View) container.getTag(R.id.user_restaurant_detail_page);
    }

    private void getViews(View v) {
        // header view
        SimpleDraweeView restaurantBackgroundImage = v.findViewById(R.id.restaurantDetailImage);
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithResourceId(R.drawable.naber_default_image).build();
        restaurantBackgroundImage.setImageURI(imageRequest.getSourceUri());

        SimpleDraweeView restaurantIcon = v.findViewById(R.id.restaurantImageView);
        restaurantIcon.setImageURI(ImageRequestBuilder.newBuilderWithResourceId(R.drawable.naber_icon_logo_reverse).build().getSourceUri());

        restaurantBulletinText = v.findViewById(R.id.restaurantDetailBulletinText);
        restaurantNameText = v.findViewById(R.id.restaurantNameText);
        businessTimeText = v.findViewById(R.id.businessTimeText);
        addressText = v.findViewById(R.id.addressText);
        distanceText = v.findViewById(R.id.distanceText);

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
        adapter.setItemClickListener(new ItemOnClickListener());

        bgaRefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                bgaRefreshLayout.endRefreshing();
                ApiManager.test(new ApiCallback(getContext()) {
                    @Override
                    public void onSuccess(String responseBody) {
                        list.clear();
                        for (int i = 0; i < 30; i++) {
                            list.add("" + i);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFail(Exception error, String msg) {
                        bgaRefreshLayout.endRefreshing();
                    }
                });
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                bgaRefreshLayout.endLoadingMore();
                ApiManager.test(new ApiCallback(getContext()) {
                    @Override
                    public void onSuccess(String responseBody) {
                        for (int i = 0; i < 30; i++) {
                            list.add("" + i);
                        }
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFail(Exception error, String msg) {

                    }
                });
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // 回到此畫面即時更新種類列表
        if (list.size() == 0) {
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

        if (TO_CATEGORY_MENU_INDEX >= 0) {
            toCategoryMenuPage(TO_CATEGORY_MENU_INDEX);
        }
    }

    private void toCategoryMenuPage(int i) {
        TO_CATEGORY_MENU_INDEX = i;
        BaseCore.FRAGMENT_TAG = PageType.CATEGORY_MENU.name();
        Bundle b = new Bundle();
        b.putString("categoryName", "XXX " + (Integer.parseInt(list.get(i)) - 1));
        Fragment f = PageFragmentFactory.of(PageType.CATEGORY_MENU, b);
        getFragmentManager().beginTransaction().replace(R.id.frameContainer, f).addToBackStack(f.toString()).commit();
    }

    private void doLoadData(boolean isRefresh) {
        if (isRefresh) {
            list.clear();
        }
        ApiManager.test(new ApiCallback(getContext()) {
            @Override
            public void onSuccess(String responseBody) {
                for (int i = 0; i < 10; i++) {
                    list.add("" + i);
                }
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
        public void onClick(View view) {
            toCategoryMenuPage((int) view.getTag());
        }
    }

}
