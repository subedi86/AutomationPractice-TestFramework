package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    private final By automationPracticeLink =
            By.xpath("//a[normalize-space(.)='Automation Practice' or contains(@href,'/automation-practice/')]");

    public HomePage(WebDriver driver) { super(driver); }

    public AutomationPracticePage goToAutomationPractice() {
        // Wait for the link to be present and clickable, then click
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
            .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(automationPracticeLink));
        click(automationPracticeLink);
        return new AutomationPracticePage(driver);
    }
}