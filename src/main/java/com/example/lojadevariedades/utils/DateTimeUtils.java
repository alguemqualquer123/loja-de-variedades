package com.example.lojadevariedades.utils;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    private static final DateTimeFormatter ISO = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    public static String nowIso() {
        return OffsetDateTime.now().format(ISO);
    }

    public static String nowIso(ZoneId zone) {
        return OffsetDateTime.now(zone).format(ISO);
    }

    public static OffsetDateTime parseIso(String iso) {
        return OffsetDateTime.parse(iso, ISO);
    }
}
