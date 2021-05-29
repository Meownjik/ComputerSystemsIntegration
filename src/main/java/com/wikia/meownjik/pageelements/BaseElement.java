package com.wikia.meownjik.pageelements;

import com.wikia.meownjik.locators.Locator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * A template for any web element
 */
public abstract class BaseElement {
    protected By path;
    protected WebElement element;
    protected WebDriver driver;

    protected BaseElement(WebDriver driver, Locator locator) {
        this.driver = driver;
        this.path = locator.getPath();
        element = driver.findElement(path);
    }
    protected BaseElement(WebElement elementToParse, Locator locator) {
        this.path = locator.getPath();
        this.element = elementToParse.findElement(this.path);
    }
    protected  BaseElement(WebElement element) {
        this.element = element;
    }

    public String getText(){
        return element.getText();
    }
}
