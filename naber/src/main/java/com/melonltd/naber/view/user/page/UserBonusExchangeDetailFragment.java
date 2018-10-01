package com.melonltd.naber.view.user.page;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.melonltd.naber.R;
import com.melonltd.naber.view.factory.PageType;
import com.melonltd.naber.view.user.UserMainActivity;
import com.melonltd.naber.vo.ActivitiesVo;

public class UserBonusExchangeDetailFragment extends Fragment {
    private static final String TAG = UserBonusExchangeDetailFragment.class.getSimpleName();
    public static UserBonusExchangeDetailFragment FRAGMENT = null;

    public Fragment getInstance(Bundle bundle) {
//        if (FRAGMENT == null) {
            FRAGMENT = new UserBonusExchangeDetailFragment();
            FRAGMENT.setArguments(bundle);
//        }
        return FRAGMENT;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();

        ActivitiesVo data = (ActivitiesVo)bundle.get("BONUS_DETAIL");

        Log.i(TAG,data.title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.user_bonus_exchange_detail_page) == null) {
            View v = inflater.inflate(R.layout.fragment_user_bonus_exchange_detail, container, false);
            getViews(v);
            container.setTag(R.id.user_bonus_exchange_detail_page, v);
            return v;
        }
        return (View) container.getTag(R.id.user_bonus_exchange_detail_page);

    }

    private void getViews (View v){
        
    }

    @Override
    public void onResume() {
        super.onResume();

        UserMainActivity.changeTabAndToolbarStatus();

        if (UserMainActivity.toolbar != null) {
            UserMainActivity.navigationIconDisplay(true, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UserMainActivity.removeAndReplaceWhere(FRAGMENT, PageType.USER_BONUS_EXCHANGE, null);
                }
            });
        }
    }



}
