package com.melonltd.naberc.view.common.page.impl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.melonltd.naberc.model.helper.okhttp.ApiCallback;
import com.melonltd.naberc.model.helper.okhttp.ApiManager;
import com.melonltd.naberc.model.preferences.SharedPreferencesService;
import com.melonltd.naberc.util.Tools;
import com.melonltd.naberc.view.common.BaseActivity;
import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.common.factory.PageFragmentFactory;
import com.melonltd.naberc.view.common.type.PageType;
import com.melonltd.naberc.view.intro.IntroActivity;
import com.melonltd.naberc.view.seller.SellerMainActivity;
import com.melonltd.naberc.view.user.UserMainActivity;
import com.melonltd.naberc.vo.AccountInfoVo;
import com.melonltd.naberc.vo.RespData;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = LoginFragment.class.getSimpleName();
    public static LoginFragment FRAGMENT = null;
    private EditText accountEdit, passwordEdit;

    public LoginFragment() {
    }

    public Fragment newInstance(Object... o) {
        return new LoginFragment();
    }

    public Fragment getInstance(Bundle bundle) {
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
        Fragment fragment = null;
        switch (v.getId()) {
            case R.id.loginBtn:
                if (verifyInput()) {
                    String token = SharedPreferencesService.getOauth();
                    Log.i(TAG , "account token" + token);
                    AccountInfoVo req = new AccountInfoVo();
                    req.phone = accountEdit.getText().toString();
                    req.password = "GVGhhGhb";
//                    req.device_token = FirebaseInstanceId.getInstance().getToken();
//                    req.device_category = "ANDROID";
                    ApiManager.login(req, new ApiCallback(getContext()) {
                        @Override
                        public void onSuccess(String responseBody) {
                            AccountInfoVo resp = Tools.GSON.fromJson(responseBody, AccountInfoVo.class);
                            SharedPreferencesService.setOauth(resp.getAccount_uuid());

                        }

                        @Override
                        public void onFail(Exception error, String msg) {
                            new AlertView.Builder()
                                    .setTitle("")
                                    .setMessage(msg)
                                    .setContext(getContext())
                                    .setStyle(AlertView.Style.Alert)
                                    .setCancelText("取消")
                                    .build()
                                    .setCancelable(true)
                                    .show();
                        }
                    });
                }

//                String token = FirebaseInstanceId.getInstance().getToken();
//                Log.d("FCM token" , token + "");



//                if ("1".equals(accountEdit.getText().toString())) {
//                    BaseActivity.context.startActivity(new Intent(BaseActivity.context, SellerMainActivity.class));
//                }else if ("3".equals(accountEdit.getText().toString())){
//                    BaseActivity.context.startActivity(new Intent(BaseActivity.context, IntroActivity.class));
//                } else {
//                    BaseActivity.context.startActivity(new Intent(BaseActivity.context, UserMainActivity.class));
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
        if (Strings.isNullOrEmpty(accountEdit.getText().toString())) {
            message = "請輸入帳號";
            result = false;
        }
        if (Strings.isNullOrEmpty(passwordEdit.getText().toString())) {
            message = "請輸入帳號";
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

}
