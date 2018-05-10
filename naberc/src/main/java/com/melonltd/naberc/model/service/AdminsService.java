package com.melonltd.naberc.model.service;

import com.google.firebase.database.DatabaseReference;
import com.melonltd.naberc.model.helper.ConnectHelper;
import com.melonltd.naberc.model.helper.ThreadCallback;

public class AdminsService extends AbstractService {
    private static final String TAG = AdminsService.class.getSimpleName();
    private static DatabaseReference reference = ConnectHelper.getAdminsReference();

    public static void findByKey(String key, final ThreadCallback callback) {
        reference.child(key).addValueEventListener(getValueEventListener(callback));
    }
}
