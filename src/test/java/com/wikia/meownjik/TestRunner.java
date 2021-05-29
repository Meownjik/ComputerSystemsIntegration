package com.wikia.meownjik;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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
    protected static WebDriver driver;
    //protected static WaitsSwitcher waitsSwitcher;
    public TestRunner() {

    }

    @BeforeClass
    public static void setUpClass() {
        String webDriverPath = System.getenv("webDriverPath");
        System.setProperty("webdriver.chrome.driver", webDriverPath);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(1500, TimeUnit.MILLISECONDS);
        //waitsSwitcher = new WaitsSwitcher(driver, 1, 5);
        driver.manage().timeouts().pageLoadTimeout(65, TimeUnit.SECONDS);
    }

    @AfterClass
    public static void tearDownClass() {
        driver.close();
        driver.quit();
    }

    @BeforeMethod
    public void setUp() {
        driver.navigate().to("https://www.google.com.ua/");
    }

    @AfterMethod
    public void tearDown() {
        //logout and so on
    }

}
