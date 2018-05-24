package com.melonltd.naberc.view.common.factory;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.melonltd.naberc.view.user.page.impl.CategoryMenuFragment;
import com.melonltd.naberc.view.user.page.impl.MenuDetailFragment;
import com.melonltd.naberc.view.user.page.impl.RestaurantDetailFragment;
import com.melonltd.naberc.view.common.page.impl.ResetPasswordFragment;
import com.melonltd.naberc.view.user.page.impl.AccountDetailFragment;
import com.melonltd.naberc.view.user.page.impl.OrderDetailFragment;
import com.melonltd.naberc.view.common.page.impl.RecoverPasswordFragment;
import com.melonltd.naberc.view.user.page.impl.RegisteredSellerFragment;
import com.melonltd.naberc.view.common.abs.AbsPageFragment;
import com.melonltd.naberc.view.user.page.impl.HistoryFragment;
import com.melonltd.naberc.view.user.page.impl.HomeFragment;
import com.melonltd.naberc.view.common.page.impl.LoginFragment;
import com.melonltd.naberc.view.user.page.impl.RegisteredFragment;
import com.melonltd.naberc.view.user.page.impl.RestaurantFragment;
import com.melonltd.naberc.view.user.page.impl.SetUpFragment;
import com.melonltd.naberc.view.user.page.impl.ShoppingCartFragment;
import com.melonltd.naberc.view.user.page.impl.SimpleInformationFragment;
import com.melonltd.naberc.view.user.page.impl.SubmitOrdersFragment;
import com.melonltd.naberc.view.user.page.impl.VerifySMSFragment;
import com.melonltd.naberc.view.common.type.PageType;

public class PageFragmentFactory {
    private final static String TAG = PageFragmentFactory.class.getSimpleName();

    public static AbsPageFragment of(@NonNull PageType pageType, Bundle bundle) {

        switch (pageType) {
            case HOME:
                return new HomeFragment().getInstance(bundle);
            case LOGIN:
                return new LoginFragment().getInstance(bundle);
            case SET_UP:
                return new SetUpFragment().getInstance(bundle);
            case ACCOUNT_DETAIL:
                return new AccountDetailFragment().getInstance(bundle);
            case HISTORY:
                return new HistoryFragment().getInstance(bundle);
            case ORDER_DETAIL:
                return new OrderDetailFragment().getInstance(bundle);
            case REGISTERED:
                return new RegisteredFragment().getInstance(bundle);
            case RESTAURANT:
                return new RestaurantFragment().getInstance(bundle);
            case RESTAURANT_DETAIL:
                return new RestaurantDetailFragment().getInstance(bundle);
            case CATEGORY_MENU:
                return new CategoryMenuFragment().getInstance(bundle);
            case VERIFY_SMS:
                return new VerifySMSFragment().getInstance(bundle);
            case SHOPPING_CART:
                return new ShoppingCartFragment().getInstance(bundle);
            case REGISTERED_SELLER:
                return new RegisteredSellerFragment().getInstance(bundle);
            case SUBMIT_ORDER:
                return new SubmitOrdersFragment().getInstance(bundle);
            case SIMPLE_INFO:
                return new SimpleInformationFragment().getInstance(bundle);
            case RECOVER_PASSWORD:
                return new RecoverPasswordFragment().getInstance(bundle);
            case RESET_PASSWORD:
                return new ResetPasswordFragment().getInstance(bundle);
            case MENU_DETAIL:
                return new MenuDetailFragment().getInstance(bundle);
            default:
                return new HomeFragment().getInstance(bundle);
        }
//        try{
//            Class<? extends AbsPageFragment> zlass = pageType.toClass();
//            AbsPageFragment fragment = (AbsPageFragment) zlass.newInstance().getInstance(bundle);
//            Log.d(TAG, zlass.getName());
//            return fragment;
//        }catch (IllegalAccessException | InstantiationException e){
//            Log.e(TAG, e.getMessage());
//            return new HomeFragment().getInstance(bundle);
//        }

    }
}
