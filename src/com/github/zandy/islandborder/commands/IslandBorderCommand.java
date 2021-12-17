package com.github.zandy.islandborder.commands;

import com.github.zandy.bamboolib.BambooLib;
import com.github.zandy.bamboolib.command.ParentCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

import static com.github.zandy.islandborder.files.Settings.SettingsEnum.COMMAND_ALIASES;
import static com.github.zandy.islandborder.files.languages.Languages.LanguageEnum.CONSOLE_NOT_AVAILABLE;
import static com.github.zandy.islandborder.files.languages.Languages.LanguageEnum.NO_PERMISSION_COMMAND;
import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class IslandBorderCommand extends ParentCommand {
    private final PluginDescriptionFile pluginDescriptionFile;

    public IslandBorderCommand() {
        super("isborder");
        setAliases(COMMAND_ALIASES.getStringList());
        setDescription("Main command for IslandBorder plugin.");
        pluginDescriptionFile = BambooLib.getPluginInstance().getDescription();
    }

    @Override
    public void sendDefaultMessage(CommandSender s) {
        if (!(s instanceof Player)) {
            s.sendMessage(CONSOLE_NOT_AVAILABLE.getString());
            return;
        }
        Player p = (Player) s;
        p.sendMessage("");
        p.sendMessage("");
        p.sendMessage(translateAlternateColorCodes('&', "        &a" + pluginDescriptionFile.getName() + " &7- &a" + pluginDescriptionFile.getVersion()));
        showCommandsList(p);
    }

    @Override
    public String noPermissionMessage(CommandSender s) {
        return NO_PERMISSION_COMMAND.getString(((Player) s).getUniqueId());
    }
}
