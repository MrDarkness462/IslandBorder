package com.github.zandy.islandborder.support;

import com.github.zandy.islandborder.player.PlayerData;
import com.wasteofplastic.askyblock.ASkyBlockAPI;
import com.wasteofplastic.askyblock.Island;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import static com.github.zandy.islandborder.files.languages.Languages.LanguageEnum.PLACEHOLDERS_SIZE_NOT_ON_ISLAND;
import static org.magenpurp.api.MagenAPI.getVersionSupport;

public class ASkyBlock extends BorderSupport {
    private final ASkyBlockAPI aSkyBlockAPI = ASkyBlockAPI.getInstance();

    @Override
    public void send(Player p) {
        if (aSkyBlockAPI == null) return;
        if (!aSkyBlockAPI.getIslandWorld().equals(p.getWorld())) return;
        if (p.getLocation() == null) return;
        Island island = aSkyBlockAPI.getIslandAt(p.getLocation());
        if (island == null) return;
        Location center = island.getCenter();
        if (center == null) return;
        int x = center.getBlockX(), z = center.getBlockZ();
        double radius = island.getProtectionSize();
        getVersionSupport().sendBorder(p, PlayerData.get(p.getUniqueId()).getBorderColor(), x, z, radius);
    }

    @Override
    public String size(Player p) {
        if (aSkyBlockAPI == null || !aSkyBlockAPI.getIslandWorld().equals(p.getWorld()) || !aSkyBlockAPI.playerIsOnIsland(p)) return PLACEHOLDERS_SIZE_NOT_ON_ISLAND.getString(p.getUniqueId());
        int size = aSkyBlockAPI.getIslandAt(p.getLocation()).getProtectionSize() / 2;
        return size + "x" + size;
    }

    @Override
    public boolean inSkyBlockWorld(Player p) {
        return aSkyBlockAPI != null && aSkyBlockAPI.getIslandWorld().equals(p.getWorld());
    }
}
