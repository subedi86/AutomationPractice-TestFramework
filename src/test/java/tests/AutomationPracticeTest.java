package tests;

import base.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.junit.jupiter.api.*;
import utils.ConfigReader;
import utils.TestDataReader;
import pages.HomePage;
import pages.AutomationPracticePage;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class AutomationPracticeTest {

    private WebDriver driver;
    private AutomationPracticePage page;

    @BeforeEach
    void setUp() {
        // Get values from config.properties
        String browser = ConfigReader.get("browser");
        boolean headless = Boolean.parseBoolean(ConfigReader.get("headless"));
        String baseUrl = ConfigReader.get("baseUrl");

        DriverFactory.initDriver(browser, headless);
        driver = DriverFactory.getDriver();

        driver.get(baseUrl);

        HomePage home = new HomePage(driver);
        page = home.goToAutomationPractice();
    }

    @Test
    void fullPracticeFlow() {
        page.waitReady()
            .acceptConsentIfPresent()

            // Fill form
            .typeName(TestDataReader.get("name"))
            .typeEmail(TestDataReader.get("email"))
            .typePhone(TestDataReader.get("phone"))
            .typeAddress(TestDataReader.get("address"))
            .selectGenderMale()
            .selectDay(TestDataReader.get("day"))
            .chooseCountry(TestDataReader.get("country"))
            .chooseColors(TestDataReader.get("colors").split(","))
            .acceptConsentIfPresent()

            // Dynamic Button
            .clickDynamicButton();

        // Handle alert after clicking dynamic button
        try {
            driver.switchTo().alert().accept();
        } catch (Exception ignored) {}

        page
            // Alerts
            .clickSimpleAlert()
            .clickConfirmationAlert(true) // OK
            .clickPromptAlert(TestDataReader.get("promptInput"), true)

            // Tabs and Popup
            .clickNewTab()
            .clickPopupWindow()

            // AJAX button
            .clickAjaxHiddenButton()

            // Hover
            .hoverAndSelectOption2()

            // Double click
            .doubleClickCopyText();

        // --- Verifications ---
        assertTrue(page.isGenderMaleSelected(), "Gender should be Male");
        assertTrue(page.isDaySelected(TestDataReader.get("day")), "Day should be selected");
        assertEquals(TestDataReader.get("country"), page.getSelectedCountry(), "Country should match");

        for (String c : TestDataReader.get("colors").split(",")) {
            assertTrue(page.getSelectedColors().contains(c.trim()), c + " should be selected");
        }

        assertEquals("Hello Automation!", page.getTargetText(), "Text should be copied after double click");
    }

}