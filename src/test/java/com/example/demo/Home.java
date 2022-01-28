package com.example.demo;

import com.example.demo.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;

public abstract class Home extends Page {
    public Home(SearchContext context) {
        super(context);
        logout = By.cssSelector("a[href='/logout']");
    }

    String homeHref;
    By logout;

    WebElement sidebarButton() {
        WebElement button = context.findElement(By.cssSelector("header > div > button"));
        return button;
    }

    WebElement userMenuButton() {
        return context.findElement(By.cssSelector("button#account-button"));
    }

    WebElement logoutLink() {
        return context.findElement(logout);
    }

    public SideBar sideBar() {
        String cssSelector = String.format("li + ul a[href^='/%s']", homeHref);
        List<WebElement> sidebarLinks = context.findElements(By.cssSelector(cssSelector));
        if (sidebarLinks.size() == 0 || !sidebarLinks.get(0).isDisplayed()) {
            sidebarButton().click();
        }

        return new SideBar(context);
    }

    public void logout() {
        userMenuButton().click();
        DriverUtil.clickUntilNewPageOpens(context, logout, loginScreenSmallForm);
    }
}
