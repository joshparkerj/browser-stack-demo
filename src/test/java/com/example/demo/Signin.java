package com.example.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class Signin extends Auth {
    public Signin(SearchContext context) {
        super(context);
    }

    private WebElement usernameInput() {
        return authForm().findElement(By.id("signInFormUsername"));
    }

    private WebElement passwordInput() {
        return authForm().findElement(By.id("signInFormPassword"));
    }

    private WebElement submitButton() {
        return authForm().findElement(By.name("signInSubmitButton"));
    }

    private WebElement signupLink() {
        return authForm().findElement(By.cssSelector("a[href^='/signup']"));
    }

    public void enterUsername(String username) {
        usernameInput().sendKeys(username);
    }

    public void enterPassword(String password) {
        passwordInput().sendKeys(password);
    }

    public void submit() {
        submitButton().click();
    }

    private WebElement signin() {
        return context.findElement(By.cssSelector("a[href='/oauth2/authorization/cognito']"));
    }

    private void login(String username, String password) {
        login(username, password, By.cssSelector("a[href='/oauth2/authorization/cognito']"), By.cssSelector("button#account-button"));
    }

    private void login(String username, String password, By signinBy, By appBy) {
        if (retries == 0) {
            throw new RuntimeException("can't login");
        }

        retries--;
        enterUsername(username);
        enterPassword(password);
        submit();

        String nextElement = DriverUtil.whichElementAppears(context, "signin", signinBy, "app", appBy);
        if (nextElement == "signin") {
            signin().click();
            // I think it used to prompt for username and password again, now it redirects to homepage
            // login(username, password);
            return;
        } else if (nextElement == "app") {
            return;
        }
    }

    private boolean redirectedToSignin() {
        return DriverUtil.elementAppears(context, By.cssSelector("a[href='/oauth2/authorization/cognito']"), 10, 3);
    }

    public LenderHome loginLender(String lenderUsername, String lenderPassword) {
        retries = 5;
        login(lenderUsername, lenderPassword);
        retries = 0;
        return new LenderHome(context);
    }
}
