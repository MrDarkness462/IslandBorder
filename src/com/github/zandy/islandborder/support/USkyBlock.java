package com.github.zandy.islandborder.support;

import com.github.zandy.islandborder.player.PlayerData;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.magenpurp.api.utils.FileManager;
import us.talabrek.ultimateskyblock.api.uSkyBlockAPI;

import static com.github.zandy.islandborder.files.languages.Languages.LanguageEnum.PLACEHOLDERS_SIZE_NOT_ON_ISLAND;
import static org.bukkit.Bukkit.getPluginManager;
import static org.magenpurp.api.MagenAPI.getVersionSupport;

public class USkyBlock extends BorderSupport {
    private final uSkyBlockAPI uSkyBlockAPI = (uSkyBlockAPI) getPluginManager().getPlugin("uSkyBlock");
    private final int protectionRange;
    private final String worldName;

    public USkyBlock() {
        FileManager uSkyBlockConfiguration = new FileManager("config", "plguins/uSkyBlock", true);
        protectionRange = uSkyBlockConfiguration.getInt("options.island.protectionRange");
        worldName = uSkyBlockConfiguration.getString("options.general.worldName");
    }

    @Override
    public void send(Player p) {
        if (uSkyBlockAPI == null || !uSkyBlockAPI.getPlayerInfo(p).getHasIsland() || !uSkyBlockAPI.getIslandInfo(p).contains(p.getLocation())) return;
        Location islandLocation = uSkyBlockAPI.getIslandInfo(p).getIslandLocation();
        int x = islandLocation.getBlockX(), z = islandLocation.getBlockZ();
        getVersionSupport().sendBorder(p, PlayerData.get(p.getUniqueId()).getBorderColor(), x, z, protectionRange - 2);
    }

    @Override
    public String size(Player p) {
        if (!worldName.equals(p.getWorld().getName())) return PLACEHOLDERS_SIZE_NOT_ON_ISLAND.getString(p.getUniqueId());
        int size = protectionRange / 2;
        return size + "x" + size;
    }

    @Override
    public boolean inSkyBlockWorld(Player p) {
        return p.getWorld().getName().equals(worldName);
    }
}
