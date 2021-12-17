package com.github.zandy.islandborder;

import com.github.zandy.islandborder.commands.IslandBorderCommand;
import com.github.zandy.islandborder.commands.subcommands.*;
import com.github.zandy.islandborder.features.Placeholders;
import com.github.zandy.islandborder.features.borders.Border;
import com.github.zandy.islandborder.features.guis.BorderGUI;
import com.github.zandy.islandborder.features.guis.ColorGUI;
import com.github.zandy.islandborder.files.Settings;
import com.github.zandy.islandborder.files.guis.BorderGUIFile;
import com.github.zandy.islandborder.files.guis.ColorGUIFile;
import com.github.zandy.islandborder.files.languages.Languages;
import com.github.zandy.islandborder.listeners.PluginEvents;
import com.github.zandy.islandborder.player.PlayerEngine;
import com.github.zandy.islandborder.storage.Database;
import com.github.zandy.islandborder.support.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.magenpurp.api.MagenAPI;

import static com.github.zandy.islandborder.files.Settings.SettingsEnum.*;
import static com.github.zandy.islandborder.files.languages.Languages.LanguageEnum.*;
import static org.magenpurp.api.MagenAPI.*;
import static org.magenpurp.api.utils.SpigotUpdater.checkForUpdates;

public class Main extends JavaPlugin {
    private static BorderGUI borderGUI;
    private static ColorGUI colorGUI;
    private static Border border;
    private static BorderSupport borderSupport;
    private static PlayerEngine playerEngine;

    @Override
    public void onEnable() {
        new MagenAPI(this).initDB();
        print("&f&m--------------------------");
        print("   &fIsland Border " + getDescription().getVersion());
        print(" ");
        print("Initializing...");
        print(" ");
        if (!getVersionSupport().contains(8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18)) {
            print("&c&lCan't run on: " + getVersion());
            print(" ");
            print("&f&m--------------------------");
            setEnabled(false);
            return;
        }
        print("&lRunning on: " + getVersion());
        print(" ");
        print("&lFinding SkyBlock plugin...");
        print(" ");
        if (isPluginEnabled("ASkyBlock")) {
            borderSupport = new ASkyBlock();
            print("&eASkyBlock &fplugin found!");
        } else if (isPluginEnabled("AcidIsland")) {
            borderSupport = new AcidIsland();
            print("&eAcidIsland &fplugin found!");
        } else if (isPluginEnabled("BentoBox")) {
            borderSupport = new BentoBox();
            print("&eBentoBox &fplugin found!");
        } else if (isPluginEnabled("uSkyBlock")) {
            borderSupport = new USkyBlock();
            print("&euSkyBlock &fplugin found!");
        } else if (isPluginEnabled("IslandWorld")) {
            borderSupport = new IslandWorld();
            print("&eIslandWorld &fplugin found!");
        } else {
            print("&cNO SKYBLOCK PLUGIN FOUND! DISABLING...");
            print(" ");
            print("&f&m--------------------------");
            setEnabled(false);
            return;
        }
        print(" ");
        print("Loading Settings...");
        new Settings();
        print(" ");
        print("Loading Languages...");
        new Languages();
        print(" ");
        print("Initializing Database & Accounts...");
        print("Database type: " + getVersionSupport().makeFirstLetterUppercase(getDatabase().getDatabaseType().name().toLowerCase()).replace("_", " "));
        new Database();
        playerEngine = new PlayerEngine();
        print("");
        print("Loading Functionality...");
        border = new Border();
        new PluginEvents();
        print(" ");
        print("Loading Commands...");
        IslandBorderCommand islandBorderCommand = new IslandBorderCommand();
        if (SUBCOMMAND_ENABLED_GUI.getBoolean()) {
            print(" ");
            print("Loading GUIs...");
            borderGUI = new BorderGUI();
            new ColorGUIFile();
            colorGUI = new ColorGUI();
            islandBorderCommand.addSubCommand(new GUISubCommand(), INFO_SUBCOMMAND_GUI.getString());
        }
        if (SUBCOMMAND_ENABLED_BORDER_ENABLE.getBoolean()) islandBorderCommand.addSubCommand(new EnableSubCommand(), INFO_SUBCOMMAND_ENABLE.getString());
        if (SUBCOMMAND_ENABLED_BORDER_DISABLE.getBoolean()) islandBorderCommand.addSubCommand(new DisableSubCommand(), INFO_SUBCOMMAND_DISABLE.getString());
        if (SUBCOMMAND_ENABLED_BORDER_TOGGLE.getBoolean()) islandBorderCommand.addSubCommand(new ToggleSubCommand(), INFO_SUBCOMMAND_TOGGLE.getString());
        if (SUBCOMMAND_ENABLED_BORDER_COLOR.getBoolean()) islandBorderCommand.addSubCommand(new ColorSubCommand(), INFO_SUBCOMMAND_COLOR.getString());
        if (SUBCOMMAND_ENABLED_BORDER_LANGUAGE.getBoolean()) islandBorderCommand.addSubCommand(new LanguageSubCommand(), INFO_SUBCOMMAND_LANGUAGE.getString());
        getVersionSupport().registerCommand(islandBorderCommand);
        print(" ");
        print("Looking for hooks...");
        print(" ");
        boolean enablePlaceholders = false;
        if (isPluginEnabled("PlaceholderAPI")) {
            print("PlaceholderAPI hook found!");
            enablePlaceholders = true;
        }
        if (isPluginEnabled("MVdWPlaceholderAPI")) {
            print("MVdWPlaceholderAPI hook found!");
            enablePlaceholders = true;
        }
        if (!enablePlaceholders) print("No hooks found!");
        else new Placeholders();
        print(" ");
        checkForUpdates(56320);
        print(" ");
        print("&aIsland Border loaded successfully!");
        print("&f&m--------------------------");
    }

    @Override
    public void onDisable() {
        if (!isEnabled()) return;
        print("&f&m--------------------------");
        print("   &fIsland Border " + getDescription().getVersion());
        print(" ");
        print("&cDisabling...");
        print(" ");
        print("&lDisabling Database...");
        getDatabase().close();
        print(" ");
        print("&aIsland Border unloaded successfully!");
        print("&f&m--------------------------");
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
