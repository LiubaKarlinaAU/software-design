package ru.itmo.karlina.lab2;

import com.xebialabs.restito.server.StubServer;
import org.glassfish.grizzly.http.Method;
import org.junit.Assert;
import org.junit.Test;

import java.util.function.Consumer;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.stringContent;
import static com.xebialabs.restito.semantics.Condition.method;
import static com.xebialabs.restito.semantics.Condition.startsWithUri;

import static ru.itmo.karlina.lab2.SearchQueryExecutor.ACCESS_TOKEN;

public class QueryExecutorTest {
    private final QueryExecutor executor = new SearchQueryExecutor();
    private static final int PORT = 32453;

    @Test
    public void buildQuery() {
        String expected = "https://api.vk.com/method/newsfeed.search?count=100&v=5.81&access_token=" + ACCESS_TOKEN +
                "&q=%23toast&start_time=123";
        Assert.assertEquals(expected, executor.buildQuery("toast", 123));
    }

    @Test
    public void readHTTPResponse() {
        String answer = "{\"response\" : 1}\n";
        withStubServer(PORT, s -> {
            whenHttp(s)
                    .match(method(Method.GET), startsWithUri("/ping"))
                    .then(stringContent(answer));

            String result = executor.executeQuery("http://localhost:" + PORT + "/ping");

            Assert.assertEquals(answer, result);
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
