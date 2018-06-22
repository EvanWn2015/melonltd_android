package com.melonltd.naber.util;

import android.net.Uri;

public interface UpLoadCallBack {
    public void getUri(Uri uri);
    public void failure(String errMsg);
}
