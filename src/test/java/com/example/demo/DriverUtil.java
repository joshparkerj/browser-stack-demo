package com.example.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class DriverUtil {
    public static boolean elementChanges(SearchContext context, By by, Predicate<WebElement> change) {
        return elementChanges(context, by, change, 5, 2);
    }

    public static boolean elementChanges(SearchContext context, By by, Predicate<WebElement> change, int pollsPerSecond, int maxSecondsToWait) {
        for (int polls = pollsPerSecond * maxSecondsToWait; polls > 0; polls--) {
            if (change.test(context.findElement(by))) {
                return true;
            }

            try {
                Thread.sleep(1000 / pollsPerSecond);
            } catch (InterruptedException ie) {
            }
        }

        return false;
    }

    public static void clickUntilNewPageOpens(SearchContext context, By toClick, By onNewPage) {
        clickUntilNewPageOpens(context, toClick, onNewPage, 5, 5);
    }

    public static void clickUntilNewPageOpens(SearchContext context, By toClick, By onNewPage, int retriesPerSecond, int secondsToWait) {
        for (int retries = retriesPerSecond * secondsToWait; retries > 0; retries--) {
            if (context.findElements(onNewPage).size() > 0) {
                return;
            }

            context.findElement(toClick).click();
            try {
                Thread.sleep(1000 / retriesPerSecond);
            } catch (InterruptedException ie) {
            }
        }
    }


    public static String whichElementAppears(SearchContext context, String elementName1, By by1, String elementName2, By by2) {
        return whichElementAppears(context, elementName1, by1, elementName2, by2, 10, 3);
    }

    public static String whichElementAppears(SearchContext context, String elementName1, By by1, String elementName2, By by2, int checksPerSecond, int secondsToWait) {
        for (int retries = checksPerSecond * secondsToWait; retries > 0; retries--) {

            if (context.findElements(by1).size() == 1) {
                return elementName1;
            } else if (context.findElements(by2).size() == 1) {
                return elementName2;
            }

            try {
                Thread.sleep(1000 / checksPerSecond);
            } catch (InterruptedException ie) {
            }
        }

        throw new RuntimeException(String.format("Neither element appeared: %s nor %s", elementName1, elementName2));
    }

    public static boolean elementAppears(SearchContext context, By by) {
        return elementAppears(context, by, 10, 2);
    }

    public static boolean elementAppears(SearchContext context, By by, int checksPerSecond, int secondsToWait) {
        for (int retries = checksPerSecond * secondsToWait; retries > 0; retries--) {
            if (context.findElements(by).size() > 0) {
                return true;
            }
        }

        return false;
    }

    public static void inputText(WebElement element, String text) {
        element.clear();
        int[] backspace = {0xE003};
        String back = new String(backspace, 0, 1);
        while (element.getAttribute("value").length() > 0) {
            element.sendKeys(back);
        }

        element.sendKeys(text);
    }

    public static void clickPossiblyIntercepted(WebElement element) {
        clickPossiblyIntercepted(element, 5, 5);
    }

    public static void clickPossiblyIntercepted(WebElement element, int retriesPerSecond, int secondsToWait) {
        for (int retries = retriesPerSecond * secondsToWait; retries > 0; retries--) {
            try {
                element.click();
                return;
            } catch (ElementClickInterceptedException e) {
                if (retries <= 0) {
                    throw e;
                }

                try {
                    Thread.sleep(1000 / retriesPerSecond);
                } catch (InterruptedException ie) {
                    throw e;
                }
            }
        }
    }

    public static void tryActionUntilCondition(WebElement element, Consumer<WebElement> action, Predicate<WebElement> condition) {
        tryActionUntilCondition(element, action, condition, 5, 5);
    }

    public static void tryActionUntilCondition(WebElement element, Consumer<WebElement> action, Predicate<WebElement> condition, int retriesPerSecond, int secondsToWait) {
        for (int retries = retriesPerSecond * secondsToWait; retries > 0; retries--) {
            action.accept(element);
            if (condition.test(element)) {
                return;
            }
            try {
                Thread.sleep(1000 / retriesPerSecond);
            } catch (InterruptedException ie) {
            }
        }
    }
}
