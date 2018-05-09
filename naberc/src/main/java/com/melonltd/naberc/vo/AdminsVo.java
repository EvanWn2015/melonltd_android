package com.melonltd.naberc.vo;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class AdminsVo {
    public String name;
    public String email;
    private String photoURL;
    private String restaurantId;

    public AdminsVo() {
    }

    public AdminsVo(String name, String email, String photoURL, String restaurantId) {
        this.name = name;
        this.email = email;
        this.photoURL = photoURL;
        this.restaurantId = restaurantId;
    }
}
