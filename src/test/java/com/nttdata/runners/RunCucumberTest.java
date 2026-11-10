package com.nttdata.runners;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

/**
 * Runner = punto de entrada para ejecutar Cucumber con JUnit Platform.
 * @Suite identifica una suite; @IncludeEngines activa el motor Cucumber.
 * @SelectClasspathResource busca features en src/test/resources/features y los
 * generados desde CSV (copiados al classpath por Maven).
 * GLUE indica dónde se ubican las Step Definitions y los Hooks.
 * PLUGIN configura salida por consola y reportes HTML/JSON.
 *
 * No contiene assert: las validaciones funcionales viven en Step Definitions.
 * Los tags se filtran desde consola con -Dcucumber.filter.tags=@smoke.
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(
        key = GLUE_PROPERTY_NAME,
        value = "com.nttdata.stepdefinitions,com.nttdata.hooks"
)
@ConfigurationParameter(
        key = PLUGIN_PROPERTY_NAME,
        value = "pretty,html:target/cucumber-report.html,json:target/cucumber.json"
)
public class RunCucumberTest {
    // Vacío intencionalmente: el motor Cucumber ejecuta los escenarios .feature.
}
