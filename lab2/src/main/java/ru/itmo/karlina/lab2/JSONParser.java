package ru.itmo.karlina.lab2;

public interface JSONParser {
    long[] parseResponse(String jsonString, long startTime);
}
