package com.melonltd.naber.view.user.page;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.melonltd.naber.R;
import com.melonltd.naber.model.api.ApiManager;
import com.melonltd.naber.model.api.ThreadCallback;
import com.melonltd.naber.model.bean.Model;
import com.melonltd.naber.util.IntegerTools;
import com.melonltd.naber.util.Tools;
import com.melonltd.naber.view.factory.PageType;
import com.melonltd.naber.view.user.UserMainActivity;
import com.melonltd.naber.vo.AccountInfoVo;
import com.melonltd.naber.vo.ActivitiesVo;

public class UserBonusExchangeDetailFragment extends Fragment {
    private static final String TAG = UserBonusExchangeDetailFragment.class.getSimpleName();
    public static UserBonusExchangeDetailFragment FRAGMENT = null;
    private ViewHolder holder;

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

//        ActivitiesVo data = (ActivitiesVo)bundle.get("BONUS_DETAIL");
//        Log.i(TAG,data.title);
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
        holder = new ViewHolder(v);




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
        holder.activitiesVo = (ActivitiesVo)getArguments().getSerializable("BONUS_DETAIL");
        holder.titleText.setText(holder.activitiesVo.title);
        holder.needBonusText.setText("所需紅利("+holder.activitiesVo.need_bonus+")");
        ApiManager.userFindAccountInfo(new ThreadCallback(getContext()) {
            @Override
            public void onSuccess(String responseBody) {
                holder.accountInfoVo = Tools.JSONPARSE.fromJson(responseBody, AccountInfoVo.class);
                holder.nameEdit.setText(holder.accountInfoVo.name);
                holder.phoneEdit.setText(holder.accountInfoVo.phone);
                int bonus = IntegerTools.parseInt(holder.accountInfoVo.bonus,0);
                int use_bonus = IntegerTools.parseInt(holder.accountInfoVo.use_bonus,0);
                holder.haveBonusText.setText("擁有紅利 "+ (bonus - use_bonus));
            }

            @Override
            public void onFail(Exception error, String msg) {

            }
        });
    }
    private class ViewHolder {
        TextView titleText,needBonusText,haveBonusText;
        EditText nameEdit,phoneEdit,emailEdit,cityEdit,areaEdit,addressEdit;
        AccountInfoVo accountInfoVo;
        ActivitiesVo activitiesVo;

        ViewHolder(View v) {
            this.titleText = v.findViewById(R.id.titleText);
            this.needBonusText = v.findViewById(R.id.needBonusText);
            this.haveBonusText = v.findViewById(R.id.haveBonusText);
            this.nameEdit = v.findViewById(R.id.nameEdit);
            this.phoneEdit = v.findViewById(R.id.phoneEdit);
            this.emailEdit = v.findViewById(R.id.emailEdit);
            this.cityEdit = v.findViewById(R.id.cityEdit);
            this.areaEdit = v.findViewById(R.id.areaEdit);
            this.addressEdit = v.findViewById(R.id.addressEdit);
        }
    }
    public int STATUS = -1;
    class pickcityChoose implements View.OnClickListener {
        @Override
        public void onClick(final View view) {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int index1, int option2, int options3, View v) {
                    STATUS = 1;

                }
            }) .setTitleSize(20)
                    .setSubmitText("選擇縣市")//确定按钮文字
                    .setCancelText("取消")//取消按钮文字
                    .setTitleBgColor(getResources().getColor(R.color.naber_dividing_line_gray))
                    .setCancelColor(getResources().getColor(R.color.naber_dividing_gray))
                    .setSubmitColor(getResources().getColor(R.color.naber_dividing_gray))
                    .build();

            pvOptions.setOnDismissListener(new com.bigkoo.pickerview.listener.OnDismissListener() {
                @Override
                public void onDismiss(Object o) {
                    if(STATUS == 1){
                        STATUS = -1;
                        // TODO 代表按了確認
                    } else {
                    }
                }
            });
//            pvOptions.setPicker();
            pvOptions.show();
        }
    }
    class pickareaChoose implements View.OnClickListener {
        @Override
        public void onClick(final View view) {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int index1, int option2, int options3, View v) {
                    STATUS = 1;

                }
            }) .setTitleSize(20)
                    .setSubmitText("選擇區域")//确定按钮文字
                    .setCancelText("取消")//取消按钮文字
                    .setTitleBgColor(getResources().getColor(R.color.naber_dividing_line_gray))
                    .setCancelColor(getResources().getColor(R.color.naber_dividing_gray))
                    .setSubmitColor(getResources().getColor(R.color.naber_dividing_gray))
                    .build();

            pvOptions.setOnDismissListener(new com.bigkoo.pickerview.listener.OnDismissListener() {
                @Override
                public void onDismiss(Object o) {
                    if(STATUS == 1){
                        STATUS = -1;
                        // TODO 代表按了確認
                    } else {
                    }
                }
            });
//            pvOptions.setPicker();
            pvOptions.show();
        }
    }
}
