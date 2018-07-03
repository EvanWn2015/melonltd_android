package com.melonltd.naber.view.factory;

import android.support.v4.app.Fragment;

import com.melonltd.naber.R;
import com.melonltd.naber.view.common.page.LoginFragment;
import com.melonltd.naber.view.common.page.RecoverPasswordFragment;
import com.melonltd.naber.view.common.page.RegisteredFragment;
import com.melonltd.naber.view.common.page.RegisteredSellerFragment;
import com.melonltd.naber.view.common.page.VerifySMSFragment;
import com.melonltd.naber.view.seller.page.SellerCategoryListFragment;
import com.melonltd.naber.view.seller.page.SellerFoodListFragment;
import com.melonltd.naber.view.seller.page.SellerDetailFragment;
import com.melonltd.naber.view.seller.page.SellerFoodEditFragment;
import com.melonltd.naber.view.seller.page.SellerOrderLogsDetailFragment;
import com.melonltd.naber.view.seller.page.SellerOrdersFragment;
import com.melonltd.naber.view.seller.page.SellerOrdersLogsFragment;
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


/**
 * LOGIN 登入 100
 * REGISTERED 註冊 101
 * REGISTERED_SELLER 賣家註冊 102
 * VERIFY_SMS 驗證短訊 4
 * HOME 首頁 0
 * RESTAURANT 餐館 1
 * SHOPPING_CART 購物車 2
 * HISTORY 紀錄 3
 * SET_UP 設定 4
 * ACCOUNT_DETAIL 帳號細節與編輯
 * (int id or tag , int positionByPageTab, Class<T> zlass)
 */
public enum PageType {
    // common
    LOGIN(R.string.common_page_login_title, 100, LoginFragment.class),
    RECOVER_PASSWORD(R.string.common_page_recover_password_title, 101, RecoverPasswordFragment.class),
    REGISTERED_USER(R.string.user_page_registered_title, 103, RegisteredFragment.class),
    VERIFY_SMS(R.string.user_page_verify_sms_title, 105, VerifySMSFragment.class),
    REGISTERED_SELLER(R.string.seller_page_registered_title, 104, RegisteredSellerFragment.class),
    // user

    HOME(R.string.menu_home_btn, 0, HomeFragment.class),
    RESTAURANT(R.string.menu_restaurant_btn, 1, RestaurantFragment.class),
    RESTAURANT_DETAIL(R.string.user_page_restaurant_detail_title, 1, RestaurantDetailFragment.class),
    CATEGORY_MENU(R.string.user_page_category_menu_title, 1, CategoryMenuFragment.class),
    MENU_DETAIL(R.string.user_page_menu_detail_title, 1, MenuDetailFragment.class),
    SHOPPING_CART(R.string.menu_shopping_cart_btn, 2, ShoppingCartFragment.class),
    SUBMIT_ORDER(R.string.user_page_submit_order_title, 2, SubmitOrdersFragment.class),
    HISTORY(R.string.menu_history_btn, 3, HistoryFragment.class),
    ORDER_DETAIL(R.string.user_page_order_detail_title, 3, OrderDetailFragment.class),
    SET_UP(R.string.menu_set_up_btn, 4, SetUpFragment.class),
    ACCOUNT_DETAIL(R.string.user_page_account_detail_title, 4, AccountDetailFragment.class),
    SIMPLE_INFO(R.string.user_page_simple_info_title, 4, SimpleInformationFragment.class),
    RESET_PASSWORD(R.string.common_page_reset_password_title, 4, ResetPasswordFragment.class),

    // seller
    SELLER_SEARCH(R.string.seller_menu_search_btn, 0, SellerSearchFragment.class),
    SELLER_ORDERS(R.string.seller_menu_orders_btn, 1, SellerOrdersFragment.class),
    SELLER_STAT(R.string.seller_menu_stat_btn, 2, SellerStatFragment.class),
    SELLER_ORDERS_LOGS(R.string.seller_menu_orders_logs, 2, SellerOrdersLogsFragment.class),
    SELLER_ORDERS_LOGS_DETAIL(R.string.seller_menu_orders_logs_detail, 2, SellerOrderLogsDetailFragment.class),
    SELLER_CATEGORY_LIST(R.string.seller_menu_category_list_btn, 3, SellerCategoryListFragment.class),
    SELLER_FOOD_LIST(R.string.seller_menu_food_list, 3, SellerFoodListFragment.class),
    SELLER_FOOD_EDIT(R.string.seller_menu_food_edit, 3, SellerFoodEditFragment.class),
    SELLER_SET_UP(R.string.seller_menu_set_up_btn, 4, SellerSetUpFragment.class),
    SELLER_DETAIL(R.string.seller_menu_seller_detail, 4, SellerDetailFragment.class),
    SELLER_SIMPLE_INFO(R.string.seller_menu_simple_information, 4, SellerSimpleInformationFragment.class);



    private final int id;
    private final int position;
    private final Class zlass;

    PageType(int id, int position, Class zlass) {
        this.id = id;

        this.position = position;
        this.zlass = zlass;
    }

    public boolean equals(int id) {
        return this.id == id;
    }

    public boolean equalsPosition(int position) {
        return this.position == position;
    }

    public static PageType ofId(int id) {
        for (PageType entity : values()) {
            if (entity.equals(id)) {
                return entity;
            }
        }
        return HOME;
    }

    public int getId (){
        return id;
    }

    public static PageType ofPosition(int position) {
        for (PageType entity : values()) {
            if (entity.equalsPosition(position)) {
                return entity;
            }
        }
        return HOME;
    }

    public static PageType ofName(String name) {
        for (PageType entity : values()) {
            if (entity.name().equals(name)) {
                return entity;
            }
        }
        return HOME;
    }

    public static int equalsPositionByName(String name, boolean checkMaxTab) {
        for (PageType entity : values()) {
            if (entity.name().equals(name)) {
                if (checkMaxTab) {
                    return entity.position < 10 ? entity.position : SET_UP.position;
                } else {
                    return entity.position;
                }

            }
        }
        return HOME.position;
    }

    public static int equalsPositionByName(String name) {
        for (PageType entity : values()) {
            if (entity.name().equals(name)) {
                return entity.position;
            }
        }
        return 100;
    }


    public static int equalsIdByName(String name) {
        for (PageType entity : values()) {
            if (entity.name().equals(name)) {
                return entity.id;
            }
        }
        return 0;
    }

    public static boolean isMainPage(String name) {
        for (PageType entity : values()) {
            if (entity.position < 4) {
                return true;
            }
        }
        return true;
    }


    public Class toClass() {
        return this.zlass.asSubclass(Fragment.class);
    }
}
