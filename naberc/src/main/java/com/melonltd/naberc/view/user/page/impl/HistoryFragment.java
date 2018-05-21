package com.melonltd.naberc.view.user.page.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.common.collect.Lists;
import com.melonltd.naberc.R;
import com.melonltd.naberc.model.helper.okhttp.ApiCallback;
import com.melonltd.naberc.model.helper.okhttp.ApiManager;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;
import com.melonltd.naberc.view.common.factory.PageFragmentFactory;
import com.melonltd.naberc.view.common.type.PageType;
import com.melonltd.naberc.view.customize.OnLoadLayout;
import com.melonltd.naberc.view.user.UserMainActivity;
import com.melonltd.naberc.view.user.adapter.HistoryAdapter;

import java.util.ArrayList;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class HistoryFragment extends AbsPageFragment {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private static HistoryFragment FRAGMENT = null;
    private OnLoadLayout historyOnLoadLayout;
    private HistoryAdapter adapter;
    private ListView historyListView;
    private ArrayList<String> list = Lists.newArrayList();
    public static int TO_ORDER_DETAIL_INDEX = -1;

    public HistoryFragment() {
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new HistoryFragment();
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new HistoryFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new HistoryAdapter(getContext(), list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.user_history_page) == null) {
            View v = inflater.inflate(R.layout.fragment_history, container, false);
            getViews(v);
            setListener();
            container.setTag(R.id.user_history_page, v);
            return v;
        } else {
            return (View) container.getTag(R.id.user_history_page);
        }
    }

    private void getViews(View v) {
        historyOnLoadLayout = v.findViewById(R.id.historyOnLoadLayout);
        historyListView = v.findViewById(R.id.historyListView);
        historyListView.setAdapter(adapter);
    }


    private void setListener() {
        historyOnLoadLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                doLoadData(true);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return ((OnLoadLayout) frame).isTop();
            }
        });

        historyOnLoadLayout.setOnLoadListener(new OnLoadLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                doLoadData(false);
            }
        });

        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TO_ORDER_DETAIL_INDEX = i;
                toOrderDetail(TO_ORDER_DETAIL_INDEX);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (TO_ORDER_DETAIL_INDEX >= 0) {
            toOrderDetail(TO_ORDER_DETAIL_INDEX);
        }
        doLoadData(true);
    }

    private void doLoadData(boolean isRefresh) {
        if (isRefresh) {
            list.clear();
        }
        ApiManager.test(new ApiCallback(getActivity()) {
            @Override
            public void onSuccess(String responseBody) {
                for (int i = 0; i < 5; i++) {
                    list.add("" + i);
                }
                adapter.notifyDataSetChanged();
                historyOnLoadLayout.refreshComplete();
            }

            @Override
            public void onFail(Exception error) {

            }
        });
    }

    private void toOrderDetail(int resultIndex) {
        Bundle b = new Bundle();
        b.putString("test", list.get(resultIndex));
        UserMainActivity.FRAGMENT_TAG = PageType.ORDER_DETAIL.name();
        AbsPageFragment f = PageFragmentFactory.of(PageType.ORDER_DETAIL, b);
        getFragmentManager().beginTransaction().replace(R.id.frameContainer, f).commit();
    }
}
