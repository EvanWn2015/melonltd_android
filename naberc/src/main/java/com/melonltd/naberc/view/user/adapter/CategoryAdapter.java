package com.melonltd.naberc.view.user.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.melonltd.naberc.R;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<String> list;

    public CategoryAdapter(Context context, List list) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
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
        CategoryItem item = null;
        if (view == null) {
            view = inflater.inflate(R.layout.user_category_item, null);
            item = CategoryItem.valueOf(view);
            view.setTag(item);
        } else {
            item = (CategoryItem) view.getTag();
        }
        item.categoryText.setText("XXX " + i);
        return view;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }

    static class CategoryItem {
        private TextView categoryText;

        public static CategoryItem valueOf(View v) {
            CategoryItem item = new CategoryItem();
            item.categoryText = v.findViewById(R.id.categoryText);
            return item;
        }
    }
}



