package com.github.zandy.islandborder.support;

import com.github.zandy.bamboolib.BambooLib;
import com.github.zandy.bamboolib.utils.BambooUtils;
import com.github.zandy.bamboolib.versionsupport.VersionSupport;
import com.github.zandy.islandborder.files.languages.Languages.LanguageEnum;
import com.github.zandy.islandborder.player.PlayerData;
import com.wasteofplastic.askyblock.ASkyBlockAPI;
import com.wasteofplastic.askyblock.Island;
import com.wasteofplastic.askyblock.events.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class ASkyBlock extends BorderSupport implements Listener {
    private final ASkyBlockAPI aSkyBlockAPI = ASkyBlockAPI.getInstance();

    public ASkyBlock() {
        BambooUtils.registerEvent(this);
    }

    @Override
    public void send(Player p) {
        if (aSkyBlockAPI == null) return;
        if (!aSkyBlockAPI.getIslandWorld().equals(p.getWorld())) return;
        if (p.getLocation() == null) return;
        Island island = aSkyBlockAPI.getIslandAt(p.getLocation());
        if (island == null) return;
        Location center = island.getCenter();
        if (center == null) return;
        int x = center.getBlockX(), z = center.getBlockZ(), size = island.getProtectionSize();
        VersionSupport.getInstance().sendBorder(p, PlayerData.get(p.getUniqueId()).getBorderColor(), x, z, size);
    }

    @Override
    public String size(Player p) {
        if (aSkyBlockAPI == null || !aSkyBlockAPI.getIslandWorld().equals(p.getWorld()) || !aSkyBlockAPI.playerIsOnIsland(p)) return LanguageEnum.PLACEHOLDERS_SIZE_NOT_ON_ISLAND.getString(p.getUniqueId());
        int size = aSkyBlockAPI.getIslandAt(p.getLocation()).getProtectionSize() / 2;
        return size + "x" + size;
    }

    @Override
    public boolean inSkyBlockWorld(Player p) {
        return aSkyBlockAPI != null && aSkyBlockAPI.getIslandWorld().equals(p.getWorld());
    }

    @EventHandler
    private void onIslandEnter(IslandEnterEvent e) {
        Player p = Bukkit.getPlayer(e.getPlayer());
        if (!p.isOnline()) return;
        PlayerData playerData = PlayerData.get(e.getPlayer());
        if (playerData == null) return;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!aSkyBlockAPI.playerIsOnIsland(p) || !playerData.getBorderState()) return;
                send(p);
                Bukkit.getPluginManager().callEvent(new com.github.zandy.islandborder.api.island.IslandEnterEvent(p));
            }
        }.runTaskLater(BambooLib.getPluginInstance(), 10);
    }

    @EventHandler
    private void onIslandJoin(IslandJoinEvent e) {
        Player p = Bukkit.getPlayer(e.getPlayer());
        if (!p.isOnline()) return;
        PlayerData playerData = PlayerData.get(e.getPlayer());
        if (playerData == null) return;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!aSkyBlockAPI.playerIsOnIsland(p) || !playerData.getBorderState()) return;
                send(p);
                Bukkit.getPluginManager().callEvent(new com.github.zandy.islandborder.api.island.IslandEnterEvent(p));
            }
        }.runTaskLater(BambooLib.getPluginInstance(), 10);
    }

    @EventHandler
    private void onIslandLeave(IslandLeaveEvent e) {
        remove(Bukkit.getPlayer(e.getPlayer()));
        Bukkit.getPluginManager().callEvent(new com.github.zandy.islandborder.api.island.IslandLeaveEvent(Bukkit.getPlayer(e.getPlayer())));
    }

    @EventHandler
    private void onIslandReset(IslandResetEvent e) {
        if (inSkyBlockWorld(e.getPlayer())) remove(e.getPlayer());
    }

    @EventHandler
    private void onIslandExit(IslandExitEvent e) {
        remove(Bukkit.getPlayer(e.getPlayer()));
        Bukkit.getPluginManager().callEvent(new com.github.zandy.islandborder.api.island.IslandLeaveEvent(Bukkit.getPlayer(e.getPlayer())));
    }
}
