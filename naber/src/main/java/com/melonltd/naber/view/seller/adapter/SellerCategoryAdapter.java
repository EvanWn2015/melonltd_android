package com.melonltd.naber.view.seller.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.util.NumberUtils;
import com.google.android.gms.flags.impl.DataUtils;
import com.google.common.base.Strings;
import com.google.common.primitives.Ints;
import com.melonltd.naber.R;
import com.melonltd.naber.model.bean.Model;
import com.melonltd.naber.view.common.HideKeyboardListener;
import com.melonltd.naber.view.customize.SwitchButton;

import java.text.NumberFormat;

public class SellerCategoryAdapter extends RecyclerView.Adapter<SellerCategoryAdapter.ViewHolder> {
    private static final String TAG = SellerCategoryAdapter.class.getSimpleName();
    private SwitchButton.OnCheckedChangeListener aSwitchListener;
    private View.OnClickListener deleteListener, editListener;
    private View.OnClickListener hideKeyboardListener = new HideKeyboardListener();

    private boolean IS_SORT_EDIT = false;

    public SellerCategoryAdapter(SwitchButton.OnCheckedChangeListener aSwitchListener, View.OnClickListener editListener, View.OnClickListener deleteListener) {
        this.aSwitchListener = aSwitchListener;
        this.editListener = editListener;
        this.deleteListener = deleteListener;
    }

    // TODO 新增可否編輯排序，用法
    // 開啟編輯 adapter.setSortEdit(true).notifyDataSetChanged();
    // 關閉編輯 adapter.setSortEdit(false).notifyDataSetChanged();
    public SellerCategoryAdapter setSortEdit(boolean isSortEdit){
        this.IS_SORT_EDIT = isSortEdit;
        return this;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_category_edit_items, parent, false);
        SellerCategoryAdapter.ViewHolder vh = new SellerCategoryAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,final int position) {
        holder.v.setOnClickListener(this.hideKeyboardListener);
        holder.topEdit.setText(Model.SELLER_CATEGORY_LIST.get(position).top + "");


        holder.topEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.i(TAG,v.toString());
            }
        });

        holder.topEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i(TAG,s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(TAG,s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, s.toString());

                Integer tmp = Ints.tryParse(s.toString());
                Log.i(TAG, tmp.toString());


                if (s.toString().length() >= 2){

                }else if (s.toString().length() >= 1){

                }else {

                }

//                if (Strings.isNullOrEmpty(s.toString())){
//                    Model.SELLER_CATEGORY_LIST.get(position).top = tmp;
//                    s.clear();
//                    s.append("0");
////                    notifyItemChanged(position);
//                }else if (s.toString().length() >= 2){
//                    tmp = Ints.tryParse(s.toString());
//                    Model.SELLER_CATEGORY_LIST.get(position).top = tmp;
//                    s.clear();
////                    s.toString().toCharArray();
//                    char[] cs = tmp.toString().toCharArray();
//                    for (int i=0; i<cs.length ; i++){
//                        s.append(cs[i]);
//                    }
////                    notifyItemChanged(position);
//                }


            }
        });



        holder.topEdit.setEnabled(this.IS_SORT_EDIT);


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
        private EditText topEdit;
        private Button editBtn, deleteBtn;
        private SwitchButton aSwitch;
        private View v;

        ViewHolder(View v) {
            super(v);
            this.v = v;
            this.categoryText = v.findViewById(R.id.categoryText);
            this.topEdit = v.findViewById(R.id.top_edit);
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
