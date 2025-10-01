package DriverFactory;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;

import java.util.HashMap;
import java.util.Map;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    /*public static void setupDriver(String browser) //Edge edge EDGE
    {
        switch (browser.toLowerCase())
        {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                driverThreadLocal.set(new ChromeDriver(chromeOptions));
                break;
            case "firefox":
                driverThreadLocal.set(new FirefoxDriver());
                break;
            default:
                EdgeOptions options = new EdgeOptions();
                options.addArguments("--start-maximized");
                driverThreadLocal.set(new EdgeDriver(options));
        }

    }*/


    public static void setupDriver(String browser) //Edge edge EDGE
    {

        switch (browser.toLowerCase())
        {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                driverThreadLocal.set(new ChromeDriver(chromeOptions));
                break;
            case "firefox":
                driverThreadLocal.set(new FirefoxDriver());
                break;
             default:
                EdgeOptions options = new EdgeOptions();
                options.addArguments("--start-maximized");
                options.setPageLoadStrategy(PageLoadStrategy.EAGER);
                Map<String, Object> prefs = new HashMap<>();
                String userHome = System.getProperty("user.dir");
                String downloadPath = userHome + "\\src\\test\\resources";
                prefs.put("profile.default_content_settings.popups", 0);
                prefs.put("download.prompt_for_download", false);
                prefs.put("download.default_directory",downloadPath);
                options.setExperimentalOption("prefs", prefs);
                options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
                options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
                options.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
                options.setCapability(CapabilityType.ENABLE_DOWNLOADS, true);
                driverThreadLocal.set(new EdgeDriver(options));


        }

    }


    public static WebDriver getDriver()
    {
        return driverThreadLocal.get();
    }
    public static void quitDriver()
    {
        getDriver().quit();
        driverThreadLocal.remove();
    }

}
