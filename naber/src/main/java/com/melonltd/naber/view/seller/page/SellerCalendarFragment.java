package com.melonltd.naber.view.seller.page;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.google.common.collect.Lists;
import com.melonltd.naber.R;
import com.melonltd.naber.util.Tools;
import com.melonltd.naber.view.customize.SwitchButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SellerCalendarFragment extends Fragment {
    private String TAG = SellerCalendarFragment.class.getSimpleName();
    private SellerCalendarFragment FRAGMENT = null;
    private GridView gridView;
    private List<String> list = Lists.newArrayList();
    private BaseAdapter adapter;
    private Bundle bundle;


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_seller_calendar, container, false);
        getViews(v);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Date now = new Date();
        long day = 1000 * 60 * 60 * 24L;
        SimpleDateFormat format = new SimpleDateFormat("MM/dd");
        for(int i = 0;i<30; i++){
            String date = Tools.FORMAT.formatStartDate(now, "T00:00:00.0000Z");
            list.add(date);
            now.setTime(now.getTime() + day);
        }
//        Log.i(TAG,list+"");
        adapter = new CalendarAdapter(list,getContext());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
    private void getViews(View v){
        gridView = v.findViewById(R.id.daysGrid);
        gridView.setAdapter(new CalendarAdapter(list,getContext()));

    }

    class CalendarAdapter extends BaseAdapter{
        private List<String> list;
        private Context context;
        CalendarAdapter(List<String> list, Context context){
            this.list = list;
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(this.context);
            textView.setText(list.get(position));
            return textView;
        }
    }
}
