package com.melonltd.naberc.view.user.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.melonltd.naberc.R;
import com.melonltd.naberc.model.bean.Model;
import com.melonltd.naberc.model.constant.NaberConstant;
import com.melonltd.naberc.model.type.OrderStatus;
import com.melonltd.naberc.util.Tools;
import com.melonltd.naberc.vo.OrderDetail;
import com.melonltd.naberc.vo.OrderVo;

import java.util.List;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private static final String TAG = HistoryAdapter.class.getSimpleName();
    private View.OnClickListener itemClickListener;
    private Context context;

    public void setListener(View.OnClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_history_item, parent, false);
        HistoryAdapter.ViewHolder vh = new HistoryAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(this.itemClickListener);
        OrderDetail detail = Tools.JSONPARSE.fromJson(Model.USER_ORDER_HISTORY_LIST.get(position).order_data, OrderDetail.class);
        Log.d(TAG, detail.toString());
        holder.getOrderTimeText.setText(Tools.FORMAT.format(NaberConstant.DATE_FORMAT_PATTERN, "dd日 hh時 mm分", Model.USER_ORDER_HISTORY_LIST.get(position).fetch_date));
        holder.restaurantNameText.setText(Model.USER_ORDER_HISTORY_LIST.get(position).restaurant_name);
        OrderStatus status = OrderStatus.of(Model.USER_ORDER_HISTORY_LIST.get(position).status);
        if (status != null){
            holder.orderStatusText.setText(status.getText());
            holder.orderStatusText.setTextColor(this.context.getResources().getColor(status.getColor()));
        }
        holder.totalAmountText.setText(Model.USER_ORDER_HISTORY_LIST.get(position).order_price);
    }

    @Override
    public int getItemCount() {
        return Model.USER_ORDER_HISTORY_LIST.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView restaurantNameText, totalAmountText, orderStatusText, getOrderTimeText;

        public ViewHolder(View v) {
            super(v);
            restaurantNameText = v.findViewById(R.id.restaurantNameText);
            totalAmountText = v.findViewById(R.id.totalAmountText);
            orderStatusText = v.findViewById(R.id.orderStatusText);
            getOrderTimeText = v.findViewById(R.id.getOrderTimeText);
        }
    }
}


