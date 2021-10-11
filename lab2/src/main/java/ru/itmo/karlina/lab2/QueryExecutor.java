package ru.itmo.karlina.lab2;

public interface QueryExecutor {
    String executeQuery(String uri);
    String buildQuery(String hashtag, long startTime);
}
