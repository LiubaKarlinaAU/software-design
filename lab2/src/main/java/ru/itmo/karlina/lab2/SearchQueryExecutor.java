package ru.itmo.karlina.lab2;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class SearchQueryExecutor implements QueryExecutor {
    static final String ACCESS_TOKEN = "token";
    private static final String QUERY_START = "https://api.vk.com/method/newsfeed.search?count=100&v=5.81&access_token=" + ACCESS_TOKEN;

    public String buildQuery(String hashtag, long startTime) {
        return QUERY_START + "&q=%23" + hashtag + "&start_time=" + startTime;
    }

    @Override
    public String executeQuery(String sourceURL) {
        URL url = toUrl(sourceURL);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
            StringBuilder buffer = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                buffer.append(inputLine);
                buffer.append("\n");
            }
            return buffer.toString();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private URL toUrl(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Malformed url: " + url);
        }
    }
}
