package com.github.zandy.islandborder.files;

import org.magenpurp.api.utils.FileManager;

import java.util.List;

import static java.util.Arrays.asList;
import static org.magenpurp.api.versionsupport.BorderColor.BLUE;

public class Settings extends FileManager {
    public enum SettingsEnum {
        COMMAND_ALIASES("Command-Aliases", asList("ib", "islandborder", "aiborder", "bbborder", "bsborder", "usborder", "iwborder")),
        SUBCOMMAND_ENABLED_GUI("SubCommand-Enabled.GUI", true),
        SUBCOMMAND_ENABLED_BORDER_ENABLE("SubCommand-Enabled.Border.Enable", true),
        SUBCOMMAND_ENABLED_BORDER_DISABLE("SubCommand-Enabled.Border.Disable", true),
        DEFAULT_LANGUAGE("Default.Language", "EN"),
        DEFAULT_BORDER_STATE("Default.Border-State", true),
        DEFAULT_BORDER_COLOR("Default.Border-Color", BLUE.name()),
        COOLDOWN_ENABLED("Cooldown.Enabled", true),
        COOLDOWN_SECONDS("Cooldown.Seconds", 5);

        final String path;
        final Object defaultValue;

        SettingsEnum(String path, Object defaultValue) {
            this.path = path;
            this.defaultValue = defaultValue;
        }

        public String getPath() {
            return path;
        }

        public Object getDefaultValue() {
            return defaultValue;
        }

        public boolean getBoolean() {
            return getInstance().getBoolean(getPath());
        }

        public String getString() {
            return getInstance().getString(getPath());
        }

        public List<String> getStringList() {
            return getInstance().getStringList(getPath());
        }

        public int getInt() {
            return getInstance().getInt(getPath());
        }
    }
    private static Settings instance;

    public Settings() {
        super("Settings");
        for (SettingsEnum settingsEnum : SettingsEnum.values()) addDefault(settingsEnum.getPath(), settingsEnum.getDefaultValue());
        copyDefaults();
        save();
        instance = this;
    }

    public static Settings getInstance() {
        return instance;
    }
}
