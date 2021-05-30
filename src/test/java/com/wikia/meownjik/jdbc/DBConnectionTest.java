package com.wikia.meownjik.jdbc;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DBConnectionTest {

    @Test
    public void getAllTest() {
        for(var news : new EcoNewsDao().selectAll()) {
            System.out.println(news.getTitle());
            System.out.println(news.getText());
        }
    }

    //@Test
    public void insertTest() {
        var dao = new EcoNewsDao();
        var news = new EcoNewsEntity("Insert title", "Insert text");
        dao.insert(news);
        System.out.println(dao.selectByTitle(news.getTitle()));
    }

    @Test
    public void getUnexistingTest() {
        var news = new EcoNewsDao().selectByTitle("not existing");
        Assert.assertEquals(news.size(), 0, "Error - news list is not empty!");
    }
}
