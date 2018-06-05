package com.melonltd.naberc.view.common.page.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.melonltd.naberc.R;
import com.melonltd.naberc.model.helper.okhttp.ApiCallback;
import com.melonltd.naberc.model.helper.okhttp.ApiManager;
import com.melonltd.naberc.util.VerifyUtil;
import com.melonltd.naberc.view.common.BaseActivity;
import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;
import com.melonltd.naberc.view.common.factory.PageFragmentFactory;
import com.melonltd.naberc.view.common.type.PageType;

public class RecoverPasswordFragment extends AbsPageFragment implements View.OnClickListener {
    private static final String TAG = RecoverPasswordFragment.class.getSimpleName();
    public static RecoverPasswordFragment FRAGMENT = null;
    private Button submitBtn;
    private EditText mailEdit;

    public RecoverPasswordFragment() {
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new RecoverPasswordFragment();
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new RecoverPasswordFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.common_recover_password_page) == null) {
            View v = inflater.inflate(R.layout.fragment_recover_password, container, false);
            getView(v);
            container.setTag(R.id.common_recover_password_page, v);
            return v;
        }
        return (View) container.getTag(R.id.common_recover_password_page);
    }

    private void getView(View v) {
        submitBtn = v.findViewById(R.id.submitRecoverPasswordBtn);
        mailEdit = v.findViewById(R.id.recoverEmailEdit);
        submitBtn.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mailEdit.setText("");
        if (BaseActivity.toolbar != null) {
            BaseActivity.navigationIconDisplay(true, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    backToLoginPage();
                    BaseActivity.navigationIconDisplay(false, null);
                }
            });
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        BaseActivity.navigationIconDisplay(false, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submitRecoverPasswordBtn) {
            if (VerifyUtil.email(mailEdit.getText().toString())) {
                ApiManager.test(new ApiCallback(getActivity()) {
                    @Override
                    public void onSuccess(String responseBody) {
                        backToLoginPage();
                    }

                    @Override
                    public void onFail(Exception error) {

                    }
                });
            }
        }
    }

    private void backToLoginPage() {
        BaseCore.FRAGMENT_TAG = PageType.LOGIN.name();
        AbsPageFragment f = PageFragmentFactory.of(PageType.LOGIN, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.baseContainer, f).commit();
    }
}
