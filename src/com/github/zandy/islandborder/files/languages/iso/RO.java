package com.github.zandy.islandborder.files.languages.iso;

import org.magenpurp.api.utils.FileManager;

import static com.github.zandy.islandborder.files.languages.Languages.LanguageEnum.*;

public class RO extends FileManager {

    public RO() {
        super("RO");
        addDefault(NO_PERMISSION_COMMAND.getPath(), "&7[&aIB&7] &cNu ai permisiunea sa folosesti aceasta comanda.");
        addDefault(NO_PERMISSION_GUI.getPath(), "&7[&aIB&7] &cNu ai permisiunea sa accesezi acest meniu.");
        addDefault(NO_PERMISSION_COLOR.getPath(), "&7[&aIB&7] &cNu ai pemrisiunea sa schimbi culoarea border-ului in [color]&c.");
        addDefault(COMMAND_AVAILABLE_ON_ISLAND.getPath(), "&7[&aIB&7] &cAceasta comanda poate fi folosita cat timp esti pe insula.");
        addDefault(BORDER_COOLDOWN.getPath(), "&7[&aIB&7] &fCooldown-ul border-ului expira in &e[seconds] [secondsFormatted]&f.");
        addDefault(BORDER_TOGGLED_ON.getPath(), "&7[&aIB&7] &fBorder-ul este acum vizibil cu culoarea [color]&f.");
        addDefault(BORDER_TOGGLED_OFF.getPath(), "&7[&aIB&7] &fBorder-ul este acum invizibil.");
        addDefault(BORDER_ALREADY_TOGGLED_ON.getPath(), "&7[&aIB&7] &fBorder-ul tau este deja activat.");
        addDefault(BORDER_ALREADY_TOGGLED_OFF.getPath(), "&7[&aIB&7] &fBorder-ul tau este deja dezactivat.");
        addDefault(BORDER_GUI_TITLE.getPath(), "&fSetari Border");
        addDefault(COLOR_CHANGED.getPath(), "&7[&aIB&7] &fCuloarea border-ului a fost schimbata in [color]&f.");
        addDefault(COLOR_ALREADY_DISPLAYING.getPath(), "&7[&aIB&7] &fBorder-ul afiseaza deja aceasta culoare.");
        addDefault(COLOR_RED.getPath(), "&cRosu");
        addDefault(COLOR_GREEN.getPath(), "&aVerde");
        addDefault(COLOR_BLUE.getPath(), "&9Albastru");
        addDefault(PLACEHOLDERS_SIZE_NOT_ON_ISLAND.getPath(), "&7Nu esti pe insula");
        addDefault(UNITS_SECOND.getPath(), "secunda");
        addDefault(UNITS_SECONDS.getPath(), "secunde");
        copyDefaults();
        save();
    }

}
