package com.wikia.meownjik.httpserver;

import com.sun.net.httpserver.HttpServer;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ServerTest {

    private HttpServer server;

    @Test
    public void startServer() throws IOException, InterruptedException {
        server = HttpServer.create(new InetSocketAddress("localhost", 8001), 0);

        server.createContext("/test", new SimpleServer());
        var threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        server.setExecutor(threadPoolExecutor);
        server.start();
        System.out.println(" Server started on port 8001");
        Thread.sleep(10000);
    }

    @Test(dependsOnMethods = "startServer")
    public void stopServer() {
        server.stop(5);
    }
}
