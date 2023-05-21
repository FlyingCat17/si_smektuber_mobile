package com.nekoid.smektuber.network;

import java.util.HashMap;
import java.util.Map;

public class Cache {

    private static Cache instance;

    private final Map<String, Object> cache;

    private Cache() {
        cache = new HashMap<>();
    }

    public static Cache getInstance() {
        if (instance == null) {
            instance = new Cache();
        }
        return instance;
    }

    public void put(String key, Object obj) {
        cache.put(key, obj);
    }

    public Object get(String key) {
        return cache.get(key);
    }

    public boolean containsKey(String key) {
        return cache.containsKey(key);
    }

    public void clear() {
        cache.clear();
    }
}
