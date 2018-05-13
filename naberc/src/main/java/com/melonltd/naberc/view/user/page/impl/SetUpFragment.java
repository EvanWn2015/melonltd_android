package com.melonltd.naberc.view.user.page.impl;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melonltd.naberc.R;
import com.melonltd.naberc.view.user.BaseCore;
import com.melonltd.naberc.view.user.page.abs.AbsPageFragment;
import com.melonltd.naberc.view.user.page.factory.PageFragmentFactory;
import com.melonltd.naberc.view.user.page.intf.PageFragment;
import com.melonltd.naberc.view.user.page.type.PageType;


public class SetUpFragment extends AbsPageFragment {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private static SetUpFragment FRAGMENT = null;

    public SetUpFragment() {
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new SetUpFragment();
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new SetUpFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (BaseCore.IS_HAS_ACC){
            getFragmentManager().beginTransaction().remove(this).replace(R.id.frameContainer, PageFragmentFactory.of(PageType.REGISTERED, null)).commit();
        }
        return inflater.inflate(R.layout.fragment_set_up, container, false);
    }


}
