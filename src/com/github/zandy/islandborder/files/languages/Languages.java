package com.github.zandy.islandborder.files.languages;

import com.github.zandy.islandborder.files.languages.iso.EN;
import com.github.zandy.islandborder.files.languages.iso.RO;
import org.magenpurp.api.utils.FileManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Languages {
    public enum LanguageEnum {
        COOLDOWN("Cooldown", "&7[&aIB&7] &fBorder cooldown expires in &e[seconds] [secondsFormatted]&f."),
        TOGGLED_ON("Toggled.On", "&7[&aIB&7] &fThe Border is now shown with the color [color]&f."),
        TOGGLED_OFF("Toggled.On", "&7[&aIB&7] &fThe Border is now hidden."),
        ALREADY_TOGGLED_ON("Already-Toggled.On", "&7[&aIB&7] &fThe Border is already toggled on."),
        ALREADY_TOGGLED_OFF("Already-Toggled.Off", "&7[&aIB&7] &fThe Border is already toggled off."),
        COLOR_CHANGED("Color.Changed", "&7[&aIB&7] &fThe Border color has been changed to [color]&f."),
        COLOR_ALREADY_DISPLAYING("Color.Already-Showing", "&7[&aIB&7] &fThe Border already displays this color."),
        COLORS_RED("Colors.Red", "&cRed"),
        COLORS_GREEN("Colors.Green", "&aGreen"),
        COLORS_BLUE("Colors.Blue", "&9Blue"),
        PLACEHOLDERS_SIZE_NOT_ON_ISLAND("Placeholders.Size-Not-On-Island", "&7Not on island"),
        UNITS_SECOND("Units.Second", "second"),
        UNITS_SECONDS("Units.Seconds", "seconds");

        final String path;
        final Object defaultValue;

        LanguageEnum(String path, Object defaultValue) {
            this.path = path;
            this.defaultValue = defaultValue;
        }

        public String getString(UUID uuid) {
            return getLocaleFiles().get(getPlayerLocale().getOrDefault(uuid, "EN")).getString(getPath());
        }

        public String getString() {
            return getLocaleFiles().get("EN").getString(getPath());
        }

        public String getPath() {
            return path;
        }

        public Object getDefaultValue() {
            return defaultValue;
        }
    }
    private static HashMap<UUID, String> playerLocaleMap = new HashMap<>();
    private static HashMap<String, FileManager> localeFileMap = new HashMap<>();
    private final List<String> isoList = new ArrayList<>();

    public Languages() {
        new EN();
        new RO();
        List<String> localeList = new ArrayList<>();
        File dir = new File("plugins/IslandBorder/Languages");
        File[] filesArray = dir.listFiles();
        for (int i = 0; i < filesArray.length; i++) {
            File fileFromArray = filesArray[i];
            if (fileFromArray.isFile() && !fileFromArray.getName().contains("DS_Store")) {
                localeList.add(fileFromArray.getName().replace("Language_", "").replace(".yml", ""));
            }
        }
        for (String iso : localeList) {
            FileManager locale = new FileManager("Language_" + iso, "Languages");
            for (LanguageEnum langEnum : LanguageEnum.values()) locale.addDefault(langEnum.getPath(), langEnum.getDefaultValue());
            locale.copyDefaults();
            locale.save();
            localeFileMap.put(iso, locale);
            isoList.add(iso);
        }
    }

    public static HashMap<String, FileManager> getLocaleFiles() {
        return localeFileMap;
    }

    public static HashMap<UUID, String> getPlayerLocale() {
        return playerLocaleMap;
    }
}
