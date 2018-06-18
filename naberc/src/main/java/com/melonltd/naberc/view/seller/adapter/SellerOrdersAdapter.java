package com.melonltd.naberc.view.seller.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.melonltd.naberc.R;
import com.melonltd.naberc.util.Tools;
import com.melonltd.naberc.view.seller.page.SellerOrdersFragment;

import java.util.List;

public class SellerOrdersAdapter extends RecyclerView.Adapter<SellerOrdersAdapter.ViewHolder> {
    private static final String TAG = SellerOrdersAdapter.class.getSimpleName();
    private List<String> list;
    private View.OnClickListener cancelListener, processingListener, failureListener, canFetchListener, finishListener;

    public SellerOrdersAdapter(List<String> list) {
        this.list = list;
    }

    public void setOnClickListeners(View.OnClickListener cancelListener, View.OnClickListener processingListener, View.OnClickListener failureListener, View.OnClickListener canFetchListener, View.OnClickListener finishListener) {
        this.cancelListener = cancelListener;
        this.processingListener = processingListener;
        this.failureListener = failureListener;
        this.canFetchListener = canFetchListener;
        this.finishListener = finishListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_orders_items, parent, false);
        SellerOrdersAdapter.ViewHolder vh = new SellerOrdersAdapter.ViewHolder(v);
        vh.setOnClickListeners(cancelListener, processingListener, failureListener, canFetchListener, finishListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String foodItems = "";
        for (int i = 0; i <= position; i++) {
            foodItems += Tools.MAKEUP.makeUpCharacter("機腿", 40, Tools.MakeUp.Direction.RIGHT)
                    + Tools.MAKEUP.makeUpCharacter("x1", 5, Tools.MakeUp.Direction.RIGHT)
                    + Tools.MAKEUP.makeUpCharacter("$10", 10, Tools.MakeUp.Direction.LEFT)
                    + "\n";
        }

        // set Tag
        holder.cancelBtn.setTag(list.get(position));
        holder.processingBtn.setTag(list.get(position));
        holder.failureBtn.setTag(list.get(position));
        holder.canFetchBtn.setTag(list.get(position));
        holder.finishBtn.setTag(list.get(position));

        holder.foodItemsCountText.setText(list.get(position));
        holder.foodItemsText.setText(foodItems);
        holder.remarkText.setText(list.get(position));

        holder.fetchTimeText.setText("2018-05-0" + position);
        holder.userPhoneNumberText.setText("09876543" + position);
        holder.userNameText.setText("某某" + position);
        holder.totalAmountText.setText((position * 2) + "$");
        holder.foodItemsText.setText(foodItems);

        holder.processingBtn.setVisibility(View.GONE);
        holder.canFetchBtn.setVisibility(View.GONE);
        holder.finishBtn.setVisibility(View.GONE);
        holder.failureBtn.setVisibility(View.GONE);

        if (SellerOrdersFragment.TAB_TYPE.equals(SellerOrdersFragment.TabType.UNTREATED)) {
            holder.canFetchBtn.setVisibility(View.VISIBLE);
            holder.processingBtn.setVisibility(View.VISIBLE);
        } else if (SellerOrdersFragment.TAB_TYPE.equals(SellerOrdersFragment.TabType.PROCESSING)) {
            holder.canFetchBtn.setVisibility(View.VISIBLE);
            holder.finishBtn.setVisibility(View.VISIBLE);
        } else if (SellerOrdersFragment.TAB_TYPE.equals(SellerOrdersFragment.TabType.CAN_FETCH)) {
            holder.failureBtn.setVisibility(View.VISIBLE);
            holder.finishBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView foodItemsCountText, foodItemsText, remarkText, fetchTimeText, userPhoneNumberText, userNameText, totalAmountText;
        private Button cancelBtn, failureBtn, processingBtn, canFetchBtn, finishBtn;

        ViewHolder(View v) {
            super(v);
            foodItemsCountText = v.findViewById(R.id.foodItemsCountText);
            foodItemsText = v.findViewById(R.id.foodItemsText);
            remarkText = v.findViewById(R.id.remarkText);

            cancelBtn = v.findViewById(R.id.cancelBtn);
            processingBtn = v.findViewById(R.id.processingBtn);
            failureBtn = v.findViewById(R.id.failureBtn);
            canFetchBtn = v.findViewById(R.id.canFetchBtn);
            finishBtn = v.findViewById(R.id.finishBtn);

            fetchTimeText = v.findViewById(R.id.fetchTimeText);
            userPhoneNumberText = v.findViewById(R.id.userPhoneNumberText);
            userNameText = v.findViewById(R.id.userNameText);
            totalAmountText = v.findViewById(R.id.totalAmountText);
        }

        private void setOnClickListeners(View.OnClickListener cancelListener, View.OnClickListener processingListener, View.OnClickListener failureListener, View.OnClickListener canFetchListener, View.OnClickListener finishListener) {
            cancelBtn.setOnClickListener(cancelListener);
            processingBtn.setOnClickListener(processingListener);
            failureBtn.setOnClickListener(failureListener);
            canFetchBtn.setOnClickListener(canFetchListener);
            finishBtn.setOnClickListener(finishListener);
        }
    }
}