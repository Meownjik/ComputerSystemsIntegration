package com.wikia.meownjik;

import org.testng.annotations.Test;

import java.awt.*;

public class AppTest {

    @Test
    public void notificationTest() throws AWTException, InterruptedException {
        if (SystemTray.isSupported()) {
            Notificator.displayTray("Test", "tst");
            Thread.sleep(2000);
        } else {
            System.err.println("System tray not supported!");
        }
    }
}
