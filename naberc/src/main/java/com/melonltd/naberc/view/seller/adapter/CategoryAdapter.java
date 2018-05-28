package com.melonltd.naberc.view.seller.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.melonltd.naberc.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<String> listData;
    private CompoundButton.OnCheckedChangeListener aSwitchListener;
    private View.OnClickListener deleteListener, editListener;

    public CategoryAdapter(List<String> listData) {
        this.listData = listData;
    }

    public void setListener(CompoundButton.OnCheckedChangeListener aSwitchListener, View.OnClickListener editListener, View.OnClickListener deleteListener) {
        this.aSwitchListener = aSwitchListener;
        this.editListener = editListener;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_category_edit_items, parent, false);
        CategoryAdapter.ViewHolder vh = new CategoryAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.categoryText.setText("test :: " + position);
        holder.aSwitch.setTag(listData.get(position));
        holder.editBtn.setTag(listData.get(position));
        holder.deleteBtn.setTag(listData.get(position));

        holder.aSwitch.setOnCheckedChangeListener(this.aSwitchListener);
        holder.editBtn.setOnClickListener(this.editListener);
        holder.deleteBtn.setOnClickListener(this.deleteListener);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryText;
        private Button editBtn, deleteBtn;
        private Switch aSwitch;

        ViewHolder(View v) {
            super(v);
            categoryText = v.findViewById(R.id.categoryText);
            editBtn = v.findViewById(R.id.editBtn);
            deleteBtn = v.findViewById(R.id.deleteBtn);
            aSwitch = v.findViewById(R.id.aSwitch);
        }
    }

}
