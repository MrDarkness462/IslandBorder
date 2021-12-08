package com.github.zandy.islandborder;

import com.github.zandy.islandborder.commands.IslandBorderCommand;
import com.github.zandy.islandborder.commands.subcommands.GUISubCommand;
import com.github.zandy.islandborder.features.borders.Border;
import com.github.zandy.islandborder.features.guis.BorderGUI;
import com.github.zandy.islandborder.features.guis.ColorGUI;
import com.github.zandy.islandborder.files.Settings;
import com.github.zandy.islandborder.files.languages.Languages;
import com.github.zandy.islandborder.support.BorderSupport;
import org.bukkit.plugin.java.JavaPlugin;
import org.magenpurp.api.MagenAPI;

import static com.github.zandy.islandborder.files.Settings.SettingsEnum.SUBCOMMAND_ENABLED_GUI;
import static com.github.zandy.islandborder.files.languages.Languages.LanguageEnum.INFO_SUBCOMMAND_GUI;

public class Main extends JavaPlugin {
    private static BorderGUI borderGUI;
    private static ColorGUI colorGUI;
    private static Border border;
    private static BorderSupport borderSupport;

    @Override
    public void onEnable() {
        new MagenAPI(this).initDB();
        new Settings();
        new Languages();
        borderGUI = new BorderGUI();
        colorGUI = new ColorGUI();
        border = new Border();
        borderSupport = ;
        IslandBorderCommand islandBorderCommand = new IslandBorderCommand();
        if (SUBCOMMAND_ENABLED_GUI.getBoolean()) islandBorderCommand.addSubCommand(new GUISubCommand(), INFO_SUBCOMMAND_GUI.getString());
    }

    @Override
    public void onDisable() {

    }

    public static BorderGUI getBorderGUI() {
        return borderGUI;
    }

    public static ColorGUI getColorGUI() {
        return colorGUI;
    }

    public static Border getBorder() {
        return border;
    }

    public static BorderSupport getBorderSupport() {
        return borderSupport;
    }
}
