package com.melonltd.naberc.view.seller.page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.melonltd.naberc.R;
import com.melonltd.naberc.model.okhttp.ApiCallback;
import com.melonltd.naberc.model.okhttp.ApiManager;
import com.melonltd.naberc.view.seller.SellerMainActivity;
import com.melonltd.naberc.view.seller.adapter.SearchAdapter;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class SellerSearchFragment extends Fragment {
    private static final String TAG = SellerSearchFragment.class.getSimpleName();
    public static SellerSearchFragment FRAGMENT = null;

    private EditText phoneEditText;
    private Button phoneSearchBtn;

    private BGARefreshLayout searchRefreshLayout;
    private SearchAdapter adapter;
    private RecyclerView recyclerView;
    private List<String> listData = Lists.newArrayList();

    public SellerSearchFragment() {
    }

    public Fragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new SellerSearchFragment();
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }

    public Fragment newInstance(Object... o) {
        return new SellerSearchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i < 10; i++) {
            listData.add("listData : " + i);
        }
        adapter = new SearchAdapter(listData);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.seller_search_main_page) == null) {
            View v = inflater.inflate(R.layout.fragment_seller_search, container, false);
            getViews(v);
            setListener();
            container.setTag(R.id.seller_search_main_page, v);
            return v;
        }
        return (View) container.getTag(R.id.seller_search_main_page);
    }

    @Override
    public void onResume() {
        super.onResume();
        SellerMainActivity.lockDrawer(true);
        SellerMainActivity.changeTabAndToolbarStatus();
//        SellerMainActivity.toolbar.setNavigationIcon(null);
    }

    private void getViews(View v) {
        phoneEditText = v.findViewById(R.id.phoneEditText);
        phoneSearchBtn = v.findViewById(R.id.phoneSearchBtn);
        searchRefreshLayout = v.findViewById(R.id.sellerSearchRefreshLayout);
        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(getContext(), true);
        refreshViewHolder.setPullDownRefreshText("Pull");
        refreshViewHolder.setRefreshingText("Pull to refresh");
        refreshViewHolder.setReleaseRefreshText("Pull to refresh");
        refreshViewHolder.setLoadingMoreText("加載");

        searchRefreshLayout.setRefreshViewHolder(refreshViewHolder);
        recyclerView = v.findViewById(R.id.searchRecyclerView);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setListener() {
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListeners(new CancelListener(), new ProcessingListener(), new FailureListener(), new CanFetchListener(), new FinishListener());
        searchRefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                ApiManager.test(new ApiCallback(getContext()) {
                    @Override
                    public void onSuccess(String responseBody) {
                        listData.clear();
                        adapter.notifyDataSetChanged();
                        for (int i = 0; i < 10; i++) {
                            listData.add("listData : " + i);
                        }
                        adapter.notifyDataSetChanged();
                        searchRefreshLayout.endRefreshing();
                    }

                    @Override
                    public void onFail(Exception error, String msg) {
                        searchRefreshLayout.endRefreshing();
                    }
                });

            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
//                searchRefreshLayout.endLoadingMore();
//                for (int i = 0; i < 10; i++) {
//                    listData.add("listData : " + i);
//                }
//                adapter.notifyDataSetChanged();
//                searchRefreshLayout.endRefreshing();
                ApiManager.test(new ApiCallback(getContext()) {
                    @Override
                    public void onSuccess(String responseBody) {
                        for (int i = 0; i < 10; i++) {
                            listData.add("listData : " + i);
                        }
                        adapter.notifyDataSetChanged();
                        searchRefreshLayout.endRefreshing();
                    }

                    @Override
                    public void onFail(Exception error, String msg) {
                        searchRefreshLayout.endRefreshing();
                    }
                });
                return false;
            }
        });
    }

    class CancelListener implements View.OnClickListener {
        @Override
        public void onClick(final View view) {
            final CancelListenerView extView = new CancelListenerView();
            new AlertView.Builder()
                    .setContext(getContext())
                    .setStyle(AlertView.Style.Alert)
                    .setOthers(new String[]{"返回", "送出"})
                    .setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            if (position == 1) {
                                int index = listData.indexOf((String) view.getTag());
                                listData.remove((String) view.getTag());
                                adapter.notifyItemRemoved(index);
                            }
                        }
                    })
                    .build()
                    .addExtView(extView.getView())
                    .setCancelable(true)
                    .show();
        }
    }

    class ProcessingListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Object o = view.getTag();
            Log.d(TAG, o + "");
            int index = listData.indexOf((String) view.getTag());
            listData.remove((String) view.getTag());
            adapter.notifyItemRemoved(index);
        }
    }

    class FailureListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int index = listData.indexOf((String) view.getTag());
            listData.remove((String) view.getTag());
            adapter.notifyItemRemoved(index);
        }
    }

    class CanFetchListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int index = listData.indexOf((String) view.getTag());
            listData.remove((String) view.getTag());
            adapter.notifyItemRemoved(index);
        }
    }

    class FinishListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int index = listData.indexOf((String) view.getTag());
            listData.remove((String) view.getTag());
            adapter.notifyItemRemoved(index);
        }
    }

    class CancelListenerView implements View.OnClickListener {
        private View view;
        private TextView defText1;
        private TextView defText2;
        private EditText customEdit;
        private String msg = "";

        CancelListenerView() {
            this.view = getLayoutInflater().inflate(R.layout.seller_cancel_order_notifiy_to_user_message, null);
            this.defText1 = view.findViewById(R.id.defaultText1);
            this.defText2 = view.findViewById(R.id.defaultText2);
            this.customEdit = view.findViewById(R.id.customEdit);
            // 先帶入預設一
            this.msg = defText1.getText().toString();
            setListener();
        }

        private void setListener() {
            this.defText1.setOnClickListener(CancelListenerView.this);
            this.defText2.setOnClickListener(CancelListenerView.this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.defaultText1:
                    v.setBackgroundResource(R.drawable.naber_reverse_orange_button_style);
                    this.defText2.setBackgroundResource(R.drawable.naber_reverse_gary_button_style);
                    this.msg = defText1.getText().toString();
                    break;
                case R.id.defaultText2:
                    v.setBackgroundResource(R.drawable.naber_reverse_orange_button_style);
                    this.defText1.setBackgroundResource(R.drawable.naber_reverse_gary_button_style);
                    this.msg = defText2.getText().toString();
                    break;
            }
        }

        private View getView() {
            return this.view;
        }

        private String getMessage() {
            if (!Strings.isNullOrEmpty(customEdit.getText().toString()) && customEdit.getText().toString().length() > 0) {
                return customEdit.getText().toString();
            }
            return this.msg;
        }
    }

}
