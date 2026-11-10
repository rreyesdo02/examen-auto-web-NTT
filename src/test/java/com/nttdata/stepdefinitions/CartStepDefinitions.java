package com.nttdata.stepdefinitions;

import com.nttdata.steps.CartSteps;
import com.nttdata.steps.LoginSteps;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;

import static com.nttdata.hooks.BrowserHooks.takeScreenshot;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class CartStepDefinitions {

    private final CartSteps cartSteps = new CartSteps();

    @When("navego a la categoría {string} y subcategoría {string}")
    public void navegar(String category, String subcategory) {
        cartSteps.navegarCategoria(category, subcategory);
    }

    @And("agrego {int} unidades del primer producto al carrito")
    public void agregarProductos(int quantity) {
        cartSteps.agregarProducto(quantity);
        takeScreenshot("Agregar producto al carrito");
    }

    @Then("valido en el popup la confirmación del producto agregado")
    public void validarPopup() {
        String mensaje = cartSteps.obtenerMensajeConfirmacion();
        assertTrue(mensaje.contains("Producto añadido correctamente a su carrito de compra"),
                "No se encontró el mensaje esperado. Texto obtenido: " + mensaje
        );
        takeScreenshot("Validar popup");
    }

    @When("finalizo la compra")
    public void finalizarCompra() {
        cartSteps.finalizarCompra();
    }

    @Then("valido el título de la página del carrito")
    public void validarCarrito() {
        String tituloActual = cartSteps.obtenerTituloCarrito();
        assertEquals("CARRITO", tituloActual
        );
    }

    @And("vuelvo a validar el cálculo de precios en el carrito")
    public void validarPrecios() {
        BigDecimal precioUnitario = cartSteps.obtenerPrecioUnitario();
        int cantidad = cartSteps.obtenerCantidad();
        BigDecimal totalActual = cartSteps.obtenerTotalProducto();

        BigDecimal totalEsperado = precioUnitario.multiply(
                        BigDecimal.valueOf(cantidad)
        );

        assertEquals(0, totalEsperado.compareTo(totalActual),"El total del carrito no coincide con precio × cantidad"
        );
        takeScreenshot("Carrito complete");
    }

    @When("intento navegar a la categoría inexistente {string}")
    public void validarCategoríaInexistente(String category) {
        cartSteps.validarCategoria(category);
    }
}
