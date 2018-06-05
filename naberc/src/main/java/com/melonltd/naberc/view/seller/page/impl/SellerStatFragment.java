package com.melonltd.naberc.view.seller.page.impl;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.melonltd.naberc.R;
import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;
import com.melonltd.naberc.view.common.factory.PageFragmentFactory;
import com.melonltd.naberc.view.common.type.PageType;
import com.melonltd.naberc.view.seller.SellerMainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SellerStatFragment extends AbsPageFragment implements View.OnClickListener {
    private static final String TAG = SellerStatFragment.class.getSimpleName();
    public static SellerStatFragment FRAGMENT = null;
    public static int TO_SELLER_ORDERS_LOGS_INDEX = -1;

    private TextView finishOrderText;

    public SellerStatFragment() {
        // Required empty public constructor
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new SellerStatFragment();
            TO_SELLER_ORDERS_LOGS_INDEX = -1;
        }
        FRAGMENT.setArguments(bundle);
        return FRAGMENT;
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new SellerStatFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container.getTag(R.id.seller_stat_main_page) == null) {
            View v = inflater.inflate(R.layout.fragment_seller_stat, container, false);
            getViews(v);
            setListener();
            container.setTag(R.id.seller_stat_main_page, v);
        }
        return (View) container.getTag(R.id.seller_stat_main_page);
    }

    private void getViews(View v) {
        finishOrderText = v.findViewById(R.id.finishOrderText);
    }

    private void setListener() {
        finishOrderText.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        SellerMainActivity.toolbar.setNavigationIcon(null);
        if (TO_SELLER_ORDERS_LOGS_INDEX >= 0) {
            toOrdersLogsPage();
        }
    }

    private void toOrdersLogsPage() {
        TO_SELLER_ORDERS_LOGS_INDEX = 1;
        AbsPageFragment fragment = PageFragmentFactory.of(PageType.SELLER_ORDERS_LOGS, null);
        BaseCore.FRAGMENT_TAG = PageType.SELLER_ORDERS_LOGS.toString();
        getFragmentManager().beginTransaction().remove(this).replace(R.id.sellerFrameContainer, fragment).commit();
    }

    @Override
    public void onClick(View view) {
        toOrdersLogsPage();
    }
}
