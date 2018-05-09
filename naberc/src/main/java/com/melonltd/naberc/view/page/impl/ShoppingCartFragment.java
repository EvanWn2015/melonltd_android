package com.melonltd.naberc.view.page.impl;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melonltd.naberc.R;
import com.melonltd.naberc.view.page.abs.AbsPageFragment;
import com.melonltd.naberc.view.page.intf.PageFragment;

public class ShoppingCartFragment extends AbsPageFragment {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private static ShoppingCartFragment FRAGMENT = null;

    public ShoppingCartFragment() {
    }

    @Override
    public PageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null){
            FRAGMENT = new ShoppingCartFragment();
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }

    @Override
    public PageFragment newInstance(Object... o) {
        return new ShoppingCartFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shopping_cart, container, false);
    }


}
