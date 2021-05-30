package com.wikia.meownjik.webdriver.pageobjects;

import com.wikia.meownjik.webdriver.util.WaitsSwitcher;
import com.wikia.meownjik.webdriver.util.StableWebElementSearch;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base Abstract Class of Header and Footer.
 * All page classes should extend this class
 */
public abstract class TopPart extends BasePageObject implements StableWebElementSearch {
    /*
    private final int WINDOW_WIDTH_TO_SCROLL = 1024;
    private final int WINDOW_HEIGHT_TO_CLICK_FOOTER = 480;

    protected final String OPTION_NULL_MESSAGE = "DropdownComponent is null";
    */
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    //private WebElement copyright;

    public TopPart(WebDriver driver) {
        super(driver);
    }

    /*
    public WebElement getCopyright() {
        return copyright = driver.findElement(By.cssSelector(".footer_bottom-part"));
    }

    public String getCopyrightText() {
        return getCopyright().getText();
    }

    public void clickCopyright() {
        getCopyright().click();
    }

    protected void scrollToElementByAction(final WebElement element) {
        final Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.build().perform();
    }

    protected boolean isMenuClickable() {
        return driver.manage().window().getSize().height > WINDOW_HEIGHT_TO_CLICK_FOOTER;
    }


    public int getWindowWidth(int width){
        JavascriptExecutor js= (JavascriptExecutor)driver;
        String windowSize = js.executeScript("return (window.outerWidth - window.innerWidth + "+width+"); ").toString();
        width = Integer.parseInt((windowSize));
        return width;
    }
    public int getWindowHeight(int height){
        JavascriptExecutor js= (JavascriptExecutor)driver;
        String windowSize = js.executeScript("return (window.outerHeight - window.innerHeight + "+height+"); ").toString();
        height = Integer.parseInt((windowSize));
        return height;
    }
    */
    @Override
    public WebDriver setDriver() {
        return this.driver;
    }
}
