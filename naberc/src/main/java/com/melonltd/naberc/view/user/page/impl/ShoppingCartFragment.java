package com.melonltd.naberc.view.user.page.impl;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.common.collect.Lists;
import com.melonltd.naberc.R;
import com.melonltd.naberc.model.helper.okhttp.ApiCallback;
import com.melonltd.naberc.model.helper.okhttp.ApiManager;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class ShoppingCartFragment extends AbsPageFragment {
    private static final String TAG = ShoppingCartFragment.class.getSimpleName();
    private static ShoppingCartFragment FRAGMENT = null;


    private BGARefreshLayout bgaRefreshLayout;
    private ShoppingCartAdapter adapter;
    private RecyclerView recyclerView;
    private List<String> listData = Lists.newArrayList();

    public ShoppingCartFragment() {
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new ShoppingCartFragment();
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new ShoppingCartFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(getContext());
//        adapter = new ShoppingCartAdapter(getContext(), list);
        adapter = new ShoppingCartAdapter(listData);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.user_shopping_cart_page) == null) {
            View v = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
            getViews(v);
            setListener();
            ApiManager.test(new ApiCallback(getActivity()) {
                @Override
                public void onSuccess(String responseBody) {
                    testLoadData(true);
                }

                @Override
                public void onFail(Exception error) {

                }
            });
            container.setTag(R.id.user_shopping_cart_page, v);
            return v;
        }
        return (View) container.getTag(R.id.user_shopping_cart_page);
    }

    private void testLoadData(boolean isRefresh) {
        if (isRefresh) {
            listData.clear();
            adapter.notifyDataSetChanged();
        }
        for (int i = 0; i < 15; i++) {
            listData.add("" + i);
        }
        adapter.notifyDataSetChanged();
    }

    private void getViews(View v) {
        bgaRefreshLayout = v.findViewById(R.id.shoppingCartBGARefreshLayout);
        recyclerView = v.findViewById(R.id.shoppingCartRecyclerView);

        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(getContext(), true);
        refreshViewHolder.setPullDownRefreshText("Pull");
        refreshViewHolder.setRefreshingText("Pull to refresh");
        refreshViewHolder.setReleaseRefreshText("Pull to refresh");
        refreshViewHolder.setLoadingMoreText("Loading more !");

        bgaRefreshLayout.setRefreshViewHolder(refreshViewHolder);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

    }

    private void setListener() {
        adapter.setListener(new DeleteListener(), new SubmitListener(), new DeleteSubViewListener());
        recyclerView.setAdapter(adapter);
        bgaRefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                bgaRefreshLayout.endRefreshing();
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                bgaRefreshLayout.endLoadingMore();
                return false;
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    class DeleteListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Log.d(TAG, v.getTag() + "");
        }
    }

    class SubmitListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Log.d(TAG, v.getTag() + "");
        }
    }

    class DeleteSubViewListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Log.d(TAG, v.getTag() + "");
        }
    }

    class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {
        private Context context;
        private List<String> listData;
        private View.OnClickListener deleteListener, submitListener, deleteSubViewListener;

        ShoppingCartAdapter(List<String> listData) {
            this.listData = listData;
        }

        private void setListener(View.OnClickListener deleteListener, View.OnClickListener submitListener, View.OnClickListener deleteSubViewListener) {
            this.deleteListener = deleteListener;
            this.submitListener = submitListener;
            this.deleteSubViewListener = deleteSubViewListener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            this.context = parent.getContext();
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_shopping_detail_item, parent, false);
            ShoppingCartAdapter.ViewHolder vh = new ShoppingCartAdapter.ViewHolder(v);
            vh.setListener(this.deleteSubViewListener);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            List<String> sublist = Lists.newArrayList();
            for(int i=0; i<position; i++){
                sublist.add("sub" + i);
            }
            holder.setSubViews(sublist);
            holder.deleteBtn.setTag(listData.get(position));
            holder.submitBtn.setTag(listData.get(position));
            holder.deleteBtn.setOnClickListener(this.deleteListener);
            holder.submitBtn.setOnClickListener(this.submitListener);
        }

        @Override
        public int getItemCount() {
            return listData.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView nameText, totalAmountText, bonusText;
            private LinearLayout layout;
            private Button deleteBtn, submitBtn;
            private View.OnClickListener deleteSubViewListener;

            ViewHolder(View v) {
                super(v);
                this.nameText = v.findViewById(R.id.restaurantNameText);
                this.totalAmountText = v.findViewById(R.id.ordersTotalAmountText);
                this.bonusText = v.findViewById(R.id.bonusText);
                this.layout = v.findViewById(R.id.ordersItemsLayout);
                this.deleteBtn = v.findViewById(R.id.deleteOrdersBtn);
                this.submitBtn = v.findViewById(R.id.submitOrdersBtn);
            }

            private void setListener(View.OnClickListener deleteSubViewListener) {
                this.deleteSubViewListener = deleteSubViewListener;
            }

            private void setSubViews(List<String> sublist) {
                this.layout.removeAllViews();
                for (String ss : sublist) {
                    View sebView = new OrderSubItemHolder(context)
                            .setName("subName")
                            .setTag(ss)
                            .setDeleteListener(this.deleteSubViewListener)
                            .setIconPath("http://i.epochtimes.com/assets/uploads/2017/09/Fotolia_58802987_Subscription_L-600x400.jpg")
                            .setPrice(20)
                            .setQuantity(1)
                            .setScope("規格:" + "\n" + "追加")
                            .build();
                    this.layout.addView(sebView);
                }
            }

            class OrderSubItemHolder {
                private SimpleDraweeView iconImageView;
                private TextView nameText, scopeText;
                private ImageButton minusBtn, addBtn, deleteBtn;
                private TextView quantityText, priceText;
                public int quantity = 0, price = 0;
                private String name = "", scope = "";
                private View subView;


                OrderSubItemHolder(Context context) {
                    View v = LayoutInflater.from(context).inflate(R.layout.user_shopping_order_item, null);
                    this.iconImageView = v.findViewById(R.id.ordersItemIconImageView);
                    this.nameText = v.findViewById(R.id.ordersItemNameText);
                    this.scopeText = v.findViewById(R.id.ordersItemScopeText);
                    this.minusBtn = v.findViewById(R.id.ordersItemMinusBtn);
                    this.addBtn = v.findViewById(R.id.ordersItemAddBtn);
                    this.deleteBtn = v.findViewById(R.id.menuEditDeleteBtn);
                    this.quantityText = v.findViewById(R.id.ordersItemQuantityText);
                    this.priceText = v.findViewById(R.id.ordersItemPriceText);
                    this.subView = v;
                }

                private OrderSubItemHolder setTag(Object o) {
                    this.minusBtn.setTag(o);
                    this.addBtn.setTag(o);
                    this.deleteBtn.setTag(o);
                    return this;
                }

                private OrderSubItemHolder setName(String name) {
                    this.name = name;
                    return this;
                }

                private OrderSubItemHolder setScope(String scope) {
                    this.scope = scope;
                    return this;
                }

                private OrderSubItemHolder setQuantity(int quantity) {
                    this.quantity = quantity < 1 ? 1 : quantity;
                    return this;
                }

                private OrderSubItemHolder setIconPath(String path) {
                    Uri uri = Uri.parse(path);
                    setIconPath(uri);
                    return this;
                }

                private OrderSubItemHolder setIconPath(Uri uri) {
                    this.iconImageView.setImageURI(uri);
                    return this;
                }

                private OrderSubItemHolder setPrice(int price) {
                    this.price = price;
                    return this;
                }

                private OrderSubItemHolder setDeleteListener(View.OnClickListener listener) {
                    this.deleteBtn.setOnClickListener(listener);
                    return this;
                }

                private void setAddAndMinusListener() {
                    this.addBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d(TAG, v.getTag() + "");
                            quantity++;
                            if (quantity > 50) {
                                quantity = 50;
                            }
                            setPriceComp();
                        }
                    });
                    this.minusBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d(TAG, v.getTag() + "");
                            quantity--;
                            if (quantity <= 0) {
                                quantity = 1;
                            }
                            setPriceComp();
                        }
                    });
                }

                private void setPriceComp() {
                    int sun = this.price * this.quantity;
                    this.quantityText.setText(this.quantity + "");
                    this.priceText.setText(sun + "");
                }

                private View build() {
                    this.nameText.setText(name);
                    this.scopeText.setText(scope);
                    this.quantityText.setText(quantity + "");
                    setAddAndMinusListener();
                    setPriceComp();
                    return this.subView;
                }
            }
        }
    }

}
