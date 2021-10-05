package ru.itmo.karlina.lab2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.mockito.Mockito.when;

public class FrequencyCounterTest {

    @Mock
    private QueryExecutor executor;
    @Mock
    private JSONParser parser;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void mockBuildQuery() {
        String hashtag = "toast";
        long startTime = 400;
        int n = 24;
        String expectedQuery = hashtag + " and " + startTime;
        String expectedJSONString = "{\"Good\": \"JSON String\"}";
        long[] expectedFrequencies = new long[] {1, 2, 3};

        FrequencyCounter counter = new FrequencyCounter("cake", n, startTime, executor, parser);

        when(executor.buildQuery(hashtag, startTime))
                .thenReturn(expectedQuery);
        when(executor.executeQuery(expectedQuery))
                .thenReturn(expectedJSONString);
        when(parser.parseResponse(expectedJSONString, startTime))
                .thenReturn(expectedFrequencies);

        Assert.assertEquals(expectedFrequencies, counter.run());
    }
}
