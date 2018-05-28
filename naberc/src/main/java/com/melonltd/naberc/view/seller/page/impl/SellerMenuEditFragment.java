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

public class SellerMenuEditFragment extends AbsPageFragment {
    private static final String TAG = SellerMenuEditFragment.class.getSimpleName();
    private static SellerMenuEditFragment FRAGMENT = null;

    public SellerMenuEditFragment() {
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new SellerMenuEditFragment();
        }
        FRAGMENT.setArguments(bundle);
        return FRAGMENT;
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new SellerMenuEditFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_seller_menu_edit, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (SellerMainActivity.toolbar != null) {
            SellerMainActivity.navigationIconDisplay(true, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    backToSellerCategoryListPage();
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

    private void backToSellerCategoryListPage() {
        SellerCategoryListFragment.TO_MENU_EDIT_PAGE_INDEX = -1;
        BaseCore.FRAGMENT_TAG = PageType.SELLER_CATEGORY_LIST.name();
        AbsPageFragment f = PageFragmentFactory.of(PageType.SELLER_CATEGORY_LIST, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.sellerFrameContainer, f).commit();
    }

}
