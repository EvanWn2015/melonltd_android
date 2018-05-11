package com.melonltd.naberc.view.page.type;

import com.melonltd.naberc.R;
import com.melonltd.naberc.view.page.abs.AbsPageFragment;
import com.melonltd.naberc.view.page.impl.HistoryFragment;
import com.melonltd.naberc.view.page.impl.HomeFragment;
import com.melonltd.naberc.view.page.impl.LoginFragment;
import com.melonltd.naberc.view.page.impl.RegisteredFragment;
import com.melonltd.naberc.view.page.impl.RestaurantFragment;
import com.melonltd.naberc.view.page.impl.SetUpFragment;
import com.melonltd.naberc.view.page.impl.ShoppingCartFragment;

/**
 * LOGIN 登入
 * REGISTERED 註冊
 * HOME 首頁
 * RESTAURANT 餐館
 * SHOPPING_CART 購物車
 * HISTORY 紀錄
 * SET_UP 設定
 */
public enum PageType {

    LOGIN(R.id.loginBtn, 99, LoginFragment.class),
    REGISTERED(R.id.registeredBtn, 999, RegisteredFragment.class),
    HOME(R.id.menuHomeBtn, 0, HomeFragment.class),
    RESTAURANT(R.id.menuRestaurantBtn, 1, RestaurantFragment.class),
    SHOPPING_CART(R.id.menuShoppingCartBtn, 2, ShoppingCartFragment.class),
    HISTORY(R.id.menuHistoryBtn, 3, HistoryFragment.class),
    SET_UP(R.id.menuSetUpBtn, 4, SetUpFragment.class);

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

//    public static PageType ofId(int id) {
//        for (PageType entity : values()) {
//            if (entity.equals(id)) {
//                return entity;
//            }
//        }
//        return HOME;
//    }

    public static PageType ofPosition(int position) {
        for (PageType entity : values()) {
            if (entity.equalsPosition(position)) {
                return entity;
            }
        }
        return HOME;
    }


    public static PageType equalsName(String name) {
        for (PageType entity : values()) {
            if (entity.name().equals(name)) {
                return entity;
            }
        }
        return HOME;
    }

    public Class toClass() {
        return this.zlass.asSubclass(AbsPageFragment.class);
    }
}
