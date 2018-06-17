package com.melonltd.naberc.view.common.page.impl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.google.common.base.Strings;
import com.google.firebase.iid.FirebaseInstanceId;
import com.melonltd.naberc.R;
import com.melonltd.naberc.util.VerifyUtil;
import com.melonltd.naberc.view.common.BaseActivity;
import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;
import com.melonltd.naberc.view.common.factory.PageFragmentFactory;
import com.melonltd.naberc.view.common.type.PageType;
import com.melonltd.naberc.view.intro.IntroActivity;
import com.melonltd.naberc.view.seller.SellerMainActivity;
import com.melonltd.naberc.view.user.UserMainActivity;

public class LoginFragment extends AbsPageFragment implements View.OnClickListener {
    private static final String TAG = LoginFragment.class.getSimpleName();
    public static LoginFragment FRAGMENT = null;
    private EditText accountEdit, passwordEdit;

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
        return v;
    }

    private void getView(View v) {
        accountEdit = v.findViewById(R.id.accountEdit);
        passwordEdit = v.findViewById(R.id.passwordEdit);
        Button loginBtn = v.findViewById(R.id.loginBtn);
        Button toVerifySMSBtn = v.findViewById(R.id.toVerifySMSBtn);
        Button toRegisteredSellerBtn = v.findViewById(R.id.toRegisteredSellerBtn);
        TextView recoverPasswordText = v.findViewById(R.id.recoverPasswordText);

        // setListener
        loginBtn.setOnClickListener(this);
        toVerifySMSBtn.setOnClickListener(this);
        toRegisteredSellerBtn.setOnClickListener(this);
        recoverPasswordText.setOnClickListener(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        BaseActivity.changeToolbarStatus();
    }

    @Override
    public void onClick(View v) {
        AbsPageFragment fragment = null;
        switch (v.getId()) {
            case R.id.loginBtn:

                String token = FirebaseInstanceId.getInstance().getToken();
                Log.d("FCM token" , token + "");
                if ("1".equals(accountEdit.getText().toString())) {
                    BaseActivity.context.startActivity(new Intent(BaseActivity.context, SellerMainActivity.class));
//                    getActivity().startActivity(new Intent(getContext(), SellerMainActivity.class));
                }else if ("3".equals(accountEdit.getText().toString())){
                    BaseActivity.context.startActivity(new Intent(BaseActivity.context, IntroActivity.class));
//                    getActivity().startActivity(new Intent(getContext(), IntroActivity.class));
                } else {
//                    BaseCore.FRAGMENT_TAG = PageType.HOME.name();
                    BaseActivity.context.startActivity(new Intent(BaseActivity.context, UserMainActivity.class));
//                    getActivity().startActivity(new Intent(getContext(), UserMainActivity.class));

//                    getFragmentManager().beginTransaction().replace(R.id.frameContainer, PageFragmentFactory.of(PageType.HOME, null)).commit();
//                    if (UserMainActivity.bottomMenuTabLayout != null) {
//                        UserMainActivity.bottomMenuTabLayout.setVisibility(View.VISIBLE);
//                    }
                }
//                if (verifyInput()) {
//
////                    ApiManager.test(new ApiCallback(getActivity()) {
////                        @Override
////                        public void onSuccess(String responseBody) {
////
////                        }
////
////                        @Override
////                        public void onFail(Exception error) {
////
////                        }
////                    });
//
////                    AuthService.signInWithEmailAndPassword(mail, password, getFragmentManager(), null);
//                }
                break;
            case R.id.toVerifySMSBtn:
//                UserMainActivity.bottomMenuTabLayout.setVisibility(View.VISIBLE);
                BaseCore.FRAGMENT_TAG = PageType.VERIFY_SMS.name();
                fragment = PageFragmentFactory.of(PageType.VERIFY_SMS, null);
                getFragmentManager().beginTransaction().remove(this).replace(R.id.baseContainer, fragment).addToBackStack(fragment.toString()).commit();
                break;
            case R.id.toRegisteredSellerBtn:
                BaseCore.FRAGMENT_TAG = PageType.REGISTERED_SELLER.name();
                fragment = PageFragmentFactory.of(PageType.REGISTERED_SELLER, null);
                getFragmentManager().beginTransaction().remove(this).replace(R.id.baseContainer, fragment).addToBackStack(fragment.toString()).commit();
                break;
            case R.id.recoverPasswordText:
                BaseCore.FRAGMENT_TAG = PageType.RECOVER_PASSWORD.name();
                fragment = PageFragmentFactory.of(PageType.RECOVER_PASSWORD, null);
                getFragmentManager().beginTransaction().remove(this).replace(R.id.baseContainer, fragment).addToBackStack(fragment.toString()).commit();
                break;
        }
    }

    private boolean verifyInput() {
        boolean result = true;
        String message = "";
        // 驗證帳號不為空
        if (Strings.isNullOrEmpty(accountEdit.getText().toString())) {
            message = BaseCore.context.getString(R.string.mail_wrong_format);
            result = false;
        }
        // 驗證帳號格式
        if (!VerifyUtil.phoneNumber(accountEdit.getText().toString())) {
            message = BaseCore.context.getString(R.string.mail_wrong_format);
            result = false;
        }
        // 驗證密碼不為空
        if (Strings.isNullOrEmpty(passwordEdit.getText().toString())) {
            message = BaseCore.context.getString(R.string.mail_wrong_format);
            result = false;
        }
        if (!result) {
            new AlertView.Builder()
                    .setTitle("")
                    .setMessage(message)
                    .setContext(getContext())
                    .setStyle(AlertView.Style.Alert)
                    .setCancelText("取消")
                    .build()
                    .setCancelable(true)
                    .show();
        }
        return result;
    }

//    @Override
//    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
//        Log.d(TAG, "TimePickerDialog  onDateSet");
//    }
}
