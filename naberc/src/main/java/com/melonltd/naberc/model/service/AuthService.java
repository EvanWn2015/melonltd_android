package com.melonltd.naberc.model.service;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.base.Strings;
import com.google.firebase.auth.AuthResult;
import com.melonltd.naberc.R;
import com.melonltd.naberc.model.preferences.SharedPreferencesService;
import com.melonltd.naberc.util.VerifyUtil;
import com.melonltd.naberc.view.BaseCore;
import com.melonltd.naberc.view.MainActivity;
import com.melonltd.naberc.view.page.factory.PageFragmentFactory;
import com.melonltd.naberc.view.page.type.PageType;

public class AuthService {


    public static void signInWithEmailAndPassword(String mail, String password, FragmentManager fm, OnCompleteListener<AuthResult> listener) {

        // 驗證Email不為空 & 錯誤格式
        if (!VerifyUtil.email(mail)) {
            BaseCore.POPUP.show(R.string.mail_wrong_format);
            return;
        }

        // 驗證密碼不為空
        if (Strings.isNullOrEmpty(password)) {
            BaseCore.POPUP.show(R.string.password_wrong_format);
            return;
        }

        BaseCore.LOADING_BAR.show();
        if (listener != null) {
            BaseCore.auth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(listener);
        } else {
            BaseCore.auth.signInWithEmailAndPassword(mail, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                BaseCore.currentUser = BaseCore.auth.getCurrentUser();
                                String uid = BaseCore.currentUser.getUid();
                                SharedPreferencesService.setUserUID(uid);
                                BaseCore.LOADING_BAR.hide();
                                if (MainActivity.bottomMenuLayout != null) {
                                    MainActivity.bottomMenuLayout.setVisibility(View.VISIBLE);
                                }
                                fm.beginTransaction().replace(R.id.frame_container, PageFragmentFactory.of(PageType.HOME, null)).commit();
                            } else {
                                BaseCore.LOADING_BAR.hide();
                                BaseCore.POPUP.show(R.string.account_does_not_exist);
                            }
                        }
                    });
        }

    }
}
