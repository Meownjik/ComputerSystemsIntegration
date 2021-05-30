package com.wikia.meownjik.webdriver.pageobjects;

import com.wikia.meownjik.webdriver.util.WaitsSwitcher;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.wikia.meownjik.webdriver.locators.EcoNewsPageLocator.*;

/**
 * The list of all eco news: https://ita-social-projects.github.io/GreenCityClient/#/news
 */
public class EcoNewsPage extends TopPart {

    /*
    protected WebDriverWait wait;
    SoftAssert softAssert = new SoftAssert();
    */

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
            ; //Everything is OK
        }
    }

    /*
    public List<WebElement> getTopicsInPage() {
        logger.info("Find all displayed topics");
        return searchElementsByXpath(FOUND_ITEMS.getPath());
    }

    //Header
    public WebElement getHeader() {
        return driver.findElement(HEADER.getPath());
    }
    
    private WebElement getFoundItems() {
        return searchElementByXpath(FOUND_ITEMS.getPath());
    }

    private String getFoundItemsText() {
        return getFoundItems().getText();
    }
    */

    public WebElement getGridView() {
        return waitsSwitcher.setExplicitWait(10,
                ExpectedConditions.visibilityOfElementLocated(GALLERY_VIEW_BUTTON.getPath()));
    }
    
    public boolean isActiveGridView() {
        try{
            driver.findElement(GALLERY_VIEW_WRAPPER.getPath()).isDisplayed();
            return true;
        }
        catch (NoSuchElementException e){
            logger.info("Grid view is not active");
            return false;
        }
    }
    
    public boolean isGridViewDisplayed() {
        return getGridView().isDisplayed();
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

    /*
    public EcoNewsPage hoverToGridView() {
        Actions action = new Actions(driver);
        action.moveToElement(getGridView())
                .moveToElement(
                waitsSwitcher.setExplicitWait(3,
                        ExpectedConditions.visibilityOfElementLocated(GALLERY_VIEW_BUTTON_HOVER.getPath()))
        ).build().perform();
        return this;
    }


    public WebElement getListViewButtonComponent() {
        return searchElementByCss(LIST_VIEW_BUTTON.getPath());
    }

    public String getListViewButtonColor() {
        return waitsSwitcher.setExplicitWait(3,
                ExpectedConditions.visibilityOfElementLocated(LIST_VIEW_BUTTON_HOVER.getPath()))
                .getCssValue("color");
    }
    
    public boolean isDisplayedListView() {
        return getListView().isDisplayed();
    }
    
    public EcoNewsPage hoverToListView() {
        Actions action = new Actions(driver);
        action.moveToElement(getListView())
                .moveToElement(
                waitsSwitcher.setExplicitWait(3,
                        ExpectedConditions.visibilityOfElementLocated(LIST_VIEW_BUTTON_HOVER.getPath()))
        ).build().perform();
        return this;
    }
    */
    
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
        try{
            waitsSwitcher.setExplicitWait(3,
                    ExpectedConditions.presenceOfElementLocated(LIST_VIEW_WRAPPER.getPath()));
            driver.findElement(LIST_VIEW_WRAPPER.getPath()).isDisplayed();
            return true;
        }
        catch (NoSuchElementException | TimeoutException e){
            return false;
        }
    }

    
    private void clickListView() {
        if (!isActiveListView()) {
            scrollToElement(getListView());
            getListView().click();
        }
    }

    /*
    private WebElement getCreateNewsButton() {
        return driver.findElement(CREATE_NEWS_BUTTON.getPath());
    }


    private String getCreateNewsButtonText() {
        return getCreateNewsButton().getText();
    }

    
    private void clickCreateNewsButton() {
        getCreateNewsButton().click();
    }

    
    public boolean isCreateNewsButtonDisplayed() {
        return getCreateNewsButton().isDisplayed();
    }

    
    public boolean isCreateNewsButtonPresent() {
        try {
            driver.findElement(CREATE_NEWS_BUTTON.getPath());
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    */

    /**
     * Scroll to WebElement, in case when need to click on it or without scrolling are invisible
     */
    
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
        if(isListViewPresent()){
        clickListView();}
        checkNewsDisplayed();
        return this;
    }

    /*
    public void countNewsColumns(int width) {
        int count = 1;
        List<WebElement> elements = getDisplayedArticles();
        if (elements.get(0).getLocation().y == elements.get(1).getLocation().y) {
            count++;

            if (width > 991 && (elements.get(1).getLocation().y == elements.get(2).getLocation().y)) {
                count++;
                logger.info("3 columns when width = " + width);
                softAssert.assertTrue( count == 3);
            }
            else if ((width > 575) && (width < 992) && (elements.get(1).getLocation().y < elements.get(2).getLocation().y)) {
                logger.info("2 columns when width = " + width);
                softAssert.assertTrue( count == 2);
            }
            else {
                logger.info("Error! " + width + "  " + Boolean.toString(elements.get(1).getLocation().y < elements.get(2).getLocation().y));
                logger.info(elements.get(1).getLocation().y + " " + elements.get(2).getLocation().y);
            }

        }
        else if (width < 576 && (elements.get(0).getLocation().y < elements.get(1).getLocation().y)) {
            logger.info("1 column when width = " + width);
            softAssert.assertTrue( count == 1);
        }
        else {
            logger.info("Error! " + width + "  " + Boolean.toString(elements.get(0).getLocation().y < elements.get(1).getLocation().y));
        }
        softAssert.assertAll();
    }
    
    public void isUiElementsDisplayedWithDifferentScreenResolution() {
        logger.info("Verify UI of the News page in Gallery view for different screen resolutions");
        softAssert.assertTrue(
                 searchElementByCss(HEADER.getPath()).isDisplayed() &&
                         searchElementByCss(TAGS_FILTER_BLOCK.getPath()).isDisplayed() &&
                         searchElementByCss(ARTICLES_FOUND_COUNTER.getPath()).isDisplayed() &&
                         searchElementByCss(DISPLAYED_ARTICLES.getPath()).isDisplayed() &&
                         searchElementByCss(ARTICLE_IMAGE.getPath()).isDisplayed() &&
                         searchElementByCss(ARTICLE_TITLE.getPath()).isDisplayed() &&
                         searchElementByCss(ARTICLE_TEXT.getPath()).isDisplayed() &&
                         searchElementByCss(ARTICLE_CREATION_DATE.getPath()).isDisplayed() &&
                         searchElementByCss(ARTICLE_AUTHOR_NAME.getPath()).isDisplayed(),
                "Assert that all UI elements in Eco News page is visible"
        );
    }
    
    public boolean isNewsDisplayedByTitle(String title) {
        logger.info("Check if news is displayed by title");
        refreshPage();
        int retriesLeft = 5;
        //The site performs the same GET request twice and redraws page, so StaleElementReferences appear
        do {
            try {
                for (WebElement current : getDisplayedArticlesTitles()) {
                    if (current.getText().toLowerCase().trim().equals(title.trim().toLowerCase())) {
                        return true;
                    }
                }
                return false;
            } catch (StaleElementReferenceException error) {
                logger.warn("StaleElementReferenceException during ItemsContainer.getItems() method caught, retrying...");
                WaitsSwitcher.sleep(100);
            }
            retriesLeft--;
        } while (retriesLeft > 0);

        return false;
    }
    
    public List<WebElement> getDisplayedArticlesTitles() {
        searchElementsByCss(DISPLAYED_ARTICLES_TITLES.getPath());
        return driver.findElements(DISPLAYED_ARTICLES_TITLES.getPath());
    }
    */

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

    
    public List<WebElement> getDisplayedArticles() {
        return waitsSwitcher.setExplicitWaitWithStaleReferenceWrap(10,
                ExpectedConditions.visibilityOfAllElementsLocatedBy(DISPLAYED_ARTICLES.getPath()));
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
        logger.info("short explicit wait visibility Of element \n" + String.valueOf(element));
        waitsSwitcher.setExplicitWait(30, ExpectedConditions.visibilityOf(element));
    }

    private void waiting(List<WebElement> elements) {
        logger.info("short explicit wait visibility Of elements list \n" + String.valueOf(elements));
        waitsSwitcher.setExplicitWait(30, ExpectedConditions.visibilityOfAllElements(elements));
    }

    /*
    private void isArticleContentDisplayed(WebElement element) {
        logger.info("<---------------------------------------------------------------->");
        logger.info("Verification that all content in the chosen article displayed");
        logger.info("assert all items displayed");
        logger.info("Title: " + element.findElement(ARTICLE_TITLE.getPath()).getText());
        softAssert.assertTrue(
                searchElementByCss(ARTICLE_IMAGE.getPath()).isDisplayed() &&
                        searchElementByCss(ARTICLE_ECO_BUTTON.getPath()).isDisplayed() &&
                        searchElementByCss(ARTICLE_TITLE.getPath()).isDisplayed() &&
                        searchElementByCss(ARTICLE_TEXT.getPath()).isDisplayed() &&
                        searchElementByCss(ARTICLE_CREATION_DATE.getPath()).isDisplayed() &&
                        searchElementByCss(ARTICLE_AUTHOR_NAME.getPath()).isDisplayed(),
                "Assert that all content is displayed in article");
        logger.info("assert text length < 170");


        softAssert.assertTrue(
                element.findElement(ARTICLE_TITLE.getPath()).getText().trim()
                        .replace("\\s", "").length() < 171,
                "Assert that topic text length < 170. Title: " +
                        element.findElement(ARTICLE_TITLE.getPath()).getText());
    }

    private void isArticleTextContentDisplayed(WebElement element) {
        logger.info("<---------------------------------------------------------------->");
        logger.info("Verification that all text content in the chosen article displayed");
        logger.info("assert all text  items displayed");
        logger.info("Text: " + element.findElement(ARTICLE_TEXT.getPath()).getText());
        softAssert.assertTrue(
                searchElementByCss(ARTICLE_TITLE.getPath()).isDisplayed() &&
                        searchElementByCss(ARTICLE_TEXT.getPath()).isDisplayed() &&
                        searchElementByCss(ARTICLE_CREATION_DATE.getPath()).isDisplayed() &&
                        searchElementByCss(ARTICLE_AUTHOR_NAME.getPath()).isDisplayed()
                , "Assert that all text content is displayed in article");
        logger.info("assert text length < 200");
        softAssert.assertTrue(
                element.findElement(ARTICLE_TEXT.getPath()).getText().trim()
                        .replace("\\s", "").length() < 201,
                "Assert that text length < 200. Title: " +
                        element.findElement(ARTICLE_TEXT.getPath()).getText());
    }

    public void isArticleContentDisplayed(List<WebElement> elements) {
        logger.info("Verification that all content in the list of articles displayed");
        for (WebElement element : elements) {
            logger.info("element: " + element);
            isArticleContentDisplayed(element);
        }
        softAssert.assertAll();
    }

    public void isArticleTextContentDisplayed(List<WebElement> elements) {
        logger.info("Verification that all text content in the list of articles displayed");

        for (WebElement element : elements) {
            logger.info("element: " + element);
            isArticleTextContentDisplayed(element);
        }
        softAssert.assertAll();
    }

    public String getArticleCreationDate(WebElement element) {
        logger.info("Get creation date");
        String date = element.findElement(ARTICLE_CREATION_DATE.getPath())
                .getText().replace(",", "");
        String[] dateFlip = date.split(" ");
        String dbFormatDate = dateFlip[2] + "-" + dateFlip[0] + "-" + dateFlip[1].replaceAll("^0", ""); //Year-month-day
        return dbFormatDate
                .replace("Jan", "01")
                .replace("Feb", "02")
                .replace("Mar", "03")
                .replace("Apr", "04")
                .replace("May", "05")
                .replace("Jun", "06")
                .replace("Jul", "07")
                .replace("Aug", "08")
                .replace("Sep", "09")
                .replace("Oct", "10")
                .replace("Nov", "11")
                .replace("Dec", "12");
    }

    public int articleDisplayedCount() {
        return articleExistCount = getElements(DISPLAYED_ARTICLES.getPath()).size();
    }

    public String formatChronologicalDateFromDB(String topic) {
        Pattern pattern = Pattern.compile("creation_date=[^\"]+");
        final Matcher m = pattern.matcher(topic);
        m.find();

        String res = m.group(0)
                .replace(",", "")
                .substring(0, 24)
                .replace("creation_date=", "");

        String[] dateFlip = res.split("-");
        String dbFormatDate = dateFlip[0] + "-" + dateFlip[1] + "-" + dateFlip[2].replaceAll("^0", ""); //Year-month-day

        return dbFormatDate;
    }
    
    public WebElement getRandomTopic() {
        final int topicNumber = getRandom();
        logger.info("get random topic: " + topicNumber + " css: \n"
                + searchElementsByCss(DISPLAYED_ARTICLES.getPath()).get(topicNumber).getCssValue("class"));
        return searchElementsByCss(DISPLAYED_ARTICLES.getPath()).get(topicNumber);
    }

    public int getCreationDateLength(WebElement element) {
        return element.findElement(ARTICLE_CREATION_DATE.getPath()).getSize().getWidth();
    }

    public int getCreationAuthorNameLength(WebElement element) {
        return element.findElement(ARTICLE_AUTHOR_NAME.getPath()).getSize().getWidth();
    }

    public int getRandom() {
        return (int) (Math.random() * ((20 - 1) - 10 + 1) + 1);
    }
    */

    public List<WebElement> getNewsTitles() {
        return searchElementsByCss(ARTICLE_TITLE.getPath());
    }

    public List<WebElement> getNewsTexts() {
        return searchElementsByCss(ARTICLE_TEXT.getPath());
    }
    //------------------------------------------------

    @Override
    public WebDriver setDriver() {
        return this.driver;
    }
}
