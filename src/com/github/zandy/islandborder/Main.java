package com.github.zandy.islandborder;

import com.github.zandy.islandborder.features.borders.Border;
import com.github.zandy.islandborder.files.Settings;
import com.github.zandy.islandborder.files.languages.Languages;
import com.github.zandy.islandborder.support.BorderSupport;
import org.bukkit.plugin.java.JavaPlugin;
import org.magenpurp.api.MagenAPI;

public class Main extends JavaPlugin {
    private static Border border;
    private static BorderSupport borderSupport;

    @Override
    public void onEnable() {
        new MagenAPI(this).initDB();
        new Settings();
        new Languages();
        border = new Border();
        borderSupport = ;
    }

    @Override
    public void onDisable() {

    }

    public static Border getBorder() {
        return border;
    }

    public static BorderSupport getBorderSupport() {
        return borderSupport;
    }
}
