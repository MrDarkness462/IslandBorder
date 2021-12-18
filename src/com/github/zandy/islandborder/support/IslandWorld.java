package com.github.zandy.islandborder.support;

import com.github.zandy.bamboolib.BambooLib;
import com.github.zandy.bamboolib.utils.BambooUtils;
import com.github.zandy.bamboolib.versionsupport.VersionSupport;
import com.github.zandy.islandborder.files.languages.Languages.LanguageEnum;
import com.github.zandy.islandborder.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import pl.islandworld.Config;
import pl.islandworld.api.IslandWorldApi;
import pl.islandworld.api.events.IslandCreateEvent;
import pl.islandworld.api.events.IslandEnterEvent;
import pl.islandworld.api.events.IslandLeaveEvent;
import pl.islandworld.entity.SimpleIsland;

public class IslandWorld extends BorderSupport implements Listener {
    private final pl.islandworld.IslandWorld islandWorldAPI = pl.islandworld.IslandWorld.getInstance();
    private final int isleSize = Config.ISLE_SIZE, isleRadius = isleSize - 2;

    public IslandWorld() {
        BambooUtils.registerEvent(this);
    }

    @Override
    public void send(Player p) {
        SimpleIsland island = IslandWorldApi.getIsland(p.getName(), true);
        if (island == null || !islandWorldAPI.isInsideIsland(p, island) || island.getLocation() != null) return;
        int[] coordinates = IslandWorldApi.getIslandXZCoords(p.getName(), true);
        int x = coordinates[0] + isleSize / 2, z = coordinates[1] + isleSize / 2;
        VersionSupport.getInstance().sendBorder(p, PlayerData.get(p.getUniqueId()).getBorderColor(), isleRadius * island.getRegionSpacing(), x, z);
    }

    @Override
    public String size(Player p) {
        SimpleIsland island = IslandWorldApi.getIsland(p.getUniqueId());
        if (island == null || !IslandWorldApi.getIslandWorld().equals(p.getWorld())) return LanguageEnum.PLACEHOLDERS_SIZE_NOT_ON_ISLAND.getString(p.getUniqueId());
        int size = isleRadius * island.getRegionSpacing();
        return size + "x" + size;
    }

    @Override
    public boolean inSkyBlockWorld(Player p) {
        return IslandWorldApi.getIslandWorld().equals(p.getWorld());
    }

    @EventHandler
    private void onIslandEnter(IslandEnterEvent e) {
        Player p = e.getPlayer();
        if (!p.isOnline()) return;
        PlayerData playerData = PlayerData.get(p.getUniqueId());
        if (playerData == null) return;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!islandWorldAPI.isInsideIsland(p, e.getIsland()) || !playerData.getBorderState()) return;
                send(p);
                Bukkit.getPluginManager().callEvent(new com.github.zandy.islandborder.api.island.IslandEnterEvent(p));
            }
        }.runTaskLater(BambooLib.getPluginInstance(), 10);
    }

    @EventHandler
    private void onIslandCreate(IslandCreateEvent e) {
        Player p = e.getOwner();
        if (!p.isOnline()) return;
        PlayerData playerData = PlayerData.get(p.getUniqueId());
        if (playerData == null) return;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!islandWorldAPI.isInsideIsland(p, e.getIsland()) || !playerData.getBorderState()) return;
                send(p);
                Bukkit.getPluginManager().callEvent(new com.github.zandy.islandborder.api.island.IslandEnterEvent(p));
            }
        }.runTaskLater(BambooLib.getPluginInstance(), 10);
    }

    @EventHandler
    private void onIslandLeave(IslandLeaveEvent e) {
        remove(e.getPlayer());
        Bukkit.getPluginManager().callEvent(new com.github.zandy.islandborder.api.island.IslandLeaveEvent(e.getPlayer()));
    }
}