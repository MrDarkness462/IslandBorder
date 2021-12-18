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

import static com.github.zandy.islandborder.files.Settings.SettingsEnum.*;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        BambooLib.setPluginInstance(this);
        BambooUtils.consolePrint("&f&m--------------------------");
        BambooUtils.consolePrint("   &fIsland Border " + getDescription().getVersion());
        BambooUtils.consolePrint(" ");
        BambooUtils.consolePrint("Initializing...");
        BambooUtils.consolePrint(" ");
        if (!BambooUtils.isVersion(8, 12, 17)) {
            BambooUtils.consolePrint("&c&lCan't run on: " + VersionSupport.getInstance().getVersion());
            BambooUtils.consolePrint(" ");
            BambooUtils.consolePrint("&f&m--------------------------");
            setEnabled(false);
            return;
        }
        BambooUtils.consolePrint("&lRunning on: " + VersionSupport.getInstance().getVersion());
        BambooUtils.consolePrint(" ");
        BambooUtils.consolePrint("&lFinding SkyBlock plugin...");
        BambooUtils.consolePrint(" ");
        setEnabled(BorderSupport.init());
        BambooUtils.consolePrint(" ");
        BambooUtils.consolePrint("Loading Settings...");
        new Settings();
        BambooUtils.consolePrint(" ");
        BambooUtils.consolePrint("Initializing Database & Accounts...");
        new Database();
        BambooUtils.consolePrint("Database type: " + BambooUtils.capitalizeFirstLetter(com.github.zandy.bamboolib.database.Database.getInstance().getDatabaseType().name().toLowerCase()).replace("_", " "));
        PlayerEngine.getInstance().init();
        BambooUtils.consolePrint("");
        BambooUtils.consolePrint("Loading Functionality...");
        new PluginEvents();
        BambooUtils.consolePrint(" ");
        BambooUtils.consolePrint("Loading Commands...");
        IslandBorderCommand islandBorderCommand = new IslandBorderCommand();
        if (SUBCOMMAND_ENABLED_GUI.getBoolean()) {
            BambooUtils.consolePrint(" ");
            BambooUtils.consolePrint("Loading GUIs...");
            islandBorderCommand.addSubCommand(new GUISubCommand());
        }
        if (SUBCOMMAND_ENABLED_BORDER_ENABLE.getBoolean()) islandBorderCommand.addSubCommand(new EnableSubCommand());
        if (SUBCOMMAND_ENABLED_BORDER_DISABLE.getBoolean()) islandBorderCommand.addSubCommand(new DisableSubCommand());
        if (SUBCOMMAND_ENABLED_BORDER_TOGGLE.getBoolean()) islandBorderCommand.addSubCommand(new ToggleSubCommand());
        if (SUBCOMMAND_ENABLED_BORDER_COLOR.getBoolean()) islandBorderCommand.addSubCommand(new ColorSubCommand());
        if (SUBCOMMAND_ENABLED_BORDER_LANGUAGE.getBoolean()) islandBorderCommand.addSubCommand(new LanguageSubCommand());
        VersionSupport.getInstance().registerCommand(islandBorderCommand);
        BambooUtils.consolePrint(" ");
        BambooUtils.consolePrint("Looking for hooks...");
        BambooUtils.consolePrint(" ");
        boolean enablePlaceholders = false;
        if (BambooUtils.isPluginEnabled("PlaceholderAPI")) {
            BambooUtils.consolePrint("PlaceholderAPI hook found!");
            enablePlaceholders = true;
        }
        if (BambooUtils.isPluginEnabled("MVdWPlaceholderAPI")) {
            BambooUtils.consolePrint("MVdWPlaceholderAPI hook found!");
            enablePlaceholders = true;
        }
        if (!enablePlaceholders) BambooUtils.consolePrint("No hooks found!");
        else new Placeholders();
        BambooUtils.consolePrint(" ");
        SpigotUpdater.getInstance().checkForUpdates(56320);
        BambooUtils.consolePrint(" ");
        BambooUtils.consolePrint("&aIsland Border loaded successfully!");
        BambooUtils.consolePrint("&f&m--------------------------");
    }

    @Override
    public void onDisable() {
        if (!isEnabled()) return;
        BambooUtils.consolePrint("&f&m--------------------------");
        BambooUtils.consolePrint("   &fIsland Border " + getDescription().getVersion());
        BambooUtils.consolePrint(" ");
        BambooUtils.consolePrint("&cDisabling...");
        BambooUtils.consolePrint(" ");
        BambooUtils.consolePrint("&lDisabling Database...");
        com.github.zandy.bamboolib.database.Database.getInstance().close();
        BambooUtils.consolePrint(" ");
        BambooUtils.consolePrint("&aIsland Border unloaded successfully!");
        BambooUtils.consolePrint("&f&m--------------------------");
    }
}
