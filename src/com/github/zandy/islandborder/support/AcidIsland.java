package com.github.zandy.islandborder.support;

import com.github.zandy.islandborder.player.PlayerData;
import com.wasteofplastic.acidisland.ASkyBlockAPI;
import com.wasteofplastic.acidisland.Island;
import com.wasteofplastic.acidisland.events.*;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import static com.github.zandy.islandborder.files.languages.Languages.LanguageEnum.PLACEHOLDERS_SIZE_NOT_ON_ISLAND;
import static org.bukkit.Bukkit.getPlayer;
import static org.magenpurp.api.MagenAPI.*;

public class AcidIsland extends BorderSupport implements Listener {
    private final ASkyBlockAPI acidIslandAPI = ASkyBlockAPI.getInstance();

    public AcidIsland() {
        registerEvent(this);
    }

    @Override
    public void send(Player p) {
        if (acidIslandAPI == null) return;
        if (!acidIslandAPI.getIslandWorld().equals(p.getWorld())) return;
        if (p.getLocation() == null) return;
        Island island = acidIslandAPI.getIslandAt(p.getLocation());
        if (island == null) return;
        Location center = island.getCenter();
        if (center == null) return;
        int x = center.getBlockX(), z = center.getBlockZ();
        double radius = island.getProtectionSize();
        getVersionSupport().sendBorder(p, PlayerData.get(p.getUniqueId()).getBorderColor(), x, z, radius);
    }

    @Override
    public String size(Player p) {
        if (acidIslandAPI == null || !acidIslandAPI.getIslandWorld().equals(p.getWorld()) || !acidIslandAPI.playerIsOnIsland(p)) return PLACEHOLDERS_SIZE_NOT_ON_ISLAND.getString(p.getUniqueId());
        int size = acidIslandAPI.getIslandAt(p.getLocation()).getProtectionSize() / 2;
        return size + "x" + size;
    }

    @Override
    public boolean inSkyBlockWorld(Player p) {
        return acidIslandAPI != null && acidIslandAPI.getIslandWorld().equals(p.getWorld());
    }

    @EventHandler
    private void onIslandEnter(IslandEnterEvent e) {
        Player p = getPlayer(e.getPlayer());
        if (!p.isOnline()) return;
        PlayerData playerData = PlayerData.get(e.getPlayer());
        if (playerData == null) return;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!acidIslandAPI.playerIsOnIsland(p) || !playerData.getBorderState()) return;
                send(p);
            }
        }.runTaskLater(getPlugin(), 10);
    }

    @EventHandler
    private void onIslandJoin(IslandJoinEvent e) {
        Player p = getPlayer(e.getPlayer());
        if (!p.isOnline()) return;
        PlayerData playerData = PlayerData.get(e.getPlayer());
        if (playerData == null) return;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!acidIslandAPI.playerIsOnIsland(p) || !playerData.getBorderState()) return;
                send(p);
            }
        }.runTaskLater(getPlugin(), 10);
    }

    @EventHandler
    private void onIslandLeave(IslandLeaveEvent e) {
        remove(getPlayer(e.getPlayer()));
    }

    @EventHandler
    private void onIslandReset(IslandResetEvent e) {
        if (inSkyBlockWorld(e.getPlayer())) remove(e.getPlayer());
    }

    @EventHandler
    private void onIslandExit(IslandExitEvent e) {
        remove(getPlayer(e.getPlayer()));
    }
}
