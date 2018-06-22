package com.melonltd.naber.view.user.page;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.melonltd.naber.R;
import com.melonltd.naber.model.bean.Model;
import com.melonltd.naber.model.service.SPService;
import com.melonltd.naber.view.common.BaseCore;
import com.melonltd.naber.view.factory.PageFragmentFactory;
import com.melonltd.naber.view.factory.PageType;
import com.melonltd.naber.view.user.UserMainActivity;
import com.melonltd.naber.vo.OrderDetail;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class ShoppingCartFragment extends Fragment {
    private static final String TAG = ShoppingCartFragment.class.getSimpleName();
    public static ShoppingCartFragment FRAGMENT = null;
    private ShoppingCartAdapter adapter;

//    private int page = 0;
//    private List<OrderDetail> listData = Lists.<OrderDetail>newArrayList();

    public static int TO_SUBMIT_ORDERS_PAGE_INDEX = -1;

//    private boolean loadingMore = true;

    public ShoppingCartFragment() {
    }

    public Fragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new ShoppingCartFragment();
            TO_SUBMIT_ORDERS_PAGE_INDEX = -1;
        }
        if (bundle != null) {
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(getContext());
        adapter = new ShoppingCartAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.user_shopping_cart_page) == null) {
            View v = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
            getViews(v);
            container.setTag(R.id.user_shopping_cart_page, v);
            return v;
        }
        return (View) container.getTag(R.id.user_shopping_cart_page);
    }

    private void getViews(View v) {
        final BGARefreshLayout bgaRefreshLayout = v.findViewById(R.id.shoppingCartBGARefreshLayout);
        RecyclerView recyclerView = v.findViewById(R.id.shoppingCartRecyclerView);

        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(getContext(), true);
        refreshViewHolder.setPullDownRefreshText("Pull");
        refreshViewHolder.setRefreshingText("Pull to refresh");
        refreshViewHolder.setReleaseRefreshText("Pull to refresh");
        refreshViewHolder.setLoadingMoreText("Loading more !");

        bgaRefreshLayout.setRefreshViewHolder(refreshViewHolder);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        // setListener
        adapter.setListener(new DeleteListener(), new SubmitListener(), new DeleteSubViewListener());
        recyclerView.setAdapter(adapter);
        bgaRefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                bgaRefreshLayout.endRefreshing();
                adapter.notifyDataSetChanged();
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
        // 清除 model 已 偏好儲存資料為主，並刷新 model
        Model.USER_CACHE_SHOPPING_CART.clear();
        Model.USER_CACHE_SHOPPING_CART.addAll(SPService.getUserCacheShoppingCarData());
        // 分頁
        UserMainActivity.changeTabAndToolbarStatus();
        adapter.notifyDataSetChanged();
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
            int index = Model.USER_CACHE_SHOPPING_CART.indexOf(v.getTag());
            Model.USER_CACHE_SHOPPING_CART.remove(v.getTag());
            adapter.notifyItemRemoved(index);
            Log.d(TAG, v.getTag() + "");
        }
    }

    class SubmitListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Log.d(TAG, v.getTag() + "");
            toSubmitOrdersPage(1);
        }
    }

    private void toSubmitOrdersPage(int i) {
        TO_SUBMIT_ORDERS_PAGE_INDEX = i;
        BaseCore.FRAGMENT_TAG = PageType.SUBMIT_ORDER.name();
        Fragment f = PageFragmentFactory.of(PageType.SUBMIT_ORDER, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.frameContainer, f).addToBackStack(f.toString()).commit();
    }

    class DeleteSubViewListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Log.d(TAG, v.getTag() + "");
            Log.d(TAG, v.getTag() + "");
        }
    }

    class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {
        private Context context;
        private View.OnClickListener deleteListener, submitListener, deleteSubViewListener;

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
            List<OrderDetail.OrderData> order = Lists.newArrayList();
            int amount = 0;
            for (int i = 0; i < Model.USER_CACHE_SHOPPING_CART.get(position).orders.size(); i++) {
                order.add(Model.USER_CACHE_SHOPPING_CART.get(position).orders.get(i));
                amount += Integer.parseInt(Model.USER_CACHE_SHOPPING_CART.get(position).orders.get(i).item.price);
            }

            holder.setSubViews(position, order);
            holder.totalAmountText.setText(amount + "");
            holder.bonusText.setText(((int) Math.floor(amount / 10d)) + "");
            holder.nameText.setText(Model.USER_CACHE_SHOPPING_CART.get(position).restaurant_name);
            holder.deleteBtn.setTag(Model.USER_CACHE_SHOPPING_CART.get(position));
            holder.submitBtn.setTag(Model.USER_CACHE_SHOPPING_CART.get(position));
            holder.deleteBtn.setOnClickListener(this.deleteListener);
            holder.submitBtn.setOnClickListener(this.submitListener);
        }

        @Override
        public int getItemCount() {
            return Model.USER_CACHE_SHOPPING_CART.size();
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

            private void setSubViews(int position, List<OrderDetail.OrderData> order) {
                this.layout.removeAllViews();

                for (int i = 0; i < order.size(); i++) {

                    String msg = "規格：";
                    msg += order.get(i).item.scopes.get(0).name + "\n";

                    for (int j = 0; j < order.get(i).item.demands.size(); j++) {
                        msg += order.get(i).item.demands.get(j).name + ":";
                        msg += order.get(i).item.demands.get(j).datas.get(0).name + ",";
                    }
                    msg += "\n";
                    if (order.get(i).item.opts.size() > 0) {
                        msg += "追加：";
                        for (int k = 0; k < order.get(i).item.opts.size(); k++) {
                            msg += order.get(i).item.opts.get(k).name + ",";
                        }
                    }

                    View sebView = new OrderSubItemHolder(context)
                            .setName(order.get(i).item.food_name)
                            .setTag(position + "," + i)
                            .setDeleteListener(this.deleteSubViewListener)
                            .setIconPath(order.get(i).item.food_photo)
                            .setPrice(Integer.parseInt(order.get(i).item.price))
                            .setQuantity(order.get(i).count)
                            .setScope(msg)
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

                //
                private OrderSubItemHolder setName(String name) {
                    this.nameText.setText(name);

                    return this;
                }

                //
                private OrderSubItemHolder setScope(String scope) {
                    this.scopeText.setText(scope);
                    return this;
                }

                private OrderSubItemHolder setQuantity(int quantity) {
                    this.quantity = quantity < 1 ? 1 : quantity;
                    return this;
                }

                private OrderSubItemHolder setIconPath(String path) {
                    if (!Strings.isNullOrEmpty(path)) {
                        setIconPath(Uri.parse(path));
                    } else {
                        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithResourceId(R.drawable.naber_icon_logo_reverse).build();
                        setIconPath(imageRequest.getSourceUri());
                    }
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
                    this.quantityText.setText(quantity + "");
                    setAddAndMinusListener();
                    setPriceComp();
                    return this.subView;
                }
            }
        }
    }

}
