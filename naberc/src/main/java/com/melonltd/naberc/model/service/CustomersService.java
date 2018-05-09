package com.melonltd.naberc.model.service;

import com.google.firebase.database.DatabaseReference;
import com.melonltd.naberc.model.helper.ConnectHelper;
import com.melonltd.naberc.model.helper.ThreadCallback;

public abstract class CustomersService extends AbstractService {
    private static final String TAG = CustomersService.class.getSimpleName();
    private static DatabaseReference reference = ConnectHelper.getCustomersReference();

    public static void findByEmail(String mail, final ThreadCallback callback) {
        reference.orderByChild("email").equalTo(mail).addValueEventListener(getValueEventListener(callback));
    }

    public static void findByKey(String key, final ThreadCallback callback) {
        reference.child(key).addValueEventListener(getValueEventListener(callback));
    }

    public static void findAll(final ThreadCallback callback) {
        reference.addValueEventListener(getValueEventListener(callback));
    }

    public static void delete(String key, final ThreadCallback callback) {
//        reference.child()
//        .equalTo(key).addValueEventListener(getValueEventListener(callback));
    }
}
