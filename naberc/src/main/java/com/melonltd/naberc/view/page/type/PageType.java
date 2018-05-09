package com.melonltd.naberc.view.page.type;

import com.melonltd.naberc.R;
import com.melonltd.naberc.view.page.impl.HistoryFragment;
import com.melonltd.naberc.view.page.impl.LoginFragment;
import com.melonltd.naberc.view.page.abs.AbsPageFragment;
import com.melonltd.naberc.view.page.impl.HomeFragment;
import com.melonltd.naberc.view.page.impl.SetUpFragment;
import com.melonltd.naberc.view.page.impl.ShoppingCartFragment;

public enum PageType {

    LOGIN(R.id.loginBtn, LoginFragment.class),
    HOME(R.id.menuHomeBtn, HomeFragment.class),
    SHOPPING_CART(R.id.menuShoppingCartBtn, ShoppingCartFragment.class),
    HISTORY(R.id.menuHistoryBtn, HistoryFragment.class),
    SET_UP(R.id.menuSetUpBtn, SetUpFragment.class);

    private final int id;
    private final Class zlass;

    PageType(int id, Class zlass) {
        this.id = id;
        this.zlass = zlass;
    }

    public boolean equals(int id) {
        return this.id == id;
    }

    public static PageType ofId(int id) {
        for (PageType entity : values()) {
            if (entity.equals(id)) {
                return entity;
            }
        }
        return null;
    }


    public static PageType equalsName(String name){
        for (PageType entity : values()) {
            if (entity.name().equals(name)) {
                return entity;
            }
        }
        return null;
    }

    public Class toClass() {
        return this.zlass.asSubclass(AbsPageFragment.class);
    }
}
