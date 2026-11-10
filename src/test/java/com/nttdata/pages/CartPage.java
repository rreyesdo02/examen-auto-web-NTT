package com.nttdata.pages;

import org.openqa.selenium.By;

public final class CartPage extends BasePage {
    private final By productTitle = By.cssSelector(".product-title > a");
    private final By increaseQuantity = By.cssSelector(".bootstrap-touchspin-up");
    private final By quantity = By.id("quantity_wanted");
    private final By addToCart = By.cssSelector("[data-button-action='add-to-cart']");
    private final By popupMessage = By.id("myModalLabel");
    private final By finishPurchase = By.cssSelector(".cart-content-btn a.btn-primary");
    private final By cartTitle = By.cssSelector(".card-block h1");
    private final By unitPrice = By.cssSelector(".product-line-info .current-price .price");
    private final By quantityCart = By.cssSelector(".js-cart-line-product-quantity");
    private final By productTotal = By.cssSelector(".col-md-6.col-xs-2.price .product-price");

    // Localizadores dinámicos
    private By menuOption(String name) {
        return By.xpath("//a[contains(.,'" + name + "')]");
    }

    public void hoverCategory(String category) {
        hover(menuOption(category));
    }

    public void clickSubcategory(String subcategory) {
        click(menuOption(subcategory));
    }

    public void clickProduct() {
        click(productTitle);
    }

    public void clickIncreaseQuantity() {
        click(increaseQuantity);
    }

    public void addToCart() {
        click(addToCart);
    }

    public String getConfirmationMessage() {
        return text(popupMessage);
    }

    public void finishPurchase() {
        click(finishPurchase);
    }

    public String getCartTitle() {
        return text(cartTitle);
    }

    public String getUnitPrice() {
        return text(unitPrice);
    }

    public int getQuantity() {
        return Integer.parseInt(visible(quantityCart).getAttribute("value")
        );
    }

    public String getProductTotal() {
        return text(productTotal);
    }
}
