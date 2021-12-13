package com.github.zandy.islandborder.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.magenpurp.api.database.utils.ColumnInfo;
import org.magenpurp.api.utils.FileManagerDatabase;
import org.magenpurp.api.versionsupport.BorderColor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.github.zandy.islandborder.files.Settings.SettingsEnum.*;
import static java.lang.Boolean.parseBoolean;
import static org.bukkit.Bukkit.getOnlinePlayers;
import static org.bukkit.Bukkit.getPlayer;
import static org.magenpurp.api.MagenAPI.getDatabase;
import static org.magenpurp.api.MagenAPI.registerEvent;
import static org.magenpurp.api.database.Database.getMySQLCredentials;

public class PlayerEngine implements Listener {
    private final Boolean defaultState = DEFAULT_BORDER_STATE.getBoolean();
    private final String defaultColor = DEFAULT_BORDER_COLOR.getString(), defaultLanguage = DEFAULT_LANGUAGE.getString();
    private List<UUID> cachedPlayers = new ArrayList<>();

    public PlayerEngine() {
        registerEvent(this);
        for (Player p : getOnlinePlayers()) playerJoin(p.getUniqueId());
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent e) {
        playerJoin(e.getPlayer().getUniqueId());
    }

    public boolean hasAccount(UUID uuid) {
        return cachedPlayers.contains(uuid);
    }

    private boolean hasDBAccount(UUID uuid) {
        return getDatabase().hasAccount(uuid, "Island-Border");
    }

    private void playerJoin(UUID uuid) {
        if (!hasDBAccount(uuid)) createAccount(uuid);
        if (hasDBAccount(uuid)) cachedPlayers.add(uuid);
        if (!getMySQLCredentials().isEnabled()) patchProfile(uuid);
        if (!PlayerData.isCached(uuid)) new PlayerData(uuid, parseBoolean(getDatabase().getString(uuid, "Enabled", "Island-Border")), BorderColor.valueOf(getDatabase().getString(uuid, "Color", "Island-Border").toUpperCase()), getDatabase().getString(uuid, "Language", "Island-Border"));
    }

    private void createAccount(UUID uuid) {
        if (getMySQLCredentials().isEnabled()) {
            List<ColumnInfo> columnInfoList = new ArrayList<>();
            columnInfoList.add(new ColumnInfo("Player", getPlayer(uuid).getName()));
            columnInfoList.add(new ColumnInfo("UUID", uuid.toString()));
            columnInfoList.add(new ColumnInfo("Enabled", defaultState.toString()));
            columnInfoList.add(new ColumnInfo("Color", defaultColor));
            columnInfoList.add(new ColumnInfo("Language", defaultLanguage));
            getDatabase().createPlayer(uuid, "Island-Border", columnInfoList);
            return;
        }
        getDatabase().setString(uuid, getPlayer(uuid).getName(), "Player", "Island-Border");
        getDatabase().setString(uuid, uuid.toString(), "UUID", "Island-Border");
        getDatabase().setString(uuid, defaultState.toString(), "Enabled", "Island-Border");
        getDatabase().setString(uuid, defaultColor, "Color", "Island-Border");
        getDatabase().setString(uuid, defaultLanguage, "Language", "Island-Border");
    }

    private void patchProfile(UUID uuid) {
        FileManagerDatabase account = getDatabase().getProfile(uuid);
        account.addDefault("Island-Border.Player", getPlayer(uuid).getName());
        account.addDefault("Island-Border.UUID", uuid.toString());
        account.addDefault("Island-Border.Enabled", defaultState.toString());
        account.addDefault("Island-Border.Color", defaultColor);
        account.addDefault("Island-Border.Language", defaultLanguage);
        account.copyDefaults();
        account.save();
    }
}
