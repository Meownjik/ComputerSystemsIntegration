package com.wikia.meownjik.webdriver.search;

import com.wikia.meownjik.webdriver.TestRunner;
import com.wikia.meownjik.webdriver.pageobjects.EcoNewsPage;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class EcoNewsIntegrationTest extends TestRunner {

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
            logger.info(titles.get(i).getText());
            logger.info(texts.get(i).getText());
            logger.info("--------------------");
        }
    }
}
