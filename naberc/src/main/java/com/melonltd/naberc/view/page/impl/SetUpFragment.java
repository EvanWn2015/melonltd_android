package com.melonltd.naberc.view.page.impl;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melonltd.naberc.R;
import com.melonltd.naberc.view.page.abs.AbsPageFragment;
import com.melonltd.naberc.view.page.intf.PageFragment;


public class SetUpFragment extends AbsPageFragment {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private static SetUpFragment FRAGMENT = null;

    public SetUpFragment() {
    }

    @Override
    public PageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new SetUpFragment();
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }

    @Override
    public PageFragment newInstance(Object... o) {
        return new SetUpFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_set_up, container, false);
    }


}
