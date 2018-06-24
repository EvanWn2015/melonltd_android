package com.melonltd.naber.view.intro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro2;
import com.melonltd.naber.R;
import com.melonltd.naber.model.service.SPService;

public class IntroActivity extends AppIntro2 {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFadeAnimation();
        showSkipButton(false);
        showStatusBar(false);
        showSkipButton(false);
        showDoneButton(true);

        addSlide(Slide.newInstance(R.layout.intro_1, R.drawable.intro_01));
        addSlide(Slide.newInstance(R.layout.intro_1, R.drawable.intro_02));
        addSlide(Slide.newInstance(R.layout.intro_1, R.drawable.intro_03));
        addSlide(Slide.newInstance(R.layout.intro_1, R.drawable.intro_04));

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
