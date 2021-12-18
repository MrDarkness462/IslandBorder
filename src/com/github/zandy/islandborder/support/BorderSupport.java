package com.github.zandy.islandborder.support;

import com.github.zandy.bamboolib.BambooLib;
import com.github.zandy.bamboolib.utils.BambooUtils;
import com.github.zandy.bamboolib.versionsupport.VersionSupport;
import com.github.zandy.bamboolib.versionsupport.utils.BorderColor;
import com.github.zandy.islandborder.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class BorderSupport {
    private static BorderSupport instance;

    public static boolean init() {
        if (BambooUtils.isPluginEnabled("ASkyBlock")) {
            instance = new ASkyBlock();
            BambooUtils.consolePrint("&eASkyBlock &fplugin found!");
        } else if (BambooUtils.isPluginEnabled("AcidIsland")) {
            instance = new AcidIsland();
            BambooUtils.consolePrint("&eAcidIsland &fplugin found!");
        } else if (BambooUtils.isPluginEnabled("BentoBox")) {
            instance = new BentoBox();
            BambooUtils.consolePrint("&eBentoBox &fplugin found!");
        } else if (BambooUtils.isPluginEnabled("uSkyBlock")) {
            instance = new USkyBlock();
            BambooUtils.consolePrint("&euSkyBlock &fplugin found!");
        } else if (BambooUtils.isPluginEnabled("IslandWorld")) {
            instance = new IslandWorld();
            BambooUtils.consolePrint("&eIslandWorld &fplugin found!");
        } else {
            BambooUtils.consolePrint("&cNO SKYBLOCK PLUGIN FOUND! DISABLING...");
            BambooUtils.consolePrint(" ");
            BambooUtils.consolePrint("&f&m--------------------------");
            return false;
        }
        return true;
    }

    public BorderSupport() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (PlayerData.isCached(p.getUniqueId()) && PlayerData.get(p.getUniqueId()).getBorderState()) {
                        if (inSkyBlockWorld(p)) send(p);
                        else remove(p);
                    }
                }
            }
        }.runTaskTimerAsynchronously(BambooLib.getPluginInstance(), 60, 60);
    }

    public abstract void send(Player p);

    public void remove(Player p) {
        if (PlayerData.isCached(p.getUniqueId())) VersionSupport.getInstance().sendBorder(p, BorderColor.BLUE, 0, 0, Integer.MAX_VALUE);
    }

    public abstract String size(Player p);

    public abstract boolean inSkyBlockWorld(Player p);

    public static BorderSupport getInstance() {
        return instance;
    }
}
