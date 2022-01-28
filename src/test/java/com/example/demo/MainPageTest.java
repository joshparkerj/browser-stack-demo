package com.example.demo;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class MainPageTest {
    private static String chromeDriverPath = "src/main/resources/chromedriver.exe";
    private static WebDriver driver;

    public static void main(String[] args) {
        String startUrl;
        if (args.length > 0) {
            startUrl = args[0];
        } else {
            startUrl = "https://sherpas.nerdepiphany.com/";
        }


        String browserStackUsername = System.getenv("BROWSER_STACK_USERNAME");
        String browserStackAccessKey = System.getenv("BROWSER_STACK_ACCESS_KEY");
        System.out.println(browserStackUsername);
        System.out.println(browserStackAccessKey);
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("os", "Windows");
        caps.setCapability("browser", "chrome");
        // caps.setCapability("browserstack.local", true);
        String browserStackUrl = "https://" + browserStackUsername + ":" + browserStackAccessKey + "@hub.browserstack.com/wd/hub";

        try {

            URL url = new URL(browserStackUrl);

            driver = new RemoteWebDriver(url, caps);
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            Signin auth = Signin.openSignin(driver, startUrl);
            List<WebElement> proceedLinks = driver.findElements(By.id("proceed-link"));
            if (proceedLinks.size() > 0) {
                driver.findElement(By.id("details-button")).click();
                proceedLinks.get(0).click();

            }

            String username = "lender+luserabc";
            String password = "Password1!";
            auth.loginLender(username, password);

            driver.quit();
        } catch (MalformedURLException e) {
            System.out.println(browserStackUrl);
            e.printStackTrace();
        }
    }
}
