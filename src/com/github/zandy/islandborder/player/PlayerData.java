package com.github.zandy.islandborder.player;

import org.magenpurp.api.versionsupport.BorderColor;

import java.util.HashMap;
import java.util.UUID;

public class PlayerData {
    private static HashMap<UUID, PlayerData> playerDataMap = new HashMap<>();
    private boolean borderState;
    private BorderColor borderColor;

    public PlayerData(UUID uuid, boolean borderState, BorderColor borderColor) {
        this.borderState = borderState;
        this.borderColor = borderColor;
    }

    public boolean isCached(UUID uuid) {
        return playerDataMap.containsKey(uuid);
    }

    public PlayerData get(UUID uuid) {
        return playerDataMap.get(uuid);
    }

    public boolean getBorderState() {
        return borderState;
    }

    public BorderColor getBorderColor() {
        return borderColor;
    }
}
