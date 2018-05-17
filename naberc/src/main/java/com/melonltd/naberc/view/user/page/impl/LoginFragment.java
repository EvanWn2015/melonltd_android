package com.melonltd.naberc.view.user.page.impl;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.melonltd.naberc.R;
import com.melonltd.naberc.model.service.AuthService;
import com.melonltd.naberc.view.user.page.abs.AbsPageFragment;
import com.melonltd.naberc.view.user.page.factory.PageFragmentFactory;
import com.melonltd.naberc.view.user.page.type.PageType;

import static com.melonltd.naberc.view.user.BaseCore.FRAGMENT_TAG;

public class LoginFragment extends AbsPageFragment implements View.OnClickListener, OnDateSetListener {
    private static final String TAG = LoginFragment.class.getSimpleName();
    private static LoginFragment FRAGMENT = null;
    private EditText accountEdit, passwordEdit;
    private Button loginBtn, toVerifySMSBtn, toRegisteredSellerBtn;
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
        toVerifySMSBtn = v.findViewById(R.id.toVerifySMSBtn);
        toRegisteredSellerBtn = v.findViewById(R.id.toRegisteredSellerBtn);
        recoverPasswordText = v.findViewById(R.id.recoverPasswordText);
    }

    private void setListener() {
        loginBtn.setOnClickListener(this);
        toVerifySMSBtn.setOnClickListener(this);
        toRegisteredSellerBtn.setOnClickListener(this);
        recoverPasswordText.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onClick(View v) {
        AbsPageFragment fragment = null;
        switch (v.getId()) {
            case R.id.loginBtn:
                String mail = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                AuthService.signInWithEmailAndPassword(mail, password, getFragmentManager(), null);
                break;
            case R.id.toVerifySMSBtn:
//                MainActivity.bottomMenuTabLayout.setVisibility(View.VISIBLE);
                FRAGMENT_TAG = PageType.VERIFY_SMS.name();
                fragment = PageFragmentFactory.of(PageType.VERIFY_SMS, null);
                getFragmentManager().beginTransaction().remove(this).replace(R.id.frameContainer, fragment).addToBackStack(fragment.toString()).commit();
                break;
            case R.id.toRegisteredSellerBtn:
                FRAGMENT_TAG = PageType.VERIFY_SMS.name();
                fragment = PageFragmentFactory.of(PageType.REGISTERED_SELLER, null);
                getFragmentManager().beginTransaction().remove(this).replace(R.id.frameContainer, fragment).addToBackStack(fragment.toString()).commit();
                break;
            case R.id.recoverPasswordText:

                Log.d(TAG, "找回密碼");
                break;
        }
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        Log.d(TAG, "TimePickerDialog  onDateSet" );
    }
}
