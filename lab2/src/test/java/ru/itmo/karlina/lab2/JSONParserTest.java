package ru.itmo.karlina.lab2;

import org.junit.Assert;
import org.junit.Test;

public class JSONParserTest {

    private static final long START_TIME = 60 * 60 * 12;

    @Test
    public void parseSimpleJSON() {
        String testJSON = "{\"response\": {\"items\": []}}";
        long[] expected = new long[]{0, 0, 0};
        JSONParser parser = new SearchJSONParser(3);
        Assert.assertArrayEquals(expected, parser.parseResponse(testJSON, START_TIME));
    }

    @Test
    public void parseSimpleJSONWithOtherAttributes() {
        String testJSON = "{\"response\" : { " +
                "\"attr1\" : 2," +
                "\"attr2\" : 3, " +
                "\"items\": [] } " +
                "}";
        long[] expected = new long[]{0, 0, 0};
        JSONParser parser = new SearchJSONParser(3);
        Assert.assertArrayEquals(expected, parser.parseResponse(testJSON, START_TIME));
    }

    @Test
    public void parseJSONOneDate() {
        String testJSON = "{\"response\" : { " +
                "\"attr1\" : 2," +
                "\"attr2\" : 3, " +
                "\"items\": [{\"date\":3700}] } " +
                "}";
        long[] expected = new long[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0};
        JSONParser parser = new SearchJSONParser(12);
        Assert.assertArrayEquals(expected, parser.parseResponse(testJSON, START_TIME));
    }

    @Test
    public void parseJSONManyDates() {
        String testJSON = "{\"response\" : { " +
                "\"attr1\" : 2," +
                "\"attr2\" : 3, " +
                "\"items\": [{\"date\":3700, \"attr1\" : 1, \"attr2\" : 2}," +
                "{\"date\":20700, \"attr1\" : 3, \"attr2\" : 4}," +
                "{\"date\":40400, \"attr1\" : 5, \"attr2\" : 6}] } " +
                "}";
        long[] expected = new long[]{1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0};
        JSONParser parser = new SearchJSONParser(12);
        Assert.assertArrayEquals(expected, parser.parseResponse(testJSON, START_TIME));
    }
}
