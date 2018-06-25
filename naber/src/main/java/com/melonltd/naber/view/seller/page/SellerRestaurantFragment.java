package com.melonltd.naber.view.seller.page;


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
import android.widget.EditText;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.melonltd.naber.R;
import com.melonltd.naber.view.common.BaseCore;
import com.melonltd.naber.view.customize.SwitchButton;
import com.melonltd.naber.view.factory.PageFragmentFactory;
import com.melonltd.naber.view.factory.PageType;
import com.melonltd.naber.view.seller.SellerMainActivity;
import com.melonltd.naber.view.seller.adapter.CategoryAdapter;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class SellerRestaurantFragment extends Fragment {
    private static final String TAG = SellerRestaurantFragment.class.getSimpleName();
    public static SellerRestaurantFragment FRAGMENT = null;

    private EditText categoryEdit;
    private Button newCategoryBtn;
    private List<String> listData = Lists.newArrayList();
    private CategoryAdapter adapter;
    private BGARefreshLayout bagRefreshLayout;
    private RecyclerView recyclerView;
    public static int TO_CATEGORY_LIST_PAGE_INDEX = -1;

    public SellerRestaurantFragment() {
    }

    public Fragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new SellerRestaurantFragment();
            TO_CATEGORY_LIST_PAGE_INDEX = -1;
        }
        FRAGMENT.setArguments(bundle);
        return FRAGMENT;
    }

    public Fragment newInstance(Object... o) {
        return new SellerRestaurantFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new CategoryAdapter(listData);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.seller_restaurant_main_page) == null) {
            View v = inflater.inflate(R.layout.fragment_seller_restaurant, container, false);
            getViews(v);
            setListener();
            container.setTag(R.id.seller_restaurant_main_page, v);
            return v;
        }
        return (View) container.getTag(R.id.seller_restaurant_main_page);
    }


    private void getViews(View v) {
        categoryEdit = v.findViewById(R.id.categoryEdit);
        newCategoryBtn = v.findViewById(R.id.newCategoryBtn);
        bagRefreshLayout = v.findViewById(R.id.categoryBGARefreshLayout);
        recyclerView = v.findViewById(R.id.categoryRecyclerView);

        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(getContext(), true);
        refreshViewHolder.setPullDownRefreshText("Pull");
        refreshViewHolder.setRefreshingText("Pull to refresh");
        refreshViewHolder.setReleaseRefreshText("Pull to refresh");
        refreshViewHolder.setLoadingMoreText("Loading more !");
        bagRefreshLayout.setRefreshViewHolder(refreshViewHolder);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setListener() {
        recyclerView.setAdapter(adapter);
        adapter.setListener(new SwitchListener(), new EditListener(), new DeleteListener());
        newCategoryBtn.setOnClickListener(new NewCategory());
    }

    @Override
    public void onResume() {
        super.onResume();
        SellerMainActivity.changeTabAndToolbarStatus();
//        SellerMainActivity.toolbar.setNavigationIcon(null);
        SellerMainActivity.lockDrawer(true);
        for (int i = 0; i < 10; i++) {
            listData.add(i + "XX 系列");
        }
        adapter.notifyDataSetChanged();
        if (TO_CATEGORY_LIST_PAGE_INDEX >= 0) {
            toCategoryListPage(TO_CATEGORY_LIST_PAGE_INDEX);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    private void toCategoryListPage(int index) {
        TO_CATEGORY_LIST_PAGE_INDEX = index;
        BaseCore.FRAGMENT_TAG = PageType.SELLER_CATEGORY_LIST.name();
        Fragment f = PageFragmentFactory.of(PageType.SELLER_CATEGORY_LIST, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.sellerFrameContainer, f).commit();
    }

    class SwitchListener implements SwitchButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(SwitchButton view, boolean isChecked) {
            Log.d(TAG, isChecked + "");
            Log.d(TAG, view.getTag() + "");
        }
    }

    class EditListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int index = listData.indexOf(view.getTag());
            toCategoryListPage(index);
        }
    }

    class DeleteListener implements View.OnClickListener {
        @Override
        public void onClick(final View view) {
            new AlertView.Builder()
                    .setTitle("")
                    .setMessage("請注意刪除種類\n，將會影響種類下的產品!")
                    .setContext(getContext())
                    .setStyle(AlertView.Style.Alert)
                    .setOthers(new String[]{"確定刪除", "取消"})
                    .setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            if (position == 0) {
                                listData.remove(view.getTag());
                                adapter.notifyDataSetChanged();
                            }
                        }
                    })
                    .build()
                    .setCancelable(true)
                    .show();
        }
    }


    class NewCategory implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (Strings.isNullOrEmpty(categoryEdit.getText().toString())) {
                new AlertView.Builder()
                        .setTitle("")
                        .setMessage("請輸入種類名稱")
                        .setContext(getContext())
                        .setStyle(AlertView.Style.Alert)
                        .setOthers(new String[]{"取消"})
                        .build()
                        .setCancelable(true)
                        .show();
            } else {
                listData.add(0, categoryEdit.getText().toString());
                adapter.notifyDataSetChanged();
                categoryEdit.setText("");
            }
        }
    }

}
