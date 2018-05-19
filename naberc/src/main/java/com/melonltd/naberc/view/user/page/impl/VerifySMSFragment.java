package com.melonltd.naberc.view.user.page.impl;

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
import com.melonltd.naberc.model.helper.ApiCallback;
import com.melonltd.naberc.model.helper.ApiManager;
import com.melonltd.naberc.view.user.MainActivity;
import com.melonltd.naberc.view.user.page.abs.AbsPageFragment;
import com.melonltd.naberc.view.user.page.factory.PageFragmentFactory;
import com.melonltd.naberc.view.user.page.type.PageType;

import static com.melonltd.naberc.view.user.BaseCore.FRAGMENT_TAG;

public class VerifySMSFragment extends AbsPageFragment implements View.OnClickListener {
    private static final String TAG = VerifySMSFragment.class.getSimpleName();
    private static VerifySMSFragment FRAGMENT = null;
    private Button requestVerifyCodeBtn, submitToRegisteredBun;
    private TextView privacyPolicyText;
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
            setListener();
            container.setTag(R.id.user_verify_sms_page, v);
        }
        return (View) container.getTag(R.id.user_verify_sms_page);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MainActivity.toolbar != null) {
            MainActivity.navigationIconDisplay(true, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    backToLoginPage();
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

    private void getView(View v) {
        requestVerifyCodeBtn = v.findViewById(R.id.requestVerifyCodeBtn);
        submitToRegisteredBun = v.findViewById(R.id.submitToRegisteredBun);
        privacyPolicyText = v.findViewById(R.id.privacyPolicyText);
        phoneNamberEdit = v.findViewById(R.id.phoneNamberEdit);
        verifySMSEdit = v.findViewById(R.id.verifySMSEdit);
    }

    private void setListener() {
        requestVerifyCodeBtn.setOnClickListener(this);
        submitToRegisteredBun.setOnClickListener(this);
        privacyPolicyText.setOnClickListener(this);
    }

    private void backToLoginPage() {
        MainActivity.FRAGMENT_TAG = PageType.LOGIN.name();
        AbsPageFragment f = PageFragmentFactory.of(PageType.LOGIN, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.frameContainer, f).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.requestVerifyCodeBtn:
                ApiManager.test(new ApiCallback(getContext()) {
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
                ApiManager.test(new ApiCallback(getContext()) {
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
        FRAGMENT_TAG = PageType.REGISTERED.name();
        getFragmentManager().beginTransaction().remove(this).replace(R.id.frameContainer, PageFragmentFactory.of(PageType.REGISTERED, null)).commit();
    }
}
