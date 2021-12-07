package com.github.zandy.islandborder.support;

import com.github.zandy.islandborder.player.PlayerData;
import org.bukkit.entity.Player;

import static java.lang.Integer.MAX_VALUE;
import static org.bukkit.Bukkit.getOnlinePlayers;
import static org.magenpurp.api.MagenAPI.getVersionSupport;
import static org.magenpurp.api.versionsupport.BorderColor.BLUE;

public abstract class BorderSupport {

    public BorderSupport() {
        for (Player p : getOnlinePlayers()) {
            if (PlayerData.isCached(p.getUniqueId()) && PlayerData.get(p.getUniqueId()).getBorderState()) {
                if (inSkyBlockWorld(p)) send(p);
                else remove(p);
            }
        }
    }

    public abstract void send(Player p);

    public void remove(Player p) {
        if (PlayerData.isCached(p.getUniqueId())) getVersionSupport().sendBorder(p, BLUE, 0, 0, MAX_VALUE);
    }

    public abstract String size(Player p);

    public abstract boolean inSkyBlockWorld(Player p);

}
