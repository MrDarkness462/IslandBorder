package com.github.zandy.islandborder.files.languages.iso;

import org.magenpurp.api.utils.FileManager;

import static com.github.zandy.islandborder.files.languages.Languages.LanguageEnum.*;

public class RO extends FileManager {

    public RO() {
        super("Language_RO", "Languages");
        addDefault(NO_PERMISSION_COMMAND.getPath(), "&7[&aIB&7] &cNu ai permisiunea sa folosesti aceasta comanda.");
        addDefault(NO_PERMISSION_GUI.getPath(), "&7[&aIB&7] &cNu ai permisiunea sa accesezi acest meniu.");
        addDefault(NO_PERMISSION_COLOR.getPath(), "&7[&aIB&7] &cNu ai pemrisiunea sa schimbi culoarea border-ului in [color]&c.");
        addDefault(COMMAND_AVAILABLE_ON_ISLAND.getPath(), "&7[&aIB&7] &cAceasta comanda poate fi folosita cat timp esti pe insula.");
        addDefault(COMMAND_USAGE_WRONG.getPath(), "&7[&aIB&7] &cComanda a fost folosita incorect.");
        addDefault(COMMAND_USAGE_EXAMPLE.getPath(), COMMAND_USAGE_EXAMPLE.getDefaultValue());
        addDefault(COMMAND_USAGE_CLICK.getPath(), "&e⦁ &f[name] &c&l[Click Aici]");
        addDefault(COMMAND_CLICK_TO_SUGGEST.getPath(), "&eClick pentru a sugera aceasta comanda.");
        addDefault(COMMAND_CLICK_TO_RUN.getPath(), "&eClick pentru a rula aceasta comanda.");
        addDefault(LANGUAGE_DISPLAY.getPath(), "Romana");
        addDefault(LANGUAGE_AVAILABLE.getPath(), "&7Limbi disponibile:");
        addDefault(LANGUAGE_NOT_FOUND.getPath(), "&7[&aIB&7] &cLimba aleasa nu exista! Limbi disponibile:");
        addDefault(LANGUAGE_LIST_FORMAT.getPath(), "&e⦁ &7[&e[languageAbbreviation]&7] &8| &f[languageName]");
        addDefault(LANGUAGE_CHANGED.getPath(), "&7[&aIB&7] &fLimba a fost schimbata in &e[languageName] &f[&b[languageAbbreviation]&f]!");
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
        addDefault(COLOR_GUI_TITLE.getPath(), "&fSetari Culoare");
        addDefault(PLACEHOLDERS_SIZE_NOT_ON_ISLAND.getPath(), "&7Nu esti pe insula");
        addDefault(PLACEHOLDERS_STATE_ENABLED.getPath(), "&aPornit");
        addDefault(PLACEHOLDERS_STATE_DISABLED.getPath(), "&aOprit");
        addDefault(PLACEHOLDERS_COLOR_NONE.getPath(), "&cNiciuna");
        addDefault(PLACEHOLDERS_COOLDOWN.getPath(), "&7Fara Cooldown");
        addDefault(UNITS_SECOND.getPath(), "secunda");
        addDefault(UNITS_SECONDS.getPath(), "secunde");
        copyDefaults();
        save();
    }

}
