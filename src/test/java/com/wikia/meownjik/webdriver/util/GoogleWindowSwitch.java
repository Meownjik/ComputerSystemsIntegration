package com.wikia.meownjik.webdriver.util;

import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;


 public class GoogleWindowSwitch   {

    public static String  WindowsHandling(Set<String> oldWindowsSet, WebDriver driver){
        return (new WebDriverWait(driver, 10))
                .until(new ExpectedCondition<String>() {
                           public String apply(WebDriver driver1) {
                               Set<String> newWindowsSet = driver1.getWindowHandles();
                               newWindowsSet.removeAll(oldWindowsSet);
                               return newWindowsSet.size() > 0 ?
                                       newWindowsSet.iterator().next() : null;
                           }
                       }
                );
    }

}
