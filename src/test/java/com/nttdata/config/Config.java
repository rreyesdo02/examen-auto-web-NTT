package com.nttdata.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** Permite configurar con config.properties y sobreescribir con -Dclave=valor. */
public final class Config {

    private static final Properties PROPERTIES = load();

    private Config() {
    }

    private static Properties load() {
        Properties properties = new Properties();

        try (InputStream stream = Config.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (stream == null) {
                throw new IllegalStateException("No se encontró config.properties");
            }
            properties.load(stream);
        } catch (IOException exception) {
            throw new IllegalStateException("Error al leer config.properties", exception);
        }

        return properties;
    }

    public static String get(String key, String defaultValue) {
        return System.getProperty(key, PROPERTIES.getProperty(key, defaultValue));
    }

    public static int timeoutSeconds() {
        int value = Integer.parseInt(get("timeout.seconds", "10"));
        if (value <= 0) {
            throw new IllegalArgumentException("timeout.seconds debe ser mayor a cero");
        }
        return value;
    }
}
