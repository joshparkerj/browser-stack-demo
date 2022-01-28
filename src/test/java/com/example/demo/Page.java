package com.example.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;

public abstract class Page {
    SearchContext context;
    By loginScreenSmallForm;

    public Page(SearchContext context) {
        this.context = context;
        loginScreenSmallForm = By.cssSelector(".modal-dialog .visible-sm form");
    }
}
