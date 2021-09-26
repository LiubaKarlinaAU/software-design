package ru.itmo.karlina.lab1;

import java.util.*;
import java.util.function.Function;

public class LRUCache<T, R> {
    private final Map<T, MyMapEntry<T, R>> cache = new HashMap<>();
    private final int maxCacheSize;
    private final Function<T, R> function;
    private final FastLinkedList<T> order = new FastLinkedList<>();

    public LRUCache(Function<T, R> function, int cacheSize) {
        this.maxCacheSize = cacheSize;
        this.function = function;
    }

    public R getValue(T key) {
        assert cache.size() <= maxCacheSize;
        if (cache.containsKey(key)) {
            MyMapEntry<T, R> entry = cache.get(key);
            order.remove(entry.node);
            order.add(key);
            return entry.result;
        }
        if (cache.size() == maxCacheSize) {
            FastLinkedList.Node<T> keyToDelete = order.remove();
            cache.remove(keyToDelete.value);
        }
        // assuming cacheMap.size() < maxCacheSize

        R result = function.apply(key);
        FastLinkedList.Node<T> node = order.add(key);
        cache.put(key, new MyMapEntry<>(node, result));
        assert cache.size() <= maxCacheSize && cache.containsKey(key);
        return result;
    }

    static class MyMapEntry<T, R> {
        FastLinkedList.Node<T> node;
        R result;

        MyMapEntry(FastLinkedList.Node<T> node, R result) {
            this.node = node;
            this.result = result;
        }
    }
}
