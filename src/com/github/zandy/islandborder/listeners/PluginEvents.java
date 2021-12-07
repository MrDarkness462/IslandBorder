package com.github.zandy.islandborder.listeners;

import com.github.zandy.islandborder.player.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.scheduler.BukkitRunnable;

import static com.github.zandy.islandborder.Main.getBorderSupport;
import static org.magenpurp.api.MagenAPI.getPlugin;

public class PluginEvents implements Listener {

    @EventHandler
    private void onWorldChange(PlayerChangedWorldEvent e) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (PlayerData.isCached(e.getPlayer().getUniqueId())) getBorderSupport().remove(e.getPlayer());
            }
        }.runTaskLaterAsynchronously(getPlugin(), 160);
    }

}
