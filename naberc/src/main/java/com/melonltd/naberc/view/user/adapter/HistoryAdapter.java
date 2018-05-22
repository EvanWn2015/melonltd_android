package com.melonltd.naberc.view.user.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.melonltd.naberc.R;

import java.util.ArrayList;

public class HistoryAdapter extends BaseAdapter {
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
            view = inflater.inflate(R.layout.user_history_item, null);
            item = HistoryItem.valueOf(view);
            view.setTag(item);
        } else {
            item = (HistoryItem) view.getTag();
        }

        // TODO item view
        item.restaurantNameText.setText("營業時間 10:00~ 11:0" + list.get(i));
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

    static class HistoryItem {
        TextView restaurantNameText, totalAmountText, orderStatusText, getOrderTimeText;

        public static HistoryItem valueOf(View v) {
            HistoryItem item = new HistoryItem();
            item.restaurantNameText = v.findViewById(R.id.restaurantNameText);
            item.totalAmountText = v.findViewById(R.id.totalAmountText);
            item.orderStatusText = v.findViewById(R.id.orderStatusText);
            item.getOrderTimeText = v.findViewById(R.id.getOrderTimeText);
            return item;
        }
    }
}

