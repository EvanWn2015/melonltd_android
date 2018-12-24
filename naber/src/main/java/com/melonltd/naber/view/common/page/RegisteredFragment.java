package com.melonltd.naber.view.common.page;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.firebase.iid.FirebaseInstanceId;
import com.melonltd.naber.R;
import com.melonltd.naber.model.api.ApiManager;
import com.melonltd.naber.model.api.ThreadCallback;
import com.melonltd.naber.model.service.SPService;
import com.melonltd.naber.model.type.Identity;
import com.melonltd.naber.util.Tools;
import com.melonltd.naber.util.VerifyUtil;
import com.melonltd.naber.view.common.BaseActivity;
import com.melonltd.naber.view.factory.PageType;
import com.melonltd.naber.view.user.UserMainActivity;
import com.melonltd.naber.vo.AccountInfoVo;
import com.melonltd.naber.vo.IdentityTableVo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RegisteredFragment extends Fragment implements View.OnClickListener {
        private static final String TAG = RegisteredFragment.class.getSimpleName();
    public static RegisteredFragment FRAGMENT = null;
    private int STATUS = -1;
    private TextView identityText, schoolEditText, birthdayText, genderText;
    private EditText nameEditText, passwordEditText, confirmPasswordEditText, emailEditText;

    private List<String> genderItems = Lists.newArrayList("男", "女");

    private List<IdentityTableVo> identityTables = Lists.newArrayList();
    private  IdRange idRange = new IdRange();
    public RegisteredFragment() {
    }

    public Fragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new RegisteredFragment();
        }
        if (bundle != null) {
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
        return v;
    }

    private void getViews(View v) {
        identityText = v.findViewById(R.id.identityEditText);
        schoolEditText = v.findViewById(R.id.schoolEditText);
        birthdayText = v.findViewById(R.id.birthdayEditText);
        nameEditText = v.findViewById(R.id.nameEditText);
//        addressEditText = v.findViewById(R.id.addressEditText);
        emailEditText = v.findViewById(R.id.emailEditText);
        passwordEditText = v.findViewById(R.id.passwordEditText);
        confirmPasswordEditText = v.findViewById(R.id.confirmPasswordEditText);
        genderText = v.findViewById(R.id.genderText);

        Button submitBtn = v.findViewById(R.id.submit);
        Button backToLoginBtn = v.findViewById(R.id.backToLoginBtn);

        ConstraintLayout largeLabel = v.findViewById(R.id.largeLabel);

        // setListener
        HideKeyboard hideKeyboard = new HideKeyboard();
        identityText.setOnFocusChangeListener(hideKeyboard);
        schoolEditText.setOnFocusChangeListener(hideKeyboard);
        birthdayText.setOnFocusChangeListener(hideKeyboard);
        nameEditText.setOnFocusChangeListener(hideKeyboard);
//        addressEditText.setOnFocusChangeListener(hideKeyboard);
        emailEditText.setOnFocusChangeListener(hideKeyboard);
        passwordEditText.setOnFocusChangeListener(hideKeyboard);
        confirmPasswordEditText.setOnFocusChangeListener(hideKeyboard);
        genderText.setOnFocusChangeListener(hideKeyboard);

        submitBtn.setOnClickListener(this);
        backToLoginBtn.setOnClickListener(this);
        largeLabel.setOnClickListener(this);
        identityText.setOnClickListener(new IdentityClick());
        schoolEditText.setOnClickListener(new SchoolClick());
        schoolEditText.setEnabled(false);
        birthdayText.setOnClickListener(new BirthdayClick());
        genderText.setOnClickListener(new GenderClick());

    }

    @Override
    public void onResume() {
        super.onResume();
        BaseActivity.changeToolbarStatus();
        genderText.setText("男");
        idRange = new IdRange();


        ApiManager.getIdentityTable(new ThreadCallback(getContext()) {
            @Override
            public void onSuccess(String responseBody) {
                identityTables.clear();
                List<IdentityTableVo> list = Tools.JSONPARSE.fromJsonList(responseBody, IdentityTableVo[].class);
                identityTables.addAll(list);
            }

            @Override
            public void onFail(Exception error, String msg) {
                Log.i(TAG, msg);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        switch (v.getId()) {
            case R.id.submit:
                if (verifyInput()) {
                    AccountInfoVo account = new AccountInfoVo();
                    account.password = passwordEditText.getText().toString();
                    account.name = nameEditText.getText().toString();
                    account.email = emailEditText.getText().toString();
                    account.phone = getArguments().getString("phone");
                    account.birth_day = birthdayText.getText().toString();
//                    account.address = addressEditText.getText().toString();
                    account.level = "USER";
                    account.gender = genderText.getText().toString().equals("男") ? "M" : "W";
                    account.identity = (String) identityText.getTag();
                    account.school_name = schoolEditText.getText().toString();
                    ApiManager.userRegistered(account, new ThreadCallback(getContext()) {
                        @Override
                        public void onSuccess(String responseBody) {
                            AccountInfoVo newAccount = Tools.JSONPARSE.fromJson(responseBody, AccountInfoVo.class);
                            newAccount.password = passwordEditText.getText().toString();
                            newAccount.device_category = "ANDROID";
                            newAccount.device_token = FirebaseInstanceId.getInstance().getToken();
                            ApiManager.login(newAccount, new ThreadCallback(getContext()) {
                                @Override
                                public void onSuccess(String responseBody) {
                                    AccountInfoVo resp = Tools.JSONPARSE.fromJson(responseBody, AccountInfoVo.class);

                                    SPService.clearAccount();
                                    SPService.setOauth(resp.account_uuid);
                                    SPService.setUserName(resp.name);
                                    SPService.setAccout(resp.account);
                                    SPService.setUserPhone(resp.phone);
                                    SPService.setIdentity(resp.identity);
                                    SPService.setLoginLimit(new Date().getTime());
                                    SPService.setRememberAccount(resp.phone);

                                    new Handler().postDelayed(new AlertRun("", "完成註冊，\n歡迎加入NABER！", new String[]{"開始使用"}, new OnItemClickListener() {
                                        @Override
                                        public void onItemClick(Object o, int position) {
                                            if (position == 0) {
                                                startActivity(new Intent(getActivity().getBaseContext(), UserMainActivity.class));
                                            }
                                        }
                                    }), 300);
                                }

                                @Override
                                public void onFail(Exception error, final String msg) {
                                    new Handler().postDelayed(new AlertRun("", msg, new String[]{"我知道了"}, null), 300);
                                }
                            });
                        }

                        @Override
                        public void onFail(Exception error, final String msg) {
                            new Handler().postDelayed(new AlertRun("", msg, new String[]{"我知道了"}, null), 300);
                        }
                    });
                }
                break;
            case R.id.backToLoginBtn:
                BaseActivity.removeAndReplaceWhere(FRAGMENT, PageType.LOGIN, null);
                break;
        }
    }

    class AlertRun implements Runnable {
        private String title;
        private String msg;
        private String[] others;
        private OnItemClickListener lstener;

        AlertRun(String title, String msg, String[] others, OnItemClickListener lstener) {
            this.title = title;
            this.msg = msg;
            this.others = others;
            this.lstener = lstener;
        }

        @Override
        public void run() {
            new AlertView.Builder()
                    .setContext(getContext())
                    .setStyle(AlertView.Style.Alert)
                    .setTitle(this.title)
                    .setMessage(this.msg)
                    .setOnItemClickListener(this.lstener)
                    .setOthers(this.others)
                    .build()
                    .setCancelable(true)
                    .show();
        }
    }

    class IdentityClick implements View.OnClickListener {
        List<String> opt1 = Lists.newArrayList();
        List<List<String>> opt2 = Lists.newArrayList();

        @Override
        public void onClick(final View view) {
            opt1.clear();
            opt2.clear();
            for(IdentityTableVo vo: identityTables){
                opt1.add(vo.area);
                List<String> idNames = Lists.newArrayList();
                for (IdentityTableVo.Identitys id : vo.identitys){
                    idNames.add(id.name);
                }
                opt2.add(idNames);
            }

            schoolEditText.setText("");
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    identityText.setText(opt1.get(options1) + ", " + opt2.get(options1).get(options2));
                    idRange.options1 = options1;
                    idRange.options2 = options2;
                    identityText.setTag(Identity.ofName(opt2.get(options1).get(options2)).name());
                    if(identityTables.get(options1).identitys.get(options2).items.isEmpty()){
                        schoolEditText.setHint("該身份無須選擇校園");
                        schoolEditText.setEnabled(false);
                    } else {
                        schoolEditText.setHint("請選擇校園");
                        schoolEditText.setEnabled(true);
                    }
                }
            }).setTitleSize(20)
                    .setTitleBgColor(getResources().getColor(R.color.naber_dividing_line_gray))
                    .setCancelColor(getResources().getColor(R.color.naber_dividing_gray))
                    .setSubmitColor(getResources().getColor(R.color.naber_dividing_gray))
                    .build();
            pvOptions.setPicker(opt1, opt2);
            pvOptions.show();
        }
    }


    class SchoolClick implements View.OnClickListener {
        List<String> opt1 = Lists.newArrayList();
        @Override
        public void onClick(final View view) {
            opt1.clear();
            if (!identityTables.get(idRange.options1).identitys.get(idRange.options2).items.isEmpty()){
                opt1.addAll(identityTables.get(idRange.options1).identitys.get(idRange.options2).items);
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        schoolEditText.setText(opt1.get(options1));
                    }
                }).setTitleSize(20)
                        .setTitleBgColor(getResources().getColor(R.color.naber_dividing_line_gray))
                        .setCancelColor(getResources().getColor(R.color.naber_dividing_gray))
                        .setSubmitColor(getResources().getColor(R.color.naber_dividing_gray))
                        .build();
                pvOptions.setPicker(opt1);
                pvOptions.show();
            }
        }
    }

    class BirthdayClick implements View.OnClickListener {
        @Override
        public void onClick(final View view) {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            Calendar selectedDate = Calendar.getInstance();
            Calendar startDate = Calendar.getInstance();
            startDate.set(1948, 1, 1);
            Calendar endDate = Calendar.getInstance();
            TimePickerView tp = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    String birth_day = new SimpleDateFormat("yyyy-MM-dd").format(date);
                    birthdayText.setText(birth_day);
                }
            }).setType(new boolean[]{true, true, true, false, false, false})//"year","month","day","hours","minutes","seconds "
                    .setTitleSize(20)
                    .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                    .isCyclic(false)//是否循环滚动
                    .setTitleBgColor(getResources().getColor(R.color.naber_dividing_line_gray))
                    .setCancelColor(getResources().getColor(R.color.naber_dividing_gray))
                    .setSubmitColor(getResources().getColor(R.color.naber_dividing_gray))
                    .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                    .setRangDate(startDate, endDate)//起始终止年月日设定
                    .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                    .isDialog(false)//是否显示为对话框样式
                    .build();

            tp.show();
        }
    }

    class GenderClick implements View.OnClickListener {
        @Override
        public void onClick(final View view) {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    STATUS = 1;
                    String gender = genderItems.get(options1);
                    genderText.setText(gender);
                }
            }).setTitleSize(20)
                    .setTitleBgColor(getResources().getColor(R.color.naber_dividing_line_gray))
                    .setCancelColor(getResources().getColor(R.color.naber_dividing_gray))
                    .setSubmitColor(getResources().getColor(R.color.naber_dividing_gray))
                    .build();
            pvOptions.setOnDismissListener(new com.bigkoo.pickerview.listener.OnDismissListener() {
                @Override
                public void onDismiss(Object o) {
                    if (STATUS == 1) {
                        STATUS = -1;
                        // TODO 代表按了確認
                    } else {
                        genderText.setText("");
                    }
                }
            });
            pvOptions.setPicker(genderItems);
            pvOptions.show();
        }
    }

    class HideKeyboard implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
    }

    private boolean verifyInput() {
        boolean result = true;
        String message = "";
        // 驗證性別不為空
        if (Strings.isNullOrEmpty(genderText.getText().toString())) {
            message = "性別不為空";
            result = false;
        }
        // 驗證生日不為空
        if (Strings.isNullOrEmpty(birthdayText.getText().toString())) {
            message = "生日不為空";
            result = false;
        }
        // 驗證密碼與確認密碼一致
        if (!passwordEditText.getText().toString().equals(confirmPasswordEditText.getText().toString())) {
            message = "密碼與確認密碼不一致";
            result = false;
        }
        // 驗證密碼不為空 並需要英文大小寫數字 6 ~ 20
        if (!VerifyUtil.password(passwordEditText.getText().toString())) {
            message = "密碼不為空 並需要英文大小寫數字 6 ~ 20";
            result = false;
        }
        // 驗證Email錯誤格式
        if (!VerifyUtil.email(emailEditText.getText().toString())) {
            message = "Email錯誤格式";
            result = false;
        }
        // 驗證Email不為空
        if (Strings.isNullOrEmpty(emailEditText.getText().toString())) {
            message = "Email不為空";
            result = false;
        }
        // 驗證姓名長度大於二
        if (nameEditText.getText().toString().length() <= 1 || nameEditText.getText().toString().length() >= 5) {
            message = "姓名格式不正確";
            result = false;
        }
        // 驗證姓名不為空
        if (Strings.isNullOrEmpty(nameEditText.getText().toString())) {
            message = "姓名不為空";
            result = false;
        }

        // 驗證校園
        if (!identityTables.get(idRange.options1).identitys.get(idRange.options2).items.isEmpty() && Strings.isNullOrEmpty(schoolEditText.getText().toString())){
            message = "該身份請選擇校園";
            result = false;
        }

        // 驗證身份不為空
        if (Strings.isNullOrEmpty(identityText.getText().toString())) {
            message = "請選擇身份";
            result = false;
        }

        if (!result) {
            new Handler().postDelayed(new AlertRun("", message, new String[]{"我知道了"}, null), 100);
        }

        return result;
    }

    class IdRange {
       public int options1 = 0 ;
       public int options2 = 0;
    }

}

