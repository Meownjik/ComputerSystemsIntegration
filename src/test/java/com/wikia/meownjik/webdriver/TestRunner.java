package com.wikia.meownjik.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

/**
 * Default class for testing https://www.google.com.ua/
 * Other classes for testing this page should extend this class
 */
public class TestRunner {
    private final static String INITIAL_URL //"https://www.google.com.ua/"
            = "https://ita-social-projects.github.io/GreenCityClient/#/news";

    protected static WebDriver driver;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @BeforeClass
    public static void setUpClass() {
        String webDriverPath = System.getenv("webDriverPath");
        System.setProperty("webdriver.chrome.driver", webDriverPath);
        var options = new ChromeOptions();
        options.setHeadless(true);
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(1500, TimeUnit.MILLISECONDS);
        driver.manage().timeouts().pageLoadTimeout(65, TimeUnit.SECONDS);
    }

    @AfterClass
    public void tearDownClass() {
        driver.close();
        driver.quit();
    }

    @BeforeMethod
    public void setUp() {
        driver.navigate().to(INITIAL_URL);
    }

    @AfterMethod
    public void tearDown() {
        //logout and so on
    }

}
