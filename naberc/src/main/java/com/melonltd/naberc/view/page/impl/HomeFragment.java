package com.melonltd.naberc.view.page.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melonltd.naberc.R;
import com.melonltd.naberc.view.BaseActivity;
import com.melonltd.naberc.view.page.abs.AbsPageFragment;

/**
 * getArguments(); get this Bundle
 */
public class HomeFragment extends AbsPageFragment {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private static HomeFragment FRAGMENT = null;

    public HomeFragment() {
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new HomeFragment();
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new HomeFragment();
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, BaseActivity.currentUser.getUid() + "");
        Log.d(TAG, BaseActivity.currentUser.getEmail() + "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

}
