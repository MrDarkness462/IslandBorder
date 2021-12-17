package com.github.zandy.islandborder.player;

import com.github.zandy.bamboolib.database.Database;
import com.github.zandy.bamboolib.database.utils.ColumnInfo;
import com.github.zandy.bamboolib.database.utils.DatabaseProfile;
import com.github.zandy.bamboolib.utils.BambooUtils;
import com.github.zandy.bamboolib.versionsupport.utils.BorderColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.github.zandy.islandborder.files.Settings.SettingsEnum.*;
import static java.lang.Boolean.parseBoolean;
import static org.bukkit.Bukkit.getOnlinePlayers;
import static org.bukkit.Bukkit.getPlayer;

public class PlayerEngine implements Listener {
    private final Boolean defaultState = DEFAULT_BORDER_STATE.getBoolean();
    private final String defaultColor = DEFAULT_BORDER_COLOR.getString(), defaultLanguage = DEFAULT_LANGUAGE.getString();
    private final List<UUID> cachedPlayers;

    public PlayerEngine() {
        cachedPlayers = new ArrayList<>();
        BambooUtils.registerEvent(this);
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
        return Database.getInstance().hasAccount(uuid, "Island-Border");
    }

    private void playerJoin(UUID uuid) {
        if (!hasDBAccount(uuid)) createAccount(uuid);
        if (hasDBAccount(uuid)) cachedPlayers.add(uuid);
        if (!Database.getInstance().getDatabaseCredentials().isEnabled()) patchProfile(uuid);
        if (!PlayerData.isCached(uuid)) new PlayerData(uuid, parseBoolean(Database.getInstance().getString(uuid, "Enabled", "Island-Border")), BorderColor.valueOf(Database.getInstance().getString(uuid, "Color", "Island-Border").toUpperCase()), Database.getInstance().getString(uuid, "Language", "Island-Border"));
    }

    private void createAccount(UUID uuid) {
        if (Database.getInstance().getDatabaseCredentials().isEnabled()) {
            List<ColumnInfo> columnInfoList = new ArrayList<>();
            columnInfoList.add(new ColumnInfo("Player", getPlayer(uuid).getName()));
            columnInfoList.add(new ColumnInfo("UUID", uuid.toString()));
            columnInfoList.add(new ColumnInfo("Enabled", defaultState.toString()));
            columnInfoList.add(new ColumnInfo("Color", defaultColor));
            columnInfoList.add(new ColumnInfo("Language", defaultLanguage));
            Database.getInstance().createPlayer(uuid, "Island-Border", columnInfoList);
            return;
        }
        Database.getInstance().setString(uuid, getPlayer(uuid).getName(), "Player", "Island-Border");
        Database.getInstance().setString(uuid, uuid.toString(), "UUID", "Island-Border");
        Database.getInstance().setString(uuid, defaultState.toString(), "Enabled", "Island-Border");
        Database.getInstance().setString(uuid, defaultColor, "Color", "Island-Border");
        Database.getInstance().setString(uuid, defaultLanguage, "Language", "Island-Border");
    }

    private void patchProfile(UUID uuid) {
        DatabaseProfile profile = Database.getInstance().getProfile(uuid);
        profile.addDefault("Island-Border.Player", getPlayer(uuid).getName());
        profile.addDefault("Island-Border.UUID", uuid.toString());
        profile.addDefault("Island-Border.Enabled", defaultState.toString());
        profile.addDefault("Island-Border.Color", defaultColor);
        profile.addDefault("Island-Border.Language", defaultLanguage);
        profile.copyDefaults();
        profile.save();
    }
}
