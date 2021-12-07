package com.github.zandy.islandborder.files.languages.iso;

import org.magenpurp.api.utils.FileManager;

import static com.github.zandy.islandborder.files.languages.Languages.LanguageEnum.*;

public class RO extends FileManager {

    public RO() {
        super("RO");
        addDefault(COOLDOWN.getPath(), "&7[&aIB&7] &fCooldown-ul border-ului expira in &e[seconds] [secondsFormatted]&f.");
        addDefault(TOGGLED_ON.getPath(), "&7[&aIB&7] &fBorder-ul este acum vizibil cu culoarea [color]&f.");
        addDefault(TOGGLED_OFF.getPath(), "&7[&aIB&7] &fBorder-ul este acum invizibil.");
        addDefault(ALREADY_TOGGLED_ON.getPath(), "&7[&aIB&7] &fBorder-ul tau este deja activat.");
        addDefault(ALREADY_TOGGLED_OFF.getPath(), "&7[&aIB&7] &fBorder-ul tau este deja dezactivat.");
        addDefault(COLOR_CHANGED.getPath(), "&7[&aIB&7] &fCuloarea border-ului a fost schimbata in [color]&f.");
        addDefault(COLOR_ALREADY_DISPLAYING.getPath(), "&7[&aIB&7] &fBorder-ul afiseaza deja aceasta culoare.");
        addDefault(COLORS_RED.getPath(), "&cRosu");
        addDefault(COLORS_GREEN.getPath(), "&aVerde");
        addDefault(COLORS_BLUE.getPath(), "&9Albastru");
        addDefault(PLACEHOLDERS_SIZE_NOT_ON_ISLAND.getPath(), "&7Nu esti pe insula");
        addDefault(UNITS_SECOND.getPath(), "secunda");
        addDefault(UNITS_SECONDS.getPath(), "secunde");
        copyDefaults();
        save();
    }

}
