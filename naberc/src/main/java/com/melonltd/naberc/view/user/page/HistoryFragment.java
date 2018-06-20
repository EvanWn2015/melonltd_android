package com.melonltd.naberc.view.user.page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.collect.Lists;
import com.melonltd.naberc.R;
import com.melonltd.naberc.model.api.ThreadCallback;
import com.melonltd.naberc.model.api.ApiManager;
import com.melonltd.naberc.model.bean.Model;
import com.melonltd.naberc.model.constant.NaberConstant;
import com.melonltd.naberc.util.Tools;
import com.melonltd.naberc.view.factory.PageFragmentFactory;
import com.melonltd.naberc.view.factory.PageType;
import com.melonltd.naberc.view.user.UserMainActivity;
import com.melonltd.naberc.view.user.adapter.HistoryAdapter;
import com.melonltd.naberc.vo.OrderVo;
import com.melonltd.naberc.vo.ReqData;

import java.util.Date;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class HistoryFragment extends Fragment {
    private static final String TAG = HomeFragment.class.getSimpleName();
    public static HistoryFragment FRAGMENT = null;

    private HistoryAdapter adapter;
    private ReqData reqData = new ReqData();

    public static int TO_ORDER_DETAIL_INDEX = -1;

    public HistoryFragment() {
    }

    public Fragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new HistoryFragment();
            TO_ORDER_DETAIL_INDEX = -1;
        }
        if (bundle != null){
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new HistoryAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.user_history_page) == null) {
            View v = inflater.inflate(R.layout.fragment_history, container, false);
            getViews(v);
            container.setTag(R.id.user_history_page, v);
            return v;
        } else {
            return (View) container.getTag(R.id.user_history_page);
        }
    }

    private void getViews(View v) {

        final BGARefreshLayout bgaRefreshLayout = v.findViewById(R.id.historyBGARefreshLayout);
        RecyclerView recyclerView = v.findViewById(R.id.historyRecyclerView);

        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(getContext(), true);
        refreshViewHolder.setPullDownRefreshText("Pull");
        refreshViewHolder.setRefreshingText("Pull to refresh");
        refreshViewHolder.setReleaseRefreshText("Pull to refresh");
        refreshViewHolder.setLoadingMoreText("Loading more !");

        bgaRefreshLayout.setRefreshViewHolder(refreshViewHolder);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        //setListener
        adapter.setListener(new ItemOnClickListener());
        recyclerView.setAdapter(adapter);
        bgaRefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                bgaRefreshLayout.endRefreshing();
                doLoadData(true);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                bgaRefreshLayout.endLoadingMore();
                reqData.page ++;
                doLoadData(false);
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        UserMainActivity.changeTabAndToolbarStatus();
        if (TO_ORDER_DETAIL_INDEX >= 0) {
            toOrderDetail(TO_ORDER_DETAIL_INDEX);
        } else {
            if (Model.USER_ORDER_HISTORY_LIST.size() == 0) {
                doLoadData(true);
            }
        }
    }

    private void doLoadData(boolean isRefresh) {
        if (isRefresh) {
            Model.USER_ORDER_HISTORY_LIST.clear();
        }
        reqData.page = 1;
        ApiManager.userOrderHistory(reqData,new ThreadCallback(getContext()) {
            @Override
            public void onSuccess(String responseBody) {
                List<OrderVo> list = Tools.JSONPARSE.fromJsonList(responseBody, OrderVo[].class);
                Model.USER_ORDER_HISTORY_LIST.addAll(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(Exception error, String msg) {

            }
        });
    }

    private void toOrderDetail(int index) {
        TO_ORDER_DETAIL_INDEX = index;
        Bundle bundle = new Bundle();
        OrderVo vo = Model.USER_ORDER_HISTORY_LIST.get(index);
        bundle.putSerializable(NaberConstant.ORDER_INFO, vo);
        UserMainActivity.FRAGMENT_TAG = PageType.ORDER_DETAIL.name();
        Fragment f = PageFragmentFactory.of(PageType.ORDER_DETAIL, bundle);
        getFragmentManager().beginTransaction().replace(R.id.frameContainer, f).addToBackStack(f.toString()).commit();
    }


    class ItemOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            toOrderDetail((int) view.getTag());
        }
    }
}
