package com.melonltd.naberc.view.user.page.impl;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.google.common.base.Strings;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.melonltd.naberc.R;
import com.melonltd.naberc.util.VerifyUtil;
import com.melonltd.naberc.view.user.BaseCore;
import com.melonltd.naberc.view.user.page.abs.AbsPageFragment;
import com.melonltd.naberc.view.user.page.factory.PageFragmentFactory;
import com.melonltd.naberc.view.user.page.type.PageType;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisteredFragment extends AbsPageFragment implements View.OnClickListener, View.OnFocusChangeListener {
    private static final String TAG = RegisteredFragment.class.getSimpleName();
    private static RegisteredFragment FRAGMENT = null;

    private EditText identityEditText, nameEditText, addressEditText, emailEditText, passwordEditText, confirmPasswordEditText,
            birthdayEditText;
    private Button submitBtn, backToLoginBtn;


    public RegisteredFragment() {
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new RegisteredFragment();
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new RegisteredFragment();
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
        View v = inflater.inflate(R.layout.fragment_registered, container, false);
        getViews(v);
        setListener();
        return v;
    }

    private void getViews(View v) {
        identityEditText = v.findViewById(R.id.identityEditText);
        nameEditText = v.findViewById(R.id.nameEditText);
        addressEditText = v.findViewById(R.id.addressEditText);
        emailEditText = v.findViewById(R.id.emailEditText);
        passwordEditText = v.findViewById(R.id.passwordEditText);
        confirmPasswordEditText = v.findViewById(R.id.confirmPasswordEditText);
        birthdayEditText = v.findViewById(R.id.birthdayEditText);
        submitBtn = v.findViewById(R.id.submit);
        backToLoginBtn = v.findViewById(R.id.backToLoginBtn);

        birthdayEditText.setInputType(InputType.TYPE_NULL);
        identityEditText.setInputType(InputType.TYPE_NULL);


    }

    public void closeKeyBoard(EditText editText) {
        InputMethodManager inputMeg = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMeg.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    private void setListener() {
        submitBtn.setOnClickListener(this);
        backToLoginBtn.setOnClickListener(this);

        identityEditText.setOnFocusChangeListener(this);
        birthdayEditText.setOnFocusChangeListener(this);
        birthdayEditText.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void backToLoginPage() {
        AbsPageFragment fragment = PageFragmentFactory.of(PageType.LOGIN, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.frameContainer, fragment).addToBackStack(fragment.toString()).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                if (verifyInput()) {
                    backToLoginPage();
                }
                break;
            case R.id.backToLoginBtn:
                backToLoginPage();
                break;
            case R.id.birthdayEditText:
                long tenYears = 70L * 365 * 1000 * 60 * 60 * 24L;
                new TimePickerDialog.Builder()
                        .setTitleStringId("")
                        .setYearText(getResources().getString(R.string.data_time_picker_years_text))
                        .setMonthText(getResources().getString(R.string.data_time_picker_month_text))
                        .setDayText(getResources().getString(R.string.data_time_picker_day_text))
                        .setCyclic(true)
                        .setMinMillseconds(System.currentTimeMillis() - tenYears)
                        .setMaxMillseconds(System.currentTimeMillis())
                        .setCurrentMillseconds(System.currentTimeMillis())
                        .setThemeColor(getResources().getColor(R.color.naber_basis))
                        .setType(Type.YEAR_MONTH_DAY)
                        .setWheelItemTextNormalColor(getResources().getColor(R.color.naber_dividing_line_gray))
                        .setWheelItemTextSize(16)
                        .setCallBack(new OnDateSetListener() {
                            @Override
                            public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                                Log.d(TAG, "");
                                birthdayEditText.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date(millseconds)));
                            }
                        })
                        .build().show(getFragmentManager(), "YEAR_MONTH_DAY");
                break;
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        Log.d(TAG, "onFocusChange");
        if (b) {
            closeKeyBoard(birthdayEditText);
            onClick(view);
        }
    }


    private boolean verifyInput() {
        boolean result = true;
        String message = "";
        // 驗證身份不為空
        if (Strings.isNullOrEmpty(identityEditText.getText().toString())) {
            message = BaseCore.context.getString(R.string.mail_wrong_format);
            result = false;
        }
        // 驗證姓名不為空
        if (Strings.isNullOrEmpty(nameEditText.getText().toString())) {
            message = BaseCore.context.getString(R.string.mail_wrong_format);
            result = false;
        }
        // 驗證姓名長度大於二
        if (nameEditText.getText().toString().length() <= 4) {
            message = BaseCore.context.getString(R.string.mail_wrong_format);
            result = false;
        }
        // 驗證Email不為空
        if (Strings.isNullOrEmpty(emailEditText.getText().toString())) {
            message = BaseCore.context.getString(R.string.mail_wrong_format);
            result = false;
        }
        // 驗證Email錯誤格式
        if (!VerifyUtil.email(emailEditText.getText().toString())) {
            message = BaseCore.context.getString(R.string.mail_wrong_format);
            result = false;
        }
        // 驗證密碼不為空 並需要英文大小寫數字 6 ~ 20
        if (!VerifyUtil.password(passwordEditText.getText().toString())) {
            message = BaseCore.context.getString(R.string.mail_wrong_format);
            result = false;
        }
        // 驗證密碼與確認密碼一致
        if (!passwordEditText.getText().toString().equals(confirmPasswordEditText.getText().toString())) {
            message = BaseCore.context.getString(R.string.mail_wrong_format);
            result = false;
        }
        // 驗證生日不為空
        if (Strings.isNullOrEmpty(birthdayEditText.getText().toString())) {
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
}

