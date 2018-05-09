package com.melonltd.naberc.model.helper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.melonltd.naberc.vo.ColumnAbs;

import java.util.List;

public interface ThreadCallback {

    void onSuccess(DataSnapshot dataSnapshot);

    void onFail(DatabaseError error);


}
