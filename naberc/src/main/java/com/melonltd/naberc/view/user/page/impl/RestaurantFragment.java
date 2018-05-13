package com.melonltd.naberc.view.user.page.impl;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melonltd.naberc.R;
import com.melonltd.naberc.view.user.page.abs.AbsPageFragment;

public class RestaurantFragment extends AbsPageFragment {
    private static final String TAG = RestaurantFragment.class.getSimpleName();
    private static RestaurantFragment FRAGMENT = null;

    public RestaurantFragment() {
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new RestaurantFragment();
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new RestaurantFragment();
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restaurant, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

}


