package com.melonltd.naberc.view.user.page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.common.collect.Lists;
import com.melonltd.naberc.R;
import com.melonltd.naberc.model.okhttp.ApiCallback;
import com.melonltd.naberc.model.okhttp.ApiManager;
import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.factory.PageFragmentFactory;
import com.melonltd.naberc.view.factory.PageType;
import com.melonltd.naberc.view.user.UserMainActivity;
import com.melonltd.naberc.view.user.adapter.RestaurantAdapter;

import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;


public class HomeFragment extends Fragment {
    private static final String TAG = HomeFragment.class.getSimpleName();
    public static HomeFragment FRAGMENT = null;

    private BGABanner banner;

    private RestaurantAdapter adapter;
    private List<String> list = Lists.newArrayList();
    private List<String> images = Lists.<String>newArrayList();

    public HomeFragment() {
    }

    public Fragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new HomeFragment();
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(getContext());
        adapter = new RestaurantAdapter(list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.user_home_page) == null) {
            View v = inflater.inflate(R.layout.fragment_home, container, false);
            getViews(v);
            container.setTag(R.id.user_home_page, v);
            return v;
        }
        return (View) container.getTag(R.id.user_home_page);
    }

    private void getViews(View v) {
        banner = v.findViewById(R.id.homeBanner);

        final BGARefreshLayout bgaRefreshLayout = v.findViewById(R.id.top30BGARefreshLayout);
        RecyclerView recyclerView = v.findViewById(R.id.top30RecyclerView);
        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(getContext(), true);
        refreshViewHolder.setPullDownRefreshText("Pull");
        refreshViewHolder.setRefreshingText("Pull to refresh");
        refreshViewHolder.setReleaseRefreshText("Pull to refresh");
        refreshViewHolder.setLoadingMoreText("Loading more !");

        bgaRefreshLayout.setRefreshViewHolder(refreshViewHolder);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        //setListener

        banner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                Glide.with(itemView.getContext())
                        .load(model)
                        .apply(new RequestOptions().placeholder(R.drawable.naber_default_image).error(R.drawable.naber_default_image).dontAnimate())
                        .into(itemView);
            }
        });

        banner.setDelegate(new BGABanner.Delegate<ImageView, String>() {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position) {
                Log.d(TAG, model + position);
            }
        });

        recyclerView.setAdapter(adapter);
        adapter.setItemOnClickListener(new ItemOnClickListener());

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
                ApiManager.test(new ApiCallback(getContext()) {
                    @Override
                    public void onSuccess(String responseBody) {
                        for (int i = 0; i < 30; i++) {
                            list.add("" + i);
                        }
                        adapter.notifyDataSetChanged();
                        bgaRefreshLayout.endRefreshing();
                    }

                    @Override
                    public void onFail(Exception error, String msg) {
                        bgaRefreshLayout.endRefreshing();
                    }
                });
                return false;
            }
        });
    }


    private void loadBannerData(){
        images = Lists.<String>newArrayList(
                "https://www.mcdonalds.com/content/dam/usa/documents/mcdelivery/mcdelivery_new11.jpg",
                "http://a57.foxnews.com/media2.foxnews.com/2016/06/09/640/360/060916_chew_crispychicken_1280.jpg",
                "http://farm5.staticflickr.com/4532/38105655392_0b57dca007_c.jpg",
                "http://www.mrsteak.com.tw/photos/327_3854411.jpg",
                "https://ibw.bwnet.com.tw/image/pool/2017/05/c0ee05d61c65709a30621f46e2d22908.jpg"
                );
        banner.setData(images,Lists.<String>newArrayList());
    }

    private void doLoadData(boolean isRefresh) {
        if (isRefresh) {
            list.clear();
        }
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
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        UserMainActivity.changeTabAndToolbarStatus();
//        if ( RestaurantFragment.TO_RESTAURANT_DETAIL_INDEX >= 0) {
//            Bundle b = new Bundle();
//            b.putString("where", "RESTAURANT");
//            BaseCore.FRAGMENT_TAG = PageType.RESTAURANT_DETAIL.name();
//            AbsPageFragment f = PageFragmentFactory.of(PageType.RESTAURANT_DETAIL, b);
//            getFragmentManager().beginTransaction().replace(R.id.frameContainer, f).commit();
//        }else {
        if (list.size() == 0) {
            doLoadData(true);
        }
        loadBannerData();
//        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    class ItemOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Log.d(TAG, view.getTag() + "");
            Log.d(TAG, "item on :" + view.getTag());
            Bundle b = new Bundle();
            b.putString("where", "RESTAURANT");
            BaseCore.FRAGMENT_TAG = PageType.RESTAURANT_DETAIL.name();
            Fragment f = PageFragmentFactory.of(PageType.RESTAURANT_DETAIL, b);
            getFragmentManager().beginTransaction().replace(R.id.frameContainer, f).commit();

        }
    }
}
