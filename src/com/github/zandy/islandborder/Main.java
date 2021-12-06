package com.github.zandy.islandborder;

import com.github.zandy.islandborder.features.borders.Border;
import com.github.zandy.islandborder.files.Settings;
import org.bukkit.plugin.java.JavaPlugin;
import org.magenpurp.api.MagenAPI;

public class Main extends JavaPlugin {
    private static Border border;

    @Override
    public void onEnable() {
        new MagenAPI(this).initDB();
        new Settings();
        border = new Border();
    }

    @Override
    public void onDisable() {

    }

    public static Border getBorder() {
        return border;
    }
}
