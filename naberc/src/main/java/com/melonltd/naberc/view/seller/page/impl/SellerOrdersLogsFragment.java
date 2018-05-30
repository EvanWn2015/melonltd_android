package com.melonltd.naberc.view.seller.page.impl;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.common.collect.Lists;
import com.melonltd.naberc.R;
import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;
import com.melonltd.naberc.view.common.factory.PageFragmentFactory;
import com.melonltd.naberc.view.common.type.PageType;
import com.melonltd.naberc.view.seller.SellerMainActivity;
import com.melonltd.naberc.view.seller.adapter.OrdersLogsAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class SellerOrdersLogsFragment extends AbsPageFragment implements View.OnClickListener {
    private static final String TAG = SellerOrdersLogsFragment.class.getSimpleName();
    private static SellerOrdersLogsFragment FRAGMENT = null;

    private TextView startTimeText, endTimeText;
    private BGARefreshLayout bgaRefreshLayout;
    private List<String> listData = Lists.newArrayList();
    private OrdersLogsAdapter adapter;
    private RecyclerView recyclerView;

    public static int TO_ORDERS_LOGS_DETAIL_INDEX = -1;

    public SellerOrdersLogsFragment() {
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new SellerOrdersLogsFragment();
        }
        FRAGMENT.setArguments(bundle);
        return FRAGMENT;
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new SellerOrdersLogsFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new OrdersLogsAdapter(listData);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.seller_orders_logs_page) == null) {
            View v = inflater.inflate(R.layout.fragment_seller_orders_logs, container, false);
            getViews(v);
            setListener();
            container.setTag(R.id.seller_orders_logs_page, v);
            return v;
        }
        return (View) container.getTag(R.id.seller_orders_logs_page);
    }

    private void getViews(View v) {
        bgaRefreshLayout = v.findViewById(R.id.sellerOrdersLogsBGARefreshLayout);
        recyclerView = v.findViewById(R.id.sellerOrdersLogsRecyclerView);
        startTimeText = v.findViewById(R.id.startTimeText);
        endTimeText = v.findViewById(R.id.endTimeText);

        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(getContext(), true);
        refreshViewHolder.setPullDownRefreshText("Pull");
        refreshViewHolder.setRefreshingText("Pull to refresh");
        refreshViewHolder.setReleaseRefreshText("Pull to refresh");
        refreshViewHolder.setLoadingMoreText("Loading more !");

        bgaRefreshLayout.setRefreshViewHolder(refreshViewHolder);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

    }

    private void setListener() {
        startTimeText.setOnClickListener(this);
        endTimeText.setOnClickListener(this);
        adapter.setClickListener(new ItemStatusOnClickListener(), new ItemOnClickListener());
        recyclerView.setAdapter(adapter);
        bgaRefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                bgaRefreshLayout.endRefreshing();
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                bgaRefreshLayout.endLoadingMore();
                return false;
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();

        for (int i = 0; i < 10; i++) {
            listData.add("item" + i);
        }
        adapter.notifyDataSetChanged();
        if (SellerMainActivity.toolbar != null) {
            SellerMainActivity.navigationIconDisplay(true, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    backToSellerStatPage();
                    SellerMainActivity.navigationIconDisplay(false, null);
                }
            });
        }

        if (TO_ORDERS_LOGS_DETAIL_INDEX >= 0) {
            toOrderLogsDetailPag(TO_ORDERS_LOGS_DETAIL_INDEX);
        }
    }

    private void toOrderLogsDetailPag(int index) {
        BaseCore.FRAGMENT_TAG = PageType.SELLER_ORDERS_LOGS_DETAIL.name();
        TO_ORDERS_LOGS_DETAIL_INDEX = index;
        AbsPageFragment f = PageFragmentFactory.of(PageType.SELLER_ORDERS_LOGS_DETAIL, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.sellerFrameContainer, f).commit();
    }

    private void backToSellerStatPage() {
        BaseCore.FRAGMENT_TAG = PageType.SELLER_STAT.name();
        SellerStatFragment.TO_SELLER_ORDERS_LOGS_INDEX = -1;
        AbsPageFragment f = PageFragmentFactory.of(PageType.SELLER_STAT, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.sellerFrameContainer, f).commit();
    }

    @Override
    public void onStop() {
        super.onStop();
        SellerMainActivity.navigationIconDisplay(false, null);
    }

    @Override
    public void onClick(final View view) {
        final int viewId = view.getId();
        switch (view.getId()) {
            case R.id.startTimeText:
            case R.id.endTimeText:
                long oneYears = 1L * 365 * 1000 * 60 * 60 * 24L;
                Calendar now = Calendar.getInstance();
                Calendar startDate = Calendar.getInstance();
                startDate.setTimeInMillis(now.getTime().getTime() - oneYears);
                TimePickerView tp = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        Log.d(TAG, date.toString());
                        Log.d(TAG, "");
                        if (viewId == R.id.startTimeText) {
                            startTimeText.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));
                        } else if (viewId == R.id.endTimeText) {
                            endTimeText.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));
                        }
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})//"year","month","day","hours","minutes","seconds "
                        .setTitleSize(20)//标题文字大小
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(false)//是否循环滚动
                        .setTitleBgColor(getResources().getColor(R.color.naber_dividing_line_gray))
                        .setCancelColor(getResources().getColor(R.color.naber_dividing_gray))
                        .setSubmitColor(getResources().getColor(R.color.naber_dividing_gray))
                        .setRangDate(startDate, now)//起始终止年月日设定
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .isDialog(false)//是否显示为对话框样式
                        .build();
                tp.show();
//                new TimePickerDialog.Builder()
//                        .setTitleStringId("")
//                        .setTitleStringId("請選擇")
//                        .setYearText(getResources().getString(R.string.data_time_picker_years_text))
//                        .setMonthText(getResources().getString(R.string.data_time_picker_month_text))
//                        .setDayText(getResources().getString(R.string.data_time_picker_day_text))
//                        .setCyclic(false)
//                        .setToolBarTextColor(getResources().getColor(R.color.naber_basis_blue))
//                        .setMinMillseconds(System.currentTimeMillis() - oneYears)
//                        .setMaxMillseconds(System.currentTimeMillis())
//                        .setCurrentMillseconds(System.currentTimeMillis())
//                        .setThemeColor(getResources().getColor(R.color.naber_dividing_line_gray))
//                        .setType(Type.YEAR_MONTH_DAY)
//                        .setWheelItemTextNormalColor(getResources().getColor(R.color.naber_dividing_line_gray))
//                        .setWheelItemTextSize(16)
//                        .setCallBack(new OnDateSetListener() {
//                            @Override
//                            public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
//                                Log.d(TAG, "");
//                                if (viewId == R.id.startTimeText) {
//                                    startTimeText.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date(millseconds)));
//                                } else if (viewId == R.id.endTimeText) {
//                                    endTimeText.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date(millseconds)));
//                                }
//
//                            }
//                        })
//                        .build().show(getFragmentManager(), "YEAR_MONTH_DAY");
                break;

        }
    }


    class ItemStatusOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Log.d(TAG, view.getTag() + "");
        }
    }

    class ItemOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int index = listData.indexOf(view.getTag());
            toOrderLogsDetailPag(index);
        }
    }

}
