package com.wikia.meownjik.webdriver.pageobjects;

import com.wikia.meownjik.webdriver.pageelements.Link;
import com.wikia.meownjik.webdriver.util.StableWebElementSearch;
import com.wikia.meownjik.webdriver.util.WaitsSwitcher;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * A template for any page object.
 * It's recommended also to implement IAnonymousPO or ILoggedInPO where possible.
 */
public abstract class BasePageObject implements StableWebElementSearch {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected WebDriver driver;
    protected WebElement element;
    protected String url;
    protected WaitsSwitcher waitsSwitcher; //NB! Not available if constructor form element is used

    protected BasePageObject(WebDriver driver) {
        this.driver = driver;
        waitsSwitcher = new WaitsSwitcher(driver, 1, 5);
    }

    protected BasePageObject(WebElement element) {
        this.element = element;
    }

    @Override
    public WebDriver getDriver() {
        return this.driver;
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    public BasePageObject goToUrl(String url) {
        driver.get(url);
        this.url = url;
        return this;
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    /**
     * Every page may have links, so it's to BasePageObject
     *
     * @return An array of all link elements ("a" with "href")
     */
    public ArrayList<Link> getAllLinks() {
        By allLinksSelector = By.xpath("//a[@href!='']"); //Sometimes href="" (empty) is used to make link unclickable
        ArrayList<Link> allLinks = new ArrayList<>();
        ArrayList<WebElement> allLinks0 = (ArrayList<WebElement>) driver.findElements(allLinksSelector);
        for (WebElement l : allLinks0) {
            allLinks.add(new Link(l));
        }
        return allLinks;
    }
}
