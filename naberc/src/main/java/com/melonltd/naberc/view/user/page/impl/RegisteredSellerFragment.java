package com.melonltd.naberc.view.user.page.impl;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melonltd.naberc.R;
import com.melonltd.naberc.view.user.page.abs.AbsPageFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisteredSellerFragment extends AbsPageFragment implements View.OnClickListener {
    private static final String TAG = RegisteredSellerFragment.class.getSimpleName();
    private static RegisteredSellerFragment FRAGMENT = null;

    public RegisteredSellerFragment() {
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new RegisteredSellerFragment();
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new RegisteredSellerFragment();
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
        return inflater.inflate(R.layout.fragment_registered_seller, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {

    }

}
