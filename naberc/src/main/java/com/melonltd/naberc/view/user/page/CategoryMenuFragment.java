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
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.common.collect.Lists;
import com.melonltd.naberc.R;
import com.melonltd.naberc.model.api.ThreadCallback;
import com.melonltd.naberc.model.api.ApiManager;
import com.melonltd.naberc.model.constant.NaberConstant;
import com.melonltd.naberc.util.Tools;
import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.factory.PageFragmentFactory;
import com.melonltd.naberc.view.factory.PageType;
import com.melonltd.naberc.view.user.UserMainActivity;
import com.melonltd.naberc.view.user.adapter.MenuAdapter;
import com.melonltd.naberc.vo.CategoryFoodRelVo;
import com.melonltd.naberc.vo.RestaurantCategoryRelVo;
import com.melonltd.naberc.vo.RestaurantInfoVo;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class CategoryMenuFragment extends Fragment {
    private static final String TAG = CategoryMenuFragment.class.getSimpleName();
    public static CategoryMenuFragment FRAGMENT = null;

//    private TextView categoryNameText;
    private MenuAdapter adapter;
    private ViewHolder holder;

    private List<CategoryFoodRelVo> list = Lists.<CategoryFoodRelVo>newArrayList();
    public static int TO_MENU_DETAIL_INDEX = -1;


    public CategoryMenuFragment() {
    }


    public Fragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new CategoryMenuFragment();
            TO_MENU_DETAIL_INDEX = -1;
        }
        if (bundle != null){
            FRAGMENT.setArguments(bundle);
        }
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
        serHeaderValue(getArguments());
        return (View) container.getTag(R.id.user_category_menu_page);
    }


    private void serHeaderValue(Bundle bundle) {
        RestaurantCategoryRelVo vo = (RestaurantCategoryRelVo) bundle.getSerializable(NaberConstant.RESTAURANT_CATEGORY_REL);
        holder.uuid = vo.category_uuid;
        holder.categoryNameText.setText(vo.category_name);
    }

    private void getViews(View v) {
        holder = new ViewHolder(v);
        Bundle bundle = getArguments();
        serHeaderValue(bundle);

        final BGARefreshLayout bgaRefreshLayout = v.findViewById(R.id.menuBGARefreshLayout);
        RecyclerView recyclerView = v.findViewById(R.id.menuRecyclerView);

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

            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                return false;
            }
        });
    }

    private void doLoadData(boolean isRefresh) {
        if (isRefresh) {
            list.clear();
        }
        ApiManager.restaurantFoodList(holder.uuid ,new ThreadCallback(getContext()) {
            @Override
            public void onSuccess(String responseBody) {
                List<CategoryFoodRelVo> vos = Tools.JSONPARSE.fromJsonList(responseBody , CategoryFoodRelVo[].class);
                list.addAll(vos);
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
        if (TO_MENU_DETAIL_INDEX == -1){
            doLoadData(true);
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
            toMenuDetailPage(list.get(TO_MENU_DETAIL_INDEX));
        }
    }

    private void toMenuDetailPage(CategoryFoodRelVo vo) {
        TO_MENU_DETAIL_INDEX = list.indexOf(vo);
        BaseCore.FRAGMENT_TAG = PageType.MENU_DETAIL.name();
        Bundle bundle = new  Bundle();
        bundle.putSerializable("", vo);
        Fragment f = PageFragmentFactory.of(PageType.MENU_DETAIL, bundle);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.frameContainer, f).addToBackStack(f.toString()).commit();
    }

    private void backToRestaurantDetail() {
        BaseCore.FRAGMENT_TAG = PageType.RESTAURANT_DETAIL.name();
        RestaurantDetailFragment.TO_CATEGORY_MENU_INDEX = -1;
        Fragment f = PageFragmentFactory.of(PageType.RESTAURANT_DETAIL, null);
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


    class ItemClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            toMenuDetailPage((CategoryFoodRelVo)v.getTag());
        }
    }


    private class ViewHolder {
        private String uuid;
        private  TextView categoryNameText;
        ViewHolder(View v){
            this.categoryNameText = v.findViewById(R.id.categoryNameText);
        }

    }
}
