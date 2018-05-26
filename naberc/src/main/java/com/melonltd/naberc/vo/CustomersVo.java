package com.melonltd.naberc.vo;

//import com.google.firebase.database.IgnoreExtraProperties;

//@IgnoreExtraProperties
public class CustomersVo extends ColumnAbs {

    public String name;
    public String email;
    public String identity;
    public String phoneNumber;
    public String birth;
    public String photoURL;

    public CustomersVo() {
    }

    public CustomersVo(String name, String email, String identity, String phoneNumber, String birth, String photoURL) {
        this.name = name;
        this.email = email;
        this.identity = identity;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
        this.photoURL = photoURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    @Override
    public String toString() {
        return "CustomersVo{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", identity='" + identity + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birth='" + birth + '\'' +
                ", photoURL='" + photoURL + '\'' +
                '}';
    }
}
