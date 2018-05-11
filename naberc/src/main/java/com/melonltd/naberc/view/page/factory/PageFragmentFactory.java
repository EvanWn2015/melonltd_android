package com.melonltd.naberc.view.page.factory;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.melonltd.naberc.view.page.abs.AbsPageFragment;
import com.melonltd.naberc.view.page.impl.HomeFragment;
import com.melonltd.naberc.view.page.type.PageType;

public class PageFragmentFactory {
    private final static String TAG = PageFragmentFactory.class.getSimpleName();

    public static AbsPageFragment of(@NonNull PageType pageType, Bundle bundle)  {
        try{
            Class<? extends AbsPageFragment> zlass = pageType.toClass();
            AbsPageFragment fragment = (AbsPageFragment) zlass.newInstance().getInstance(bundle);
            Log.d(TAG, zlass.getName());
            return fragment;
        }catch (IllegalAccessException | InstantiationException e){
            Log.e(TAG, e.getMessage());
            return new HomeFragment().getInstance(bundle);
        }

    }
}
