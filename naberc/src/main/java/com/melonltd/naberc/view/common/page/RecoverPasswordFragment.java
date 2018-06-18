package com.melonltd.naberc.view.common.page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.melonltd.naberc.R;
import com.melonltd.naberc.model.okhttp.ApiCallback;
import com.melonltd.naberc.model.okhttp.ApiManager;
import com.melonltd.naberc.util.VerifyUtil;
import com.melonltd.naberc.view.common.BaseActivity;
import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.factory.PageFragmentFactory;
import com.melonltd.naberc.view.factory.PageType;

public class RecoverPasswordFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = RecoverPasswordFragment.class.getSimpleName();
    public static RecoverPasswordFragment FRAGMENT = null;
    private EditText mailEdit;

    public RecoverPasswordFragment() {
    }

    public Fragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new RecoverPasswordFragment();
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }

    public Fragment newInstance(Object... o) {
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
        Button submitBtn = v.findViewById(R.id.submitRecoverPasswordBtn);
        mailEdit = v.findViewById(R.id.recoverEmailEdit);
        submitBtn.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        BaseActivity.changeToolbarStatus();
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
                ApiManager.test(new ApiCallback(getContext()) {
                    @Override
                    public void onSuccess(String responseBody) {
                        backToLoginPage();
                    }

                    @Override
                    public void onFail(Exception error, String msg) {

                    }
                });
            }
        }
    }

    private void backToLoginPage() {
        BaseCore.FRAGMENT_TAG = PageType.LOGIN.name();
        Fragment f = PageFragmentFactory.of(PageType.LOGIN, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.baseContainer, f).commit();
    }
}
