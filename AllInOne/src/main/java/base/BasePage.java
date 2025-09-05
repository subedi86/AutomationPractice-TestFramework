package base;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait  = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    protected WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    protected void click(By locator) { waitClickable(locator).click(); }
    protected void type(By locator, String text) {
        WebElement e = waitVisible(locator);
        e.clear(); e.sendKeys(text);
    }
    protected void selectByVisibleText(By locator, String value) {
        new Select(waitVisible(locator)).selectByVisibleText(value);
    }
    protected void waitInvisibility(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
}
