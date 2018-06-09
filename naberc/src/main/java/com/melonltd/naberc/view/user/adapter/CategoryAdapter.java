package com.melonltd.naberc.view.user.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.melonltd.naberc.R;

import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private static final String TAG = CategoryAdapter.class.getSimpleName();
    private List<String> listData;
    private View.OnClickListener itemClickListener;


    public CategoryAdapter(List<String> listData) {
        this.listData = listData;
    }

    public void setItemClickListener(View.OnClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_category_item, parent, false);
        CategoryAdapter.ViewHolder vh = new CategoryAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(this.itemClickListener);
        holder.categoryText.setTag(position);
        holder.categoryText.setText("XXX " + position);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryText;

        public ViewHolder(View v) {
            super(v);
            categoryText = v.findViewById(R.id.categoryText);
        }
    }
}
//public class CategoryAdapter extends BaseAdapter {
//    private LayoutInflater inflater;
//    private List<String> list;
//
//    public CategoryAdapter(Context context, List list) {
//        this.inflater = LayoutInflater.from(context);
//        this.list = list;
//    }
//
//    @Override
//    public int getCount() {
//        return list.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return list.get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return i;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        CategoryItem item = null;
//        if (view == null) {
//            view = inflater.inflate(R.layout.user_category_item, null);
//            item = CategoryItem.valueOf(view);
//            view.setTag(item);
//        } else {
//            item = (CategoryItem) view.getTag();
//        }
//        item.categoryText.setText("XXX " + i);
//        return view;
//    }
//
//    @Nullable
//    @Override
//    public CharSequence[] getAutofillOptions() {
//        return new CharSequence[0];
//    }
//
//    static class CategoryItem {
//        private TextView categoryText;
//
//        public static CategoryItem valueOf(View v) {
//            CategoryItem item = new CategoryItem();
//            item.categoryText = v.findViewById(R.id.categoryText);
//            return item;
//        }
//    }
//}



