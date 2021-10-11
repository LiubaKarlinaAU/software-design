package ru.itmo.karlina.lab2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

public class FrequencyCounterTest {
    private FrequencyCounter counter;

    @Mock
    private QueryExecutor executor;
    @Mock
    private JSONParser parser;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void mockBuildQuery() {
        String hashtag = "toast";
        long startTime = 24 * 60 * 60;
        int n = 24;
        String expectedQuery = hashtag + " and " + startTime;
        String expectedJSONString = "{\"Good\": \"JSON String\"}";
        long[] expectedFrequencies = new long[]{1, 2, 3};

        when(executor.buildQuery(hashtag, 0))
                .thenReturn(expectedQuery);
        when(executor.executeQuery(expectedQuery))
                .thenReturn(expectedJSONString);
        when(parser.parseResponse(expectedJSONString, startTime))
                .thenReturn(expectedFrequencies);

        counter = new FrequencyCounter(hashtag, n, startTime, executor, parser);
        Assert.assertEquals(expectedFrequencies, counter.run());
    }
}
