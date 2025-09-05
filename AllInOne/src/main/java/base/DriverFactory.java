package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public final class DriverFactory {
    private static final ThreadLocal<WebDriver> TL_DRIVER = new ThreadLocal<>();

    private DriverFactory() {}

    public static void initDriver(String browser, boolean headless) {
        if (TL_DRIVER.get() != null) return;

        switch ((browser == null ? "chrome" : browser).toLowerCase()) {
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions fo = new FirefoxOptions();
                if (headless) fo.addArguments("--headless");
                TL_DRIVER.set(new FirefoxDriver(fo));
                getDriver().manage().window().maximize(); // maximize Firefox
            }
            default -> {
                WebDriverManager.chromedriver().setup();
                ChromeOptions co = new ChromeOptions();
                if (headless) co.addArguments("--headless=new");
                TL_DRIVER.set(new ChromeDriver(co));
                getDriver().manage().window().maximize(); // maximize Chrome
            }
        }
    }

    public static WebDriver getDriver() {
        return TL_DRIVER.get();
    }

    public static void quitDriver() {
        // Do nothing so the browser always stays open
    }
}
