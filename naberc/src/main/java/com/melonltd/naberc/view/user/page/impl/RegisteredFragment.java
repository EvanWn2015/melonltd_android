package com.melonltd.naberc.view.user.page.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.melonltd.naberc.R;
import com.melonltd.naberc.model.bean.IdentityJsonBean;
import com.melonltd.naberc.util.VerifyUtil;

import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;
import com.melonltd.naberc.view.common.factory.PageFragmentFactory;
import com.melonltd.naberc.view.common.type.PageType;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegisteredFragment extends AbsPageFragment implements View.OnClickListener {
    private static final String TAG = RegisteredFragment.class.getSimpleName();
    private static RegisteredFragment FRAGMENT = null;
    private TextView identityEditText, birthdayEditText;
    private EditText nameEditText, addressEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private Button submitBtn, backToLoginBtn;


    private List<String> options1Items = Lists.newArrayList();
    private List<List<String>> options2Items = Lists.newArrayList();
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private Thread thread;
    private boolean isLoaded = false;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;
                case MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    break;
                case MSG_LOAD_FAILED:
                    break;
            }
        }
    };

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
        if (container.getTag(R.id.user_registered_page) == null) {
            View v = inflater.inflate(R.layout.fragment_registered, container, false);
            getViews(v);
            setListener();
            mHandler.sendEmptyMessage(MSG_LOAD_DATA);
            container.setTag(R.id.user_registered_page, v);
            return v;
        }
        return (View) container.getTag(R.id.user_registered_page);
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

    private void setListener() {
        submitBtn.setOnClickListener(this);
        backToLoginBtn.setOnClickListener(this);
        identityEditText.setOnClickListener(this);
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

    private void showOptIdentity() {
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        if (isLoaded) {
            OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    String tx = options1Items.get(options1) + " : " + options2Items.get(options1).get(option2);
                    Log.d(TAG, tx);
                    identityEditText.setText(tx);
                }
            })
                    .setTitleBgColor(getResources().getColor(R.color.naber_dividing_line_gray))
                    .setCancelColor(getResources().getColor(R.color.naber_dividing_gray))
                    .setSubmitColor(getResources().getColor(R.color.naber_dividing_gray))
                    .build();
            pvOptions.setPicker(options1Items, options2Items);
            pvOptions.show();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                if (verifyInput()) {
                    backToLoginPage();
                }
                break;
            case R.id.identityEditText:
                Log.d(TAG, " identityEditText");
                showOptIdentity();
                break;
            case R.id.backToLoginBtn:
                backToLoginPage();
                break;
            case R.id.birthdayEditText:
                long tenYears = 70L * 365 * 1000 * 60 * 60 * 24L;
                new TimePickerDialog.Builder()
                        .setTitleStringId("")
                        .setTitleStringId("請選擇")
                        .setYearText(getResources().getString(R.string.data_time_picker_years_text))
                        .setMonthText(getResources().getString(R.string.data_time_picker_month_text))
                        .setDayText(getResources().getString(R.string.data_time_picker_day_text))
                        .setCyclic(false)
                        .setToolBarTextColor(getResources().getColor(R.color.naber_basis_blue))
                        .setMinMillseconds(System.currentTimeMillis() - tenYears)
                        .setMaxMillseconds(System.currentTimeMillis())
                        .setCurrentMillseconds(System.currentTimeMillis())
                        .setThemeColor(getResources().getColor(R.color.naber_dividing_line_gray))
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


    private String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    private void initJsonData() {
        String JsonData = getJson(getContext(), "identity.json");
        ArrayList<IdentityJsonBean> identityBean = parseData(JsonData);
        List<String> opt1 = Lists.newArrayList();
        List<List<String>> opt2 = Lists.newArrayList();
        for (IdentityJsonBean b : identityBean) {
            opt1.add(b.getName());
            List<String> datas = Lists.newArrayList();
            for (String d : b.getDatas()) {
                datas.add(d);
            }
            opt2.add(datas);
        }
        options1Items.clear();
        options1Items.addAll(opt1);
        options2Items.clear();
        options2Items.addAll(opt2);

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }

    public ArrayList<IdentityJsonBean> parseData(String result) {//Gson 解析
        ArrayList<IdentityJsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                IdentityJsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), IdentityJsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
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

