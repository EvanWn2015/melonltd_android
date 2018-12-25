package com.melonltd.naber.view.seller.page;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.melonltd.naber.R;
import com.melonltd.naber.model.api.ApiManager;
import com.melonltd.naber.model.api.ThreadCallback;
import com.melonltd.naber.model.type.SwitchStatus;
import com.melonltd.naber.util.DensityUtil;
import com.melonltd.naber.util.Tools;
import com.melonltd.naber.view.factory.PageType;
import com.melonltd.naber.view.seller.SellerMainActivity;
import com.melonltd.naber.vo.RestaurantInfoVo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class SellerCalendarFragment extends Fragment {
    private String TAG = SellerCalendarFragment.class.getSimpleName();
    private SellerCalendarFragment FRAGMENT = null;
    private List<CalendarDate> list = Lists.<CalendarDate>newArrayList();
    private CalendarAdapter adapter;
    private int RADIO_BUTTON_WIDTH = 0,CELL_SIZE = 0;
    private List<String> not_business = Lists.newArrayList();
//    refreshLayout

    public SellerCalendarFragment() {
        // Required empty public constructor
    }

    public Fragment getInstance(Bundle bundle){
        if (FRAGMENT == null) {
            FRAGMENT = new SellerCalendarFragment();
        }
        if (bundle != null) {
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_seller_calendar, container, false);
        getViews(v);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RADIO_BUTTON_WIDTH = SellerMainActivity.LAYOUT_WIDTH;
        CELL_SIZE = (RADIO_BUTTON_WIDTH - DensityUtil.dip2px(getContext(),16.0f)) / 7;
        int dayOfWeek = Calendar.getInstance(TimeZone.getTimeZone("Asia/Taipei"), Locale.TAIWAN).get(Calendar.DAY_OF_WEEK);
        for(int i = 0; i < dayOfWeek - 1; i++){
            list.add(CalendarDate.of("", null));
        }

        Date now = new Date();
        long day = 1000 * 60 * 60 * 24L;
        for(int i = 0; i < 30; i++){
            String date = Tools.FORMAT.formatStartDate(now, "T00:00:00.0000Z");
            list.add(CalendarDate.of(date, now));
            now = new Date(now.getTime() + day);

        }
        adapter = new CalendarAdapter(list, new ClickItemListener());
    }

    @Override
    public void onResume() {
        super.onResume();

        if (SellerMainActivity.toolbar != null) {
            SellerMainActivity.navigationIconDisplay(true, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SellerSetUpFragment.TO_SELLER_SIMPLE_INFO_INDEX = -1;
                    SellerMainActivity.removeAndReplaceWhere(FRAGMENT, PageType.SELLER_SET_UP, null);
                    SellerMainActivity.navigationIconDisplay(false, null);

                }
            });
        }
        this.doLoadData(true);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void getViews(View v){


        final BGARefreshLayout refreshLayout = v.findViewById(R.id.refreshLayout);

        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);

        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(getContext(), true);
        refreshViewHolder.setPullDownRefreshText("Pull");
        refreshViewHolder.setRefreshingText("Pull to refresh");
        refreshViewHolder.setReleaseRefreshText("Pull to refresh");
        refreshViewHolder.setLoadingMoreText("Loading more !");

        refreshLayout.setRefreshViewHolder(refreshViewHolder);

        GridLayoutManager mgr = new GridLayoutManager(getContext(),7);

        recyclerView.setLayoutManager(mgr);
        RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration(){
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.left = 5;
                outRect.right = 5;
                outRect.top = 5;
                outRect.bottom = 5;
            }
        };

        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);


        refreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                refreshLayout.endRefreshing();
                doLoadData(true);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                refreshLayout.endLoadingMore();
                return false;
            }
        });

    }


    private void  doLoadData(boolean isRefresh) {
        not_business.clear();
        adapter.notifyDataSetChanged();
        ApiManager.sellerRestaurantInfo(new ThreadCallback(getContext()) {
            @Override
            public void onSuccess(String responseBody) {
                RestaurantInfoVo restaurant = Tools.JSONPARSE.fromJson(responseBody, RestaurantInfoVo.class);
                not_business.addAll(restaurant.not_business);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(Exception error, String msg) {
            }
        });

    }


    class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {
        private List<CalendarDate> list;
        private ClickItemListener clickItemListener;
        int dayOfMonth,monthDay,dayOfWeek;
        private  Context context;

        CalendarAdapter(List<CalendarDate> list, ClickItemListener clickItemListener){
            this.list = list;
            this.clickItemListener = clickItemListener;
            dayOfMonth = Calendar.getInstance(TimeZone.getTimeZone("Asia/Taipei"), Locale.TAIWAN).get(Calendar.DAY_OF_MONTH);
            monthDay = Calendar.getInstance(TimeZone.getTimeZone("Asia/Taipei"), Locale.TAIWAN).getMaximum(Calendar.DAY_OF_MONTH);
            dayOfWeek = Calendar.getInstance(TimeZone.getTimeZone("Asia/Taipei"), Locale.TAIWAN).get(Calendar.DAY_OF_WEEK);
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            this.context = parent.getContext();
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_seller_calendar_detail, parent, false);
            ViewGroup.LayoutParams lp = v.getLayoutParams();
            lp.height = CELL_SIZE - 5;
            lp.width = CELL_SIZE - 5;
            v.setLayoutParams(lp);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder h, int position) {
            CalendarDate cDate = list.get(position);

            if (cDate.date == null || Strings.isNullOrEmpty(cDate.day)) {
                h.textView.setText("");
                h.view.setBackgroundResource(0);
                return;
            }else {
                h.textView.setText(Tools.FORMAT.formatDate(cDate.date, "dd"));
                h.view.setTag(position);
                h.view.setOnClickListener(this.clickItemListener);
            }

            h.view.setBackgroundResource(0);
            h.textView.setTextColor(context.getResources().getColor(R.color.naber_tab_default_color));

            if(position >= (monthDay - (dayOfMonth - dayOfWeek))){
                h.view.setBackgroundResource(R.drawable.naber_gray_button_style);
                h.textView.setTextColor(Color.WHITE);
            }

            if(position % 7 == 0){
                h.textView.setTextColor(context.getResources().getColor(R.color.naber_basis_red));
            }

            if((position + 1) % 7 == 0){
                h.textView.setTextColor(context.getResources().getColor(R.color.naber_basis_blue));
            }

            if (not_business.contains(cDate.day)) {
                h.view.setBackgroundResource(R.drawable.naber_red_button_style);
                h.textView.setTextColor(Color.WHITE);
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView textView;
            private View view;
            public ViewHolder(View v) {
                super(v);
                this.view = v;
                this.textView = v.findViewById(R.id.daysText);
            }
        }
    }


    static class CalendarDate {
        private String day;
        private Date date;

        CalendarDate (String day, Date date){
            this.day = day;
            this.date = date;
        }

        public static CalendarDate of (String day, Date date){
            return new CalendarDate(day, date);
        }

        @Override
        public String toString() {
            return "[day: "+ day + "," + "date: " + date + "]";
        }
    }


    class ClickItemListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            int position = (int)v.getTag();
            final CalendarDate cDate = list.get(position);
            if (not_business.contains(cDate.day)) {
                new Handler().postDelayed(new SettingBusinessRun(position, cDate.day, true), 300);
            }else {
                new AlertView.Builder()
                        .setTitle("")
                        .setMessage("關閉營業時段，請先確認該日有無訂單，\n若有訂單請記得告知使用者")
                        .setContext(getContext())
                        .setStyle(AlertView.Style.Alert)
                        .setOthers(new String[]{"確定關閉", "取消"})
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                if (position == 0) {
                                    new Handler().postDelayed(new SettingBusinessRun(position, cDate.day, false), 300);
                                }
                            }
                        })
                        .build()
                        .setCancelable(false)
                        .show();
            }
        }
    }



    class SettingBusinessRun implements Runnable {
        private int position;
        private String date;
        private SwitchStatus status;

        SettingBusinessRun(int position, String date, boolean isChecked) {
            this.position = position;
            this.date = date;
            this.status = SwitchStatus.of(isChecked);
        }

        @Override
        public void run() {
            Map<String, String> req = Maps.newHashMap();
            req.put("date", this.date);
            req.put("status", this.status.name());
            ApiManager.sellerRestaurantSettingBusiness(req, new ThreadCallback(getContext()) {
                @Override
                public void onSuccess(String responseBody) {
                    if(status.getStatus()){
                        not_business.remove(date);
                    }else {
                        not_business.add(date);
                    }
//                    adapter.notifyItemChanged(position);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFail(Exception error, String msg) {
                    Log.i(TAG, msg);
                }
            });

        }
    }
}
