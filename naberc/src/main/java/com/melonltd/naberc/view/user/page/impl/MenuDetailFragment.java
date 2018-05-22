package com.melonltd.naberc.view.user.page.impl;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melonltd.naberc.R;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;


public class MenuDetailFragment extends AbsPageFragment {
    private static final String TAG = MenuDetailFragment.class.getSimpleName();
    private static MenuDetailFragment FRAGMENT = null;

    public MenuDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new MenuDetailFragment();
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new MenuDetailFragment();
        }
        FRAGMENT.setArguments(null);
        FRAGMENT.setArguments(bundle);
        return FRAGMENT;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu_detail, container, false);
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
}
