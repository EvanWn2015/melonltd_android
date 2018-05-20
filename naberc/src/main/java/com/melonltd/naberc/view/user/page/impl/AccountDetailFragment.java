package com.melonltd.naberc.view.user.page.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.melonltd.naberc.R;
import com.melonltd.naberc.model.helper.okhttp.ApiCallback;
import com.melonltd.naberc.model.helper.okhttp.ApiManager;
import com.melonltd.naberc.view.user.UserMainActivity;
import com.melonltd.naberc.view.user.page.abs.AbsPageFragment;
import com.melonltd.naberc.view.user.page.factory.PageFragmentFactory;
import com.melonltd.naberc.view.user.page.type.PageType;

public class AccountDetailFragment extends AbsPageFragment implements View.OnClickListener {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private static AccountDetailFragment FRAGMENT = null;
    private Button logoutBtn, toResetPasswordBtn;
    public static int TO_RESET_PASSWORD_INDEX = -1;

    public AccountDetailFragment() {
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
            getView(v);
            setListener();
            container.setTag(R.id.user_account_detail_page, v);
            return v;
        } else {
            return (View) container.getTag(R.id.user_account_detail_page);
        }
    }

    private void getView(View v) {
        logoutBtn = v.findViewById(R.id.logoutBtn);
        toResetPasswordBtn = v.findViewById(R.id.toResetPasswordBtn);
    }

    private void setListener() {
        logoutBtn.setOnClickListener(this);
        toResetPasswordBtn.setOnClickListener(this);
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
        if (TO_RESET_PASSWORD_INDEX >= 0) {
            toResetPassword(1);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        UserMainActivity.navigationIconDisplay(false, null);
    }

    private void backToSetUpPage() {
        UserMainActivity.FRAGMENT_TAG = PageType.SET_UP.name();
        SetUpFragment.TO_ACCOUNT_DETAIL_INDEX = -1;
        AbsPageFragment f = PageFragmentFactory.of(PageType.SET_UP, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.frameContainer, f).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logoutBtn:
                ApiManager.test(new ApiCallback(getContext()) {
                    @Override
                    public void onSuccess(String responseBody) {
                        toLoginPage();
                    }

                    @Override
                    public void onFail(Exception error) {

                    }
                });
                break;
            case R.id.toResetPasswordBtn:
                toResetPassword(1);
                break;

        }
    }


    private void toLoginPage() {
        UserMainActivity.FRAGMENT_TAG = PageType.LOGIN.name();
        AbsPageFragment f = PageFragmentFactory.of(PageType.LOGIN, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.frameContainer, f).commit();
    }

    private void toResetPassword(int i) {
        TO_RESET_PASSWORD_INDEX = i;
        UserMainActivity.FRAGMENT_TAG = PageType.RESET_PASSWORD.name();
        AbsPageFragment f = PageFragmentFactory.of(PageType.RESET_PASSWORD, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.frameContainer, f).commit();
    }
}
