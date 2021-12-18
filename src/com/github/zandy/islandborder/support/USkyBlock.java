package com.github.zandy.islandborder.support;

import com.github.zandy.bamboolib.utils.BambooFile;
import com.github.zandy.bamboolib.versionsupport.VersionSupport;
import com.github.zandy.islandborder.files.languages.Languages.LanguageEnum;
import com.github.zandy.islandborder.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import us.talabrek.ultimateskyblock.api.uSkyBlockAPI;

public class USkyBlock extends BorderSupport {
    private final uSkyBlockAPI uSkyBlockAPI = (uSkyBlockAPI) Bukkit.getPluginManager().getPlugin("uSkyBlock");
    private final int protectionRange;
    private final String worldName;

    public USkyBlock() {
        BambooFile uSkyBlockConfiguration = new BambooFile("config", "plguins/uSkyBlock", true);
        protectionRange = uSkyBlockConfiguration.getInt("options.island.protectionRange");
        worldName = uSkyBlockConfiguration.getString("options.general.worldName");
    }

    @Override
    public void send(Player p) {
        if (uSkyBlockAPI == null || !uSkyBlockAPI.getPlayerInfo(p).getHasIsland() || !uSkyBlockAPI.getIslandInfo(p).contains(p.getLocation())) return;
        Location islandLocation = uSkyBlockAPI.getIslandInfo(p).getIslandLocation();
        int x = islandLocation.getBlockX(), z = islandLocation.getBlockZ();
        VersionSupport.getInstance().sendBorder(p, PlayerData.get(p.getUniqueId()).getBorderColor(), protectionRange - 2, x, z);
    }

    @Override
    public String size(Player p) {
        if (!worldName.equals(p.getWorld().getName())) return LanguageEnum.PLACEHOLDERS_SIZE_NOT_ON_ISLAND.getString(p.getUniqueId());
        int size = protectionRange / 2;
        return size + "x" + size;
    }

    @Override
    public boolean inSkyBlockWorld(Player p) {
        return p.getWorld().getName().equals(worldName);
    }
}
