package com.wikia.meownjik.webdriver.pageelements;

import com.wikia.meownjik.webdriver.locators.Locator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Any clickable button or something with corresponding  functionality
 */
public class Button extends BaseElement {
    public Button(WebDriver driver, Locator locator) {
        super(driver, locator);

    }
    public Button(WebElement elementToParse, Locator locator) {
        super(elementToParse, locator);
    }
    public Button(WebElement element) {
        super(element);
    }


    public String getAttribute(String attribute) {
        return this.element.getAttribute(attribute);
    }
    public void click() {
        this.element.click();
    }
    public boolean isEnable(){
        return this.element.isEnabled();
    }

}
