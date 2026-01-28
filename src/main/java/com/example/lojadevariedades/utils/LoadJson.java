package com.example.lojadevariedades.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadJson {
    private static final Logger logger = LoggerFactory.getLogger(LoadJson.class);
    public static String loadJson(String filename) {
        try (InputStream is = LoadJson.class.getClassLoader().getResourceAsStream(filename)) {
            if (is != null) {
                return new String(is.readAllBytes(), StandardCharsets.UTF_8);
            }
        } catch (IOException ignored) {
            logger.error("Error reading JSON resource: " + filename, ignored);
        }

        try {
            Path p = Paths.get(filename);
            if (Files.exists(p)) {
                return Files.readString(p, StandardCharsets.UTF_8);
            }
        } catch (IOException ignored) {
        }

        return "{}";
    }

}