package com.melonltd.naberc.view.seller.page;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.common.collect.Lists;
import com.melonltd.naberc.R;
import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.factory.PageFragmentFactory;
import com.melonltd.naberc.view.factory.PageType;
import com.melonltd.naberc.view.customize.SwitchButton;
import com.melonltd.naberc.view.seller.SellerMainActivity;
import com.melonltd.naberc.view.seller.adapter.MenuAdapter;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class SellerCategoryListFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = SellerCategoryListFragment.class.getSimpleName();
    public static SellerCategoryListFragment FRAGMENT = null;

    private TextView categoryNameText;
    private Button newMenuBtn;

    private BGARefreshLayout bgaRefreshLayout;
    private RecyclerView recyclerView;
    private MenuAdapter adapter;
    private List<String> listData = Lists.newArrayList();

    public static int TO_MENU_EDIT_PAGE_INDEX = -1;

    public SellerCategoryListFragment() {
    }

    public Fragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new SellerCategoryListFragment();
            TO_MENU_EDIT_PAGE_INDEX =-1;
        }
        FRAGMENT.setArguments(bundle);
        return FRAGMENT;
    }

    public Fragment newInstance(Object... o) {
        return new SellerCategoryListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new MenuAdapter(listData);
        Fresco.initialize(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.seller_restaurant_category_list_page) == null) {
            View v = inflater.inflate(R.layout.fragment_seller_category_list, container, false);
            getViews(v);
            setListener();
            container.setTag(R.id.seller_restaurant_category_list_page, v);
            return v;
        }
        return (View) container.getTag(R.id.seller_restaurant_category_list_page);
    }


    private void getViews(View v) {
        categoryNameText = v.findViewById(R.id.categoryNameText);
        newMenuBtn = v.findViewById(R.id.newMenuBtn);
        bgaRefreshLayout = v.findViewById(R.id.menuBGARefreshLayout);
        recyclerView = v.findViewById(R.id.menuRecyclerView);

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
        adapter.setListener(new SwitchListener(), new DeleteListener(), new EditListener(), new CopyLongListener());
        recyclerView.setAdapter(adapter);
        newMenuBtn.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        SellerMainActivity.changeTabAndToolbarStatus();
        for (int i = 0; i < 10; i++) {
            listData.add("menu :: " + i);
        }
        adapter.notifyDataSetChanged();

        if (TO_MENU_EDIT_PAGE_INDEX >= 0) {
            toMenuEditPage(TO_MENU_EDIT_PAGE_INDEX);
        }

        if (SellerMainActivity.toolbar != null) {
            SellerMainActivity.navigationIconDisplay(true, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    backToSellerRestaurantPage();
                    SellerMainActivity.navigationIconDisplay(false, null);
                }
            });
        }
    }

    private void backToSellerRestaurantPage() {
        BaseCore.FRAGMENT_TAG = PageType.SELLER_RESTAURANT.name();
        SellerRestaurantFragment.TO_CATEGORY_LIST_PAGE_INDEX = -1;
        Fragment f = PageFragmentFactory.of(PageType.SELLER_RESTAURANT, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.sellerFrameContainer, f).commit();
    }

    private void toMenuEditPage(int index) {
        BaseCore.FRAGMENT_TAG = PageType.SELLER_MENU_EDIT.name();
        TO_MENU_EDIT_PAGE_INDEX = index;
        Fragment f = PageFragmentFactory.of(PageType.SELLER_MENU_EDIT, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.sellerFrameContainer, f).commit();
    }

    @Override
    public void onStop() {
        super.onStop();
        SellerMainActivity.navigationIconDisplay(false, null);
    }

    @Override
    public void onClick(View view) {
        toMenuEditPage(0);
    }

    class SwitchListener implements SwitchButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(SwitchButton view, boolean isChecked) {
            Log.d(TAG, isChecked + "");
            Log.d(TAG, view.getTag() + "");
        }
    }

    class DeleteListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Log.d(TAG, view.getTag() + "");
            final int index = listData.indexOf(view.getTag());
            new AlertView.Builder()
                    .setTitle("")
                    .setMessage("刪除後將無法找回，\n您確定要刪除嗎？")
                    .setContext(getContext())
                    .setStyle(AlertView.Style.Alert)
                    .setOthers(new String[]{"確定刪除", "取消"})
                    .setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            if (position == 0) {
                                listData.remove(index);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    })
                    .build()
                    .setCancelable(true)
                    .show();
        }
    }

    class EditListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Log.d(TAG, view.getTag() + "");
            int index = listData.indexOf(view.getTag());
            toMenuEditPage(index);
        }
    }


    class CopyLongListener implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View view) {
            Log.d(TAG, view.getTag() + "");
            final int index = listData.indexOf(view.getTag());
            new AlertView.Builder()
                    .setTitle("複製 : " + view.getTag())
                    .setContext(getContext())
                    .setStyle(AlertView.Style.Alert)
                    .setOthers(new String[]{"確定複製", "取消"})
                    .setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            if (position == 0) {
                                toMenuEditPage(index);
                            }
                        }
                    })
                    .build()
                    .setCancelable(true)
                    .show();
            return false;
        }
    }

}
