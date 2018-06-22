package com.melonltd.naber.view.intro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.melonltd.naber.model.service.SPService;
import com.melonltd.naber.R;

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(Slide.newInstance(R.layout.intro_1));
        addSlide(Slide.newInstance(R.layout.intro_1));
        addSlide(Slide.newInstance(R.layout.intro_1));
        addSlide(Slide.newInstance(R.layout.intro_1));
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        SPService.setIsFirstLogin(false);
        finish();
    }
}
