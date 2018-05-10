package com.melonltd.naberc.view.page.impl;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.common.base.Strings;
import com.melonltd.naberc.R;
import com.melonltd.naberc.model.service.AuthService;
import com.melonltd.naberc.util.VerifyUtil;
import com.melonltd.naberc.view.page.abs.AbsPageFragment;

public class LoginFragment extends AbsPageFragment implements View.OnClickListener {
    private static final String TAG = LoginFragment.class.getSimpleName();
    private static LoginFragment FRAGMENT = null;

    private Button loginBtn;
    private EditText mailEdit, passwordEdit;

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
        mailEdit = v.findViewById(R.id.emailEdit);
        passwordEdit = v.findViewById(R.id.passwordEdit);
        loginBtn = v.findViewById(R.id.loginBtn);
    }

    private void setListener() {
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.loginBtn) {

            String mail = mailEdit.getText().toString();
            String password = passwordEdit.getText().toString();
            AuthService.signInWithEmailAndPassword(mail, password, getFragmentManager(),null);
        }
    }

}
