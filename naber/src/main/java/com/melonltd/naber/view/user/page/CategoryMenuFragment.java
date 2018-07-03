package com.melonltd.naber.view.user.page;


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
import com.melonltd.naber.R;
import com.melonltd.naber.model.api.ApiManager;
import com.melonltd.naber.model.api.ThreadCallback;
import com.melonltd.naber.model.bean.Model;
import com.melonltd.naber.model.constant.NaberConstant;
import com.melonltd.naber.util.Tools;
import com.melonltd.naber.view.factory.PageType;
import com.melonltd.naber.view.user.UserMainActivity;
import com.melonltd.naber.view.user.adapter.MenuAdapter;
import com.melonltd.naber.vo.CategoryFoodRelVo;
import com.melonltd.naber.vo.RestaurantCategoryRelVo;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class CategoryMenuFragment extends Fragment {
    private static final String TAG = CategoryMenuFragment.class.getSimpleName();
    public static CategoryMenuFragment FRAGMENT = null;
    private MenuAdapter adapter;
    private ViewHolder holder;
    public static int TO_MENU_DETAIL_INDEX = -1;

    public CategoryMenuFragment() {
    }

    public Fragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new CategoryMenuFragment();
            TO_MENU_DETAIL_INDEX = -1;
        }
        if (bundle != null) {
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new MenuAdapter(Model.CATEGORY_FOOD_REL_LIST);
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
        holder.categoryInfo = vo;
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
                doLoadData(true);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                return false;
            }
        });
    }

    private void doLoadData(boolean isRefresh) {
        if (isRefresh) {
            Model.CATEGORY_FOOD_REL_LIST.clear();
        }
        ApiManager.restaurantFoodList(holder.categoryInfo.category_uuid, new ThreadCallback(getContext()) {
            @Override
            public void onSuccess(String responseBody) {
                List<CategoryFoodRelVo> vos = Tools.JSONPARSE.fromJsonList(responseBody, CategoryFoodRelVo[].class);
                Model.CATEGORY_FOOD_REL_LIST.addAll(vos);
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
        if (TO_MENU_DETAIL_INDEX == -1) {
            doLoadData(true);
        }
        if (UserMainActivity.toolbar != null) {
            UserMainActivity.navigationIconDisplay(true, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RestaurantDetailFragment.TO_CATEGORY_MENU_INDEX = -1;
                    UserMainActivity.removeAndReplaceWhere(FRAGMENT, PageType.RESTAURANT_DETAIL, null);
                    UserMainActivity.navigationIconDisplay(false, null);
                }
            });
        }
        if (TO_MENU_DETAIL_INDEX >= 0) {
            Bundle bundle = new Bundle();
            Log.i(TAG,holder.categoryInfo.restaurant_uuid);

            bundle.putSerializable(NaberConstant.RESTAURANT_INFO, getArguments().getSerializable(NaberConstant.RESTAURANT_INFO));
//            bundle.putString(NaberConstant.RESTAURANT_UUID, holder.categoryInfo.restaurant_uuid);
            bundle.putSerializable(NaberConstant.FOOD_INFO, Model.CATEGORY_FOOD_REL_LIST.get(TO_MENU_DETAIL_INDEX));
            UserMainActivity.removeAndReplaceWhere(FRAGMENT, PageType.MENU_DETAIL, bundle);
        }
        UserMainActivity.toolbar.setTitle(holder.categoryInfo.category_name);
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
            TO_MENU_DETAIL_INDEX = (int) v.getTag();
            Bundle bundle = new Bundle();
            Log.i(TAG,holder.categoryInfo.restaurant_uuid);
            bundle.putSerializable(NaberConstant.RESTAURANT_INFO, getArguments().getSerializable(NaberConstant.RESTAURANT_INFO));
//            bundle.putString(NaberConstant.RESTAURANT_NAME, getArguments().getString(NaberConstant.RESTAURANT_NAME));
//            bundle.putString(NaberConstant.RESTAURANT_UUID, holder.categoryInfo.restaurant_uuid);
            bundle.putSerializable(NaberConstant.FOOD_INFO, Model.CATEGORY_FOOD_REL_LIST.get(TO_MENU_DETAIL_INDEX));
            UserMainActivity.removeAndReplaceWhere(FRAGMENT, PageType.MENU_DETAIL, bundle);
        }
    }


    private class ViewHolder {
        private RestaurantCategoryRelVo categoryInfo;
//        private String uuid;
        private TextView categoryNameText;

        ViewHolder(View v) {
            this.categoryNameText = v.findViewById(R.id.categoryNameText);
        }

    }
}
