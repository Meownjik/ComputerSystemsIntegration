package com.wikia.meownjik.webdriver.search;

import com.wikia.meownjik.jdbc.EcoNewsDao;
import com.wikia.meownjik.jdbc.EcoNewsEntity;
import com.wikia.meownjik.rabbitmq.MqReceiver;
import com.wikia.meownjik.rabbitmq.MqSender;
import com.wikia.meownjik.webdriver.TestRunner;
import com.wikia.meownjik.webdriver.pageobjects.EcoNewsPage;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * This is not actually a test, but a core method that can be run separately
 */
public class EcoNewsIntegrationTest extends TestRunner {

    private final EcoNewsDao ecoNewsDao = new EcoNewsDao();
    MqReceiver receiver = new MqReceiver();
    MqSender sender = new MqSender();

    @BeforeClass
    public void setReceiver() throws IOException, TimeoutException {
        receiver.receiveNews();
    }

    @Test
    public void readNewsViaWebDriver() {
        logger.info("Read news via Web driver");
        var ecoNewsPage = new EcoNewsPage(driver);
        ecoNewsPage.pageExistQuickCheck();
        logger.info("Navigated eco news page");
        ecoNewsPage.updateArticlesExistCount().scrollDown();
        List<WebElement> titles = ecoNewsPage.getNewsTitles();
        List<WebElement> texts = ecoNewsPage.getNewsTexts();
        Assert.assertEquals(titles.size(), texts.size(),
                "Error - different size of titles and texts arrays!");
        for (int i = 0; i < titles.size(); i++) {
            String title = titles.get(i).getText();
            String text = texts.get(i).getText();
            //logger.info(title);
            //logger.info(text);
            //logger.info("--------------------");
            var newsEntity = new EcoNewsEntity(title, text);
            insertIntoDB(newsEntity);
        }

    }

    private void insertIntoDB(EcoNewsEntity newsEntity) {
        if (ecoNewsDao.selectByTitle(newsEntity.getTitle()).size() == 0) {
            try {
                sender.send(newsEntity.toString());
            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
        }
    }
}
