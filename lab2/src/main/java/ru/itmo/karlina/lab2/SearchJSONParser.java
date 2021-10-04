package ru.itmo.karlina.lab2;

import org.json.*;

public class SearchJSONParser implements JSONParser {
    private final int n;

    SearchJSONParser(int n) {
        this.n = n;
    }

    @Override
    public long[] parseResponse(String jsonString, long startTime) {
        long[] frequencies = new long[n];
        JSONArray arr = parseJSON(jsonString);

        // updating frequencies
        for (int i = 0; i < arr.length(); i++) {
            long time = arr.getJSONObject(i).getLong("date");
            // converting difference between time into hours
            int hours = (int) (startTime - time) / 3600;
            assert hours >= 0 && hours <= 24;
            frequencies[hours] += 1;
        }
        return frequencies;
    }

    private JSONArray parseJSON(String jsonString){
        JSONObject obj = new JSONObject(jsonString);
        return obj.getJSONObject("response").getJSONArray("items");
    }
}
