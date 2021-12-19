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
import world.bentobox.bentobox.api.events.island.IslandCreateEvent;
import world.bentobox.bentobox.api.events.island.IslandEnterEvent;
import world.bentobox.bentobox.api.events.island.IslandExitEvent;
import world.bentobox.bentobox.api.events.island.IslandResetEvent;
import world.bentobox.bentobox.database.objects.Island;

public class BentoBox extends BorderSupport implements Listener {
    private final world.bentobox.bentobox.BentoBox bentoBoxAPI = world.bentobox.bentobox.BentoBox.getInstance();

    public BentoBox() {
        BambooUtils.registerEvent(this);
    }

    @Override
    public void send(Player p) {
        if (bentoBoxAPI == null) return;
        Island island = bentoBoxAPI.getIslands().getIsland(p.getWorld(), p.getUniqueId());
        if (island == null || !island.onIsland(p.getLocation()) || island.getCenter() == null) return;
        int x = island.getCenter().getBlockX(), z = island.getCenter().getBlockZ(), size = island.getProtectionRange() * 2;
        VersionSupport.getInstance().sendBorder(p, PlayerData.get(p.getUniqueId()).getBorderColor(), size, x, z);
    }

    @Override
    public String size(Player p) {
        if (bentoBoxAPI == null || !bentoBoxAPI.getIWM().inWorld(p.getWorld())) return LanguageEnum.PLACEHOLDERS_SIZE_NOT_ON_ISLAND.getString(p.getUniqueId());
        Island island = bentoBoxAPI.getIslands().getIsland(p.getWorld(), p.getUniqueId());
        if (island == null) return 0 + "x" + 0;
        int size = island.getProtectionRange() / 2;
        return size + "x" + size;
    }

    @Override
    public boolean inSkyBlockWorld(Player p) {
        return bentoBoxAPI != null && bentoBoxAPI.getIWM().inWorld(p.getLocation());
    }

    @EventHandler
    private void onIslandEnter(IslandEnterEvent e) {
        Player p = Bukkit.getPlayer(e.getPlayerUUID());
        if (!p.isOnline()) return;
        PlayerData playerData = PlayerData.get(e.getPlayerUUID());
        if (playerData == null) return;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!inSkyBlockWorld(p) || !playerData.getBorderState()) return;
                send(p);
                Bukkit.getPluginManager().callEvent(new com.github.zandy.islandborder.api.island.IslandEnterEvent(p));
            }
        }.runTaskLater(BambooLib.getPluginInstance(), 10);
    }

    @EventHandler
    private void onIslandJoin(IslandCreateEvent e) {
        Player p = Bukkit.getPlayer(e.getPlayerUUID());
        if (!p.isOnline()) return;
        PlayerData playerData = PlayerData.get(e.getPlayerUUID());
        if (playerData == null) return;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!inSkyBlockWorld(p) || !playerData.getBorderState()) return;
                send(p);
                Bukkit.getPluginManager().callEvent(new com.github.zandy.islandborder.api.island.IslandEnterEvent(p));
            }
        }.runTaskLater(BambooLib.getPluginInstance(), 10);
    }

    @EventHandler
    private void onIslandReset(IslandResetEvent e) {
        if (inSkyBlockWorld(Bukkit.getPlayer(e.getPlayerUUID()))) remove(Bukkit.getPlayer(e.getPlayerUUID()));
    }

    @EventHandler
    private void onIslandExit(IslandExitEvent e) {
        remove(Bukkit.getPlayer(e.getPlayerUUID()));
        Bukkit.getPluginManager().callEvent(new com.github.zandy.islandborder.api.island.IslandLeaveEvent(Bukkit.getPlayer(e.getPlayerUUID())));
    }
}
