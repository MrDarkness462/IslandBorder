package com.github.zandy.islandborder.files;

import com.github.zandy.bamboolib.utils.BambooFile;
import com.github.zandy.bamboolib.versionsupport.utils.BorderColor;

import java.util.Arrays;
import java.util.List;

public class Settings extends BambooFile {
    public enum SettingsEnum {
        COMMAND_ALIASES("Command-Aliases", Arrays.asList("ib", "islandborder", "aiborder", "bbborder", "bsborder", "usborder", "iwborder")),
        SUBCOMMAND_ENABLED_GUI("SubCommand-Enabled.GUI", true),
        SUBCOMMAND_ENABLED_BORDER_ENABLE("SubCommand-Enabled.Enable", true),
        SUBCOMMAND_ENABLED_BORDER_DISABLE("SubCommand-Enabled.Disable", true),
        SUBCOMMAND_ENABLED_BORDER_TOGGLE("SubCommand-Enabled.Toggle", true),
        SUBCOMMAND_ENABLED_BORDER_COLOR("SubCommand-Enabled.Color", true),
        SUBCOMMAND_ENABLED_BORDER_LANGUAGE("SubCommand-Enabled.Language", true),
        DEFAULT_LANGUAGE("Default.Language", "EN"),
        DEFAULT_BORDER_STATE("Default.Border-State", true),
        DEFAULT_BORDER_COLOR("Default.Border-Color", BorderColor.BLUE.name()),
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
            return Settings.getInstance().getBoolean(getPath());
        }

        public String getString() {
            return Settings.getInstance().getString(getPath());
        }

        public List<String> getStringList() {
            return Settings.getInstance().getStringList(getPath());
        }

        public int getInt() {
            return Settings.getInstance().getInt(getPath());
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
