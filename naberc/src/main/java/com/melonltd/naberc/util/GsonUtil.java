package com.melonltd.naberc.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class GsonUtil {
    private static Gson GSON = new Gson();

    public final static String toJson(Object obj) {
        return GSON.toJson(obj);
    }

    public final static <T> T json(String json, Class<T> classOfT) {
        GSON.fromJson(json, (Type) classOfT);
        return GSON.fromJson(json, (Type) classOfT);
    }
}
