package com.wikia.meownjik.webdriver.search;

import com.wikia.meownjik.webdriver.TestRunner;
import com.wikia.meownjik.webdriver.pageobjects.GoogleSearchPage;
import com.wikia.meownjik.webdriver.pageobjects.GoogleSearchResultsPage;
import org.testng.annotations.Test;

public class GoogleSearchTest extends TestRunner {

    @Test
    public void searchTest() {
        var googleSearchPage = new GoogleSearchPage(driver);
        GoogleSearchResultsPage results = googleSearchPage.search("Test");
        results.goToNextPage()
                .goToNextPage();
    }
}
