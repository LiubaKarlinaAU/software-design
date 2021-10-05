package ru.itmo.karlina.lab2;

public class FrequencyCounter {
    private final String hashtag;
    private final int N;
    private final QueryExecutor queryExecutor;
    private final JSONParser parser;
    private final long classStartTime;

    public FrequencyCounter(String hashtag, int N) {
        assert N >= 1 && N <= 24;
        this.hashtag = hashtag;
        this.N = N;
        queryExecutor = new SearchQueryExecutor();
        parser = new SearchJSONParser(N);
        classStartTime = System.currentTimeMillis() / 1000L;
    }

    // comfortable for testing
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
