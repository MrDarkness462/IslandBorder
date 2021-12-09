package com.github.zandy.islandborder;

import com.github.zandy.islandborder.commands.IslandBorderCommand;
import com.github.zandy.islandborder.commands.subcommands.*;
import com.github.zandy.islandborder.features.borders.Border;
import com.github.zandy.islandborder.features.guis.BorderGUI;
import com.github.zandy.islandborder.features.guis.ColorGUI;
import com.github.zandy.islandborder.files.Settings;
import com.github.zandy.islandborder.files.guis.BorderGUIFile;
import com.github.zandy.islandborder.files.guis.ColorGUIFile;
import com.github.zandy.islandborder.files.languages.Languages;
import com.github.zandy.islandborder.player.PlayerEngine;
import com.github.zandy.islandborder.support.BorderSupport;
import org.bukkit.plugin.java.JavaPlugin;
import org.magenpurp.api.MagenAPI;

import static com.github.zandy.islandborder.files.Settings.SettingsEnum.*;
import static com.github.zandy.islandborder.files.languages.Languages.LanguageEnum.*;

public class Main extends JavaPlugin {
    private static BorderGUI borderGUI;
    private static ColorGUI colorGUI;
    private static Border border;
    private static BorderSupport borderSupport;
    private static PlayerEngine playerEngine;

    @Override
    public void onEnable() {
        new MagenAPI(this).initDB();
        new Settings();
        new Languages();
        IslandBorderCommand islandBorderCommand = new IslandBorderCommand();
        if (SUBCOMMAND_ENABLED_GUI.getBoolean()) {
            new BorderGUIFile();
            borderGUI = new BorderGUI();
            new ColorGUIFile();
            colorGUI = new ColorGUI();
            islandBorderCommand.addSubCommand(new GUISubCommand(), INFO_SUBCOMMAND_GUI.getString());
        }
        if (SUBCOMMAND_ENABLED_BORDER_ENABLE.getBoolean()) islandBorderCommand.addSubCommand(new EnableSubCommand(), INFO_SUBCOMMAND_ENABLE.getString());
        if (SUBCOMMAND_ENABLED_BORDER_DISABLE.getBoolean()) islandBorderCommand.addSubCommand(new DisableSubCommand(), INFO_SUBCOMMAND_DISABLE.getString());
        if (SUBCOMMAND_ENABLED_BORDER_TOGGLE.getBoolean()) islandBorderCommand.addSubCommand(new ToggleSubCommand(), INFO_SUBCOMMAND_TOGGLE.getString());
        if (SUBCOMMAND_ENABLED_BORDER_COLOR.getBoolean()) islandBorderCommand.addSubCommand(new ColorSubCommand(), INFO_SUBCOMMAND_COLOR.getString());
        border = new Border();
        borderSupport = ;
        playerEngine = new PlayerEngine();

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

    public static PlayerEngine getPlayerEngine() {
        return playerEngine;
    }
}
