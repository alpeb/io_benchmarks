package io_benchmarks_vertx;

import org.vertx.java.core.Handler;
import org.vertx.java.core.SimpleHandler;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.streams.Pump;

public class MyHandler implements Handler<HttpServerRequest>  {
    @Override
    public void handle(final HttpServerRequest req) {
        final String md5 = req.uri.substring(1);
        final HashStream writeStream = new HashStream();
        Pump.createPump(req, writeStream).start();
        req.endHandler(new SimpleHandler() {
            @Override
            protected void handle() {
                if (writeStream.getHash().equals(md5)) {
                    req.response.statusCode = 200;
                    req.response.statusMessage = "OK";
                } else {
                    req.response.statusCode = 500;
                    req.response.statusMessage = "Internal Server Error";
                }
                req.response.end();
            }
        });
    }
}
