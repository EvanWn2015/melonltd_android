package com.melonltd.naberc.view.page.impl;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.melonltd.naberc.R;
import com.melonltd.naberc.model.service.AuthService;
import com.melonltd.naberc.view.BaseCore;
import com.melonltd.naberc.view.page.abs.AbsPageFragment;
import com.melonltd.naberc.view.page.factory.PageFragmentFactory;
import com.melonltd.naberc.view.page.type.PageType;

public class LoginFragment extends AbsPageFragment implements View.OnClickListener {
    private static final String TAG = LoginFragment.class.getSimpleName();
    private static LoginFragment FRAGMENT = null;
    private EditText accountEdit, passwordEdit;
    private Button loginBtn, registeredBtn;
    private TextView recoverPasswordText;

    public LoginFragment() {
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new LoginFragment();
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new LoginFragment();
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
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        getView(v);
        setListener();
        return v;
    }

    private void getView(View v) {
        accountEdit = v.findViewById(R.id.accountEdit);
        passwordEdit = v.findViewById(R.id.passwordEdit);
        loginBtn = v.findViewById(R.id.loginBtn);
        registeredBtn = v.findViewById(R.id.registeredBtn);
        recoverPasswordText = v.findViewById(R.id.recoverPasswordText);
    }

    private void setListener() {
        loginBtn.setOnClickListener(this);
        registeredBtn.setOnClickListener(this);
        recoverPasswordText.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                String mail = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                AuthService.signInWithEmailAndPassword(mail, password, getFragmentManager(), null);
                break;
            case R.id.registeredBtn:
                BaseCore.FRAGMENT_TAG = PageType.REGISTERED.name();
//                fragment = PageFragmentFactory.of(PageType.SET_UP, null);
                getFragmentManager().beginTransaction().remove(this).replace(R.id.frameContainer, PageFragmentFactory.of(PageType.REGISTERED, null)).commit();
//                getFragmentManager().beginTransaction().remove(this).replace(R.id.frameContainer, PageFragmentFactory.of(PageType.SET_UP, null)).commit();
                break;
            case R.id.recoverPasswordText:
                Log.d(TAG, "找回密碼");
                break;
        }
    }

}
