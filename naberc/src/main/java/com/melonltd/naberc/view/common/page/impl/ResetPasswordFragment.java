package com.melonltd.naberc.view.common.page.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melonltd.naberc.R;
import com.melonltd.naberc.view.user.UserMainActivity;
import com.melonltd.naberc.view.user.page.abs.AbsPageFragment;
import com.melonltd.naberc.view.user.page.factory.PageFragmentFactory;
import com.melonltd.naberc.view.user.page.impl.AccountDetailFragment;
import com.melonltd.naberc.view.user.page.type.PageType;

public class ResetPasswordFragment extends AbsPageFragment {
    private static final String TAG = ResetPasswordFragment.class.getSimpleName();
    private static ResetPasswordFragment FRAGMENT = null;

    public ResetPasswordFragment() {
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new ResetPasswordFragment();
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new ResetPasswordFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_reset_password, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (UserMainActivity.toolbar != null) {
            UserMainActivity.navigationIconDisplay(true, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    backToAccountDetailPage();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void backToAccountDetailPage() {
        UserMainActivity.FRAGMENT_TAG = PageType.ACCOUNT_DETAIL.name();
        AccountDetailFragment.TO_RESET_PASSWORD_INDEX = -1;
        AbsPageFragment f = PageFragmentFactory.of(PageType.ACCOUNT_DETAIL, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.frameContainer, f).commit();
    }
}
