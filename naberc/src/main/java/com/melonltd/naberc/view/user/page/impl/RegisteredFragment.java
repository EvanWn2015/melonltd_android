package com.melonltd.naberc.view.user.page.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.melonltd.naberc.R;
import com.melonltd.naberc.view.user.page.abs.AbsPageFragment;
import com.melonltd.naberc.view.user.page.factory.PageFragmentFactory;
import com.melonltd.naberc.view.user.page.type.PageType;

public class RegisteredFragment extends AbsPageFragment implements View.OnClickListener {
    private static final String TAG = RegisteredFragment.class.getSimpleName();
    private static RegisteredFragment FRAGMENT = null;


    private Button submitBtn, backToLoginBtn;

    public RegisteredFragment() {
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new RegisteredFragment();
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new RegisteredFragment();
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_registered, container, false);
        getViews(v);
        setListener();
        return v;
    }

    private void getViews(View v) {
        submitBtn = v.findViewById(R.id.submitBtn);
        backToLoginBtn = v.findViewById(R.id.backToLoginBtn);
    }

    private void setListener() {
        submitBtn.setOnClickListener(this);
        backToLoginBtn.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void backToLoginPage() {
        AbsPageFragment fragment = PageFragmentFactory.of(PageType.LOGIN, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.frameContainer, fragment).addToBackStack(fragment.toString()).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitBtn:
                backToLoginPage();
                break;
            case R.id.backToLoginBtn:
                backToLoginPage();
                break;
        }
    }
}

