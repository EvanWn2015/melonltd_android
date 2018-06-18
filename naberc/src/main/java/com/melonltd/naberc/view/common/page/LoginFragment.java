package com.melonltd.naberc.view.common.page;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.google.common.base.Strings;
import com.google.firebase.iid.FirebaseInstanceId;
import com.melonltd.naberc.R;
import com.melonltd.naberc.model.okhttp.ApiCallback;
import com.melonltd.naberc.model.okhttp.ApiManager;
import com.melonltd.naberc.model.preferences.SharedPreferencesService;
import com.melonltd.naberc.util.Tools;
import com.melonltd.naberc.view.common.BaseActivity;
import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.factory.PageFragmentFactory;
import com.melonltd.naberc.view.factory.PageType;
import com.melonltd.naberc.view.seller.SellerMainActivity;
import com.melonltd.naberc.view.user.UserMainActivity;
import com.melonltd.naberc.vo.AccountInfoVo;

import java.util.Date;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = LoginFragment.class.getSimpleName();
    public static LoginFragment FRAGMENT = null;
    private EditText accountEdit, passwordEdit;
    private CheckBox rememberMe;

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
        rememberMe = v.findViewById(R.id.rememberMeCheckBox);

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

                    AccountInfoVo req = new AccountInfoVo();
                    req.phone = accountEdit.getText().toString();
                    req.password = "GVGhhGhb";
                    req.device_token = FirebaseInstanceId.getInstance().getToken();
                    req.device_category = "ANDROID";
                    ApiManager.login(req, new ApiCallback(getContext()) {
                        @Override
                        public void onSuccess(String responseBody) {
                            AccountInfoVo resp = Tools.GSON.fromJson(responseBody, AccountInfoVo.class);
                            SharedPreferencesService.setOauth(resp.getAccount_uuid());
                            Log.i(TAG, rememberMe.isChecked() + "");
                            if (rememberMe.isChecked()){
                                SharedPreferencesService.setLoginLimit(new Date().getTime());
                                SharedPreferencesService.setRememberAccount(resp.phone);
                                SharedPreferencesService.setRememberIdentity(resp.identity);
                            }
                            if (resp.identity.toUpperCase().equals("USER")){
                                BaseActivity.context.startActivity(new Intent(BaseActivity.context, UserMainActivity.class));
                            }else if (resp.identity.toUpperCase().equals("SELLERS")){
                                BaseActivity.context.startActivity(new Intent(BaseActivity.context, SellerMainActivity.class));
                            }
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
