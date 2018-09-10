package com.melonltd.naber.view.common;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.melonltd.naber.R;
import com.melonltd.naber.model.api.ApiManager;
import com.melonltd.naber.model.api.ApiUrl;
import com.melonltd.naber.model.api.ThreadCallback;

public class BaseIntroActivity extends AppCompatActivity {
    private SimpleDraweeView img_intro;
    public Button btn_intro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_baseintro);
        getImage();
    }

    private void getImage() {
        img_intro = findViewById(R.id.imageView_intro);
        btn_intro = findViewById(R.id.btn_intro);
        ApiManager.appIntroBulletin(new ThreadCallback(getApplicationContext()) {
            @Override
            public void onSuccess(String responseBody) {
                Uri img_url = Uri.parse(ApiUrl.APP_INTRO_BULLETIN);
                img_intro.setImageURI(img_url);
            }

            @Override
            public void onFail(Exception error, String msg) {

            }
        });
        btn_intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseIntroActivity.this,BaseActivity.class));
            }
        });
    }
}
