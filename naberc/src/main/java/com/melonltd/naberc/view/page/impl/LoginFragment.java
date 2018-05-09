package com.melonltd.naberc.view.page.impl;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.melonltd.naberc.R;
import com.melonltd.naberc.view.BaseActivity;
import com.melonltd.naberc.view.LoginActivity;
import com.melonltd.naberc.view.MainActivity;
import com.melonltd.naberc.view.page.abs.AbsPageFragment;

public class LoginFragment extends AbsPageFragment implements View.OnClickListener {
    private static final String TAG = LoginFragment.class.getSimpleName();
    private static LoginFragment FRAGMENT = null;

    private Button loginBtn;
    private EditText mailEdit, passwordEdit;

    public LoginFragment() {
    }

    public static Fragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new LoginFragment();
    }

    @Override
    public AbsPageFragment newInstance(Bundle bundle) {
        return new LoginFragment();
    }

    public static AbsPageFragment getInstance(Bundle bundle) {
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
            Log.d(TAG, " is login btn ");
            BaseActivity.LOADING_BAR.show();
//            BaseActivity.auth.signInWithEmailAndPassword(mailEdit.getText().toString(), passwordEdit.getText().toString())
//                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                BaseActivity.currentUser = BaseActivity.auth.getCurrentUser();
//                                Log.d(TAG, BaseActivity.currentUser.getUid());
//                                Log.d(TAG, BaseActivity.currentUser.getDisplayName());
//                                Log.d(TAG, BaseActivity.currentUser.getPhotoUrl().toString());
//                                Log.d(TAG, BaseActivity.currentUser.getEmail());
////                                startActivity(new Intent(context, MainActivity.class));
////                                getFragmentManager().beginTransaction().remove(this).replace(R.id.frame_container, HomeFragment.newInstance()).commit()
//                            } else {
//                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
////                                Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show();
//                            }
//                            BaseActivity.LOADING_BAR.hide();
//                        }
//                    });

            FirebaseAuth.getInstance().signInWithEmailAndPassword(mailEdit.getText().toString(), passwordEdit.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            BaseActivity.currentUser = BaseActivity.auth.getCurrentUser();
                            Log.d(TAG, BaseActivity.currentUser.getUid());
                            Log.d(TAG, BaseActivity.currentUser.getDisplayName());
                            Log.d(TAG, BaseActivity.currentUser.getPhotoUrl().toString());
                            Log.d(TAG, BaseActivity.currentUser.getEmail());
                            BaseActivity.LOADING_BAR.hide();
                            replace();
                        }
                    });
//            FragmentManager fm = getFragmentManager();
//            getFragmentManager().beginTransaction().remove(this).replace(R.id.frame_container, HomeFragment.newInstance()).commit();
//            fm.beginTransaction().replace(R.id.frame_container, HomeFragment.newInstance()).commit();
        }
    }

    private void replace() {
        getFragmentManager().beginTransaction().remove(this).replace(R.id.frame_container, HomeFragment.newInstance()).commit();
    }
}
