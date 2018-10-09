package com.melonltd.naber.view.common;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.common.base.Strings;
import com.melonltd.naber.BuildConfig;
import com.melonltd.naber.R;
import com.melonltd.naber.model.api.ApiManager;
import com.melonltd.naber.model.api.ClientManager;
import com.melonltd.naber.model.api.ThreadCallback;
import com.melonltd.naber.model.service.SPService;
import com.melonltd.naber.util.Tools;
import com.melonltd.naber.vo.AppVersionLogVo;

public class BaseIntroActivity extends AppCompatActivity {
    private static final String TAG =  BaseIntroActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_baseintro);
        getImage();

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkapp();
    }

    private void checkapp() {
        ApiManager.checkAppVersion(new ThreadCallback(this) {
            @Override
            public void onSuccess(String responseBody) {
//                Log.i(TAG,responseBody);
                AppVersionLogVo vo = Tools.JSONPARSE.fromJson(responseBody, AppVersionLogVo.class);
                if (!vo.version.equals(BuildConfig.VERSION_NAME)) {
                    SPService.removeAll();
                    final String[] action = new String[]{vo.need_upgrade.equals("Y") ? "前往更新" : "我知道了"};
                    new AlertView.Builder()
                            .setContext(BaseIntroActivity.this)
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
                                        }
                                    }
                                }
                            })
                            .build()
                            .setCancelable(false)
                            .show();
                }
            }
            @Override
            public void onFail(Exception error, String msg) {
                Log.i(TAG,msg);
            }
        });
    }

    private void getImage() {
        final SimpleDraweeView img_intro = findViewById(R.id.imageView_intro);
        final Button btn_intro = findViewById(R.id.btn_intro);
        btn_intro.setVisibility(View.GONE);

        ApiManager.appIntroBulletin(new ThreadCallback(getApplicationContext()) {
            @Override
            public void onSuccess(String responseBody) {
                if (Strings.isNullOrEmpty(responseBody)) {
                    startActivity();
                } else {
                    img_intro.setImageURI(Uri.parse(Tools.JSONPARSE.fromJson(responseBody, String.class)));
                }
                btn_intro.setVisibility(View.VISIBLE);
            }
            @Override
            public void onFail(Exception error, String msg) {
                btn_intro.setVisibility(View.VISIBLE);
                startActivity();
            }
        });

        btn_intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity();
            }
        });
    }

    private void startActivity () {
        startActivity(new Intent(BaseIntroActivity.this,BaseActivity.class));
    }
}
