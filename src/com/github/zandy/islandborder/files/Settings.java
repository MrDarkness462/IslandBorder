package com.github.zandy.islandborder.files;

import org.magenpurp.api.utils.FileManager;

public class Settings extends FileManager {
    public enum SettingsEnum {
        COOLDOWN_ENABLED("Cooldown.Enabled", true),
        COOLDOWN_SECONDS("Cooldown.Seconds", 5);

        String path;
        Object defaultValue;

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
