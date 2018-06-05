package com.melonltd.naberc.view.common.page.impl;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bigkoo.alertview.AlertView;
import com.google.common.base.Strings;
import com.melonltd.naberc.R;
//import com.melonltd.naberc.view.common.page.impl.BaseCore;
import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;
import com.melonltd.naberc.view.common.factory.PageFragmentFactory;
import com.melonltd.naberc.view.common.type.PageType;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisteredSellerFragment extends AbsPageFragment implements View.OnClickListener {
    private static final String TAG = RegisteredSellerFragment.class.getSimpleName();
    public static RegisteredSellerFragment FRAGMENT = null;
    private EditText nameEdit, addressEdit, contactPersonEdit, contactPhoneEdit;
    private Button submitBun, backToLoginBtn;

    public RegisteredSellerFragment() {
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new RegisteredSellerFragment();
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new RegisteredSellerFragment();
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
        View v = inflater.inflate(R.layout.fragment_registered_seller, container, false);
        getViews(v);
        setListener();
        return v;
    }

    private void getViews(View v) {
        nameEdit = v.findViewById(R.id.storeNameEditText);
        addressEdit = v.findViewById(R.id.storeAddressEditText);
        contactPersonEdit = v.findViewById(R.id.sellerContactPersonEditText);
        contactPhoneEdit = v.findViewById(R.id.sellerContactPhoneEditText);
        submitBun = v.findViewById(R.id.sellerSubmitBun);
        backToLoginBtn = v.findViewById(R.id.sellerBackToLoginBtn);
    }

    private void setListener() {
        submitBun.setOnClickListener(this);
        backToLoginBtn.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sellerSubmitBun:
                if (verifyInput()){
                    backToLoginPage();
                }
                Log.d(TAG, "");

                break;
            case R.id.sellerBackToLoginBtn:
                Log.d(TAG, "");
                backToLoginPage();
                break;
        }
    }

    private boolean verifyInput() {
        boolean result = true;
        String message = "";
        // 驗證身份不為空
        if (Strings.isNullOrEmpty(nameEdit.getText().toString())) {
            message = BaseCore.context.getString(R.string.mail_wrong_format);
            result = false;
        }
        // 驗證姓名不為空
        if (Strings.isNullOrEmpty(addressEdit.getText().toString())) {
            message = BaseCore.context.getString(R.string.mail_wrong_format);
            result = false;
        }
        // 驗證Email不為空
        if (Strings.isNullOrEmpty(contactPersonEdit.getText().toString())) {
            message = BaseCore.context.getString(R.string.mail_wrong_format);
            result = false;
        }
        // 驗證Email不為空
        if (Strings.isNullOrEmpty(contactPhoneEdit.getText().toString())) {
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

    private void backToLoginPage() {
        AbsPageFragment fragment = PageFragmentFactory.of(PageType.LOGIN, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.baseContainer, fragment).addToBackStack(fragment.toString()).commit();
    }

}