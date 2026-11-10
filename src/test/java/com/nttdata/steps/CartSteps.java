package com.nttdata.steps;

import com.nttdata.pages.CartPage;

import java.math.BigDecimal;

public final class CartSteps {

    private final CartPage cartPage = new CartPage();

    public void navegarCategoria(String category, String subcategory) {
        cartPage.hoverCategory(category);
        cartPage.clickSubcategory(subcategory);
    }

    public void validarCategoria(String category) {
        cartPage.hoverCategory(category);
    }

    public void agregarProducto(int quantity) {
        cartPage.clickProduct();

        for (int i = 1; i < quantity; i++) {
            cartPage.clickIncreaseQuantity();
        }

        cartPage.addToCart();
    }

    public String obtenerMensajeConfirmacion() {
        return cartPage.getConfirmationMessage();
    }

    public void finalizarCompra() {
        cartPage.finishPurchase();
    }

    public String obtenerTituloCarrito() {
        return cartPage.getCartTitle();
    }

    public BigDecimal obtenerPrecioUnitario() {
        return convertirPrecio(cartPage.getUnitPrice());
    }

    public int obtenerCantidad() {
        return cartPage.getQuantity();
    }

    public BigDecimal obtenerTotalProducto() {
        return convertirPrecio(cartPage.getProductTotal());
    }

    private BigDecimal convertirPrecio(String precio) {
        return new BigDecimal(
                precio
                        .replace("S/", "")
                        .replace("\u00A0", "")
                        .trim()
        );
    }
}
