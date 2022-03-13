package ru.ifmo.karlina.lab8;

import akka.actor.*;
import scala.concurrent.duration.Duration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class MainActor extends UntypedActor {
    private final CompletableFuture<Map<String, List<String>>> resultConsumer;
    private final Map<String, List<String>> resultMap = new HashMap<>();
    private final List<ActorRef> engines;
    private final Duration timeout = Duration.fromNanos(300000000);

    public MainActor(List<ActorRef> engines, CompletableFuture<Map<String, List<String>>> resultConsumer){
        this.engines = engines;
        this.resultConsumer = resultConsumer;
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof String query) {
            engines.forEach(engine -> engine.tell(query, self()));
            context().setReceiveTimeout(timeout);
        } else if (message instanceof ReceiveTimeout) {
            context().stop(self());
        } else if (message instanceof SearchResult searchResult) {
            resultMap.put(searchResult.getEngine(), searchResult.getResponse());
            if (resultMap.size() == engines.size()) {
                getContext().system().stop(self());
            }
        }
    }


    @Override
    public void postStop() {
        resultConsumer.complete(resultMap);
    }
}
