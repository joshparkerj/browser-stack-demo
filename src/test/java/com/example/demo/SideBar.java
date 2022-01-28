package com.example.demo;

import com.example.demo.LenderHome;
import com.example.demo.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class SideBar extends Page {
    public SideBar(SearchContext context) {
        super(context);
    }

    private WebElement lending() {
        return context.findElement(By.cssSelector("li + ul a[href='/lender']"));
    }

    private WebElement calendar() {
        return context.findElement(By.cssSelector("li + ul a[href='/lender/calendar']"));
    }

    private WebElement analytics() {
        return context.findElement(By.cssSelector("li + ul a[href='/lender/analytics']"));
    }

    private WebElement communications() {
        return context.findElement(By.cssSelector("li + ul a[href='/lender/communications']"));
    }

    private WebElement archive() {
        return context.findElement(By.cssSelector("li + ul a[href='/lender/archive']"));
    }

    // Applicant and Loans are shown in the applicant sidebar
    private WebElement applicant() {
        return context.findElement(By.cssSelector("li + ul a[href='/applicant']"));
    }

    private WebElement loans() {
        return lending();
    }

    public LenderHome goToLender() {
        lending().click();
        return new LenderHome(context);
    }
}
