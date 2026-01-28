package com.example.lojadevariedades.utils;

public class Logger {
    public static void log(String message) {
        System.out.println("[LOG] " + message);
    }
    public static void error(String message) {
        System.out.println("[ERROR] " + message);
    }
    public static void warn(String message) {
        System.out.println("[WARN] " + message);
    }
    public static void info(String message) {
        System.out.println("[INFO] " + message);
    }
    public static void debug(String message) {
        System.out.println("[DEBUG] " + message);
    }
    public static void trace(String message) {
        System.out.println("[TRACE] " + message);
    }
    public static void fatal(String message) {
        System.out.println("[FATAL] " + message);
    }
    public static void panic(String message) {
        System.out.println("[PANIC] " + message);
    }
    public static void alert(String message) {
        System.out.println("[ALERT] " + message);
    }
    public static void notice(String message) {
        System.out.println("[NOTICE] " + message);
    }
    public static void emergency(String message) {
        System.out.println("[EMERGENCY] " + message);
    }
    public static void fromJason(String json) {
        for (Object elem : json.split(",")) {
            log("Element: " + elem.toString());
        }
    }
    public static void fromArray(Object[] array) {
        for (Object elem : array) {
            log("Element: " + elem.toString());
        }
    }
}