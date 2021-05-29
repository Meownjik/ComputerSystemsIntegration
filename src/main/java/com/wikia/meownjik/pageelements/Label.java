package com.wikia.meownjik.pageelements;

import com.wikia.meownjik.locators.Locator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Any one line not editable text area
 */
public class Label extends BaseElement {
    public Label(WebDriver driver, Locator locator) {
        super(driver, locator);

    }
    public Label(WebElement elementToParse, Locator locator) {
        super(elementToParse, locator);
    }
    public Label(WebElement element) {
        super(element);
    }
    public String getAttribute(String attribute) {
        return this.element.getAttribute(attribute);
    }
}
