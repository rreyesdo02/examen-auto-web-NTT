package com.nttdata.hooks;

import com.nttdata.driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

/**
 * Hooks de Cucumber (NO son steps Gherkin).
 * @Before: crea el navegador ANTES de cada Scenario o ejemplo de Outline.
 * @After : si falla, adjunta screenshot; siempre cierra el navegador.
 * El orden es explícito para facilitar ampliaciones futuras.
 */
public final class BrowserHooks {

    private static final ThreadLocal<Scenario> SCENARIO =
            new ThreadLocal<>();

    @Before(order = 0)
    public void abrirNavegador(Scenario scenario) {

        System.out.println("[BEFORE] " + scenario.getName());

        SCENARIO.set(scenario);

        DriverFactory.createDriver();
    }

    @After(order = 100)
    public void cerrarNavegador(Scenario scenario) {

        try {

            if (scenario.isFailed()) {
                takeScreenshot("Captura de fallo");
            }

        } catch (Exception exception) {

            System.err.println(
                    "No se pudo capturar pantalla: "
                            + exception.getMessage()
            );

        } finally {

            DriverFactory.quitDriver();

            SCENARIO.remove();

            System.out.println(
                    "[AFTER] " + scenario.getName()
            );
        }
    }

    public static void takeScreenshot(String evidenceName) {

        Scenario scenario = SCENARIO.get();

        if (scenario == null) {
            throw new IllegalStateException(
                    "No existe un Scenario activo"
            );
        }

        byte[] screenshot =
                ((TakesScreenshot) DriverFactory.getDriver())
                        .getScreenshotAs(OutputType.BYTES);

        scenario.attach(
                screenshot,
                "image/png",
                evidenceName
        );
    }
}
