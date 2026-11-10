package com.nttdata.steps;

import com.nttdata.pages.InventoryPage;
import com.nttdata.pages.LoginPage;

public class LoginSteps {

    private final LoginPage loginPage = new LoginPage();
    private final InventoryPage inventoryPage = new InventoryPage();

    public void openLogin() {
        loginPage.open();
    }

    public void authenticate(String username, String password) {
        loginPage.login(username, password);
    }

    public String getInventoryTitle() {
        return inventoryPage.getTitle();
    }

    public int getProductCount() {
        return inventoryPage.getVisibleProductCount();
    }

    public String getLoginError() {
        return loginPage.getErrorMessage();
    }
}
