package com.melonltd.naberc.view.customize.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.melonltd.naberc.view.customize.BannerFragment;

import java.util.List;

public class BannerFragmentAdapter extends FragmentPagerAdapter {
    private static final  String TAG = BannerFragmentAdapter.class.getSimpleName();
    private List<BannerFragment> bannerFragmentLiat;
    private  FragmentManager fm ;

    public BannerFragmentAdapter(FragmentManager fm, List<BannerFragment> bannerFragmentLiat) {
        super(fm);
        this.fm = fm;
        this.bannerFragmentLiat = bannerFragmentLiat;
    }

    public BannerFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return bannerFragmentLiat.get(position);
    }

    @Override
    public int getCount() {
        return bannerFragmentLiat.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        Fragment fragment = (Fragment) super.instantiateItem(container, position);
//        this.fm.beginTransaction().show(fragment).commit();
//        return fragment;
        return super.instantiateItem(container,position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        Fragment fragment = getItem(position);
//        fm.beginTransaction().hide(fragment).commit();
        super.destroyItem(container, position, object);
    }
}
