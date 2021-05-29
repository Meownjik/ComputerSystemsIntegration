package com.wikia.meownjik.webdriver.pageelements;

import com.wikia.meownjik.webdriver.locators.Locator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Any image
 */
public class Image extends BaseElement {

    public Image(WebDriver driver, Locator locator) {
        super(driver, locator);
    }

    public Image(WebElement elementToParse, Locator locator) {
        super(elementToParse, locator);
    }

    public Image(WebElement element) {
        super(element);
    }

    public String getSource() {
        return element.getAttribute("src");
    }
    public String getAltText() {
        return element.getAttribute("alt");
    }
    public void click() {
        element.click();
    }

}
