package com.melonltd.naberc.view.user.page;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.melonltd.naberc.R;
import com.melonltd.naberc.model.api.ApiManager;
import com.melonltd.naberc.model.api.ThreadCallback;
import com.melonltd.naberc.model.constant.NaberConstant;
import com.melonltd.naberc.model.service.SPService;
import com.melonltd.naberc.util.Tools;
import com.melonltd.naberc.view.customize.SwitchButton;
import com.melonltd.naberc.view.factory.PageType;
import com.melonltd.naberc.view.user.UserMainActivity;
import com.melonltd.naberc.vo.AccountInfoVo;


public class SetUpFragment extends Fragment{
    private static final String TAG = HomeFragment.class.getSimpleName();
    public static SetUpFragment FRAGMENT = null;
    public static int TO_ACCOUNT_DETAIL_INDEX = -1;
    public static int TO_SIMPLE_INFO_INDEX = -1;

    private ViewHolder holder;

    public SetUpFragment() {
    }

    public Fragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new SetUpFragment();
            TO_ACCOUNT_DETAIL_INDEX = -1;
            TO_SIMPLE_INFO_INDEX = -1;
        }
        if (bundle != null){
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.user_set_up_page) == null) {
            View v = inflater.inflate(R.layout.fragment_set_up, container, false);
            holder = new ViewHolder(v);
            container.setTag(R.id.user_set_up_page, v);
            return v;
        }
        return (View) container.getTag(R.id.user_set_up_page);
    }

    @Override
    public void onResume() {
        super.onResume();
        UserMainActivity.changeTabAndToolbarStatus();
        if (TO_ACCOUNT_DETAIL_INDEX >= 0) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(NaberConstant.ACCOUNT_INFO, holder.accountInfoVo);
            UserMainActivity.removeAndReplaceWhere(FRAGMENT, PageType.ACCOUNT_DETAIL, bundle);
        } else if (TO_SIMPLE_INFO_INDEX >= 0) {
            UserMainActivity.removeAndReplaceWhere(FRAGMENT, PageType.SIMPLE_INFO, holder.bundle);
        }else {
            ApiManager.userFindAccountInfo(new ThreadCallback(getContext()) {
                @Override
                public void onSuccess(String responseBody) {
                    holder.accountInfoVo = Tools.JSONPARSE.fromJson(responseBody, AccountInfoVo.class);
                    holder.accountText.setText(holder.accountInfoVo.phone);
                    holder.bonusText.setText(holder.accountInfoVo.bonus);
                    if (!Strings.isNullOrEmpty(holder.accountInfoVo.photo)){
                        holder.accountPotoh.setImageURI(Uri.parse(holder.accountInfoVo.photo));
                    }else {
                        ImageRequest request = ImageRequestBuilder.newBuilderWithResourceId(R.drawable.naber_icon_logo).build();
                        holder.accountPotoh.setImageURI(request.getSourceUri());
                    }
                }

                @Override
                public void onFail(Exception error, String msg) {
                    Log.d(TAG, msg);
                }
            });
        }
    }

    class SetUpClick implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.toAccountEdit:
                    TO_ACCOUNT_DETAIL_INDEX = 1;
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(NaberConstant.ACCOUNT_INFO, holder.accountInfoVo);
                    UserMainActivity.removeAndReplaceWhere(FRAGMENT, PageType.ACCOUNT_DETAIL, bundle);
                    break;
                case R.id.toAboutUsText:
                    TO_SIMPLE_INFO_INDEX = 1;
                    holder.bundle.putString(NaberConstant.TOOLBAR_TITLE, ((TextView)view).getText().toString());
                    holder.bundle.putStringArrayList(NaberConstant.SIMPLE_INFO, Lists.newArrayList("ABOUT_US","APPLY_OF_SELLER"));
                    UserMainActivity.removeAndReplaceWhere(FRAGMENT, PageType.SIMPLE_INFO, holder.bundle);
                    break;
                case R.id.toHelpText:
                    TO_SIMPLE_INFO_INDEX = 1;
                    holder.bundle.putString(NaberConstant.TOOLBAR_TITLE, ((TextView)view).getText().toString());
                    holder.bundle.putStringArrayList(NaberConstant.SIMPLE_INFO, Lists.newArrayList("FAQ","CONTACT_US"));
                    UserMainActivity.removeAndReplaceWhere(FRAGMENT, PageType.SIMPLE_INFO, holder.bundle);
                    break;
                case R.id.toTeachingText:
                    TO_SIMPLE_INFO_INDEX = 1;
                    holder.bundle.putString(NaberConstant.TOOLBAR_TITLE, ((TextView)view).getText().toString());
                    holder.bundle.putStringArrayList(NaberConstant.SIMPLE_INFO, Lists.newArrayList("TEACHING"));
                    UserMainActivity.removeAndReplaceWhere(FRAGMENT, PageType.SIMPLE_INFO, holder.bundle);
                    break;
            }
        }
    }

    class NotifySetUpListener implements SwitchButton.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(SwitchButton view, boolean isChecked) {
            switch (view.getId()) {
                case R.id.soundSwitch:
                    SPService.setNotifyShake(isChecked);
                    break;
                case R.id.shakeSwitch:
                    SPService.setNotifyShake(isChecked);
                    break;
            }
        }
    }

    class ViewHolder {
        private TextView accountText, bonusText;
        private SimpleDraweeView accountPotoh;
        private AccountInfoVo accountInfoVo;
        private Bundle bundle = new Bundle();
        ViewHolder(View v){
            this.accountPotoh = v.findViewById(R.id.accountPotoh);
            this.accountText = v.findViewById(R.id.accountText);
            this.bonusText = v.findViewById(R.id.bonusText);

            SwitchButton soundSwitch = v.findViewById(R.id.soundSwitch);
            SwitchButton shakeSwitch = v.findViewById(R.id.shakeSwitch);
            TextView toAccountEdit = v.findViewById(R.id.toAccountEdit);
            TextView toAboutUsText = v.findViewById(R.id.toAboutUsText);
            TextView toHelpText = v.findViewById(R.id.toHelpText);
            TextView toTeachingText = v.findViewById(R.id.toTeachingText);

            soundSwitch.setChecked(SPService.getNotifySound());
            shakeSwitch.setChecked(SPService.getNotifyShake());

            NotifySetUpListener notifySetUpListener = new NotifySetUpListener();
            soundSwitch.setOnCheckedChangeListener(notifySetUpListener);
            shakeSwitch.setOnCheckedChangeListener(notifySetUpListener);

            SetUpClick click = new SetUpClick();
            toAccountEdit.setOnClickListener(click);
            toAboutUsText.setOnClickListener(click);
            toHelpText.setOnClickListener(click);
            toTeachingText.setOnClickListener(click);
        }
    }
}
