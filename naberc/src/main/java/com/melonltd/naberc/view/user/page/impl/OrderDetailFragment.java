package com.melonltd.naberc.view.user.page.impl;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melonltd.naberc.R;
import com.melonltd.naberc.view.user.UserMainActivity;
import com.melonltd.naberc.view.user.page.abs.AbsPageFragment;
import com.melonltd.naberc.view.user.page.factory.PageFragmentFactory;
import com.melonltd.naberc.view.user.page.type.PageType;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderDetailFragment extends AbsPageFragment {
    private static final String TAG = OrderDetailFragment.class.getSimpleName();
    private static OrderDetailFragment FRAGMENT = null;


    public OrderDetailFragment() {
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new HomeFragment();
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new OrderDetailFragment();
        }
        FRAGMENT.setArguments(bundle);
        Bundle b = FRAGMENT.getArguments();
        Log.d(TAG, b.get("test").toString());
        return FRAGMENT;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.user_order_detail_page) == null) {
            View v = inflater.inflate(R.layout.fragment_order_detail, container, false);
            container.setTag(R.id.user_order_detail_page, v);
            return v;
        } else {
            return (View) container.getTag(R.id.user_order_detail_page);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (UserMainActivity.toolbar != null) {
            UserMainActivity.navigationIconDisplay(true, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    backToHistoryPage();
                    UserMainActivity.navigationIconDisplay(false, null);
                }
            });
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "stop");
        UserMainActivity.navigationIconDisplay(false, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "destroy");
    }

    private void backToHistoryPage() {
        HistoryFragment.TO_ORDER_DETAIL_INDEX = -1;
        AbsPageFragment f = PageFragmentFactory.of(PageType.HISTORY, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.frameContainer, f).commit();
    }

}
