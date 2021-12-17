package com.github.zandy.islandborder.features.borders;

import com.github.zandy.bamboolib.BambooLib;
import com.github.zandy.bamboolib.utils.BambooUtils;
import com.github.zandy.bamboolib.versionsupport.utils.BorderColor;
import com.github.zandy.islandborder.files.languages.Languages.LanguageEnum;
import com.github.zandy.islandborder.player.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.github.zandy.islandborder.files.Settings.SettingsEnum.COOLDOWN_ENABLED;
import static com.github.zandy.islandborder.files.Settings.SettingsEnum.COOLDOWN_SECONDS;
import static com.github.zandy.islandborder.files.languages.Languages.LanguageEnum.*;
import static com.github.zandy.islandborder.files.languages.Languages.getLocaleFiles;
import static com.github.zandy.islandborder.files.languages.Languages.getPlayerLocale;
import static org.bukkit.Bukkit.getPlayer;

public class Border {
    private static Border instance = null;
    private final List<UUID> cooldownList = new ArrayList<>();
    private final boolean isCooldownEnabled;
    private final int cooldownSeconds;

    public Border() {
        isCooldownEnabled = COOLDOWN_ENABLED.getBoolean();
        cooldownSeconds = COOLDOWN_SECONDS.getInt();
    }

    public void toggleState(UUID uuid) {
        setState(uuid, !isEnabled(uuid));
    }

    public void setState(UUID uuid, boolean enabled) {
        PlayerData playerData = PlayerData.get(uuid);
        if (isCooldownEnabled && !applyCooldown(uuid, playerData)) return;
        if (enabled) {
            if (isEnabled(uuid)) {
                getPlayer(uuid).sendMessage(BORDER_ALREADY_TOGGLED_ON.getString(uuid));
            } else {
                playerData.setBorderState(true);
                getPlayer(uuid).sendMessage(BORDER_TOGGLED_ON.getString(uuid).replace("[color]", getLocaleFiles().get(getPlayerLocale().get(uuid)).getString("Color." + BambooUtils.capitalizeFirstLetter(playerData.getBorderColor().name().toLowerCase()))));
            }
        } else {
            if (!isEnabled(uuid)) {
                getPlayer(uuid).sendMessage(BORDER_ALREADY_TOGGLED_OFF.getString(uuid));
            } else {
                playerData.setBorderState(false);
                getPlayer(uuid).sendMessage(BORDER_TOGGLED_OFF.getString(uuid));
            }
        }
    }

    public void setColorState(UUID uuid, BorderColor color) {
        if (color == null) return;
        PlayerData playerData = PlayerData.get(uuid);
        Player p = getPlayer(uuid);
        if (!p.hasPermission("isborder.color." + playerData.getBorderColor().name().toLowerCase())) {
            p.sendMessage(NO_PERMISSION_COLOR.getString(uuid).replace("[color]", LanguageEnum.valueOf("COLOR_" + playerData.getBorderColor()).getString(uuid)));
            p.closeInventory();
            return;
        }
        if (playerData.getBorderColor().equals(color)) {
            p.sendMessage(COLOR_ALREADY_DISPLAYING.getString(uuid));
            return;
        }
        if (isCooldownEnabled && !applyCooldown(uuid, playerData)) return;
        playerData.setBorderColor(color);
        p.sendMessage(COLOR_CHANGED.getString(uuid).replace("[color]", getLocaleFiles().get(getPlayerLocale().get(uuid)).getString("Color." + BambooUtils.capitalizeFirstLetter(playerData.getBorderColor().name().toLowerCase()))));
    }

    public boolean isEnabled(UUID uuid) {
        return PlayerData.get(uuid).getBorderState();
    }

    private boolean applyCooldown(UUID uuid, PlayerData playerData) {
        if (!cooldownList.contains(uuid)) {
            cooldownList.add(uuid);
            playerData.setCooldownSeconds(cooldownSeconds);
            BukkitTask cooldownUpdateTask = new BukkitRunnable() {
                @Override
                public void run() {
                    playerData.setCooldownSeconds(playerData.getCooldownSeconds() - 1);
                }
            }.runTaskTimerAsynchronously(BambooLib.getPluginInstance(), 20, 20);
            new BukkitRunnable() {
                @Override
                public void run() {
                    cooldownUpdateTask.cancel();
                    cooldownList.remove(uuid);
                }
            }.runTaskLaterAsynchronously(BambooLib.getPluginInstance(), cooldownSeconds * 20L);
            return true;
        } else {
            String time = playerData.getCooldownSeconds() > 1 ? UNITS_SECONDS.getString(uuid) : UNITS_SECOND.getString(uuid);
            getPlayer(uuid).sendMessage(BORDER_COOLDOWN.getString(uuid).replace("[seconds]", String.valueOf(playerData.getCooldownSeconds())).replace("[secondsFormatted]", time));
            return false;
        }
    }

    public static Border getInstance() {
        if (instance == null) instance = new Border();
        return instance;
    }
}
