package com.wikia.meownjik.pageobjects;

import com.wikia.meownjik.pageelements.Button;
import com.wikia.meownjik.pageelements.InputBox;
import org.openqa.selenium.WebDriver;

import static com.wikia.meownjik.locators.GoogleSearchLocators.*;

public class GoogleSearchResultsPage extends BasePageObject {
    private InputBox searchField;
    private Button nextPageButton;

    public GoogleSearchResultsPage(WebDriver driver) {
        super(driver);
        //waitsSwitcher.setExplicitWait(ExpectedConditions.visibilityOfElementLocated(INPUT_FIELD.getPath()));
        searchField = new InputBox(driver.findElement(INPUT_FIELD.getPath()));
        nextPageButton = new Button(driver.findElement(NEXT_PAGE.getPath()));
    }

    public GoogleSearchResultsPage goToNextPage() {
        nextPageButton.click();
        return new GoogleSearchResultsPage(driver);
    }

}
