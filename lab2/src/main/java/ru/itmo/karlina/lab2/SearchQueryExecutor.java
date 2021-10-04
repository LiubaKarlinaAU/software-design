package ru.itmo.karlina.lab2;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.stream.Collectors;

public class SearchQueryExecutor implements QueryExecutor {

    static final String ACCESS_TOKEN = "82bd7aa23c55639a331cfada060d91c8b20009b323738c8d708d4fa35cf2199f3840700a9bfac23cb5603";
    private static final String QUERY_START = "https://api.vk.com/method/newsfeed.search?count=100&v=5.81&access_token=" + ACCESS_TOKEN;

    public String buildQuery(String hashtag, long startTime) {
        return QUERY_START + "&q=%23" + hashtag + "&start_time=" + startTime ;
    }

    @Override
    public String executeQuery(String uri) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();

        StringBuilder builder = new StringBuilder();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(builder::append)
                .join();

        return builder.toString();
    }
}
