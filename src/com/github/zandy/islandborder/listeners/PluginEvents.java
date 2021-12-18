package com.github.zandy.islandborder.listeners;

import com.github.zandy.bamboolib.BambooLib;
import com.github.zandy.bamboolib.utils.BambooUtils;
import com.github.zandy.islandborder.player.PlayerData;
import com.github.zandy.islandborder.support.BorderSupport;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PluginEvents implements Listener {

    public PluginEvents() {
        BambooUtils.registerEvent(this);
    }

    @EventHandler
    private void onWorldChange(PlayerChangedWorldEvent e) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (PlayerData.isCached(e.getPlayer().getUniqueId())) BorderSupport.getInstance().remove(e.getPlayer());
            }
        }.runTaskLaterAsynchronously(BambooLib.getPluginInstance(), 160);
    }

}
