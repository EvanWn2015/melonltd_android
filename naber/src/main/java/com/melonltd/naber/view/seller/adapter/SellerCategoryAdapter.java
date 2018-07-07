package com.melonltd.naber.view.seller.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.melonltd.naber.R;
import com.melonltd.naber.model.bean.Model;
import com.melonltd.naber.view.common.HideKeyboardListener;
import com.melonltd.naber.view.customize.SwitchButton;

public class SellerCategoryAdapter extends RecyclerView.Adapter<SellerCategoryAdapter.ViewHolder> {
    private SwitchButton.OnCheckedChangeListener aSwitchListener;
    private View.OnClickListener deleteListener, editListener;
    private View.OnClickListener hideKeyboardListener = new HideKeyboardListener();

    public SellerCategoryAdapter(SwitchButton.OnCheckedChangeListener aSwitchListener, View.OnClickListener editListener, View.OnClickListener deleteListener) {
        this.aSwitchListener = aSwitchListener;
        this.editListener = editListener;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_category_edit_items, parent, false);
        SellerCategoryAdapter.ViewHolder vh = new SellerCategoryAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.v.setOnClickListener(this.hideKeyboardListener);

        holder.categoryText.setText(Model.SELLER_CATEGORY_LIST.get(position).category_name);
        holder.setTag(position);

        holder.aSwitch.setChecked(Model.SELLER_CATEGORY_LIST.get(position).status.getStatus());
        holder.aSwitch.setOnCheckedChangeListener(this.aSwitchListener);
        holder.editBtn.setOnClickListener(this.editListener);
        holder.deleteBtn.setOnClickListener(this.deleteListener);
    }

    @Override
    public int getItemCount() {
        return Model.SELLER_CATEGORY_LIST.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryText;
        private Button editBtn, deleteBtn;
        private SwitchButton aSwitch;
        private View v;

        ViewHolder(View v) {
            super(v);
            this.v = v;
            this.categoryText = v.findViewById(R.id.categoryText);
            this.editBtn = v.findViewById(R.id.editBtn);
            this.deleteBtn = v.findViewById(R.id.deleteBtn);
            this.aSwitch = v.findViewById(R.id.aSwitch);
        }

        public void setTag(int position){
            this.aSwitch.setTag(position);
            this.editBtn.setTag(position);
            this.deleteBtn.setTag(position);
        }
    }

}
