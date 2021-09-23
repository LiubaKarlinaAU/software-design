package ru.itmo.karlina.lab1;

import org.junit.Test;

import static org.junit.Assert.*;

public class LRUCacheTest {
    @Test
    public void createLRUCache() {
        LRUCache<Integer, Integer> lruCache = new LRUCache<>((a) -> a * a, 5);

        assertEquals(lruCache.getValue(5), new Integer(25));
    }
}
