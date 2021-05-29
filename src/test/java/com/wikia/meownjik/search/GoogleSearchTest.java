package com.wikia.meownjik.search;

import com.wikia.meownjik.TestRunner;
import com.wikia.meownjik.pageobjects.GoogleSearchPage;
import com.wikia.meownjik.pageobjects.GoogleSearchResultsPage;
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
