package com.wikia.meownjik.webdriver.locators;

import org.openqa.selenium.By;

public enum GoogleSearchLocators implements Locator {
    INPUT_FIELD(By.name("q")),
    RESULT_HEADER(By.tagName("h3")),
    NEXT_PAGE(By.id("pnnext"));

    private final By path;

    GoogleSearchLocators(By path) {
        this.path=path;
    }

    @Override
    public By getPath() {
        return path;
    }
}
