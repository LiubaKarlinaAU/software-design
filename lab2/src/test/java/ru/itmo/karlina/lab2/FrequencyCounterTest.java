package ru.itmo.karlina.lab2;

import org.junit.Test;

import java.util.Arrays;

public class FrequencyCounterTest {
    @Test
    public void basicTest() {
        FrequencyCounter counter = new FrequencyCounter("cake", 24);
        long[] answer = counter.run();
        System.out.println(Arrays.toString(answer));
    }
}
