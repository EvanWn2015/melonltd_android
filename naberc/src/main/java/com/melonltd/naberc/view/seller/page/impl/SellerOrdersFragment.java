package com.melonltd.naberc.view.seller.page.impl;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.common.collect.Lists;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.melonltd.naberc.R;
import com.melonltd.naberc.model.helper.okhttp.ApiCallback;
import com.melonltd.naberc.model.helper.okhttp.ApiManager;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;
import com.melonltd.naberc.view.seller.adapter.SellerOrdersAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class SellerOrdersFragment extends AbsPageFragment implements View.OnClickListener {
    private static final String TAG = SellerOrdersFragment.class.getSimpleName();
    private static SellerOrdersFragment FRAGMENT = null;
    private TextView searchDateText;

    private TextView untreatedText, processingText, canFetchText;
    private TextView untreatedSumText, processingSumText, canFetchSumText;


    private LinearLayoutManager layoutManager;
    private SellerOrdersAdapter adapter;
    private RecyclerView ordersRecyclerView;
    private List<String> ordersList = Lists.newArrayList();


    public static TabType TAB_TYPE = TabType.UNTREATED;

    public enum TabType {
        UNTREATED, PROCESSING, CAN_FETCH
    }


    private Handler dataHandler = new Handler();

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
        adapter = new SellerOrdersAdapter(ordersList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_seller_orders, container, false);
        getViews(v);
        setListener();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        testLoadData(true);
        dataHandler.post(pushData);
    }

    Runnable pushData = new Runnable() {
        @Override
        public void run() {
            ApiManager.test(new ApiCallback(getContext()) {
                @Override
                public void onSuccess(String responseBody) {
                    ordersList.add(0, "new Data");
                    testLoadData(false);
                    untreatedSumText.setText(ordersList.size() + "");
                }

                @Override
                public void onFail(Exception error) {
                }
            });
//            dataHandler.postDelayed(pushData, 3000);
        }
    };

    private void testLoadData(boolean isRefresh) {
        if (isRefresh) {
            ordersList.clear();
            adapter.notifyDataSetChanged();
            for (int i = 0; i < 10; i++) {
                ordersList.add("訂單" + (i + 1));
            }
        }
        adapter.notifyDataSetChanged();
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
        searchDateText.setOnClickListener(this);
        untreatedText.setOnClickListener(new TabClickListener());
        processingText.setOnClickListener(new TabClickListener());
        canFetchText.setOnClickListener(new TabClickListener());

//        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ordersRecyclerView.setLayoutManager(layoutManager);
        ordersRecyclerView.setAdapter(adapter);
        adapter.setOnClickListeners(new CancelListener(), new ProcessingListener(), new FailureListener(), new CanFetchListener(), new FinishListener());
    }

    private void filterByPhoneNmber(String number) {

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.searchDateText:
                long date = 1000 * 60 * 60 * 24 * 2L;
                new TimePickerDialog.Builder()
                        .setTitleStringId("")
                        .setTitleStringId("請選擇")
                        .setYearText(getResources().getString(R.string.data_time_picker_years_text))
                        .setMonthText(getResources().getString(R.string.data_time_picker_month_text))
                        .setDayText(getResources().getString(R.string.data_time_picker_day_text))
                        .setCyclic(false)
                        .setToolBarTextColor(getResources().getColor(R.color.naber_basis_blue))
                        .setMinMillseconds(System.currentTimeMillis())
                        .setMaxMillseconds(System.currentTimeMillis() + date)
                        .setCurrentMillseconds(System.currentTimeMillis())
                        .setThemeColor(getResources().getColor(R.color.naber_dividing_line_gray))
                        .setType(Type.YEAR_MONTH_DAY)
                        .setWheelItemTextNormalColor(getResources().getColor(R.color.naber_dividing_line_gray))
                        .setWheelItemTextSize(16)
                        .setCallBack(new OnDateSetListener() {
                            @Override
                            public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                                searchDateText.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date(millseconds)));
                                searchByDate(millseconds);
                            }
                        })
                        .build().show(getFragmentManager(), "YEAR_MONTH_DAY");
                break;

        }
    }

    class CancelListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            dataHandler.removeCallbacks(pushData);
            int index = ordersList.indexOf((String)view.getTag());
            Log.d(TAG, "CancelListener Tag :: " + index);
            ordersList.remove((String)view.getTag());
            Log.d(TAG, "ordersList size :: " + ordersList.size());
            adapter.notifyItemRemoved(index);
//            adapter.notifyItemRangeChanged(index, ordersList.size());
//            adapter.notifyItemRemoved(index);
        }
    }


    class ProcessingListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

        }
    }

    class FailureListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

        }
    }

    class CanFetchListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

        }
    }

    class FinishListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

        }
    }

//    private void changVolume(final int volume) {
//        int first = layoutManager.findFirstVisibleItemPosition();
//        int last = layoutManager.findLastVisibleItemPosition();
//
//        if (position >= first && position <= last) {
//            View view = ordersRecyclerView.getChildAt(position - first);
//            if (ordersRecyclerView.getChildViewHolder(view) instanceof SpeakContentAdapter.SpeakContentHolder) {
//                //修改数据
//                ProgressImageView progressImageView = (ProgressImageView) view.findViewById(R.id.speak_item_record);
//                progressImageView.setProgress(volume);
//            }
//        }
//    }

    class TabClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view instanceof TextView) {
                changeTab((TextView) view);
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
                testLoadData(true);
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
        }
    }

//    class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {
//        private List<String> list;
//        private View.OnClickListener cancelListener, processingListener, failureListener, canFetchListener, finishListener;
//
//        OrdersAdapter(Context context, List<String> list) {
//            this.list = list;
//        }
//
//        private void setOnClickListeners(View.OnClickListener cancelListener, View.OnClickListener processingListener, View.OnClickListener failureListener, View.OnClickListener canFetchListener, View.OnClickListener finishListener) {
//            this.cancelListener = cancelListener;
//            this.processingListener = processingListener;
//            this.failureListener = failureListener;
//            this.canFetchListener = canFetchListener;
//            this.finishListener = finishListener;
//        }
//
//        @NonNull
//        @Override
//        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_orders_items, parent, false);
//            OrdersAdapter.ViewHolder vh = new OrdersAdapter.ViewHolder(v);
//            vh.setOnClickListeners(cancelListener, processingListener, failureListener, canFetchListener, finishListener);
//            return vh;
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//
//            String foodItems = "";
//            for (int i = 0; i <= position; i++) {
//                foodItems += Tools.MAKEUP.makeUpCharacter("機腿", 40, Tools.MakeUp.Direction.RIGHT)
//                        + Tools.MAKEUP.makeUpCharacter("x1", 5, Tools.MakeUp.Direction.RIGHT)
//                        + Tools.MAKEUP.makeUpCharacter("$10", 10, Tools.MakeUp.Direction.LEFT)
//                        + "\n";
//            }
//
//            holder.cancelBtn.setTag(position);
//            holder.foodItemsCountText.setText("(" + position + ")");
//            holder.foodItemsText.setText(foodItems);
//            holder.remarkText.setText(list.get(position));
//
//            holder.fetchTimeText.setText("2018-05-0" + position);
//            holder.userPhoneNumberText.setText("09876543" + position);
//            holder.userNameText.setText("某某" + position);
//            holder.totalAmountText.setText((position * 2) + "$");
//            holder.foodItemsText.setText(foodItems);
//
//            holder.processingBtn.setVisibility(View.GONE);
//            holder.canFetchBtn.setVisibility(View.GONE);
//            holder.finishBtn.setVisibility(View.GONE);
//            holder.failureBtn.setVisibility(View.GONE);
//
//            if (TAB_TYPE.equals(TabType.UNTREATED)) {
//                holder.canFetchBtn.setVisibility(View.VISIBLE);
//                holder.processingBtn.setVisibility(View.VISIBLE);
//            } else if (TAB_TYPE.equals(TabType.PROCESSING)) {
//                holder.canFetchBtn.setVisibility(View.VISIBLE);
//                holder.finishBtn.setVisibility(View.VISIBLE);
//            } else if (TAB_TYPE.equals(TabType.CAN_FETCH)) {
//                holder.failureBtn.setVisibility(View.VISIBLE);
//                holder.finishBtn.setVisibility(View.VISIBLE);
//            }
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return list.size();
//        }
//
//        class ViewHolder extends RecyclerView.ViewHolder {
//            private TextView foodItemsCountText, foodItemsText, remarkText, fetchTimeText, userPhoneNumberText, userNameText, totalAmountText;
//            private Button cancelBtn, failureBtn, processingBtn, canFetchBtn, finishBtn;
//
//            ViewHolder(View v) {
//                super(v);
//                foodItemsCountText = v.findViewById(R.id.foodItemsCountText);
//                foodItemsText = v.findViewById(R.id.foodItemsText);
//                remarkText = v.findViewById(R.id.remarkText);
//
//                cancelBtn = v.findViewById(R.id.cancelBtn);
//                processingBtn = v.findViewById(R.id.processingBtn);
//                failureBtn = v.findViewById(R.id.failureBtn);
//                canFetchBtn = v.findViewById(R.id.canFetchBtn);
//                finishBtn = v.findViewById(R.id.finishBtn);
//
//                fetchTimeText = v.findViewById(R.id.fetchTimeText);
//                userPhoneNumberText = v.findViewById(R.id.userPhoneNumberText);
//                userNameText = v.findViewById(R.id.userNameText);
//                totalAmountText = v.findViewById(R.id.totalAmountText);
//            }
//
//            private void setOnClickListeners(View.OnClickListener cancelListener, View.OnClickListener processingListener, View.OnClickListener failureListener, View.OnClickListener canFetchListener, View.OnClickListener finishListener) {
//                cancelBtn.setOnClickListener(cancelListener);
//                processingBtn.setOnClickListener(processingListener);
//                failureBtn.setOnClickListener(failureListener);
//                canFetchBtn.setOnClickListener(canFetchListener);
//                finishBtn.setOnClickListener(finishListener);
//            }
//        }
//    }
}
