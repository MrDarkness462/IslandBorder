package com.github.zandy.islandborder.support;

import com.github.zandy.islandborder.player.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import world.bentobox.bentobox.api.events.island.IslandEvent.*;
import world.bentobox.bentobox.database.objects.Island;

import static com.github.zandy.islandborder.files.languages.Languages.LanguageEnum.PLACEHOLDERS_SIZE_NOT_ON_ISLAND;
import static org.bukkit.Bukkit.getPlayer;
import static org.bukkit.Bukkit.getPluginManager;
import static org.magenpurp.api.MagenAPI.*;

public class BentoBox extends BorderSupport implements Listener {
    private final world.bentobox.bentobox.BentoBox bentoBoxAPI = world.bentobox.bentobox.BentoBox.getInstance();

    public BentoBox() {
        registerEvent(this);
    }

    @Override
    public void send(Player p) {
        if (bentoBoxAPI == null) return;
        Island island = bentoBoxAPI.getIslands().getIsland(p.getWorld(), p.getUniqueId());
        if (island == null || !island.onIsland(p.getLocation()) || island.getCenter() == null) return;
        int x = island.getCenter().getBlockX(), z = island.getCenter().getBlockZ();
        double radius = island.getProtectionRange() * 2;
        getVersionSupport().sendBorder(p, PlayerData.get(p.getUniqueId()).getBorderColor(), x, z, radius);
    }

    @Override
    public String size(Player p) {
        if (bentoBoxAPI == null || !bentoBoxAPI.getIWM().inWorld(p.getWorld())) return PLACEHOLDERS_SIZE_NOT_ON_ISLAND.getString(p.getUniqueId());
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
        Player p = getPlayer(e.getPlayerUUID());
        if (!p.isOnline()) return;
        PlayerData playerData = PlayerData.get(e.getPlayerUUID());
        if (playerData == null) return;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!inSkyBlockWorld(p) || !playerData.getBorderState()) return;
                send(p);
                getPluginManager().callEvent(new com.github.zandy.islandborder.api.island.IslandEnterEvent(p));
            }
        }.runTaskLater(getPlugin(), 10);
    }

    @EventHandler
    private void onIslandJoin(IslandCreateEvent e) {
        Player p = getPlayer(e.getPlayerUUID());
        if (!p.isOnline()) return;
        PlayerData playerData = PlayerData.get(e.getPlayerUUID());
        if (playerData == null) return;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!inSkyBlockWorld(p) || !playerData.getBorderState()) return;
                send(p);
                getPluginManager().callEvent(new com.github.zandy.islandborder.api.island.IslandEnterEvent(p));
            }
        }.runTaskLater(getPlugin(), 10);
    }

    @EventHandler
    private void onIslandReset(IslandResetEvent e) {
        if (inSkyBlockWorld(getPlayer(e.getPlayerUUID()))) remove(getPlayer(e.getPlayerUUID()));
    }

    @EventHandler
    private void onIslandExit(IslandExitEvent e) {
        remove(getPlayer(e.getPlayerUUID()));
        getPluginManager().callEvent(new com.github.zandy.islandborder.api.island.IslandLeaveEvent(getPlayer(e.getPlayerUUID())));
    }
}
