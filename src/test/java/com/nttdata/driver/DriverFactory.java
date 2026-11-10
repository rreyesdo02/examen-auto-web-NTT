package com.nttdata.driver;

import com.nttdata.config.Config;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

/** Crea un navegador por escenario. ThreadLocal evita compartirlo entre hilos. */
public final class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static void createDriver() {
        if (DRIVER.get() != null) {
            throw new IllegalStateException("Ya existe un navegador para este hilo");
        }

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1440,900");

        if (Boolean.parseBoolean(Config.get("headless", "false"))) {
            options.addArguments("--headless=new");
        }

        String mode = Config.get("driver.mode", "selenium-manager").trim().toLowerCase();
        WebDriver driver;

        switch (mode) {
            case "selenium-manager" -> driver = new ChromeDriver(options);
            case "webdrivermanager" -> {
                WebDriverManager manager = WebDriverManager.chromedriver();
                String version = Config.get("driver.version", "").trim();
                if (!version.isEmpty()) {
                    manager.driverVersion(version);
                }
                manager.setup();
                driver = new ChromeDriver(options);
            }
            case "manual" -> {
                String path = Config.get("driver.path", "").trim();
                if (path.isEmpty()) {
                    throw new IllegalArgumentException("Configura driver.path para el modo manual");
                }
                ChromeDriverService service = new ChromeDriverService.Builder()
                        .usingDriverExecutable(new File(path))
                        .build();
                driver = new ChromeDriver(service, options);
            }
            default -> throw new IllegalArgumentException("driver.mode no soportado: " + mode);
        }

        DRIVER.set(driver);
    }

    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("WebDriver no iniciado. Revisa @Before en BrowserHooks");
        }
        return driver;
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        try {
            if (driver != null) {
                driver.quit();
            }
        } finally {
            DRIVER.remove();
        }
    }
}
