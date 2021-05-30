package com.wikia.meownjik.webdriver.util;


import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * convenient way to search Web Elements
 * Waits for visibilityOfElementLocated and then returns the WebElement searched for.
 */
public interface StableWebElementSearch {
    /**
     * set Webdriver that should to be used
     *
     * @return WebDriver
     */
    WebDriver getDriver();

    default WebElement searchElementByCss(By locator) {
        return explicitSearch(visibilityOfElementLocated(locator));
    }

    default WebElement searchElementByCss(String locator) {
        return explicitSearch(visibilityOfElementLocated(By.cssSelector(locator)));
    }

    default WebElement searchElementByXpath(By xPath) {
        return explicitSearch(visibilityOfElementLocated(xPath));
    }

    default WebElement searchElementByXpath(String xPath) {
        return explicitSearch(visibilityOfElementLocated(By.xpath(xPath)));
    }

    default List<WebElement> searchElementsByCss(By locator) {
        searchElementByCss(locator);
        return getDriver().findElements(locator);
    }

    default List<WebElement> searchElementsByXpath(By locator) {
        searchElementByCss(locator);
        return getDriver().findElements(locator);
    }

    default List<WebElement> searchElementsByCss(String locator) {
        searchElementByCss(locator);
        return getDriver().findElements(By.cssSelector(locator));
    }

    default List<WebElement> searchElementsXpath(String locator) {
        searchElementByCss(locator);
        return getDriver().findElements(By.cssSelector(locator));
    }

    default <V> V explicitSearch(Function<? super WebDriver, V> condition) {
        getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        V element = (new WebDriverWait(getDriver(), 20)).until(condition);
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return element;
    }

}

