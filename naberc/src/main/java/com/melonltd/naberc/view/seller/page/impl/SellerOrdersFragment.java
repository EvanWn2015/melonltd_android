package com.melonltd.naberc.view.seller.page.impl;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.melonltd.naberc.R;
import com.melonltd.naberc.model.helper.okhttp.ApiCallback;
import com.melonltd.naberc.model.helper.okhttp.ApiManager;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;
import com.melonltd.naberc.view.seller.SellerMainActivity;
import com.melonltd.naberc.view.seller.adapter.SellerOrdersAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class SellerOrdersFragment extends AbsPageFragment {
    private static final String TAG = SellerOrdersFragment.class.getSimpleName();
    private static SellerOrdersFragment FRAGMENT = null;
    private TextView searchDateText;
    private TextView untreatedText, processingText, canFetchText;
    private TextView untreatedSumText, processingSumText, canFetchSumText;

    private SellerOrdersAdapter adapter;
    private RecyclerView ordersRecyclerView;

    private List<String> tmpList = Lists.newArrayList();
    private List<String> untreatedList = Lists.newArrayList(), processingList = Lists.newArrayList(), canFetchList = Lists.newArrayList();

    public static TabType TAB_TYPE = TabType.UNTREATED;

    public enum TabType {
        UNTREATED, PROCESSING, CAN_FETCH
    }

    public SellerOrdersFragment() {
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new SellerOrdersFragment();
        }
        FRAGMENT.setArguments(bundle);
        return FRAGMENT;
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new SellerOrdersFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new SellerOrdersAdapter(tmpList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SellerMainActivity.navigationIconDisplay(false, null);
        if (container.getTag(R.id.seller_orders_main_page) == null) {
            View v = inflater.inflate(R.layout.fragment_seller_orders, container, false);
            getViews(v);
            setListener();
            container.setTag(R.id.seller_orders_main_page, v);
            return v;
        }
        return (View) container.getTag(R.id.seller_orders_main_page);

    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        setListCount();
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            untreatedList.add("未處理訂單" + i);
            processingList.add("處理中訂單" + i);
            canFetchList.add("可領取訂單" + i);
        }
        tmpList.addAll(untreatedList);
    }

    private void setListCount() {
        untreatedSumText.setText(untreatedList.size() + "");
        processingSumText.setText(processingList.size() + "");
        canFetchSumText.setText(canFetchList.size() + "");
    }

    private void removeListData(int index) {
        switch (TAB_TYPE) {
            case UNTREATED:
                untreatedList.remove(index);
                break;
            case PROCESSING:
                processingList.remove(index);
                break;
            case CAN_FETCH:
                canFetchList.remove(index);
                break;
        }
    }

    private void getViews(View v) {
        searchDateText = v.findViewById(R.id.searchDateText);
        searchDateText.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())));
        untreatedText = v.findViewById(R.id.untreatedText);
        processingText = v.findViewById(R.id.processingText);
        canFetchText = v.findViewById(R.id.canFetchText);
        untreatedSumText = v.findViewById(R.id.untreatedSumText);
        processingSumText = v.findViewById(R.id.processingSumText);
        canFetchSumText = v.findViewById(R.id.canFetchSumText);
        ordersRecyclerView = v.findViewById(R.id.ordersRecyclerView);
    }

    private void setListener() {
        searchDateText.setOnClickListener(new SelectDateListener());
        untreatedText.setOnClickListener(new TabClickListener());
        processingText.setOnClickListener(new TabClickListener());
        canFetchText.setOnClickListener(new TabClickListener());

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ordersRecyclerView.setLayoutManager(layoutManager);
        ordersRecyclerView.setAdapter(adapter);
        adapter.setOnClickListeners(new CancelListener(), new ProcessingListener(), new FailureListener(), new CanFetchListener(), new FinishListener());
    }

    private void searchByDate(long date) {
        ApiManager.test(new ApiCallback(getContext()) {
            @Override
            public void onSuccess(String responseBody) {

            }

            @Override
            public void onFail(Exception error) {

            }
        });
    }

    class SelectDateListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            long date = 1000 * 60 * 60 * 24 * 2L;
            Calendar now = Calendar.getInstance();
            Calendar endDate = Calendar.getInstance();
            endDate.setTimeInMillis(now.getTime().getTime() + date);
            TimePickerView tp = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    Log.d(TAG, date.toString());
                    searchDateText.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));
                }
            })
                    .setType(new boolean[]{true, true, true, false, false, false})//"year","month","day","hours","minutes","seconds "
                    .setTitleSize(20)//标题文字大小
                    .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                    .isCyclic(false)//是否循环滚动
                    .setTitleBgColor(getResources().getColor(R.color.naber_dividing_line_gray))
                    .setCancelColor(getResources().getColor(R.color.naber_dividing_gray))
                    .setSubmitColor(getResources().getColor(R.color.naber_dividing_gray))
                    .setRangDate(now, endDate)//起始终止年月日设定
                    .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                    .isDialog(false)//是否显示为对话框样式
                    .build();

            tp.show();

//            long date = 1000 * 60 * 60 * 24 * 2L;
//            new TimePickerDialog.Builder()
//                    .setTitleStringId("")
//                    .setTitleStringId("請選擇")
//                    .setYearText(getResources().getString(R.string.data_time_picker_years_text))
//                    .setMonthText(getResources().getString(R.string.data_time_picker_month_text))
//                    .setDayText(getResources().getString(R.string.data_time_picker_day_text))
//                    .setCyclic(false)
//                    .setToolBarTextColor(getResources().getColor(R.color.naber_basis_blue))
//                    .setMinMillseconds(System.currentTimeMillis())
//                    .setMaxMillseconds(System.currentTimeMillis() + date)
//                    .setCurrentMillseconds(System.currentTimeMillis())
//                    .setThemeColor(getResources().getColor(R.color.naber_dividing_line_gray))
//                    .setType(Type.YEAR_MONTH_DAY)
//                    .setWheelItemTextNormalColor(getResources().getColor(R.color.naber_dividing_line_gray))
//                    .setWheelItemTextSize(16)
//                    .setCallBack(new OnDateSetListener() {
//                        @Override
//                        public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
//                            searchDateText.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date(millseconds)));
////                            searchByDate(millseconds);
//                        }
//                    })
//                    .build()
//                    .show(getFragmentManager(), "YEAR_MONTH_DAY");

        }
    }


    class CancelListenerView implements View.OnClickListener {
        private View view;
        private TextView defText1;
        private TextView defText2;
        private EditText customEdit;
        private String msg = "";

        CancelListenerView() {
            this.view = getLayoutInflater().inflate(R.layout.seller_cancel_order_notifiy_to_user_message, null);
            this.defText1 = view.findViewById(R.id.defaultText1);
            this.defText2 = view.findViewById(R.id.defaultText2);
            this.customEdit = view.findViewById(R.id.customEdit);
            // 先帶入預設一
            this.msg = defText1.getText().toString();
            setListener();
        }

        private void setListener() {
            this.defText1.setOnClickListener(CancelListenerView.this);
            this.defText2.setOnClickListener(CancelListenerView.this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.defaultText1:
                    v.setBackgroundResource(R.drawable.naber_reverse_orange_button_style);
                    this.defText2.setBackgroundResource(R.drawable.naber_reverse_gary_button_style);
                    this.msg = defText1.getText().toString();
                    break;
                case R.id.defaultText2:
                    v.setBackgroundResource(R.drawable.naber_reverse_orange_button_style);
                    this.defText1.setBackgroundResource(R.drawable.naber_reverse_gary_button_style);
                    this.msg = defText2.getText().toString();
                    break;
            }
        }

        private View getView() {
            return this.view;
        }

        private String getMessage() {
            if (!Strings.isNullOrEmpty(customEdit.getText().toString()) && customEdit.getText().toString().length() > 0) {
                return customEdit.getText().toString();
            }
            return this.msg;
        }
    }


    class CancelListener implements View.OnClickListener {
        @Override
        public void onClick(final View view) {
            final CancelListenerView extView = new CancelListenerView();
            new AlertView.Builder()
                    .setContext(getContext())
                    .setStyle(AlertView.Style.Alert)
                    .setOthers(new String[]{"返回", "送出"})
                    .setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            if (position == 1) {
                                int index = tmpList.indexOf((String) view.getTag());
                                tmpList.remove((String) view.getTag());
                                adapter.notifyItemRemoved(index);
                                removeListData(index);
                                setListCount();
                            }
                        }
                    })
                    .build()
                    .addExtView(extView.getView())
                    .setCancelable(true)
                    .show();
        }
    }

    class ProcessingListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Object o = view.getTag();
            Log.d(TAG, o + "");
            int index = tmpList.indexOf((String) view.getTag());
            tmpList.remove((String) view.getTag());
            adapter.notifyItemRemoved(index);
            processingList.add(0, untreatedList.get(index));
            untreatedList.remove(index);
            setListCount();
        }
    }

    class FailureListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int index = tmpList.indexOf((String) view.getTag());
            tmpList.remove((String) view.getTag());
            adapter.notifyItemRemoved(index);
            removeListData(index);
            setListCount();
        }
    }

    class CanFetchListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int index = tmpList.indexOf((String) view.getTag());
            tmpList.remove((String) view.getTag());
            adapter.notifyItemRemoved(index);
            switch (TAB_TYPE) {
                case UNTREATED:
                    canFetchList.add(0, untreatedList.get(index));
                    // TODO 改 remove By indexOf Object
                    untreatedList.remove(index);
                    break;
                case PROCESSING:
                    canFetchList.add(0, processingList.get(index));
                    break;
            }
            processingList.remove(view.getTag());
            setListCount();
        }
    }

    class FinishListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int index = tmpList.indexOf((String) view.getTag());
            tmpList.remove((String) view.getTag());
            adapter.notifyItemRemoved(index);
            removeListData(index);
            setListCount();
        }
    }

    class TabClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view instanceof TextView) {
                switch (view.getId()) {
                    case R.id.untreatedText:
                        TAB_TYPE = TabType.UNTREATED;
                        break;
                    case R.id.processingText:
                        TAB_TYPE = TabType.PROCESSING;
                        break;
                    case R.id.canFetchText:
                        TAB_TYPE = TabType.CAN_FETCH;
                        break;
                }
                changeTab((TextView) view);
            }
        }

        private void changeTab(TextView textView) {
            List<TextView> views = Lists.newArrayList(untreatedText, processingText, canFetchText);
            for (TextView tv : views) {
                tv.setTextColor(getResources().getColor(android.R.color.black));
                tv.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                if (tv.equals(textView)) {
                    tv.setBackgroundColor(getResources().getColor(R.color.naber_basis));
                    tv.setTextColor(getResources().getColor(android.R.color.white));
                }
            }

            tmpList.clear();
            switch (TAB_TYPE) {
                case UNTREATED:
                    tmpList.addAll(untreatedList);
                    break;
                case PROCESSING:
                    tmpList.addAll(processingList);
                    break;
                case CAN_FETCH:
                    tmpList.addAll(canFetchList);
                    break;
            }
            adapter.notifyDataSetChanged();
        }
    }

}
