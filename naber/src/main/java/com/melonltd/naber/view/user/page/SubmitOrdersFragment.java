package com.melonltd.naber.view.user.page;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.bigkoo.alertview.OnItemClickListener;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.melonltd.naber.R;
import com.melonltd.naber.model.api.ApiManager;
import com.melonltd.naber.model.api.ThreadCallback;
import com.melonltd.naber.model.bean.Model;
import com.melonltd.naber.model.constant.NaberConstant;
import com.melonltd.naber.model.service.SPService;
import com.melonltd.naber.view.factory.PageType;
import com.melonltd.naber.view.user.UserMainActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;


public class SubmitOrdersFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = SubmitOrdersFragment.class.getSimpleName();
    public static SubmitOrdersFragment FRAGMENT = null;
    private TextView selectDateText, userNameText, userPhoneNumberText, ordersPriceText, ordersBonusText;
    private EditText remarkText;
    private TimePickerView timePickerView;
    private int dataIndex = -1;
    private Handler handler = new Handler();

    public SubmitOrdersFragment() {
    }

    public Fragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new SubmitOrdersFragment();
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
        if (container.getTag(R.id.user_submit_order_page) == null) {
            View v = inflater.inflate(R.layout.fragment_submit_orders, container, false);
            getViews(v);
            container.setTag(R.id.user_submit_order_page, v);
        }
        return (View) container.getTag(R.id.user_submit_order_page);
    }

    private void getViews(View v) {
        selectDateText = v.findViewById(R.id.selectDateText);
        userNameText = v.findViewById(R.id.userNameText);
        userPhoneNumberText = v.findViewById(R.id.userPhoneText);
        ordersPriceText = v.findViewById(R.id.ordersPriceText);
        ordersBonusText = v.findViewById(R.id.ordersBonusText);
        remarkText = v.findViewById(R.id.userMessageText);
        Button submitOrdersBtn = v.findViewById(R.id.submitOrdersBtn);

        selectDateText.setOnClickListener(this);
        submitOrdersBtn.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        UserMainActivity.changeTabAndToolbarStatus();
        if (UserMainActivity.toolbar != null) {
            UserMainActivity.navigationIconDisplay(true, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    backToShoppingCartPage();
                    UserMainActivity.navigationIconDisplay(false, null);
//                    BaseCore.FRAGMENT_TAG = PageType.SHOPPING_CART.name();
                    ShoppingCartFragment.TO_SUBMIT_ORDERS_PAGE_INDEX = -1;
                    UserMainActivity.removeAndReplaceWhere(FRAGMENT, PageType.SHOPPING_CART, null);
//                    Fragment f = PageFragmentFactory.of(PageType.SHOPPING_CART, null);
//                    getFragmentManager().beginTransaction().remove(this).replace(R.id.frameContainer, f).addToBackStack(f.toString()).commit();
                }
            });
        }

        dataIndex = getArguments().getInt(NaberConstant.ORDER_DETAIL_INDEX);
        Model.USER_CACHE_SHOPPING_CART.get(dataIndex).fetch_date = "";
        userNameText.setText(SPService.getUserName());
        userPhoneNumberText.setText(SPService.getUserPhone());
        remarkText.setText(Model.USER_CACHE_SHOPPING_CART.get(dataIndex).user_message);
        int amount = 0;
        for (int i = 0; i < Model.USER_CACHE_SHOPPING_CART.get(dataIndex).orders.size(); i++) {
            amount += Integer.parseInt(Model.USER_CACHE_SHOPPING_CART.get(dataIndex).orders.get(i).item.price);
        }
        ordersPriceText.setText("$ " + amount);
        ordersBonusText.setText("應得紅利 " + ((int) Math.floor(amount / 10d)) + "");
    }

//    private void backToShoppingCartPage() {
//        BaseCore.FRAGMENT_TAG = PageType.SHOPPING_CART.name();
//        ShoppingCartFragment.TO_SUBMIT_ORDERS_PAGE_INDEX = -1;
//        Fragment f = PageFragmentFactory.of(PageType.SHOPPING_CART, null);
//        getFragmentManager().beginTransaction().remove(this).replace(R.id.frameContainer, f).addToBackStack(f.toString()).commit();
//    }

    @Override
    public void onStop() {
        super.onStop();
        UserMainActivity.navigationIconDisplay(false, null);
        selectDateText.setText("");
        remarkText.setText("");
    }


    private void showTimePicker() {

        long minutes_20 = 1000 * 60 * 20L;
        long day_3 = 1000 * 60 * 60 * 24 * 3L;
        Calendar now = Calendar.getInstance();
        final Calendar startDate = Calendar.getInstance();
        startDate.setTimeInMillis(now.getTime().getTime() + minutes_20);
        final Calendar endDate = Calendar.getInstance();
        endDate.setTimeInMillis(now.getTime().getTime() + day_3);

        timePickerView = new TimePickerBuilder(getContext(),
                new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        Model.USER_CACHE_SHOPPING_CART.get(dataIndex).fetch_date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSS'Z'").format(date);
                        selectDateText.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date));
                    }
                })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        if (startDate.getTime().getTime() > date.getTime()) {
                            Model.USER_CACHE_SHOPPING_CART.get(dataIndex).fetch_date = "";
                            setDate(startDate);
                        }
                        if (endDate.getTime().getTime() < date.getTime()) {
                            Model.USER_CACHE_SHOPPING_CART.get(dataIndex).fetch_date = "";
                            setDate(endDate);
                        }
                    }
                }).setType(new boolean[]{true, true, true, true, true, false})//"year","month","day","hours","minutes","seconds "
                .setTitleSize(20)
                .setOutSideCancelable(true)
                .isCyclic(false)
                .setTitleBgColor(getResources().getColor(R.color.naber_dividing_line_gray))
                .setCancelColor(getResources().getColor(R.color.naber_dividing_gray))
                .setSubmitColor(getResources().getColor(R.color.naber_dividing_gray))
                .setDate(startDate)
                .setRangDate(startDate, endDate)
                .isCenterLabel(false)
                .isDialog(false)
                .build();

        timePickerView.show();
    }

    /**
     * 重要function 因為無法六及聯動，強制返回時間範圍限制
     *
     * @param date
     */
    private void setDate(Calendar date) {
        timePickerView.setDate(date);
        timePickerView.returnData();
    }

    private void submitOrder() {
        Model.USER_CACHE_SHOPPING_CART.get(dataIndex).user_message = remarkText.getText().toString();
        if (Strings.isNullOrEmpty(Model.USER_CACHE_SHOPPING_CART.get(dataIndex).fetch_date)) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    new AlertView.Builder()
                            .setTitle("")
                            .setMessage("請選擇取餐時間，才可以送出訂單。")
                            .setContext(getContext())
                            .setStyle(AlertView.Style.Alert)
                            .setOthers(new String[]{"我知道了"})
                            .build()
                            .show();
                }
            }, 500);
        } else {
            ApiManager.userOrderSubmit(Model.USER_CACHE_SHOPPING_CART.get(dataIndex), new ThreadCallback(getContext()) {
                @Override
                public void onSuccess(String responseBody) {
                    handler.postDelayed(new OnResponseAlert(
                            "商家已看到您的訂單囉！\n" +
                                    "你可前往訂單頁面查看商品狀態，\n" +
                                    "提醒您，商品只保留至取餐時間後20分鐘。",
                            true), 500);
                }

                @Override
                public void onFail(Exception error, String msg) {
                    Iterator<String> iterator = Splitter.on("$split").split(msg).iterator();
                    String content_text = "";
                    while (iterator.hasNext()) {
                        content_text += iterator.next() + "\n";
                    }
                    handler.postDelayed(new OnResponseAlert(content_text, false), 500);
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    class OnResponseAlert implements Runnable {
        private String msg;
        private boolean isSuccess;

        OnResponseAlert(String msg, boolean isSuccess) {
            this.msg = msg;
            this.isSuccess = isSuccess;
        }

        @Override
        public void run() {
            if (this.isSuccess) {
                Model.USER_CACHE_SHOPPING_CART.remove(dataIndex);
                SPService.setUserCacheShoppingCarData(Model.USER_CACHE_SHOPPING_CART);
            }

            new AlertView.Builder()
                    .setTitle("")
                    .setMessage(this.msg)
                    .setContext(getContext())
                    .setStyle(AlertView.Style.Alert)
                    .setOthers(new String[]{"我知道了"})
                    .build()
                    .setOnDismissListener(new OnDismissListener() {
                        @Override
                        public void onDismiss(Object o) {
                            if (isSuccess) {
                                ShoppingCartFragment.TO_SUBMIT_ORDERS_PAGE_INDEX = -1;
                                UserMainActivity.removeAndReplaceWhere(FRAGMENT, PageType.HISTORY, null);
                            }
                        }
                    })
                    .show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.selectDateText:
                showTimePicker();
                break;
            case R.id.submitOrdersBtn:
                new AlertView.Builder()
                        .setContext(getContext())
                        .setTitle("")
                        .setMessage("確定要送出訂單嗎？")
                        .setStyle(AlertView.Style.Alert)
                        .setOthers(new String[]{"確定", "返回"})
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                if (position == 0) {
                                    submitOrder();
                                }
                            }
                        })
                        .build().show();
                break;
        }
    }
}
