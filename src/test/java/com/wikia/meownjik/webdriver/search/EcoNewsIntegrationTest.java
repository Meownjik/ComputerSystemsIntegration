package com.wikia.meownjik.webdriver.search;

import com.wikia.meownjik.jdbc.EcoNewsDao;
import com.wikia.meownjik.jdbc.EcoNewsEntity;
import com.wikia.meownjik.webdriver.TestRunner;
import com.wikia.meownjik.webdriver.pageobjects.EcoNewsPage;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * This is not actually a test, but a core method that can be run separately
 */
public class EcoNewsIntegrationTest extends TestRunner {

    private final EcoNewsDao ecoNewsDao = new EcoNewsDao();

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
            logger.info(title);
            logger.info(text);
            logger.info("--------------------");
            var newsEntity = new EcoNewsEntity(title, text);
            insertIntoDB(newsEntity);
        }

    }

    private void insertIntoDB(EcoNewsEntity newsEntity) {
        if (ecoNewsDao.selectByTitle(newsEntity.getTitle()).size() == 0) {
            ecoNewsDao.insert(newsEntity);
        }
    }
}
