package com.wikia.meownjik.pageobjects;

import com.wikia.meownjik.pageelements.Link;
import com.wikia.meownjik.util.WaitsSwitcher;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;

/**
 * A template for any page object.
 * It's recommended also to implement IAnonymousPO or ILoggedInPO where possible.
 */
public abstract class BasePageObject {
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
     * @return An array of all link elements ("a" with "href")
     */
    public ArrayList<Link> getAllLinks() {
        By allLinksSelector = By.xpath("//a[@href!='']"); //Sometimes href="" (empty) is used to make link unclickable
        ArrayList<Link> allLinks = new ArrayList<>();
        ArrayList<WebElement> allLinks0 = (ArrayList<WebElement>) driver.findElements(allLinksSelector);
        for(WebElement l : allLinks0) {
            allLinks.add(new Link(l));
        }
        return allLinks;
    }
}
