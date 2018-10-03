package com.melonltd.naber.view.user.page;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.common.collect.Lists;
import com.melonltd.naber.R;
import com.melonltd.naber.model.api.ApiManager;
import com.melonltd.naber.model.api.ThreadCallback;
import com.melonltd.naber.util.Tools;
import com.melonltd.naber.view.factory.PageType;
import com.melonltd.naber.view.user.UserMainActivity;
import com.melonltd.naber.view.user.adapter.UserBonusExchangeAdapter;
import com.melonltd.naber.vo.ActivitiesVo;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserBonusExchangeFragment extends Fragment {
    private static final String TAG = UserBonusExchangeFragment.class.getSimpleName();
    public static UserBonusExchangeFragment FRAGMENT = null;

    private UserBonusExchangeAdapter adapter;
    private List<ActivitiesVo> list = Lists.newArrayList();
    public UserBonusExchangeFragment() {
    }

    public Fragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new UserBonusExchangeFragment();
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new UserBonusExchangeAdapter(list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.user_bonus_exchange_page) == null) {
            View v = inflater.inflate(R.layout.fragment_user_bonus_exchange, container, false);
            getViews(v);
            container.setTag(R.id.user_bonus_exchange_page, v);
            return v;
        }
        return (View) container.getTag(R.id.user_bonus_exchange_page);

    }

    private void getViews (View v){
        TextView description = v.findViewById(R.id.descriptionText);
        description.setText("凡是透過NABER訂餐，\n一律回饋消費金額之10%紅利點數\n" +
                            "，並能兌換NABER所提供之獎勵。\n\n" +
                            "* 10月起 開放兌換獎勵及現金折抵\n" +
                            "* 消費10元獲得1點紅利點數\n");

        final BGARefreshLayout bgaRefreshLayout = v.findViewById(R.id.bonusExchangeBGARefreshLayout);
        RecyclerView recyclerView = v.findViewById(R.id.bonusExchangeRecyclerView);

        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(getContext(), true);
        refreshViewHolder.setPullDownRefreshText("Pull");
        refreshViewHolder.setRefreshingText("Pull to refresh");
        refreshViewHolder.setReleaseRefreshText("Pull to refresh");
        refreshViewHolder.setLoadingMoreText("Loading more !");

        bgaRefreshLayout.setRefreshViewHolder(refreshViewHolder);

        bgaRefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                bgaRefreshLayout.endRefreshing();
                doLoadData();
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                bgaRefreshLayout.endLoadingMore();
                return false;
            }
        });

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        // setListener
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(new ItemClick());
    }


    private void doLoadData (){
        list.clear();
        adapter.notifyDataSetChanged();

        ApiManager.getAllActivities(new ThreadCallback(getContext()) {
            @Override
            public void onSuccess(String responseBody) {
                List<ActivitiesVo> activitiesList = Tools.JSONPARSE.fromJsonList(responseBody,ActivitiesVo[].class);
                list.addAll(activitiesList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(Exception error, String msg) {

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        UserMainActivity.changeTabAndToolbarStatus();
        if (UserMainActivity.toolbar != null) {
            UserMainActivity.navigationIconDisplay(true, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UserMainActivity.removeAndReplaceWhere(FRAGMENT, PageType.USER_ACCOUNT, null);
                    UserMainActivity.navigationIconDisplay(false, null);
                }
            });
        }

        this.doLoadData();
    }


    class ItemClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Log.i(TAG, "item tag : " + v.getTag() );
            int index = (int)v.getTag();
            ActivitiesVo data = list.get(index);
            Log.i(TAG, "data "  + data );
            Bundle bundle = new Bundle();
            bundle.putSerializable("BONUS_DETAIL", data);
            UserMainActivity.removeAndReplaceWhere(FRAGMENT, PageType.USER_BONUS_EXCHANGE_DETAIL, bundle);

        }
    }

//    public static class BonusExchange {
//        public String title;
//        public String content;
//        public String type;
//
//        BonusExchange(String title, String content, String type){
//            this.title = title;
//            this.content = content;
//            this.type = type;
//        }
//
//        private static BonusExchange newInstance(String title, String content, String type){
//            return new BonusExchange(title, content, type);
//        }
//    }
}
