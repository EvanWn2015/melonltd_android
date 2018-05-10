package com.melonltd.naberc.view.page.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melonltd.naberc.R;
import com.melonltd.naberc.view.BaseCore;
import com.melonltd.naberc.view.page.abs.AbsPageFragment;

public class RegisteredFragment extends AbsPageFragment {
    private static final String TAG = RegisteredFragment.class.getSimpleName();
    private static RegisteredFragment FRAGMENT = null;

    public RegisteredFragment() {
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new RegisteredFragment();
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new RegisteredFragment();
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
        return inflater.inflate(R.layout.fragment_registered, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

}

