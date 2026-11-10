package com.nttdata.pages;

import com.nttdata.config.Config;
import org.openqa.selenium.By;

public final class LoginPage extends BasePage {
    private final By usernameField = By.id("field-email");

    private final By passwordField = By.id("field-password");

    private final By loginButton = By.id("submit-login");

    private final By errorBanner = By.cssSelector(".alert.alert-danger");

    public void open() {
        driver.get(Config.get("base.url", "https://qalab.bensg.com/store/pe/iniciar-sesion?back=https%3A%2F%2Fqalab.bensg.com%2Fstore%2Fpe%2F"));
    }

    public void login(String username, String password) {
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);
    }

    public String getErrorMessage() {
        return text(errorBanner);
    }
}
