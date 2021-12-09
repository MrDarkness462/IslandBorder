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
        CONSOLE_NOT_AVAILABLE("Console-Not-Available", "&7[&aIB&7] &cThis command can't be used in console.", true),
        INFO_SUBCOMMAND_GUI("Info.SubCommand.GUI", "&7&oBorder settings menu."),
        INFO_SUBCOMMAND_ENABLE("Info.SubCommand.Enable", "&7&oEnable border around island."),
        INFO_SUBCOMMAND_DISABLE("Info.SubCommand.Disable", "&7&oDisable border around island."),
        INFO_SUBCOMMAND_TOGGLE("Info.SubCommand.Toggle", "&7&oToggle border around island."),
        INFO_SUBCOMMAND_COLOR("Info.SubCommand.Color", "&7&oChange island border color."),
        NO_PERMISSION_COMMAND("No-Permission.Command", "&7[&aIB&7] &cYou don't have permission to use this command."),
        NO_PERMISSION_GUI("No-Permission.GUI", "&7[&aIB&7] &cYou don't have permission to access this menu."),
        NO_PERMISSION_COLOR("No-Permission.Color", "&7[&aIB&7] &cYou don't have permission to change border's color to [color]&c."),
        COMMAND_AVAILABLE_ON_ISLAND("Command.Available-On-Island", "&7[&aIB&7] &cThis command can be used while you're on your island."),
        COMMAND_USAGE_WRONG("Command.Usage.Wrong", "&7[&aIB&7] &cThe command has been used incorrectly."),
        COMMAND_USAGE_EXAMPLE("Command.Usage.Example", "&e⦁ &f[command]"),
        COMMAND_USAGE_CLICK("Command.Usage.Click", "&e⦁ &f[name] &c&l[Click Here]"),
        COMMAND_USAGE_COLOR("Command.Usage.Color", "/isborder color <&cRed&f, &aGreen&f, &9Blue&f>"),
        COMMAND_CLICK_TO_SUGGEST("Command.Click-To.Suggest", "&eClick to suggest this command."),
        COMMAND_CLICK_TO_RUN("Command.Click-To.Run", "&eClick to run this command."),
        BORDER_COOLDOWN("Border.Cooldown", "&7[&aIB&7] &fBorder cooldown expires in &e[seconds] [secondsFormatted]&f."),
        BORDER_TOGGLED_ON("Border.Toggled.On", "&7[&aIB&7] &fThe Border is now shown with the color [color]&f."),
        BORDER_TOGGLED_OFF("Border.Toggled.On", "&7[&aIB&7] &fThe Border is now hidden."),
        BORDER_ALREADY_TOGGLED_ON("Border.Already-Toggled.On", "&7[&aIB&7] &fThe Border is already toggled on."),
        BORDER_ALREADY_TOGGLED_OFF("Border.Already-Toggled.Off", "&7[&aIB&7] &fThe Border is already toggled off."),
        BORDER_GUI_TITLE("Border.GUI.Title", "&fBorder Settings"),
        COLOR_CHANGED("Color.Changed", "&7[&aIB&7] &fThe Border color has been changed to [color]&f."),
        COLOR_ALREADY_DISPLAYING("Color.Already-Showing", "&7[&aIB&7] &fThe Border already displays this color."),
        COLOR_RED("Color.Red", "&cRed"),
        COLOR_GREEN("Color.Green", "&aGreen"),
        COLOR_BLUE("Color.Blue", "&9Blue"),
        COLOR_GUI_TITLE("Color.GUI.Title", "&fColor Settings"),
        PLACEHOLDERS_SIZE_NOT_ON_ISLAND("Placeholders.Size-Not-On-Island", "&7Not on island"),
        PLACEHOLDERS_STATE_ENABLED("Placeholders.State.Enabled", "&aEnabled"),
        PLACEHOLDERS_STATE_DISABLED("Placeholders.State.Disabled", "&cDisabled"),
        PLACEHOLDERS_COLOR_NONE("Placeholders.Color-None", "&cNone"),
        PLACEHOLDERS_COOLDOWN("Placeholders.Cooldown", "&7No Cooldown"),
        UNITS_SECOND("Units.Second", "second"),
        UNITS_SECONDS("Units.Seconds", "seconds");
        final String path;
        final Object defaultValue;
        boolean ignoreInLanguage = false;

        LanguageEnum(String path, Object defaultValue) {
            this.path = path;
            this.defaultValue = defaultValue;
        }

        LanguageEnum(String path, Object defaultValue, boolean ignoreInLanguages) {
            this.path = path;
            this.defaultValue = defaultValue;
            this.ignoreInLanguage = ignoreInLanguages;
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

        public boolean ignoreInLanguage() {
            return ignoreInLanguage;
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
            for (LanguageEnum langEnum : LanguageEnum.values()) if (!langEnum.ignoreInLanguage()) locale.addDefault(langEnum.getPath(), langEnum.getDefaultValue());
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
