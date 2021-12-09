package com.wikia.meownjik;

import com.wikia.meownjik.graphql.NewsClient;
import com.wikia.meownjik.jdbc.EcoNewsEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;



/**
 * Update news database: mvn test -Dtest=EcoNewsIntegrationTest
 * To run desktop app, run App#main
 * Run server for web client: mvn test -Dtest=ServerTest
 */
public class App
{
    private static final BufferedReader reader = new BufferedReader(
            new InputStreamReader(System.in));

    public static void main( String[] args ) throws IOException {
        var rand = new Random();
        var newsClient = new NewsClient();
        System.out.println( "Press Enter to load next news");
        String input = reader.readLine();
        while (!input.equals("0")) {
            EcoNewsEntity news = newsClient.next();
            if (rand.nextBoolean() && input.equals("+")) {
                Notificator.displayTray("You might like!", news.getTitle());
            }
            System.out.println(news.getTitle());
            System.out.println("\t" + news.getText());
            System.out.println("========> Do you like this news? Type + or -");
            input = reader.readLine();
        }
    }

}
