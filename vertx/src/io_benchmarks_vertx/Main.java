package io_benchmarks_vertx;

import org.vertx.java.core.http.HttpServerResponse;
import org.vertx.java.deploy.Verticle;

public class Main extends Verticle {
    
    private static HttpServerResponse clientResponse;
    private final int PORT = 8080;
    
    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(new MyHandler()).listen(PORT);
    }
}
