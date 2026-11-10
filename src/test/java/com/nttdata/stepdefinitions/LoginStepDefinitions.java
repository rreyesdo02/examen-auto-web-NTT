package com.nttdata.stepdefinitions;

import com.nttdata.steps.LoginSteps;
import static com.nttdata.hooks.BrowserHooks.takeScreenshot;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class LoginStepDefinitions {

    private final LoginSteps loginSteps = new LoginSteps();

    @Given("que estoy en la pagina de login")
    public void abrirLogin() {
        loginSteps.openLogin();
    }

    @When("ingreso correo {string} y clave {string}")
    public void autenticar(String user, String password) throws Throwable {
        loginSteps.authenticate(user, password);
    }

    @Then("debo ver al catalogo")
    public void validarCatalogo() {
        assertEquals("PRODUCTOS DESTACADOS", loginSteps.getInventoryTitle(),
                "Después del login se debe mostrar el título Productos Destacados");
        assertTrue(loginSteps.getProductCount() > 0,
                "El catalogo debe presentar al menos un producto");
        takeScreenshot("Catálogo visible");
    }

    @Then("debo ver un error que contiene {string}")
    public void validarError(String expectedMessage) {
        String actualMessage = loginSteps.getLoginError();
        assertTrue(actualMessage.contains(expectedMessage),
                () -> "Mensaje esperado: '" + expectedMessage
                        + "'. Mensaje real: '" + actualMessage + "'");
        takeScreenshot("Error al iniciar sesión");
    }
}
