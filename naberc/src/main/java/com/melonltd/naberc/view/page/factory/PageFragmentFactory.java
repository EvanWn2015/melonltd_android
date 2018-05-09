package com.melonltd.naberc.view.page.factory;

import android.os.Bundle;
import android.util.Log;

import com.melonltd.naberc.view.page.abs.AbsPageFragment;
import com.melonltd.naberc.view.page.type.PageType;

public class PageFragmentFactory {

    private final static String TAG = PageFragmentFactory.class.getSimpleName();

    public static AbsPageFragment of(PageType menuType, Bundle bundle)  {

        try{
            Class<? extends AbsPageFragment> zlass = menuType.toClass();
            AbsPageFragment fragment = (AbsPageFragment) zlass.newInstance().newInstance(bundle);
            Log.d(TAG, zlass.getName());
            return fragment;
        }catch (IllegalAccessException | InstantiationException e){
            Log.e(TAG, e.getMessage());
            return null;
        }

    }
}
