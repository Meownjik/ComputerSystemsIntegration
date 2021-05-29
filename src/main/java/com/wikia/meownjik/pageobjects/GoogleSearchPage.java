package com.wikia.meownjik.pageobjects;

import com.wikia.meownjik.pageelements.InputBox;
import com.wikia.meownjik.util.WaitsSwitcher;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.wikia.meownjik.locators.GoogleSearchLocators.*;

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
