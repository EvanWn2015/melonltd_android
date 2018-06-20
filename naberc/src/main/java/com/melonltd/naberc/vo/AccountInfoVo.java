package com.melonltd.naberc.vo;

import com.google.common.base.MoreObjects;

public class AccountInfoVo {
    public String account_uuid;
    public String restaurant_uuid;
    public String password;
    public String name;
    public String email;
    public String phone;
    public String address;
    public String birth_day;
    public String identity;
    public String school_name;
    public String bonus;
    public String level;
    public String enable;
    public String is_login;
    public String login_date;
    public String photo;
    public String photo_type;

    public String device_token;
    public String device_category;

//    public String getBirth_day() {
//        return birth_day;
//    }
//
//    public void setBirth_day(String birth_day) {
//        this.birth_day = birth_day;
//    }
//
//    public String getAccount_uuid() {
//        return account_uuid;
//    }
//
//    public void setAccount_uuid(String account_uuid) {
//        this.account_uuid = account_uuid;
//    }
//
//    public String getRestaurant_uuid() {
//        return restaurant_uuid;
//    }
//
//    public void setRestaurant_uuid(String restaurant_uuid) {
//        this.restaurant_uuid = restaurant_uuid;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getIdentity() {
//        return identity;
//    }
//
//    public void setIdentity(String identity) {
//        this.identity = identity;
//    }
//
//    public String getSchool_name() {
//        return school_name;
//    }
//
//    public void setSchool_name(String school_name) {
//        this.school_name = school_name;
//    }
//
//    public String getBonus() {
//        return bonus;
//    }
//
//    public void setBonus(String bonus) {
//        this.bonus = bonus;
//    }
//
//    public String getLevel() {
//        return level;
//    }
//
//    public void setLevel(String level) {
//        this.level = level;
//    }
//
//    public String getEnable() {
//        return enable;
//    }
//
//    public void setEnable(String enable) {
//        this.enable = enable;
//    }
//
//    public String getIs_login() {
//        return is_login;
//    }
//
//    public void setIs_login(String is_login) {
//        this.is_login = is_login;
//    }
//
//    public String getLogin_date() {
//        return login_date;
//    }
//
//    public void setLogin_date(String login_date) {
//        this.login_date = login_date;
//    }
//
//    public String getPhoto() {
//        return photo;
//    }
//
//    public void setPhoto(String photo) {
//        this.photo = photo;
//    }
//
//    public String getPhoto_type() {
//        return photo_type;
//    }
//
//    public void setPhoto_type(String photo_type) {
//        this.photo_type = photo_type;
//    }
//
//    public String getDevice_token() {
//        return device_token;
//    }
//
//    public void setDevice_token(String device_token) {
//        this.device_token = device_token;
//    }
//
//    public String getDevice_category() {
//        return device_category;
//    }
//
//    public void setDevice_category(String device_category) {
//        this.device_category = device_category;
//    }

    @Override
    public String toString() {
        return MoreObjects
                .toStringHelper(this.getClass())
                .add("account_uuid", account_uuid)
                .add("restaurant_uuid", restaurant_uuid)
                .add("name", name)
                .add("password", password)
                .add("email", email)
                .add("phone", phone)
                .add("address", address)
                .add("birth_day", birth_day)
                .add("identity", identity)
                .add("school_name", school_name)
                .add("bonus", bonus)
                .add("level", level)
                .add("enable", enable)
                .add("is_login", is_login)
                .add("login_date", login_date)
                .add("photo", photo)
                .add("photo_type", photo_type)
                .add("devlcie_token", device_token)
                .add("device_category", device_category)
                .toString();
    }
}
