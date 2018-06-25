package com.melonltd.naber.view.factory;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.melonltd.naber.view.common.page.LoginFragment;
import com.melonltd.naber.view.common.page.RecoverPasswordFragment;
import com.melonltd.naber.view.common.page.RegisteredFragment;
import com.melonltd.naber.view.common.page.RegisteredSellerFragment;
import com.melonltd.naber.view.common.page.VerifySMSFragment;
import com.melonltd.naber.view.seller.page.SellerCategoryListFragment;
import com.melonltd.naber.view.seller.page.SellerDetailFragment;
import com.melonltd.naber.view.seller.page.SellerMenuEditFragment;
import com.melonltd.naber.view.seller.page.SellerOrderLogsDetailFragment;
import com.melonltd.naber.view.seller.page.SellerOrdersFragment;
import com.melonltd.naber.view.seller.page.SellerOrdersLogsFragment;
import com.melonltd.naber.view.seller.page.SellerRestaurantFragment;
import com.melonltd.naber.view.seller.page.SellerSearchFragment;
import com.melonltd.naber.view.seller.page.SellerSetUpFragment;
import com.melonltd.naber.view.seller.page.SellerSimpleInformationFragment;
import com.melonltd.naber.view.seller.page.SellerStatFragment;
import com.melonltd.naber.view.user.page.AccountDetailFragment;
import com.melonltd.naber.view.user.page.CategoryMenuFragment;
import com.melonltd.naber.view.user.page.HistoryFragment;
import com.melonltd.naber.view.user.page.HomeFragment;
import com.melonltd.naber.view.user.page.MenuDetailFragment;
import com.melonltd.naber.view.user.page.OrderDetailFragment;
import com.melonltd.naber.view.user.page.ResetPasswordFragment;
import com.melonltd.naber.view.user.page.RestaurantDetailFragment;
import com.melonltd.naber.view.user.page.RestaurantFragment;
import com.melonltd.naber.view.user.page.SetUpFragment;
import com.melonltd.naber.view.user.page.ShoppingCartFragment;
import com.melonltd.naber.view.user.page.SimpleInformationFragment;
import com.melonltd.naber.view.user.page.SubmitOrdersFragment;

public class PageFragmentFactory {
    private final static String TAG = PageFragmentFactory.class.getSimpleName();

    public static Fragment of(@NonNull PageType pageType, Bundle bundle) {

        switch (pageType) {
            case LOGIN:
                return new LoginFragment().getInstance(bundle);
            case REGISTERED_USER:
                return new RegisteredFragment().getInstance(bundle);
            case REGISTERED_SELLER:
                return new RegisteredSellerFragment().getInstance(bundle);
            case RECOVER_PASSWORD:
                return new RecoverPasswordFragment().getInstance(bundle);
            case VERIFY_SMS:
                return new VerifySMSFragment().getInstance(bundle);

            case HOME:
                return new HomeFragment().getInstance(bundle);
            case SET_UP:
                return new SetUpFragment().getInstance(bundle);
            case ACCOUNT_DETAIL:
                return new AccountDetailFragment().getInstance(bundle);
            case HISTORY:
                return new HistoryFragment().getInstance(bundle);
            case ORDER_DETAIL:
                return new OrderDetailFragment().getInstance(bundle);
            case RESTAURANT:
                return new RestaurantFragment().getInstance(bundle);
            case RESTAURANT_DETAIL:
                return new RestaurantDetailFragment().getInstance(bundle);
            case CATEGORY_MENU:
                return new CategoryMenuFragment().getInstance(bundle);
            case SHOPPING_CART:
                return new ShoppingCartFragment().getInstance(bundle);
            case SUBMIT_ORDER:
                return new SubmitOrdersFragment().getInstance(bundle);
            case SIMPLE_INFO:
                return new SimpleInformationFragment().getInstance(bundle);
            case RESET_PASSWORD:
                return new ResetPasswordFragment().getInstance(bundle);
            case MENU_DETAIL:
                return new MenuDetailFragment().getInstance(bundle);


            // seller
            case SELLER_SEARCH:
                return new SellerSearchFragment().getInstance(bundle);
            case SELLER_ORDERS:
                return new SellerOrdersFragment().getInstance(bundle);
            case SELLER_STAT:
                return new SellerStatFragment().getInstance(bundle);
            case SELLER_ORDERS_LOGS:
                return new SellerOrdersLogsFragment().getInstance(bundle);
            case SELLER_ORDERS_LOGS_DETAIL:
                return new SellerOrderLogsDetailFragment().getInstance(bundle);
            case SELLER_RESTAURANT:
                return new SellerRestaurantFragment().getInstance(bundle);
            case SELLER_CATEGORY_LIST:
                return new SellerCategoryListFragment().getInstance(bundle);
            case SELLER_MENU_EDIT:
                return new SellerMenuEditFragment().getInstance(bundle);
            case SELLER_SET_UP:
                return new SellerSetUpFragment().getInstance(bundle);
            case SELLER_SIMPLE_INFO:
                return new SellerSimpleInformationFragment().getInstance(bundle);
            case SELLER_DETAIL:
                return new SellerDetailFragment().getInstance(bundle);
            default:
                return new HomeFragment().getInstance(bundle);
        }

    }
}
