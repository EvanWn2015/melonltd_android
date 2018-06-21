package com.melonltd.naberc.util;

import android.net.Uri;

public interface UpLoadCallBack {
    public void getUri(Uri uri);
    public void failure(String errMsg);
}
