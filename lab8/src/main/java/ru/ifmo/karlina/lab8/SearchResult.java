package ru.ifmo.karlina.lab8;

import java.util.List;

public class SearchResult {
    private final String engine;
    private final List<String> response;

    public SearchResult(String engine, List<String> response) {
        this.engine = engine;
        this.response = response;
    }

    public List<String> getResponse() {
        return response;
    }

    public String getEngine() {
        return engine;
    }
}
