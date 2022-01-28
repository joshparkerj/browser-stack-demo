package com.example.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class LenderHome extends Home {
    public LenderHome(SearchContext context) {
        super(context);

        homeHref = "lender";
//        if (userType().getText().equals("Applicant")) {
//            switchToLender();
//        }
    }

    private WebElement userType() {
        return context.findElement(By.cssSelector("h6 ~ p"));
    }

    private WebElement switchMenuitem() {
        return context.findElement(By.cssSelector("li[role=menuitem]"));
    }

    private WebElement loanCard(String projectName) {
        return loanCard(context, projectName);
    }

    private WebElement loanCard(SearchContext searchContext, String projectName) {
        String loanCardSelector = String.format("div#loan-card-%s", projectName.replaceAll("\\s+", "-"));
        return searchContext.findElement(By.cssSelector(loanCardSelector));
    }

    private boolean loanCardExists(SearchContext searchContext, String projectName) {
        String dashedName = projectName.replaceAll("\\s+", "-");
        String loanCardSelector = String.format("div#loan-card-%s", dashedName);
        return searchContext.findElements(By.cssSelector(loanCardSelector)).size() > 0;
    }


    private WebElement newLoanButton() {
        return context.findElement(By.cssSelector("a[href$='/create']"));
    }


    public boolean isDisplayed() {
        return true;
    }

    public void switchToLender() {
        userType().click();
        switchMenuitem().click();
    }
}
