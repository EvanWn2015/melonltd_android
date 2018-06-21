package com.melonltd.naberc.view.user.page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.melonltd.naberc.R;
import com.melonltd.naberc.model.bean.Model;
import com.melonltd.naberc.model.constant.NaberConstant;
import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.factory.PageFragmentFactory;
import com.melonltd.naberc.view.factory.PageType;
import com.melonltd.naberc.view.user.UserMainActivity;

import java.util.List;

public class SimpleInformationFragment extends Fragment {
    private static final String TAG = SimpleInformationFragment.class.getSimpleName();
    public static SimpleInformationFragment FRAGMENT = null;

    private LinearLayout bulletinContent;

    public SimpleInformationFragment() {
    }

    public Fragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new SimpleInformationFragment();
        }
        if (bundle != null){
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
            bulletinContent = v.findViewById(R.id.bulletinContent);
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
                    SetUpFragment.TO_SIMPLE_INFO_INDEX = -1;
                    UserMainActivity.navigationIconDisplay(false, null);
                    UserMainActivity.removeAndReplaceWhere(FRAGMENT, PageType.SET_UP, null);
                }
            });
        }

        bulletinContent.removeAllViews();
        UserMainActivity.toolbar.setTitle(getArguments().getString(NaberConstant.TOOLBAR_TITLE));
        List<String> list = getArguments().getStringArrayList(NaberConstant.SIMPLE_INFO);
        for(int i=0; i<list.size(); i++){
            View v = LayoutInflater.from(getContext()).inflate(R.layout.common_bulletin_template, null);
            TextView title = v.findViewById(R.id.titleText);
            TextView msg = v.findViewById(R.id.messageText);
            title.setText(Model.BULLETIN_VOS.get(list.get(i)).get("title"));
            msg.setText(Model.BULLETIN_VOS.get(list.get(i)).get("content_text"));
            bulletinContent.addView(v);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        UserMainActivity.navigationIconDisplay(false, null);
    }
}
