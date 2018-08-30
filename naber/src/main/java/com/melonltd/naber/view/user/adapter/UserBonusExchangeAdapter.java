package com.melonltd.naber.view.user.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.melonltd.naber.R;
import com.melonltd.naber.view.user.page.UserBonusExchangeFragment;

import java.util.List;

public class UserBonusExchangeAdapter extends RecyclerView.Adapter<UserBonusExchangeAdapter.ViewHolder> {
    private List<UserBonusExchangeFragment.BonusExchange> list;

    public UserBonusExchangeAdapter(List<UserBonusExchangeFragment.BonusExchange> list){
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_bonus_exchange_item, parent, false);
        UserBonusExchangeAdapter.ViewHolder vh = new UserBonusExchangeAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        UserBonusExchangeFragment.BonusExchange data = list.get(position);
        h.title.setText(data.title);
        h.content.setText(data.content);
        h.type.setText(data.type);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, content, type;
        public ViewHolder(View v) {
            super(v);
            this.title = v.findViewById(R.id.titleText);
            this.content = v.findViewById(R.id.contentText);
            this.type = v.findViewById(R.id.typeText);
        }
    }
}
