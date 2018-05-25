package com.melonltd.naberc.model;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.WeakHashMap;

public class MemoryCache {
    private WeakHashMap<String, Object> cache = new WeakHashMap<String, Object>();

    public Object get(String key) {
        if (key != null)
            return cache.get(key);
        return null;
    }

    public void put(String key, Object value) {
        if (key != null && !"".equals(key) && value != null) {
            cache.put(key, value);
        }
    }

    public void remove(String key) {
        cache.remove(key);
    }

    public void clear() {
        cache.clear();
    }

    public List<Object> removew(String key) {
        return Lists.newArrayList(cache.values());
    }
}
