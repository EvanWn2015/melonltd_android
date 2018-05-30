package com.melonltd.naberc.view.user.page.impl;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.melonltd.naberc.R;
import com.melonltd.naberc.model.helper.okhttp.ApiCallback;
import com.melonltd.naberc.model.helper.okhttp.ApiManager;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class SubmitOrdersFragment extends AbsPageFragment implements View.OnClickListener {
    private static final String TAG = SubmitOrdersFragment.class.getSimpleName();
    private static SubmitOrdersFragment FRAGMENT = null;
    private TextView selectDateText, userNameText, userPhoneNumberText, ordersPriceText, ordersBonusText;
    private EditText remarkText;
    private Button submitOrdersBtn;

    public SubmitOrdersFragment() {
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new SubmitOrdersFragment();
        }
        FRAGMENT.setArguments(null);
        FRAGMENT.setArguments(bundle);
        return FRAGMENT;
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new SubmitOrdersFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.user_submit_order_page) == null) {
            View v = inflater.inflate(R.layout.fragment_submit_orders, container, false);
            getViews(v);
            container.setTag(R.id.user_submit_order_page, v);
        }
        return (View) container.getTag(R.id.user_submit_order_page);
    }

    private void getViews(View v) {
        selectDateText = v.findViewById(R.id.selectDateText);
        selectDateText.setOnClickListener(this);
        userNameText = v.findViewById(R.id.userNameText);
        userPhoneNumberText = v.findViewById(R.id.userPhoneNumberText);
        ordersPriceText = v.findViewById(R.id.ordersPriceText);
        ordersBonusText = v.findViewById(R.id.ordersBonusText);
        remarkText = v.findViewById(R.id.remarkText);
        submitOrdersBtn = v.findViewById(R.id.submitOrdersBtn);
        submitOrdersBtn.setOnClickListener(this);
    }


    private void showTimePicker() {
        long minutes_20 = 1000 * 60 * 20L;
        long day_3 = 1000 * 60 * 60 * 24 * 3L;
        Calendar now = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.setTimeInMillis(now.getTime().getTime() + minutes_20);
        Calendar endDate = Calendar.getInstance();
        endDate.setTimeInMillis(now.getTime().getTime() + day_3);

        TimePickerView tp = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Log.d(TAG, date.toString());
                selectDateText.setText(new SimpleDateFormat("yyyy-MM-dd hh:mm").format(date));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})//"year","month","day","hours","minutes","seconds "
                .setTitleSize(20)//标题文字大小
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
                .setTitleBgColor(getResources().getColor(R.color.naber_dividing_line_gray))
                .setCancelColor(getResources().getColor(R.color.naber_dividing_gray))
                .setSubmitColor(getResources().getColor(R.color.naber_dividing_gray))
                .setDate(startDate)
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .build();
        tp.show();


//        new TimePickerDialog.Builder()
//                .setTitleStringId("")
//                .setTitleStringId("取餐時間")
//                .setYearText(getResources().getString(R.string.data_time_picker_years_text))
//                .setMonthText(getResources().getString(R.string.data_time_picker_month_text))
//                .setDayText(getResources().getString(R.string.data_time_picker_day_text))
//                .setCyclic(false)
//                .setToolBarTextColor(getResources().getColor(R.color.naber_basis_blue))
//                .setMinMillseconds(System.currentTimeMillis() + minutes_20)
//                .setMaxMillseconds(System.currentTimeMillis() + day_3)
//                .setCurrentMillseconds(System.currentTimeMillis() + minutes_20)
//                .setThemeColor(getResources().getColor(R.color.naber_dividing_line_gray))
//                .setType(Type.MONTH_DAY_HOUR_MIN)
//                .setWheelItemTextNormalColor(getResources().getColor(R.color.naber_dividing_line_gray))
//                .setWheelItemTextSize(16)
//                .setCallBack(new OnDateSetListener() {
//                    @Override
//                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
//                        Log.d(TAG, "");
//                        selectDateText.setText(new SimpleDateFormat("yyyy-MM-dd hh:mm").format(new Date(millseconds)));
//                    }
//                })
//                .build()
//                .show(getFragmentManager(), "MONTH_DAY_HOUR_MIN");
    }

    Handler h = new Handler();
    Runnable showSubmitAlertUrn = new Runnable() {
        @Override
        public void run() {
            ApiManager.test(new ApiCallback(getActivity()) {
                @Override
                public void onSuccess(String responseBody) {
                    new AlertView.Builder()
                            .setTitle("")
                            .setMessage("商家看到你囉，請準時拿餐\n你可以到訂單頁面中，查看商品狀態")
                            .setContext(getContext())
                            .setStyle(AlertView.Style.Alert)
                            .setOthers(new String[]{"我知道了"})
                            .build().show();
                }

                @Override
                public void onFail(Exception error) {

                }
            });

        }
    };

    private void showSubmitAlert() {
        new AlertView.Builder()
                .setTitle("")
                .setMessage("確定要送出訂單嗎？")
                .setContext(getContext())
                .setStyle(AlertView.Style.Alert)
                .setOthers(new String[]{"確定", "返回"})
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        if (position == 0) {
                            h.postDelayed(showSubmitAlertUrn, 300);
                        }
                    }
                })
                .build().show();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.selectDateText:
                showTimePicker();
                break;
            case R.id.submitOrdersBtn:
                showSubmitAlert();
                break;
        }
    }
}
