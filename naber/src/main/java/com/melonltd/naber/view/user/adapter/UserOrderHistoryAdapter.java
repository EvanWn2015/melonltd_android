package com.melonltd.naber.view.user.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.melonltd.naber.R;
import com.melonltd.naber.model.bean.Model;
import com.melonltd.naber.model.constant.NaberConstant;
import com.melonltd.naber.model.type.OrderStatus;
import com.melonltd.naber.util.Tools;
import com.melonltd.naber.view.user.page.UserOrderHistoryFragment;
import com.melonltd.naber.vo.OrderDetail;


public class UserOrderHistoryAdapter extends RecyclerView.Adapter<UserOrderHistoryAdapter.ViewHolder> {
//    private static final String TAG = UserOrderHistoryAdapter.class.getSimpleName();
    private View.OnClickListener itemClickListener;
    private Context context;

    public void setListener(View.OnClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public UserOrderHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_history_item, parent, false);
        UserOrderHistoryAdapter.ViewHolder vh = new UserOrderHistoryAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull UserOrderHistoryAdapter.ViewHolder holder, int position) {

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(this.itemClickListener);
        OrderDetail detail = Tools.JSONPARSE.fromJson(Model.USER_ORDER_HISTORY_LIST.get(position).order_data, OrderDetail.class);
        holder.getOrderTimeText.setText(Tools.FORMAT.format(NaberConstant.DATE_FORMAT_PATTERN, "dd日 HH時 mm分", Model.USER_ORDER_HISTORY_LIST.get(position).fetch_date));

        holder.restaurantNameText.setText(detail.restaurant_name);
        OrderStatus status = OrderStatus.of(Model.USER_ORDER_HISTORY_LIST.get(position).status);
        if (status != null){
            holder.orderStatusText.setTextColor(this.context.getResources().getColor(status.getColor()));
            if(status.equals(OrderStatus.UNFINISH)){
                holder.orderStatusText.setText("");
            } else {
                holder.orderStatusText.setText(status.getText());
            }
        }
        holder.totalAmountText.setText("$" + (Model.USER_ORDER_HISTORY_LIST.get(position).order_price));
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
            totalAmountText = v.findViewById(R.id.priceEdit);
            orderStatusText = v.findViewById(R.id.orderStatusText);
            getOrderTimeText = v.findViewById(R.id.getOrderTimeText);
        }
    }
}


