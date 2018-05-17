package com.melonltd.naberc.view.user.page.impl;


import android.media.tv.TvView;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.bigkoo.alertview.OnItemClickListener;
import com.melonltd.naberc.R;
import com.melonltd.naberc.view.user.MainActivity;
import com.melonltd.naberc.view.user.page.abs.AbsPageFragment;
import com.melonltd.naberc.view.user.page.factory.PageFragmentFactory;
import com.melonltd.naberc.view.user.page.intf.PageFragment;
import com.melonltd.naberc.view.user.page.type.PageType;

import static com.melonltd.naberc.view.user.BaseCore.FRAGMENT_TAG;


public class VerifySMSFragment extends AbsPageFragment implements View.OnClickListener {
    private static final String TAG = VerifySMSFragment.class.getSimpleName();
    private static VerifySMSFragment FRAGMENT = null;
    private Button requestVerifyCodeBtn, submitToRegisteredBun;
    private TextView privacyPolicyText;

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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_verify_sms, container, false);
        getView(v);
        setListener();
        return v;
    }

    private void getView(View v) {
        requestVerifyCodeBtn = v.findViewById(R.id.requestVerifyCodeBtn);
        submitToRegisteredBun = v.findViewById(R.id.submitToRegisteredBun);
        privacyPolicyText = v.findViewById(R.id.privacyPolicyText);
    }

    private void setListener() {
        requestVerifyCodeBtn.setOnClickListener(this);
        submitToRegisteredBun.setOnClickListener(this);
        privacyPolicyText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.requestVerifyCodeBtn:
                break;
            case R.id.privacyPolicyText:
                View view = getLayoutInflater().inflate( R.layout.privacy_policy_content, null );
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
                // TODO Api
//                MainActivity.bottomMenuTabLayout.setVisibility(View.VISIBLE);
                FRAGMENT_TAG = PageType.REGISTERED.name();
                getFragmentManager().beginTransaction().remove(this).replace(R.id.frameContainer, PageFragmentFactory.of(PageType.REGISTERED, null)).commit();
                break;
        }
    }
}
