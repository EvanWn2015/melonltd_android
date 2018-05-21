package com.melonltd.naberc.view.user.page.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melonltd.naberc.R;
import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.user.UserMainActivity;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;
import com.melonltd.naberc.view.common.factory.PageFragmentFactory;
import com.melonltd.naberc.view.common.type.PageType;

public class SimpleInformationFragment extends AbsPageFragment {
    private static final String TAG = SimpleInformationFragment.class.getSimpleName();
    private static SimpleInformationFragment FRAGMENT = null;

    public SimpleInformationFragment() {
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new SimpleInformationFragment();
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new ShoppingCartFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.user_simple_information_page) == null) {
            View v = inflater.inflate(R.layout.fragment_simple_information, container, false);
            container.setTag(R.id.user_simple_information_page, v);
            return v;
        }
        return (View) container.getTag(R.id.user_simple_information_page);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (UserMainActivity.toolbar != null) {
            UserMainActivity.navigationIconDisplay(true, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    backToSetUpPage();
                    UserMainActivity.navigationIconDisplay(false, null);
                }
            });
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        UserMainActivity.navigationIconDisplay(false, null);
    }

    private void backToSetUpPage() {
        BaseCore.FRAGMENT_TAG = PageType.SET_UP.name();
        SetUpFragment.TO_SIMPLE_INFO_INDEX = -1;
        AbsPageFragment f = PageFragmentFactory.of(PageType.SET_UP, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.frameContainer, f).commit();
    }
}
