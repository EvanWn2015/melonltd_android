package com.melonltd.naber.view.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.melonltd.naber.BuildConfig;
import com.melonltd.naber.R;
import com.melonltd.naber.model.api.ApiManager;
import com.melonltd.naber.model.api.ThreadCallback;
import com.melonltd.naber.model.bean.Model;
import com.melonltd.naber.model.constant.NaberConstant;
import com.melonltd.naber.model.service.SPService;
import com.melonltd.naber.model.type.Identity;
import com.melonltd.naber.util.Tools;
import com.melonltd.naber.view.common.page.LoginFragment;
import com.melonltd.naber.view.common.page.RecoverPasswordFragment;
import com.melonltd.naber.view.common.page.RegisteredFragment;
import com.melonltd.naber.view.common.page.RegisteredSellerFragment;
import com.melonltd.naber.view.factory.PageFragmentFactory;
import com.melonltd.naber.view.factory.PageType;
import com.melonltd.naber.view.seller.SellerMainActivity;
import com.melonltd.naber.view.user.UserMainActivity;
import com.melonltd.naber.vo.AppVersionLogVo;

import java.util.Date;

public class BaseActivity extends BaseCore {
    private static final String TAG = BaseActivity.class.getSimpleName();
    public static Context context;
    public static Toolbar toolbar;
    public static FragmentManager FM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        context = this;
        UserMainActivity.clearAllFragment();
        FM = getSupportFragmentManager();
        getViews();
    }

    private void getViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (FRAGMENT_TAG.equals("VERIFY_SMS")){
            return;
        }

        // 檢查app 版本 機制
        new Handler().postDelayed(new CheckApp(), 1000);
    }

    class CheckApp implements Runnable {

        @Override
        public void run() {
            ApiManager.checkAppVersion(new ThreadCallback(context) {
                @Override
                public void onSuccess(String responseBody) {
                    AppVersionLogVo vo = Tools.JSONPARSE.fromJson(responseBody, AppVersionLogVo.class);
                    if (!vo.version.equals(BuildConfig.VERSION_NAME)) {
                        final String[] action = new String[]{vo.need_upgrade.equals("Y") ? "前往更新" : "我知道了"};
                        new AlertView.Builder()
                                .setContext(context)
                                .setTitle("NABER 系統提示")
                                .setMessage("您目前的APP版本(V" + BuildConfig.VERSION_NAME + ")，不是最新版本(V" + vo.version + ")，為了您的使用權益，\n請前往Google Play更新您的App")
                                .setStyle(AlertView.Style.Alert)
                                .setOthers(action)
                                .setOnItemClickListener(new OnItemClickListener() {
                                    @Override
                                    public void onItemClick(Object o, int position) {
                                        if (position >= 0) {
                                            if (action[position].equals("前往更新")) {
                                                // TODO go to google play
                                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                                intent.setData(Uri.parse("market://details?id=com.melonltd.naber"));
                                                startActivity(intent);
                                            }else if (action[position].equals("我知道了")) {
                                                new Handler().postDelayed(new StartUse(), 500);
                                            }
                                        }
                                    }
                                })
                                .build()
                                .setCancelable(false)
                                .show();
                    }else {
                        new Handler().postDelayed(new StartUse(), 500);
                    }
                }
                @Override
                public void onFail(Exception error, String msg) {
                    Log.i(TAG, msg);
                    new Handler().postDelayed(new StartUse(), 500);
                }
            });
        }
    }


    class StartUse implements Runnable {
        @Override
        public void run() {
            View extView = getLayoutInflater().inflate(R.layout.common_intro, null);
            TextView title = extView.findViewById(R.id.title);
            TextView body = extView.findViewById(R.id.body);
            title.setText("用NABER訂餐享10%紅利回饋紅利兌換項目");
            body.setText("    10點 -> 下次消費折抵3元 (無上限)\n"+
                        "  500點 -> KKBOX 30天 (點數卡)\n"+
                        "  667點 -> 中壢威尼斯 (電影票)\n"+
                        "  767點 -> 桃園IN89統領 (電影票)\n"+
                        "  767點 -> 美麗華影城 (電影票)\n"+
                        "  800點 -> LINE 240P (點數卡)\n"+
                        "  834點 -> SBC星橋 (電影票)\n"+
                        "  834點 -> 威秀影城(電影票)\n"+
                        "1000點 -> SOGO 300(禮卷)\n"+
                        "1000點 -> MYCARD 300P (點數卡)\n\n"+
                        "* 10月開始兌換獎勵及現金折抵\n"+
                        "* 消費10元獲得1點紅利點數\n");

            new AlertView.Builder()
                    .setContext(context)
                    .setStyle(AlertView.Style.Alert)
                    .setOthers(new String[]{"開始使用"})
                    .setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            if (position == 0) {
                                long limit = SPService.getLoginLimit();
                                long now = new Date().getTime();
                                if (now - NaberConstant.REMEMBER_DAY < limit) {
                                    String identity = SPService.getIdentity();
                                    if (Identity.getUserValues().contains(Identity.of(identity))) {
                                        Model.USER_CACHE_SHOPPING_CART = SPService.getUserCacheShoppingCarData();
                                        startActivity(new Intent(context, UserMainActivity.class));
                                    } else if (Identity.SELLERS.equals(Identity.of(identity))) {
                                        startActivity(new Intent(context, SellerMainActivity.class));
                                    } else {
                                        SPService.removeAll();
                                        FRAGMENT_TAG = PageType.LOGIN.name();
                                        Fragment fragment = PageFragmentFactory.of(PageType.LOGIN, null);
                                        getSupportFragmentManager().beginTransaction().replace(R.id.baseContainer, fragment, PageType.LOGIN.toClass().getSimpleName()).addToBackStack(fragment.toString()).commit();
                                    }
                                } else {
                                    FRAGMENT_TAG = PageType.LOGIN.name();
                                    Fragment fragment = PageFragmentFactory.of(PageType.LOGIN, null);
                                    getSupportFragmentManager().beginTransaction().replace(R.id.baseContainer, fragment).addToBackStack(fragment.toString()).commit();
                                }
                            }
                        }
                    })
                    .build()
                    .addExtView(extView)
                    .setCancelable(false)
                    .show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        LoginFragment.FRAGMENT = null;
        RecoverPasswordFragment.FRAGMENT = null;
        RegisteredFragment.FRAGMENT = null;
        RegisteredSellerFragment.FRAGMENT = null;
//        VerifySMSFragment.FRAGMENT = null;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static void navigationIconDisplay(boolean show, View.OnClickListener listener) {
        if (!show) {
            toolbar.setNavigationIcon(null);
        } else {
            toolbar.setNavigationIcon(context.getResources().getDrawable(R.drawable.naber_back_icon));
        }
        toolbar.setNavigationOnClickListener(listener);
    }

    public static void changeToolbarStatus() {
        toolbar.setTitle(context.getResources().getString(PageType.equalsIdByName(FRAGMENT_TAG)));
    }


    public static void removeAndReplaceWhere (Fragment fm, PageType pageType, Bundle bundle){
        FRAGMENT_TAG = pageType.name();
        Fragment fragment = PageFragmentFactory.of(pageType, bundle);
        FM.beginTransaction()
                .remove(fm)
                .replace(R.id.baseContainer, fragment)
                .addToBackStack(pageType.toClass().getSimpleName())
                .commit();
    }
}
