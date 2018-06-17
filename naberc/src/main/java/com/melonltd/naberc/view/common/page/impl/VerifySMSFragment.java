package com.melonltd.naberc.view.common.page.impl;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.melonltd.naberc.R;
import com.melonltd.naberc.model.helper.okhttp.ApiCallback;
import com.melonltd.naberc.model.helper.okhttp.ApiManager;
import com.melonltd.naberc.view.common.BaseActivity;
import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;
import com.melonltd.naberc.view.common.factory.PageFragmentFactory;
import com.melonltd.naberc.view.common.type.PageType;


public class VerifySMSFragment extends AbsPageFragment implements View.OnClickListener {
    private static final String TAG = VerifySMSFragment.class.getSimpleName();
    public static VerifySMSFragment FRAGMENT = null;
    private EditText phoneNamberEdit, verifySMSEdit;

    public VerifySMSFragment() {
    }

    @Override
    public VerifySMSFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new VerifySMSFragment();
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }

    @Override
    public VerifySMSFragment newInstance(Object... o) {
        return new VerifySMSFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.user_verify_sms_page) == null) {
            View v = inflater.inflate(R.layout.fragment_verify_sms, container, false);
            getView(v);
            container.setTag(R.id.user_verify_sms_page, v);
        }
        return (View) container.getTag(R.id.user_verify_sms_page);
    }

    @Override
    public void onResume() {
        super.onResume();
        BaseActivity.changeToolbarStatus();
        if (BaseActivity.toolbar != null) {
            BaseActivity.navigationIconDisplay(true, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    backToLoginPage();
                    BaseActivity.navigationIconDisplay(false, null);
                }
            });
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        BaseActivity.navigationIconDisplay(false, null);
    }

    private void getView(View v) {
        Button requestVerifyCodeBtn = v.findViewById(R.id.requestVerifyCodeBtn);
        Button submitToRegisteredBun = v.findViewById(R.id.submitToRegisteredBun);
        TextView privacyPolicyText = v.findViewById(R.id.privacyPolicyText);
        phoneNamberEdit = v.findViewById(R.id.phoneNamberEdit);
        verifySMSEdit = v.findViewById(R.id.verifySMSEdit);

        // setListener
        requestVerifyCodeBtn.setOnClickListener(this);
        submitToRegisteredBun.setOnClickListener(this);
        privacyPolicyText.setOnClickListener(this);
    }


    private void backToLoginPage() {
        BaseCore.FRAGMENT_TAG = PageType.LOGIN.name();
        AbsPageFragment f = PageFragmentFactory.of(PageType.LOGIN, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.baseContainer, f).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.requestVerifyCodeBtn:
                ApiManager.test(new ApiCallback(getActivity()) {
                    @Override
                    public void onSuccess(String responseBody) {

                    }

                    @Override
                    public void onFail(Exception error) {

                    }
                });
                break;
            case R.id.privacyPolicyText:
                View view = getLayoutInflater().inflate(R.layout.privacy_policy_content, null);
                new AlertView.Builder()
                        .setContext(getContext())
                        .setStyle(AlertView.Style.Alert)
                        .setTitle("NABER 隱私權政策")
                        .setCancelText("關閉")
                        .build()
                        .addExtView(view)
                        .setCancelable(true)
                        .setOnDismissListener(new OnDismissListener() {
                            @Override
                            public void onDismiss(Object o) {
                                Log.d(TAG, "onDismiss");
                            }
                        })
                        .show();
                break;
            case R.id.submitToRegisteredBun:
                ApiManager.test(new ApiCallback(getActivity()) {
                    @Override
                    public void onSuccess(String responseBody) {
                        toRegisteredPage();
                    }

                    @Override
                    public void onFail(Exception error) {

                    }
                });
                break;
        }
    }

    private void toRegisteredPage() {
        BaseCore.FRAGMENT_TAG = PageType.REGISTERED_USER.name();
        getFragmentManager().beginTransaction().remove(this).replace(R.id.baseContainer, PageFragmentFactory.of(PageType.REGISTERED_USER, null)).commit();
    }
}
