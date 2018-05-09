package com.melonltd.naberc.util;

import com.google.common.base.Strings;

public class VerifyUtil {

    public static boolean email(String mail) {
        if (Strings.isNullOrEmpty(mail)) {
            return false;
        }
        return android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches();
    }

    public static boolean phoneNumber(String number) {
        if (Strings.isNullOrEmpty(number)) {
            return false;
        }
        return android.util.Patterns.PHONE.matcher(number).matches();
    }
}
