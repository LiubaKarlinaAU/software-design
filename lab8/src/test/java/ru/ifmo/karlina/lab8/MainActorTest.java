package ru.ifmo.karlina.lab8;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.xebialabs.restito.server.StubServer;
import org.glassfish.grizzly.http.Method;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.delay;
import static com.xebialabs.restito.semantics.Action.stringContent;
import static com.xebialabs.restito.semantics.Condition.*;

public class MainActorTest {
    private static final int PORT = 32453;
    private static final String query = "what time is it";
    private final ActorSystem system = ActorSystem.create("MySystem");
    private static final String engineString = "http://localhost:" + PORT + "/";
    private final ActorRef engine = system.actorOf(Props.create(ChildActor.class, engineString), "engine");

    @Test
    public void callOneEngine() {
        String response = "first response\nsecond response\n";
        List<String> expected = List.of("first response", "second response");

        CompletableFuture<Map<String, List<String>>> resultConsumer = new CompletableFuture<>();
        ActorRef aggregator = system.actorOf(Props.create(MainActor.class, List.of(engine), resultConsumer), "aggregator");

        withStubServer(PORT, s -> {
            whenHttp(s)
                    .match(method(Method.GET), startsWithUri("/find"))
                    .then(stringContent(response));

            aggregator.tell(query, null);

            Map<String, List<String>> result = null;
            try {
                result = resultConsumer.get();
            } catch (InterruptedException | ExecutionException e) {
                Assert.fail(e.getMessage());
            }
            Assert.assertEquals(1, result.size());
            Assert.assertTrue(result.containsKey(engineString));
            Assert.assertEquals(result.get(engineString), expected);
        });
    }

    @Test
    public void receiveTimeout() {
        CompletableFuture<Map<String, List<String>>> resultConsumer = new CompletableFuture<>();
        ActorRef aggregator = system.actorOf(Props.create(MainActor.class, List.of(engine), resultConsumer), "aggregator");

        withStubServer(PORT, s -> {
            whenHttp(s)
                    .match(method(Method.GET), startsWithUri("/find"))
                    .then(delay(5000), stringContent(""));

            aggregator.tell(query, null);

            Map<String, List<String>> result = null;
            try {
                result = resultConsumer.get();
            } catch (InterruptedException | ExecutionException e) {
                Assert.fail(e.getMessage());
            }
            Assert.assertEquals(0, result.size());
        });
    }

    private void withStubServer(int port, Consumer<StubServer> callback) {
        StubServer stubServer = null;
        try {
            stubServer = new StubServer(port).run();
            callback.accept(stubServer);
        } finally {
            if (stubServer != null) {
                stubServer.stop();
            }
        }
    }
}
