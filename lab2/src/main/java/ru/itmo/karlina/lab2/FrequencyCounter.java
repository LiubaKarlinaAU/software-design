package ru.itmo.karlina.lab2;

import java.util.Arrays;

public class FrequencyCounter {
    private final String hashtag;
    private final int N;
    private final QueryExecutor queryExecutor;
    private final JSONParser parser;
    private final long classStartTime;

    public static void main(String[] args) {
        FrequencyCounter counter = new FrequencyCounter("cake", 24);

        System.out.println(Arrays.toString(counter.run()));
    }

    public FrequencyCounter(String hashtag, int N) {
        assert N >= 1 && N <= 24;
        this.hashtag = hashtag;
        this.N = N;
        queryExecutor = new SearchQueryExecutor();
        parser = new SearchJSONParser(N);
        classStartTime = System.currentTimeMillis() / 1000L;
    }

    // for tests
    public FrequencyCounter(String hashtag, int N, long classStartTime, QueryExecutor queryExecutor, JSONParser parser) {
        assert N >= 1 && N <= 24;
        this.hashtag = hashtag;
        this.N = N;
        this.queryExecutor = queryExecutor;
        this.parser = parser;
        this.classStartTime = classStartTime;
    }

    public long[] run() {
        long searchStartTime = classStartTime - N * 60 * 60L;
        String query = queryExecutor.buildQuery(hashtag, searchStartTime);

        return parser.parseResponse(queryExecutor.executeQuery(query), classStartTime);
    }

}
