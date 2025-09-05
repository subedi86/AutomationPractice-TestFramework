package pages;

import base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AutomationPracticePage extends BasePage {

    // Inputs
    private final By nameInput    = By.id("name");
    private final By emailInput   = By.id("email");
    private final By phoneInput   = By.id("phone");
    private final By addressInput = By.id("address");

    // Consent (Complianz)
    private final By consentBanner = By.cssSelector("div.cmplz-cookiebanner[role='dialog']");
    private final By consentAccept = By.cssSelector("button.cmplz-btn.cmplz-accept");

    // Occasional overlay
    private final By popupOverlay = By.id("popupOverlay");

    // Gender (text node after input)
    private final By genderMale = By.xpath(
        "//b[normalize-space()='Gender:']/following::input[@type='radio' " +
        " and normalize-space(following-sibling::text()[1])='Male'][1]");
    private final By genderFemale = By.xpath(
        "//b[normalize-space()='Gender:']/following::input[@type='radio' " +
        " and normalize-space(following-sibling::text()[1])='Female'][1]");

    // Days (text node after input)
    private By dayCheckbox(String day) {
        return By.xpath(
            "//b[normalize-space()='Days:']/following::input[@type='checkbox' " +
            " and normalize-space(following-sibling::text()[1])='" + day + "'][1]"
        );
    }

    // Selects
    private final By countrySelect = By.xpath("//label[normalize-space()='Country:']/following::select[1]");
    private final By colorsSelect  = By.xpath("//label[normalize-space()='Colors:']/following::select[1]");
    private final By dynamicButton = By.xpath("//button[text()='CLICK ME']");
 // ---------- Alerts & Popups ----------
    private final By simpleAlertBtn   = By.xpath("//button[text()='Simple Alert']");
    private final By confirmAlertBtn  = By.xpath("//button[text()='Confirmation Alert']");
    private final By promptAlertBtn   = By.xpath("//button[text()='Prompt Alert']");
    private final By newTabBtn        = By.xpath("//button[text()='New Tab']");
    private final By popupWindowBtn   = By.xpath("//button[text()='Popup Window']");
    private final By popupCloseBtn    = By.xpath("//button[text()='Close Me']");

    // ---------- AJAX Hidden Button ----------
    private final By revealButton     = By.xpath("//button[text()='Reveal Hidden Button (after 3s)']");
    private final By hiddenButton     = By.xpath("//button[text()='I was hidden!']");

    // ---------- Mouse Hover ----------
    private final By hoverButton      = By.xpath("//button[text()='Hover Over Me']");
    private final By option2          = By.xpath("//*[@id=\"post-21301\"]/div/div[1]/div[5]/div/div/p[2]");

    // ---------- Double Click ----------
    private final By copyButton       = By.xpath("//button[text()='Copy Text']");
    private final By sourceTextBox    = By.xpath("(//input[@type='text'])[1]");
    private final By targetTextBox    = By.xpath("(//input[@type='text'])[2]");
    public AutomationPracticePage(WebDriver driver) { super(driver); }

    // -------- Actions --------
    public AutomationPracticePage typeName(String v){ type(nameInput, v); return this; }
    public AutomationPracticePage typeEmail(String v){ type(emailInput, v); return this; }
    public AutomationPracticePage typePhone(String v){ type(phoneInput, v); return this; }
    public AutomationPracticePage typeAddress(String v){ type(addressInput, v); return this; }

    /** Wait for occasional overlay to disappear (non-fatal if not present). */
    public AutomationPracticePage waitReady() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.invisibilityOfElementLocated(popupOverlay));
        } catch (TimeoutException ignored) { }
        return this;
    }

    /** Click "Accept" on cookie/consent if it appears. */
    public AutomationPracticePage acceptConsentIfPresent() {
        try {
            WebDriverWait sw = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement banner = sw.until(ExpectedConditions.presenceOfElementLocated(consentBanner));
            WebElement accept = sw.until(ExpectedConditions.elementToBeClickable(consentAccept));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click()", accept);
            sw.until(ExpectedConditions.invisibilityOf(banner));
        } catch (TimeoutException ignored) {
            // Not shown
        }
        return this;
    }

    public AutomationPracticePage selectGenderMale(){ safeClick(genderMale); return this; }
    public AutomationPracticePage selectGenderFemale(){ safeClick(genderFemale); return this; }
    public AutomationPracticePage selectDay(String day){ safeClick(dayCheckbox(day)); return this; }

    public AutomationPracticePage chooseCountry(String visibleText){
        WebElement el = waitVisible(countrySelect);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'})", el);
        new Select(el).selectByVisibleText(visibleText);
        return this;
    }

    public AutomationPracticePage chooseColors(String... visibleTexts){
        WebElement el = waitVisible(colorsSelect);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'})", el);
        Select s = new Select(el); // multiple=true on the page
        for (String v : visibleTexts) s.selectByVisibleText(v);
        return this;
    }

    // ---- helpers ----
    private void safeClick(By locator) {
        WebElement el = waitClickable(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'})", el);
        try {
            el.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click()", el);
        }
    }
    public boolean isLoaded() {
        // Example: check the URL contains "/automation-practice/"
        return driver.getCurrentUrl().contains("/automation-practice/");
        
        // OR: check a key element is visible
        // return isDisplayed(By.xpath("//h1[text()='Automation Practice']"));
    }

    // --- Verifications ---
    public boolean isGenderMaleSelected() {
        return waitVisible(genderMale).isSelected();
    }

    public boolean isDaySelected(String day) {
        return waitVisible(dayCheckbox(day)).isSelected();
    }
    public AutomationPracticePage clickDynamicButton() {
        // Scroll the button into view before clicking
        WebElement button = waitVisible(dynamicButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'})", button);
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(dynamicButton));
            button.click();
        } catch (Exception e) {
            // Fallback: use JavaScript to click if normal click fails
            ((JavascriptExecutor) driver).executeScript("arguments[0].click()", button);
        }
        return this;
    }
    public String getSelectedCountry() {
        return new Select(waitVisible(countrySelect)).getFirstSelectedOption().getText().trim();
    }

    public List<String> getSelectedColors() {
        return new Select(waitVisible(colorsSelect)).getAllSelectedOptions()
                .stream().map(e -> e.getText().trim()).toList();
    }
 // --- Methods ---

    public AutomationPracticePage clickSimpleAlert() {
        WebElement button = waitVisible(simpleAlertBtn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'})", button);
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(simpleAlertBtn));
            button.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click()", button);
        }
        driver.switchTo().alert().accept();
        return this;
    }

    public AutomationPracticePage clickConfirmationAlert(boolean accept) {
        WebElement button = waitVisible(confirmAlertBtn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'})", button);
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(confirmAlertBtn));
            button.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click()", button);
        }
        if (accept) {
            driver.switchTo().alert().accept();
        } else {
            driver.switchTo().alert().dismiss();
        }
        return this;
    }

    public AutomationPracticePage clickPromptAlert(String input, boolean accept) {
        WebElement button = waitVisible(promptAlertBtn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'})", button);
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(promptAlertBtn));
            button.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click()", button);
        }
        if (input != null && !input.isEmpty()) {
            driver.switchTo().alert().sendKeys(input);
        }
        if (accept) {
            driver.switchTo().alert().accept();
        } else {
            driver.switchTo().alert().dismiss();
        }
        return this;
    }

    public AutomationPracticePage clickNewTab() {
        WebElement button = waitVisible(newTabBtn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'})", button);
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(newTabBtn));
            button.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click()", button);
        }
        String currentTab = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(currentTab)) {
                driver.switchTo().window(handle);
                break;
            }
        }
        driver.close();
        driver.switchTo().window(currentTab);
        return this;
    }

    public AutomationPracticePage clickPopupWindow() {
        WebElement button = waitVisible(popupWindowBtn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'})", button);
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(popupWindowBtn));
            button.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click()", button);
        }
        String mainWindow = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(mainWindow)) {
                driver.switchTo().window(handle);
                break;
            }
        }
        WebElement closeBtn = waitVisible(popupCloseBtn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'})", closeBtn);
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(popupCloseBtn));
            closeBtn.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click()", closeBtn);
        }
        driver.switchTo().window(mainWindow);
        return this;
    }

    public AutomationPracticePage clickAjaxHiddenButton() {
        WebElement button = waitVisible(revealButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'})", button);
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(revealButton));
            button.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click()", button);
        }
        WebElement el = waitClickable(hiddenButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'})", el);
        try {
            el.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click()", el);
        }
        return this;
    }

    public AutomationPracticePage hoverAndSelectOption2() {
        WebElement button = waitVisible(hoverButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'})", button);
        Actions actions = new Actions(driver);
        actions.moveToElement(button).perform();
        // Wait for Option 2 to be visible after hover
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(15))
            .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(option2));
        WebElement option = waitVisible(option2);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'})", option);
        try {
            option.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click()", option);
        }
        return this;
    }

    public AutomationPracticePage doubleClickCopyText() {
        WebElement button = waitVisible(copyButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'})", button);
        Actions actions = new Actions(driver);
        actions.doubleClick(button).perform();
        return this;
    }

    public String getTargetText() {
        return waitVisible(targetTextBox).getAttribute("value").trim();
    }
}