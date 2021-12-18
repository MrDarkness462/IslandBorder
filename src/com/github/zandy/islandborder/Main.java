package com.github.zandy.islandborder;

import com.github.zandy.bamboolib.BambooLib;
import com.github.zandy.bamboolib.utils.BambooUtils;
import com.github.zandy.bamboolib.utils.SpigotUpdater;
import com.github.zandy.bamboolib.versionsupport.VersionSupport;
import com.github.zandy.islandborder.commands.IslandBorderCommand;
import com.github.zandy.islandborder.commands.subcommands.*;
import com.github.zandy.islandborder.features.Placeholders;
import com.github.zandy.islandborder.files.Settings;
import com.github.zandy.islandborder.listeners.PluginEvents;
import com.github.zandy.islandborder.player.PlayerEngine;
import com.github.zandy.islandborder.storage.Database;
import com.github.zandy.islandborder.support.*;
import org.bukkit.plugin.java.JavaPlugin;

import static com.github.zandy.bamboolib.utils.BambooUtils.consolePrint;
import static com.github.zandy.islandborder.files.Settings.SettingsEnum.*;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        BambooLib.setPluginInstance(this);
        consolePrint("&f&m--------------------------");
        consolePrint("   &fIsland Border " + getDescription().getVersion());
        consolePrint(" ");
        consolePrint("Initializing...");
        consolePrint(" ");
        if (!BambooUtils.isVersion(8, 12, 17)) {
            consolePrint("&c&lCan't run on: " + VersionSupport.getInstance().getVersion());
            consolePrint(" ");
            consolePrint("&f&m--------------------------");
            setEnabled(false);
            return;
        }
        consolePrint("&lRunning on: " + VersionSupport.getInstance().getVersion());
        consolePrint(" ");
        consolePrint("&lFinding SkyBlock plugin...");
        consolePrint(" ");
        setEnabled(BorderSupport.init());
        consolePrint(" ");
        consolePrint("Loading Settings...");
        new Settings();
        consolePrint(" ");
        consolePrint("Initializing Database & Accounts...");
        new Database();
        consolePrint("Database type: " + BambooUtils.capitalizeFirstLetter(com.github.zandy.bamboolib.database.Database.getInstance().getDatabaseType().name().toLowerCase()).replace("_", " "));
        PlayerEngine.getInstance().init();
        consolePrint("");
        consolePrint("Loading Functionality...");
        new PluginEvents();
        consolePrint(" ");
        consolePrint("Loading Commands...");
        IslandBorderCommand islandBorderCommand = new IslandBorderCommand();
        if (SUBCOMMAND_ENABLED_GUI.getBoolean()) {
            consolePrint(" ");
            consolePrint("Loading GUIs...");
            islandBorderCommand.addSubCommand(new GUISubCommand());
        }
        if (SUBCOMMAND_ENABLED_BORDER_ENABLE.getBoolean()) islandBorderCommand.addSubCommand(new EnableSubCommand());
        if (SUBCOMMAND_ENABLED_BORDER_DISABLE.getBoolean()) islandBorderCommand.addSubCommand(new DisableSubCommand());
        if (SUBCOMMAND_ENABLED_BORDER_TOGGLE.getBoolean()) islandBorderCommand.addSubCommand(new ToggleSubCommand());
        if (SUBCOMMAND_ENABLED_BORDER_COLOR.getBoolean()) islandBorderCommand.addSubCommand(new ColorSubCommand());
        if (SUBCOMMAND_ENABLED_BORDER_LANGUAGE.getBoolean()) islandBorderCommand.addSubCommand(new LanguageSubCommand());
        VersionSupport.getInstance().registerCommand(islandBorderCommand);
        consolePrint(" ");
        consolePrint("Looking for hooks...");
        consolePrint(" ");
        boolean enablePlaceholders = false;
        if (BambooUtils.isPluginEnabled("PlaceholderAPI")) {
            consolePrint("PlaceholderAPI hook found!");
            enablePlaceholders = true;
        }
        if (BambooUtils.isPluginEnabled("MVdWPlaceholderAPI")) {
            consolePrint("MVdWPlaceholderAPI hook found!");
            enablePlaceholders = true;
        }
        if (!enablePlaceholders) consolePrint("No hooks found!");
        else new Placeholders();
        consolePrint(" ");
        SpigotUpdater.getInstance().checkForUpdates(56320);
        consolePrint(" ");
        consolePrint("&aIsland Border loaded successfully!");
        consolePrint("&f&m--------------------------");
    }

    @Override
    public void onDisable() {
        if (!isEnabled()) return;
        consolePrint("&f&m--------------------------");
        consolePrint("   &fIsland Border " + getDescription().getVersion());
        consolePrint(" ");
        consolePrint("&cDisabling...");
        consolePrint(" ");
        consolePrint("&lDisabling Database...");
        com.github.zandy.bamboolib.database.Database.getInstance().close();
        consolePrint(" ");
        consolePrint("&aIsland Border unloaded successfully!");
        consolePrint("&f&m--------------------------");
    }
}
