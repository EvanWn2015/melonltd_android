package com.melonltd.naberc.view.seller.page.impl;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melonltd.naberc.R;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;

public class SellerSearchFragment extends AbsPageFragment {
    private static final String TAG = SellerSearchFragment.class.getSimpleName();
    private static SellerSearchFragment FRAGMENT = null;

    public SellerSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new SellerSearchFragment();
        }
        FRAGMENT.setArguments(bundle);
        return FRAGMENT;
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new SellerSearchFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_seller_search, container, false);
    }

}
