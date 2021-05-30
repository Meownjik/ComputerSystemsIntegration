package com.wikia.meownjik.jdbc;

import org.testng.annotations.Test;

public class DBConnectionTest {

    @Test
    public void jdbcTest() {
        for(var news : new EcoNewsDao().selectAll()) {
            System.out.println(news.getTitle());
            System.out.println(news.getText());
        }

    }
}
