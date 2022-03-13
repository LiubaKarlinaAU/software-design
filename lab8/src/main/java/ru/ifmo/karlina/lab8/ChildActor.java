package ru.ifmo.karlina.lab8;

import akka.actor.UntypedActor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class ChildActor extends UntypedActor {
    private final String engine;

    public ChildActor(String engine) {
        this.engine = engine;
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof String query) {
            String response = "";
            URL url = toUrl(engine + "find?name=" + query.replace(' ', '+'));
            try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
                StringBuilder buffer = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    buffer.append(inputLine);
                    buffer.append("\n");
                }
                response = buffer.toString();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
            sender().tell(new SearchResult(engine, Arrays.asList(response.split("\n"))), self());
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