package com.melonltd.naberc.view.user.page.impl;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.google.common.collect.Lists;
import com.melonltd.naberc.R;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;
import com.melonltd.naberc.view.customize.OnLoadLayout;

import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class ShoppingCartFragment extends AbsPageFragment {
    private static final String TAG = ShoppingCartFragment.class.getSimpleName();
    private static ShoppingCartFragment FRAGMENT = null;
    private OnLoadLayout onLoadLayout;
    private ListView listView;
    private ShoppingCartAdapter adapter;
    private List<String> list = Lists.newArrayList();

    public ShoppingCartFragment() {
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new ShoppingCartFragment();
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new ShoppingCartFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ShoppingCartAdapter(getContext(), list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.user_shopping_cart_page) == null) {
            View v = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
            getViews(v);
            setListener();
            testLoadData();
            container.setTag(R.id.user_shopping_cart_page, v);
            return v;
        }
        return (View) container.getTag(R.id.user_shopping_cart_page);
    }


    // TODO test
    private void testLoadData() {
        for (int i = 0; i < 10; i++) {
            list.add("" + i);
        }
        adapter.notifyDataSetChanged();
    }

    private void getViews(View v) {
        onLoadLayout = v.findViewById(R.id.shoppingCartOnLoadLayout);
        listView = v.findViewById(R.id.shoppingCartListView);
        listView.setAdapter(adapter);
    }

    private void setListener() {
        onLoadLayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                onLoadLayout.refreshComplete();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return ((OnLoadLayout) frame).isTop();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    class ShoppingCartAdapter extends BaseAdapter {
        private LayoutInflater inflater = null;
        private List<String> list;

        ShoppingCartAdapter(Context context, List<String> list) {
            this.inflater = LayoutInflater.from(context);
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Nullable
        @Override
        public CharSequence[] getAutofillOptions() {
            return new CharSequence[0];
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            //layout/user_shopping_detail_item
            view = inflater.inflate(R.layout.user_shopping_detail_item, null);
            return view;
        }
    }
}
