package com.wikia.meownjik.httpserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.wikia.meownjik.graphql.NewsClient;
import com.wikia.meownjik.jdbc.EcoNewsEntity;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Random;

public class SimpleServer implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String requestParamValue = null;

        System.out.println(httpExchange.getRequestMethod());

        if ("GET".equals(httpExchange.getRequestMethod())) {
            requestParamValue = handleGetRequest(httpExchange);
        }

        handleResponse(httpExchange, requestParamValue);
    }

    private String handleGetRequest(HttpExchange httpExchange) {
        return httpExchange.
                getRequestURI()
                .toString()
                .split("\\?")[1]
                .split("=")[1];
    }

    private void handleResponse(HttpExchange httpExchange, String requestParamValue) throws IOException {
        OutputStream outputStream = httpExchange.getResponseBody();

        // encode HTML content
        var html = new File(this.getClass().getResource("/webClient.html").getPath());
        String htmlResponse = Files.readString(html.toPath());

        var rand = new Random();
        var newsClient = new NewsClient();
        EcoNewsEntity news = newsClient.next();
        if (rand.nextBoolean() && requestParamValue.equals("1")) {
            htmlResponse += String.format("<script>alert('You might like:\\n%s');</script>", news.getTitle());
        }
        htmlResponse += String.format("<script>insert(\"%s\", \"%s\")</script>", news.getTitle(), news.getText());

        // this line is a must
        httpExchange.getResponseHeaders().put("Content-Type", Collections.singletonList("text/html"));
        httpExchange.sendResponseHeaders(200, htmlResponse.length());

        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
