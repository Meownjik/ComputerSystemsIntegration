package com.wikia.meownjik.pageelements;


import com.wikia.meownjik.locators.Locator;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Functionality for performing actions with input box
 */
public class InputBox extends BaseElement {
    public InputBox(WebDriver driver, Locator locator) {
        super(driver, locator);
    }
    public InputBox(WebElement element, Locator locator) {
        super(element, locator);
    }
    public InputBox(WebElement element) {
        super(element);
    }
    public InputBox click() {
        this.element.click();
        return this;
    }
    public void clear() {
        this.element.clear();
    }

    public String setData(String value) {
        this.element.sendKeys(value);
        return value;
    }

    public void enter() {
        this.element.sendKeys(Keys.ENTER);
    }

    public String getAttribute(String attribute) {
        return this.element.getAttribute(attribute);
    }

    public String getValue() {
        return element.getAttribute("value");
    }
}
