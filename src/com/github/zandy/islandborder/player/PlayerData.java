package com.github.zandy.islandborder.player;

import com.github.zandy.bamboolib.database.Database;
import com.github.zandy.bamboolib.versionsupport.utils.BorderColor;
import com.github.zandy.islandborder.files.languages.Languages;
import com.github.zandy.islandborder.support.BorderSupport;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.UUID;

public class PlayerData {
    private final static HashMap<UUID, PlayerData> playerDataMap = new HashMap<>();
    private final UUID uuid;
    private boolean borderState;
    private BorderColor borderColor;
    private int cooldownSeconds = 0;

    public PlayerData(UUID uuid, boolean borderState, BorderColor borderColor, String language) {
        this.uuid = uuid;
        this.borderState = borderState;
        this.borderColor = borderColor;
        playerDataMap.put(uuid, this);
        Languages.getInstance().getPlayerLocale().put(uuid, language);
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
        Database.getInstance().setString(uuid, borderState.toString(), "Enabled", "Island-Border");
        if (borderState) BorderSupport.getInstance().send(Bukkit.getPlayer(uuid));
        else BorderSupport.getInstance().remove(Bukkit.getPlayer(uuid));
    }

    public void setBorderColor(BorderColor borderColor) {
        this.borderColor = borderColor;
        Database.getInstance().setString(uuid, borderColor.name(), "Color", "Island-Border");
        if (borderState) BorderSupport.getInstance().send(Bukkit.getPlayer(uuid));
    }

    public void setCooldownSeconds(int cooldownSeconds) {
        this.cooldownSeconds = cooldownSeconds;
    }
}