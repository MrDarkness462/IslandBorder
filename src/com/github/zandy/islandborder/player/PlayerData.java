package com.github.zandy.islandborder.player;

import org.magenpurp.api.versionsupport.BorderColor;

import java.util.HashMap;
import java.util.UUID;

import static org.magenpurp.api.MagenAPI.getDatabase;

public class PlayerData {
    private static HashMap<UUID, PlayerData> playerDataMap = new HashMap<>();
    private UUID uuid;
    private boolean borderState;
    private BorderColor borderColor;
    private int cooldownSeconds = 0;

    public PlayerData(UUID uuid, boolean borderState, BorderColor borderColor) {
        this.uuid = uuid;
        this.borderState = borderState;
        this.borderColor = borderColor;
        playerDataMap.put(uuid, this);
    }

    public static boolean isCached(UUID uuid) {
        return playerDataMap.containsKey(uuid);
    }

    public static PlayerData get(UUID uuid) {
        return playerDataMap.get(uuid);
    }

    public boolean getBorderState() {
        return borderState;
    }

    public BorderColor getBorderColor() {
        return borderColor;
    }

    public int getCooldownSeconds() {
        return cooldownSeconds;
    }

    public void setBorderState(Boolean borderState) {
        this.borderState = borderState;
        getDatabase().setString(uuid, borderState.toString(), "Enabled", "Island-Border");
    }

    public void setBorderColor(BorderColor borderColor) {
        this.borderColor = borderColor;
        getDatabase().setString(uuid, borderColor.name(), "Color", "Island-Border");
    }

    public void setCooldownSeconds(int cooldownSeconds) {
        this.cooldownSeconds = cooldownSeconds;
    }
}
