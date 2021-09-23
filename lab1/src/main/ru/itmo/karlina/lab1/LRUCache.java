package ru.itmo.karlina.lab1;

import java.util.*;
import java.util.function.Function;

public class LRUCache<T, R> {
    private final Map<T, R> cacheMap = new HashMap<>();
    private final int maxCacheSize;
    private final Function<T, R> function;
    private LinkedList<T> list = new LinkedList<>();

    public LRUCache(Function<T, R> function, int cacheSize) {
        this.maxCacheSize = cacheSize;
        this.function = function;
    }

    public R getValue(T key) {
        if (cacheMap.containsKey(key)) {
            //updateList
            list.remove(key);
            list.add(key);
            return cacheMap.get(key);
        }
        if (cacheMap.size() == maxCacheSize) {
            T keyToDelete = list.removeFirst();
            cacheMap.remove(keyToDelete);
        }
        // assuming cacheMap.size() < maxCacheSize

        R result = function.apply(key);
        list.addLast(key);
        cacheMap.put(key, result);
        return result;
    }
}
