package com.melonltd.naberc.view.seller.page.impl;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melonltd.naberc.R;
import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;
import com.melonltd.naberc.view.common.factory.PageFragmentFactory;
import com.melonltd.naberc.view.common.type.PageType;
import com.melonltd.naberc.view.seller.SellerMainActivity;

public class SellerSimpleInformationFragment extends AbsPageFragment {
    private static final String TAG = SellerSimpleInformationFragment.class.getSimpleName();
    public static SellerSimpleInformationFragment FRAGMENT = null;


    public SellerSimpleInformationFragment() {
        // Required empty public constructor
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new SellerSimpleInformationFragment();
        }
        FRAGMENT.setArguments(bundle);
        return FRAGMENT;
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new SellerSimpleInformationFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_seller_simple_information, container, false);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (SellerMainActivity.toolbar != null) {
            SellerMainActivity.navigationIconDisplay(true, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    backToSellerSetUpPage();
                    SellerMainActivity.navigationIconDisplay(false, null);
                }
            });
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        SellerMainActivity.navigationIconDisplay(false, null);
    }

    private void backToSellerSetUpPage() {
        BaseCore.FRAGMENT_TAG = PageType.SELLER_SET_UP.name();
        SellerSetUpFragment.TO_SELLER_SIMPLE_INFO_INDEX = -1;
        AbsPageFragment f = PageFragmentFactory.of(PageType.SELLER_SET_UP, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.sellerFrameContainer, f).commit();
    }
}
