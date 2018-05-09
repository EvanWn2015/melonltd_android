package com.melonltd.naberc.view.page.type;

import com.melonltd.naberc.view.page.impl.LoginFragment;
import com.melonltd.naberc.view.page.abs.AbsPageFragment;
import com.melonltd.naberc.view.page.impl.HomeFragment;

public enum PageType {

    HOME(0, HomeFragment.class),
    LOGIN(0, LoginFragment.class);

//    E_WALLET(3, EWalletFragment.class),
//    ATTRACTIONS(R.id.nav_attractions, AttractionsFragment.class),
//    RIDE_RECORD(R.id.nav_ride_record, RideRecordFragment.class),
//    VIP(R.id.nav_vip, VipFragment.class),
//    MARKETING_ACTIVITIES(R.id.nav_marketing_activities, MarketingActivitiesFragment.class),
//    MEMBER_MANAGEMENT(R.id.nav_member_management, MemberManagementFragment.class),
//    HELP(R.id.nav_help, HelpFragment.class);

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

    public Class toClass() {
        return this.zlass.asSubclass(AbsPageFragment.class);
    }
}
