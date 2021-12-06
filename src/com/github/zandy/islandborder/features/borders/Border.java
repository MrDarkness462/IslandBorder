package com.github.zandy.islandborder.features.borders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.github.zandy.islandborder.files.Settings.SettingsEnum.COOLDOWN_ENABLED;
import static com.github.zandy.islandborder.files.Settings.SettingsEnum.COOLDOWN_SECONDS;

public class Border {
    private boolean isCooldownEnabled;
    private int cooldownSeconds;
    private List<UUID> cooldownList = new ArrayList<>();

    public Border() {
        isCooldownEnabled = COOLDOWN_ENABLED.getBoolean();
        cooldownSeconds = COOLDOWN_SECONDS.getInt();
    }

    public void setState(UUID uuid, boolean enabled) {
        if ()
    }
}
