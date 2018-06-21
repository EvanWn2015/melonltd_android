package com.melonltd.naberc.view.user.page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melonltd.naberc.R;
import com.melonltd.naberc.view.factory.PageFragmentFactory;
import com.melonltd.naberc.view.factory.PageType;
import com.melonltd.naberc.view.user.UserMainActivity;

public class ResetPasswordFragment extends Fragment {
    private static final String TAG = ResetPasswordFragment.class.getSimpleName();
    public static ResetPasswordFragment FRAGMENT = null;

    public ResetPasswordFragment() {
    }


    public Fragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new ResetPasswordFragment();
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
        if (container.getTag(R.id.common_reset_password_page) == null) {
            View v = inflater.inflate(R.layout.fragment_reset_password, container, false);
            container.setTag(R.id.common_reset_password_page, v);
            return v;
        }
        return (View) container.getTag(R.id.common_reset_password_page);
    }

    @Override
    public void onResume() {
        super.onResume();
        UserMainActivity.changeTabAndToolbarStatus();
        if (UserMainActivity.toolbar != null) {
            UserMainActivity.navigationIconDisplay(true, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    backToAccountDetailPage();
                    UserMainActivity.navigationIconDisplay(false, null);
                    AccountDetailFragment.TO_RESET_PASSWORD_INDEX = -1;
                    UserMainActivity.removeAndReplaceWhere(FRAGMENT, PageType.ACCOUNT_DETAIL, null);
                }
            });
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        UserMainActivity.navigationIconDisplay(false, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

//    private void backToAccountDetailPage() {
//        AccountDetailFragment.TO_RESET_PASSWORD_INDEX = -1;
//        UserMainActivity.removeAndReplaceWhere(FRAGMENT, PageType.ACCOUNT_DETAIL, null);
//
//        UserMainActivity.FRAGMENT_TAG = PageType.ACCOUNT_DETAIL.name();
//        Fragment f = PageFragmentFactory.of(PageType.ACCOUNT_DETAIL, null);
//        getFragmentManager().beginTransaction().remove(this).replace(R.id.frameContainer, f).addToBackStack(f.toString()).commit();
//    }
}
