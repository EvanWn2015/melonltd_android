package com.melonltd.naberc.model.service;

import android.support.annotation.NonNull;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.melonltd.naberc.model.helper.ThreadCallback;

public abstract class AbstractService {

//    public static void send(@NonNull DataSnapshot dataSnapshot, @NonNull ThreadCallback callback) {
//        callback.onSuccess(dataSnapshot);
//    }

    public static ValueEventListener getValueEventListener(@NonNull final ThreadCallback callback) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null){
                    callback.onFail(DatabaseError.fromException(new RuntimeException("~~~~~")));
                }else{
                    callback.onSuccess(dataSnapshot);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                callback.onFail(error);
            }
        };

    }

    public static ChildEventListener getChildEventListener(@NonNull ThreadCallback callback) {
        return new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

    }
}
