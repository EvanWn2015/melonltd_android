package com.melonltd.naber.view.common.page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.melonltd.naber.model.api.ApiManager;
import com.melonltd.naber.model.api.ThreadCallback;
import com.melonltd.naber.util.VerifyUtil;
import com.melonltd.naber.view.common.BaseActivity;
import com.melonltd.naber.view.factory.PageType;
import com.melonltd.naber.R;

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
        View v = inflater.inflate(R.layout.fragment_recover_password, container, false);
        getView(v);
        return v;
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
                    BaseActivity.removeAndReplaceWhere(FRAGMENT, PageType.LOGIN, null);
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
                ApiManager.test(new ThreadCallback(getContext()) {
                    @Override
                    public void onSuccess(String responseBody) {
                        BaseActivity.removeAndReplaceWhere(FRAGMENT, PageType.LOGIN, null);
                    }

                    @Override
                    public void onFail(Exception error, String msg) {

                    }
                });
            }
        }
    }
}