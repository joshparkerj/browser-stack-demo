package com.example.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class Auth extends Page {
    public Auth(SearchContext context) {
        super(context);
    }

    int retries;

    WebElement authForm() {
        WebElement smallForm = context.findElement(loginScreenSmallForm);
        if (smallForm.isDisplayed()) {
            return smallForm;
        }

        WebElement largeForm = context.findElement(By.cssSelector(".modal-dialog .visible-lg form"));
        if (largeForm.isDisplayed()) {
            return largeForm;
        }

        throw new RuntimeException("neither small form nor large form was displayed");
    }

    WebElement logout() {
        return authForm().findElement(By.cssSelector("a[href^='/logout']"));
    }

    public static Signin openSignin(WebDriver driver) {
        return openSignin(driver, "http://localhost:5000");
    }

    public static Signin openSignin(WebDriver driver, String signinUrl) {
        driver.get(signinUrl);
        return new Signin(driver);
    }

    public static Signin anonSignOut(WebDriver driver, String anonSignoutUrl) {
        driver.get(anonSignoutUrl);
        Signin signin = new Signin(driver);
        signin.logout().click();
        return signin;
    }

    public static Signin anonSignOut(WebDriver driver) {
        driver.navigate().back();
        Signin signin = new Signin(driver);
        signin.logout().click();
        return signin;
    }
}
