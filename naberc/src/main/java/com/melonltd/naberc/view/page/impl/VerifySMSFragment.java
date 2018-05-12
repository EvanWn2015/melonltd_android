package com.melonltd.naberc.view.page.impl;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.melonltd.naberc.R;
import com.melonltd.naberc.view.page.abs.AbsPageFragment;
import com.melonltd.naberc.view.page.intf.PageFragment;


public class VerifySMSFragment extends AbsPageFragment implements View.OnClickListener {
    private static final String TAG = VerifySMSFragment.class.getSimpleName();
    private static VerifySMSFragment FRAGMENT = null;
    private Button requestVerifyCodeBtn, submitToRegisteredBun;

    public VerifySMSFragment() {
    }

    @Override
    public PageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new VerifySMSFragment();
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }

    @Override
    public PageFragment newInstance(Object... o) {
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
    }

    private void setListener() {
        requestVerifyCodeBtn.setOnClickListener(this);
        submitToRegisteredBun.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.requestVerifyCodeBtn:
                break;
            case R.id.submitToRegisteredBun:
                
                break;
        }
    }
}
