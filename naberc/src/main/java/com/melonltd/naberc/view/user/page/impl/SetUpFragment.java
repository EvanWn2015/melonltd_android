package com.melonltd.naberc.view.user.page.impl;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.melonltd.naberc.R;
import com.melonltd.naberc.view.user.MainActivity;
import com.melonltd.naberc.view.user.page.abs.AbsPageFragment;
import com.melonltd.naberc.view.user.page.factory.PageFragmentFactory;
import com.melonltd.naberc.view.user.page.type.PageType;


public class SetUpFragment extends AbsPageFragment implements View.OnClickListener {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private static SetUpFragment FRAGMENT = null;
    private TextView toAccountEdit, accountNumberText, dividendText;
    private Switch soundSwitch, shakeSwitch;
    public static int TO_ACCOUNT_DETAIL_INDEX = -1;

    public SetUpFragment() {
    }

    @Override
    public AbsPageFragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new SetUpFragment();
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }

    @Override
    public AbsPageFragment newInstance(Object... o) {
        return new SetUpFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.user_set_up_page) == null) {
            View v = inflater.inflate(R.layout.fragment_set_up, container, false);
            getViews(v);
            setListener();
            container.setTag(R.id.user_set_up_page, v);
            return v;
        } else {
            return (View) container.getTag(R.id.user_set_up_page);
        }
    }

    private void getViews(View v) {
        toAccountEdit = v.findViewById(R.id.toAccountEdit);
        accountNumberText = v.findViewById(R.id.accountNumberText);
        dividendText = v.findViewById(R.id.dividendText);
        soundSwitch = v.findViewById(R.id.soundSwitch);
        shakeSwitch = v.findViewById(R.id.shakeSwitch);
    }

    private void setListener() {
        toAccountEdit.setOnClickListener(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (TO_ACCOUNT_DETAIL_INDEX >= 0){
            toAccountDetail(1);
        }
    }

    private void toAccountDetail(int i) {
        Bundle b = new Bundle();
        b.putString("user detail", "");
        TO_ACCOUNT_DETAIL_INDEX = i;
        MainActivity.FRAGMENT_TAG = PageType.ACCOUNT_DETAIL.name();
        AbsPageFragment f = PageFragmentFactory.of(PageType.ACCOUNT_DETAIL, b);
        getFragmentManager().beginTransaction().replace(R.id.frameContainer, f).commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toAccountEdit:
                Log.d(TAG, "toAccoutEdit");
                toAccountDetail(1);
                break;
        }
    }
}
