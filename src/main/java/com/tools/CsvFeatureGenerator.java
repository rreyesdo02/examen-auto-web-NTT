package com.tools;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Genera un Scenario Outline con Examples a partir de un CSV.
 * Se ejecuta en Maven durante generate-test-resources.
 * NO lo invoca Selenium: solo genera el feature que después lee Cucumber.
 */
public final class CsvFeatureGenerator {

    private CsvFeatureGenerator() {
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            throw new IllegalArgumentException("Uso: CsvFeatureGenerator <entrada.csv> <salida.feature>");
        }

        Path csv = Path.of(args[0]);
        Path feature = Path.of(args[1]);
        List<String> headers = List.of("caso", "usuario", "clave", "mensaje");

        StringBuilder result = new StringBuilder("""
                @csv @regression @outline_csv
                Feature: Login negativo generado a partir de CSV

                  Scenario Outline: Rechazar credenciales - <caso>
                    Given que estoy en la pagina de login
                    When ingreso usuario "<usuario>" y clave "<clave>"
                    Then debo ver un error que contiene "<mensaje>"

                    Examples:
                      | caso | usuario | clave | mensaje |
                """);

        try (Reader reader = Files.newBufferedReader(csv, StandardCharsets.UTF_8);
             CSVParser parser = CSVFormat.DEFAULT.builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .setIgnoreSurroundingSpaces(true)
                     .build()
                     .parse(reader)) {

            if (!parser.getHeaderNames().equals(headers)) {
                throw new IllegalArgumentException(
                        "Cabeceras inválidas en " + csv + ". Esperadas: " + headers);
            }

            int examples = 0;
            for (CSVRecord record : parser) {
                if (!record.isConsistent()) {
                    throw new IllegalArgumentException(
                            "La fila " + record.getRecordNumber() + " no tiene 4 columnas");
                }

                result.append("      |");
                for (String header : headers) {
                    String value = record.get(header);
                    validateGherkinCell(value, record.getRecordNumber());
                    result.append(" ").append(value).append(" |");
                }
                result.append(System.lineSeparator());
                examples++;
            }

            if (examples == 0) {
                throw new IllegalArgumentException("CSV sin registros: " + csv);
            }
        }

        Files.createDirectories(feature.getParent());
        Files.writeString(feature, result.toString(), StandardCharsets.UTF_8);
        System.out.println("Feature generado: " + feature);
    }

    private static void validateGherkinCell(String value, long line) {
        // Delimitadores Gherkin: los ejemplos usan datos simples por claridad docente.
        if (value.contains("|") || value.contains("<") || value.contains(">")
                || value.contains("\n") || value.contains("\r")) {
            throw new IllegalArgumentException(
                    "Fila " + line + ": valor no soportado en Examples: " + value);
        }
    }
}
