package com.melonltd.naberc.view.seller.page.impl;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.melonltd.naberc.R;
import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;
import com.melonltd.naberc.view.common.factory.PageFragmentFactory;
import com.melonltd.naberc.view.common.type.PageType;
import com.melonltd.naberc.view.seller.SellerMainActivity;

public class SellerMenuEditFragment extends AbsPageFragment {
    private static final String TAG = SellerMenuEditFragment.class.getSimpleName();
    private static SellerMenuEditFragment FRAGMENT = null;
    private Button newDemandBtn;
    private LinearLayout editLayout;
    private ImageButton scopeAddBtn, optAddBtn;
    private LinearLayout scopeLayout, optLayout;

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
        if (container.getTag(R.id.seller_menu_edit_page) == null) {
            View v = inflater.inflate(R.layout.fragment_seller_menu_edit, container, false);
            getViews(v);
            setListener();
            container.setTag(R.id.seller_menu_edit_page, v);
            return v;
        }
        return (View) container.getTag(R.id.seller_menu_edit_page);
    }

    private void getViews(View v) {
        newDemandBtn = v.findViewById(R.id.newDemandBtn);
        editLayout = v.findViewById(R.id.editLayout);

        scopeAddBtn = v.findViewById(R.id.scopeAddBtn);
        scopeAddBtn.setVisibility(View.VISIBLE);
        scopeLayout = v.findViewById(R.id.scopeLayout);
        optAddBtn = v.findViewById(R.id.optAddBtn);
        optAddBtn.setVisibility(View.VISIBLE);
        optLayout = v.findViewById(R.id.optLayout);
    }

    private void setListener() {
        newDemandBtn.setOnClickListener(new NewDemandListener());
        scopeAddBtn.setOnClickListener(new ScopeAddListener());
        optAddBtn.setOnClickListener(new OptAddListener());
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

    class NewDemandListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            View vi = LayoutInflater.from(getContext()).inflate(R.layout.menu_detail_demand, null);
            editLayout.addView(vi);
        }
    }

    class ScopeAddListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            View vi = LayoutInflater.from(getContext()).inflate(R.layout.seller_edit_menu_detail, null);
            scopeLayout.addView(vi);
        }
    }

    class OptAddListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            View vi = LayoutInflater.from(getContext()).inflate(R.layout.seller_edit_menu_detail, null);
            optLayout.addView(vi);
        }
    }

}
