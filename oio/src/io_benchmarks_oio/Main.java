package io_benchmarks_oio;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Main {
    
    private final static int PORT = 8080;

    public static void main(String[] args) throws IOException {
        Executor executor = Executors.newCachedThreadPool();
        ServerSocket socket = new ServerSocket(PORT);
        while (true) {
            executor.execute(new Handler(socket.accept()));
        }
    }
}
