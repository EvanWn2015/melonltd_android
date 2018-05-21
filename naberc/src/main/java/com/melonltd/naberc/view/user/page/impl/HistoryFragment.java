package com.melonltd.naberc.view.user.page.impl;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.common.collect.Lists;
import com.melonltd.naberc.R;
import com.melonltd.naberc.view.user.UserMainActivity;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;
import com.melonltd.naberc.view.common.factory.PageFragmentFactory;
import com.melonltd.naberc.view.common.type.PageType;

import java.util.ArrayList;

public class HistoryFragment extends AbsPageFragment {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private static HistoryFragment FRAGMENT = null;
    private ArrayList<String> list = Lists.newArrayList();
    public static int TO_ORDER_DETAIL_INDEX = -1;

    public HistoryFragment() {
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new HistoryFragment();
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new HistoryFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.user_history_page) == null) {
            View v = inflater.inflate(R.layout.fragment_history, container, false);
            setUpHistoryListView(v);
            container.setTag(R.id.user_history_page, v);
            return v;
        } else {
            return (View) container.getTag(R.id.user_history_page);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (TO_ORDER_DETAIL_INDEX >= 0) {
            toDetail(TO_ORDER_DETAIL_INDEX);
        }
    }

    private void setUpHistoryListView(View v) {
        ListView historyListView = v.findViewById(R.id.historyListView);
        HistoryAdapter adapter = new HistoryAdapter(getContext(), list);
        historyListView.setAdapter(adapter);
        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TO_ORDER_DETAIL_INDEX = i;
                toDetail(TO_ORDER_DETAIL_INDEX);
            }
        });
    }

    private void getData() {
        for (int i = 0; i < 100; i++) {
            list.add("" + i);
        }
    }

    private void toDetail(int resultIndex) {
        Bundle b = new Bundle();
        b.putString("test", list.get(resultIndex));
        UserMainActivity.FRAGMENT_TAG = PageType.ORDER_DETAIL.name();
        AbsPageFragment f = PageFragmentFactory.of(PageType.ORDER_DETAIL, b);
        getFragmentManager().beginTransaction().replace(R.id.frameContainer, f).commit();
    }


    class HistoryAdapter extends BaseAdapter {
        private LayoutInflater inflater = null;
        private ArrayList<String> list;

        public HistoryAdapter(Context context, ArrayList list) {
            this.list = list;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            HistoryItem item = null;
            if (view == null) {
                item = HistoryItem.valueOf();
                view = inflater.inflate(R.layout.history_item, null);
                item.title = view.findViewById(R.id.restaurantNameText);
                view.setTag(item);
            } else {
                item = (HistoryItem) view.getTag();
            }

            // TODO item view
            item.title.setText("營業時間 10:00~ 11:0" + list.get(i));
            return view;
        }

        @Nullable
        @Override
        public CharSequence[] getAutofillOptions() {
            return new CharSequence[0];
        }

        public void setData(ArrayList<String> list) {
            this.list = list;
            this.notifyDataSetChanged();
        }
    }

    static class HistoryItem {
        TextView title;

        public static HistoryItem valueOf() {
            return new HistoryItem();
        }
    }

}
