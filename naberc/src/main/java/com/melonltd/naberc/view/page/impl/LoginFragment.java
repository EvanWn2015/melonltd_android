package com.melonltd.naberc.view.page.impl;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.base.Strings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.melonltd.naberc.R;
import com.melonltd.naberc.model.preferences.SharedPreferencesService;
import com.melonltd.naberc.util.VerifyUtil;
import com.melonltd.naberc.view.BaseActivity;
import com.melonltd.naberc.view.MainActivity;
import com.melonltd.naberc.view.page.abs.AbsPageFragment;
import com.melonltd.naberc.view.page.factory.PageFragmentFactory;
import com.melonltd.naberc.view.page.type.PageType;

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

            // 驗證Email不為空 & 格式
            if (!VerifyUtil.email(mail)) {
                // TODO show dialog
                return;
            }

            // 驗證密碼不為空
            if (Strings.isNullOrEmpty(password)) {
                // TODO show dialog
                return;
            }

            BaseActivity.LOADING_BAR.show();
            BaseActivity.auth.signInWithEmailAndPassword(mail, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                BaseActivity.currentUser = BaseActivity.auth.getCurrentUser();
                                String uid = BaseActivity.currentUser.getUid();
                                SharedPreferencesService.setUserUID(uid);
                                BaseActivity.LOADING_BAR.hide();
                                replaceToHomePage();
                            } else {
                                BaseActivity.LOADING_BAR.hide();
                                // TODO show dialog
                            }

                        }
                    });
        }
    }

    private void replaceToHomePage() {
        if (MainActivity.bottomMenuLayout != null) {
            MainActivity.bottomMenuLayout.setVisibility(View.VISIBLE);
        }
        getFragmentManager().beginTransaction().remove(this).replace(R.id.frame_container, PageFragmentFactory.of(PageType.HOME, null)).commit();
    }
}
