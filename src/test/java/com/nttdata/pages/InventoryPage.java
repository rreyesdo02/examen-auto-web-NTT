package com.nttdata.pages;

import org.openqa.selenium.By;

public final class InventoryPage extends BasePage {
    private final By title = By.xpath("//h2[normalize-space()='Productos Destacados']");

    // CSS con >: div.inventory_item es hijo directo de .inventory_list.
    private final By productCards = By.xpath("//h2[normalize-space()='Productos Destacados']/following-sibling::div[contains(@class,'products')]//div[contains(@class,'js-product')]");

    public String getTitle() {
        return text(title);
    }

    public int getVisibleProductCount() {
        return driver.findElements(productCards).size();
    }


}
