package com.melonltd.naberc.view.user.page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melonltd.naberc.R;
import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.factory.PageFragmentFactory;
import com.melonltd.naberc.view.factory.PageType;
import com.melonltd.naberc.view.user.UserMainActivity;

public class SimpleInformationFragment extends Fragment {
    private static final String TAG = SimpleInformationFragment.class.getSimpleName();
    public static SimpleInformationFragment FRAGMENT = null;

    public SimpleInformationFragment() {
    }

    public Fragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new SimpleInformationFragment();
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.user_simple_information_page) == null) {
            View v = inflater.inflate(R.layout.fragment_simple_information, container, false);
            container.setTag(R.id.user_simple_information_page, v);
            return v;
        }
        return (View) container.getTag(R.id.user_simple_information_page);
    }

    @Override
    public void onResume() {
        super.onResume();
        UserMainActivity.changeTabAndToolbarStatus();
        if (UserMainActivity.toolbar != null) {
            UserMainActivity.navigationIconDisplay(true, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    backToSetUpPage();
                    UserMainActivity.navigationIconDisplay(false, null);
                }
            });
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        UserMainActivity.navigationIconDisplay(false, null);
    }

    private void backToSetUpPage() {
        BaseCore.FRAGMENT_TAG = PageType.SET_UP.name();
        SetUpFragment.TO_SIMPLE_INFO_INDEX = -1;
        Fragment f = PageFragmentFactory.of(PageType.SET_UP, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.frameContainer, f).addToBackStack(f.toString()).commit();
    }
}
