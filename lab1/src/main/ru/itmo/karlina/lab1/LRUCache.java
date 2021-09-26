package ru.itmo.karlina.lab1;

import java.util.*;
import java.util.function.Function;

public class LRUCache<T, R> {
    private final Map<T, MyMapEntry<T,R>> cacheMap = new HashMap<>();
    private final int maxCacheSize;
    private final Function<T, R> function;
    private final MyLinkedList<T> list = new MyLinkedList<>();

    public LRUCache(Function<T, R> function, int cacheSize) {
        this.maxCacheSize = cacheSize;
        this.function = function;
    }

    public R getValue(T key) {
        assert cacheMap.size() <= maxCacheSize;
        if (cacheMap.containsKey(key)) {
            MyMapEntry<T, R> entry = cacheMap.get(key);
            list.remove(entry.node);
            list.add(key);
            return entry.result;
        }
        if (cacheMap.size() == maxCacheSize) {
            MyLinkedList.Node<T> keyToDelete = list.remove();
            cacheMap.remove(keyToDelete.value);
        }
        // assuming cacheMap.size() < maxCacheSize

        R result = function.apply(key);
        MyLinkedList.Node<T> node = list.add(key);
        cacheMap.put(key, new MyMapEntry<T,R>(node, result));
        assert cacheMap.size() <= maxCacheSize && cacheMap.containsKey(key);
        return result;
    }

    static class MyMapEntry<T,R> {
        MyLinkedList.Node<T> node;
        R result;

        MyMapEntry(MyLinkedList.Node<T> node, R result){
            this.node = node;
            this.result = result;
        }
    }
}
