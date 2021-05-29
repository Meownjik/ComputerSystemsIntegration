package com.wikia.meownjik.webdriver.pageobjects;

import com.wikia.meownjik.webdriver.pageelements.InputBox;
import org.openqa.selenium.WebDriver;

import static com.wikia.meownjik.webdriver.locators.GoogleSearchLocators.*;

public class GoogleSearchPage extends BasePageObject {
    private InputBox searchField;

    public GoogleSearchPage(WebDriver driver) {
        super(driver);
        //waitsSwitcher.setExplicitWait(ExpectedConditions.visibilityOfElementLocated(INPUT_FIELD.getPath()));
        searchField = new InputBox(driver.findElement(INPUT_FIELD.getPath()));
    }

    public GoogleSearchResultsPage search(String query) {
        searchField.click();
        searchField.clear();
        searchField.setData(query);
        searchField.enter();
        return new GoogleSearchResultsPage(driver);
    }

}
