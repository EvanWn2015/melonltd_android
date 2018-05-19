package com.melonltd.naberc.view.user.page.type;

import com.melonltd.naberc.R;
import com.melonltd.naberc.view.user.page.impl.AccountDetailFragment;
import com.melonltd.naberc.view.user.page.impl.OrderDetailFragment;
import com.melonltd.naberc.view.common.page.impl.RecoverPasswordFragment;
import com.melonltd.naberc.view.user.page.impl.RegisteredSellerFragment;
import com.melonltd.naberc.view.user.page.abs.AbsPageFragment;
import com.melonltd.naberc.view.user.page.impl.HistoryFragment;
import com.melonltd.naberc.view.user.page.impl.HomeFragment;
import com.melonltd.naberc.view.common.page.impl.LoginFragment;
import com.melonltd.naberc.view.user.page.impl.RegisteredFragment;
import com.melonltd.naberc.view.common.page.impl.ResetPasswordFragment;
import com.melonltd.naberc.view.user.page.impl.RestaurantFragment;
import com.melonltd.naberc.view.user.page.impl.SetUpFragment;
import com.melonltd.naberc.view.user.page.impl.ShoppingCartFragment;
import com.melonltd.naberc.view.user.page.impl.SimpleInformationFragment;
import com.melonltd.naberc.view.user.page.impl.VerifySMSFragment;

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
    RECOVER_PASSWORD(R.string.common_page_reset_password_title, 101, RecoverPasswordFragment.class),
    RESET_PASSWORD(R.string.common_page_reset_password_title,102,ResetPasswordFragment.class),
    // user
    REGISTERED(R.string.user_page_registered_title, 103, RegisteredFragment.class),
    VERIFY_SMS(R.string.user_page_verify_sms_title, 105, VerifySMSFragment.class),
    HOME(R.string.menu_home_btn, 0, HomeFragment.class),
    RESTAURANT(R.string.menu_restaurant_btn, 1, RestaurantFragment.class),
    SHOPPING_CART(R.string.menu_shopping_cart_btn, 2, ShoppingCartFragment.class),
    HISTORY(R.string.menu_history_btn, 3, HistoryFragment.class),
    ORDER_DETAIL(R.string.user_page_order_detail_title, 3, OrderDetailFragment.class),
    SET_UP(R.string.menu_set_up_btn, 4, SetUpFragment.class),
    ACCOUNT_DETAIL(R.string.user_page_account_detail_title, 4, AccountDetailFragment.class),
    SIMPLE_INFO(R.string.user_page_simple_info_title, 4, SimpleInformationFragment.class),
    // seller
    REGISTERED_SELLER(R.string.seller_page_registered_title, 104, RegisteredSellerFragment.class);

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

    public static boolean isMainPage(String name) {
        for (PageType entity : values()) {
            if (entity.position < 4) {
                return true;
            }
        }
        return true;
    }


    public Class toClass() {
        return this.zlass.asSubclass(AbsPageFragment.class);
    }
}
