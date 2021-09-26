package ru.itmo.karlina.lab1;

import org.junit.Test;

import static org.junit.Assert.*;

public class LRUCacheTest {
    @Test
    public void basicLRUCache() {
        final Integer expectedAnswer = 25;
        LRUCache<Integer, Integer> lruCache = new LRUCache<>((a) -> a * a, 5);

        assertEquals(expectedAnswer, lruCache.getValue(5));
    }

    @Test
    public void factorialLRUCache() {
        final Long expectedAnswer = 2432902008176640000L;
        LRUCache<Integer, Long> lruCache = new LRUCache<>(n -> {
            long result = 1;
            for (int i = 1; i <= n; i++) {
                result *= i;
            }
            return result;
        }, 1);
        long firstTimestamp = System.nanoTime();
        assertEquals(expectedAnswer, lruCache.getValue(20));
        long secondTimestamp = System.nanoTime();
        assertEquals(expectedAnswer, lruCache.getValue(20));
        long thirdTimestamp = System.nanoTime();
        // secondTimestamp - firstTimestamp > thirdTimestamp - secondTimestamp
        assertTrue(2 * secondTimestamp > thirdTimestamp + firstTimestamp);
    }


    @Test
    public void factorialsLRUCache() {
        final Long firstExpectedAnswer = 2432902008176640000L;
        final Long secondExpectedAnswer = 3628800L;
        LRUCache<Integer, Long> lruCache = new LRUCache<>(n -> {
            long result = 1;
            for (int i = 1; i <= n; i++) {
                result *= i;
            }
            return result;
        }, 2);
        long firstTimestamp = System.nanoTime();
        assertEquals(firstExpectedAnswer, lruCache.getValue(20));
        long secondTimestamp = System.nanoTime();
        assertEquals(firstExpectedAnswer, lruCache.getValue(20));
        long thirdTimestamp = System.nanoTime();
        // secondTimestamp - firstTimestamp > thirdTimestamp - secondTimestamp
        assertTrue(2 * secondTimestamp > thirdTimestamp + firstTimestamp);
        assertEquals(secondExpectedAnswer, lruCache.getValue(10));
        lruCache.getValue(1);
        firstTimestamp = System.nanoTime();
        assertEquals(secondExpectedAnswer, lruCache.getValue(10));
        secondTimestamp = System.nanoTime();
        assertEquals(secondExpectedAnswer, lruCache.getValue(10));
        thirdTimestamp = System.nanoTime();
        // secondTimestamp - firstTimestamp > thirdTimestamp - secondTimestamp
        assertTrue(2 * secondTimestamp > thirdTimestamp + firstTimestamp);
    }
}
