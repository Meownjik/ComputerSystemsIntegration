package com.wikia.meownjik.webdriver.pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static com.wikia.meownjik.webdriver.locators.EcoNewsPageLocator.*;

/**
 * The list of all eco news: https://ita-social-projects.github.io/GreenCityClient/#/news
 */
public class EcoNewsPage extends BasePageObject {

    private int articleExistCount;
    private int articleDisplayedCount;

    public EcoNewsPage(WebDriver driver) {
        super(driver);
        checkElements();
    }

    private void checkElements() {
        checkNewsDisplayed();
        waitsSwitcher.setExplicitWait(10, ExpectedConditions.visibilityOf(getGridView()));
        waitsSwitcher.setExplicitWait(10, ExpectedConditions.visibilityOf(getListView()));
    }

    private void checkNewsDisplayed() {
        waitsSwitcher.setExplicitWait(40, ExpectedConditions.presenceOfAllElementsLocatedBy(DISPLAYED_ARTICLES.getPath()));
        WebElement firstItem = driver.findElement(DISPLAYED_ARTICLES.getPath());
        /* Only try-catch works, since FluentWait doesn't ignore TimeoutException */
        try {
            waitsSwitcher.setExplicitWait(2, ExpectedConditions.stalenessOf(firstItem));
            logger.warn("The site performed the same GET request twice and redrew page");
        } catch (TimeoutException error) {
            //Everything is OK
        }
    }

    public WebElement getGridView() {
        return waitsSwitcher.setExplicitWait(10,
                ExpectedConditions.visibilityOfElementLocated(GALLERY_VIEW_BUTTON.getPath()));
    }

    public boolean isActiveGridView() {
        try {
            driver.findElement(GALLERY_VIEW_WRAPPER.getPath()).isDisplayed();
            return true;
        } catch (NoSuchElementException e) {
            logger.info("Grid view is not active");
            return false;
        }
    }

    private void clickGridView() {
        if (!isActiveGridView()) {
            scrollToElement(getGridView());
            getGridView().click();
        }
    }

    public String getListViewButtonHoverColor() {
        //It is a subelement that changes color on :hover
        return waitsSwitcher.setExplicitWait(3,
                ExpectedConditions.visibilityOfElementLocated(LIST_VIEW_BUTTON_HOVER.getPath()))
                .getCssValue("color");
    }

    public WebElement getListView() {
        return searchElementByCss(LIST_VIEW_BUTTON.getPath());
    }

    public boolean isListViewPresent() {
        try {
            getListView();
            return true;
        } catch (TimeoutException e) {
            logger.info("List view is not present");
            return false;
        }
    }

    public boolean isActiveListView() {
        try {
            waitsSwitcher.setExplicitWait(3,
                    ExpectedConditions.presenceOfElementLocated(LIST_VIEW_WRAPPER.getPath()));
            driver.findElement(LIST_VIEW_WRAPPER.getPath()).isDisplayed();
            return true;
        } catch (NoSuchElementException | TimeoutException e) {
            return false;
        }
    }

    private void clickListView() {
        if (!isActiveListView()) {
            scrollToElement(getListView());
            getListView().click();
        }
    }

    private void scrollToElement(WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();
    }

    public EcoNewsPage switchToGridView() {
        clickGridView();
        checkNewsDisplayed();
        return new EcoNewsPage(driver);
    }

    public EcoNewsPage switchToListView() {
        if (isListViewPresent()) {
            clickListView();
        }
        checkNewsDisplayed();
        return this;
    }

    @Override
    public void refreshPage() {
        driver.navigate().refresh();
        checkElements();
    }
    // Functional

    public void pageExistQuickCheck() {
        logger.info("Is element visible: \n");
        logger.info("header");
        searchElementByCss(HEADER.getPath());
        logger.info("tagsFilterBlock");
        searchElementByCss(TAGS_FILTER_BLOCK.getPath());
        logger.info("tagsFilterLabel");
        searchElementByCss(TAGS_FILTER_LABEL.getPath());
        logger.info("tags");
        searchElementByCss(TAGS.getPath());
        logger.info("articleFoundCounter");
        searchElementByCss(ARTICLES_FOUND_COUNTER.getPath());
        logger.info("displayedArticles");
        searchElementByCss(DISPLAYED_ARTICLES.getPath());
        logger.info("listViewButton");
        searchElementByCss(LIST_VIEW_BUTTON.getPath());
        logger.info("galleryViewButton");
        searchElementByCss(GALLERY_VIEW_BUTTON.getPath());
    }


    public List<WebElement> getElements(By cssSelector) {
        logger.info("Get list of elements by css:\n " + cssSelector);
        return driver.findElements(cssSelector);
    }

    public EcoNewsPage updateArticlesExistCount() {
        logger.info("refresh page");
        driver.navigate().refresh();
        logger.info("wait until at least one article is displayed");
        waitsSwitcher.setExplicitWait(10,
                ExpectedConditions.visibilityOfElementLocated(DISPLAYED_ARTICLES.getPath()));
        logger.info("Set actual information from page to articleExistCount");
        articleExistCount = Integer.parseInt(searchElementByCss(ARTICLES_FOUND_COUNTER.getPath()).getText().split(" ")[0]);
        logger.info("Articles exist: " + articleExistCount);
        return this;
    }

    public EcoNewsPage scrollDown() {
        logger.info("scroll down");
        while (articleExistCount > articleDisplayedCount) { //Site BUG!
            // Sometimes first (?) 12 displayed news appear again on scroll
            searchElementByCss(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.END);
            articleExistCount = Integer.parseInt(searchElementByCss(ARTICLES_FOUND_COUNTER.getPath())
                    .getText().split(" ")[0]);
            articleDisplayedCount = getElements(DISPLAYED_ARTICLES.getPath()).size();
            logger.info("Articles displayed: " + articleDisplayedCount);
        }
        waiting(searchElementsByCss(DISPLAYED_ARTICLES.getPath()));
        return this;
    }

    private void waiting(WebElement element) {
        logger.info("short explicit wait visibility Of element \n" + element);
        waitsSwitcher.setExplicitWait(30, ExpectedConditions.visibilityOf(element));
    }

    private void waiting(List<WebElement> elements) {
        logger.info("short explicit wait visibility Of elements list \n" + elements);
        waitsSwitcher.setExplicitWait(30, ExpectedConditions.visibilityOfAllElements(elements));
    }

    public List<WebElement> getNewsTitles() {
        return searchElementsByCss(ARTICLE_TITLE.getPath());
    }

    public List<WebElement> getNewsTexts() {
        return searchElementsByCss(ARTICLE_TEXT.getPath());
    }

}
