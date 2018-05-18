package com.melonltd.naberc.view.user.page.impl;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melonltd.naberc.R;
import com.melonltd.naberc.view.user.MainActivity;
import com.melonltd.naberc.view.user.page.abs.AbsPageFragment;
import com.melonltd.naberc.view.user.page.factory.PageFragmentFactory;
import com.melonltd.naberc.view.user.page.type.PageType;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountDetailFragment extends AbsPageFragment {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private static AccountDetailFragment FRAGMENT = null;


    public AccountDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new AccountDetailFragment();
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new AccountDetailFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.user_account_detail_page) == null) {
            View v = inflater.inflate(R.layout.fragment_account_detail, container, false);
            container.setTag(R.id.user_account_detail_page, v);
            return v;
        } else {
            return (View) container.getTag(R.id.user_account_detail_page);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MainActivity.toolbar != null) {
            MainActivity.navigationIconDisplay(true, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    backToSetUpPage();
                    MainActivity.navigationIconDisplay(false, null);
                }
            });
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        MainActivity.navigationIconDisplay(false, null);
    }

    private void backToSetUpPage() {
        SetUpFragment.TO_ACCOUNT_DETAIL_INDEX = -1;
        AbsPageFragment f = PageFragmentFactory.of(PageType.SET_UP, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.frameContainer, f).commit();
    }
}
