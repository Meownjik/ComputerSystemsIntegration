package com.wikia.meownjik.pageelements;

import com.wikia.meownjik.locators.Locator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;

public class DropdownList extends BaseElement {
    public DropdownList(WebDriver driver, Locator locator) {
        super(driver, locator);
    }
    public DropdownList(WebElement elementToParse, Locator locator) {
        super(elementToParse, locator);
    }
    public DropdownList(WebElement element) {
        super(element);
    }

    public String getAttribute(String attribute) {
        return this.element.getAttribute(attribute);
    }

    /**
     *
     * @return list of buttons - items of the list
     */
    public ArrayList<Button> getItems() {
        ArrayList<WebElement> options0 = (ArrayList<WebElement>) element.findElements(By.cssSelector(" li"));
        ArrayList<Button> options = new ArrayList<>();
        for (WebElement b : options0) {
            options.add(new Button(b));
        }
        return options;
    }

    public void click() {
        this.element.click();
    }

}
