package com.wikia.meownjik.httpserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.commons.text.StringEscapeUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Collections;

public class SimpleServer implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String requestParamValue=null;

        System.out.println(httpExchange.getRequestMethod());

        if("GET".equals(httpExchange.getRequestMethod())) {
            requestParamValue = handleGetRequest(httpExchange);
        //} else if("POST".equals(httpExchange)) {
        //    requestParamValue = handlePostRequest(httpExchange);
        }

        handleResponse(httpExchange,requestParamValue);
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
        /*var htmlBuilder = new StringBuilder();

        htmlBuilder.append("<h1>").
                append("Hello ")
                .append(requestParamValue)
                .append("</h1>");*/

        // encode HTML content
        var html = new File(this.getClass().getResource("/webClient.html").getPath());
        String htmlResponse = Files.readString(html.toPath());
        //String htmlResponse = htmlBuilder.toString();//StringEscapeUtils.escapeHtml4(htmlBuilder.toString());

        // this line is a must
        httpExchange.getResponseHeaders().put("Content-Type", Collections.singletonList("text/html"));
        httpExchange.sendResponseHeaders(200, htmlResponse.length());

        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
