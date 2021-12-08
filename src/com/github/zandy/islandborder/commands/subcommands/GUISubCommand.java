package com.github.zandy.islandborder.commands.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.magenpurp.api.command.SubCommand;

import static com.github.zandy.islandborder.Main.getBorderGUI;
import static com.github.zandy.islandborder.Main.getBorderSupport;
import static com.github.zandy.islandborder.files.languages.Languages.LanguageEnum.COMMAND_AVAILABLE_ON_ISLAND;

public class GUISubCommand extends SubCommand {

    public GUISubCommand() {
        super("gui", new String[]{"isborder.gui", "isborder.*"});
    }

    @Override
    public void execute(CommandSender s, String[] arg) {
        if (!(s instanceof Player)) return;
        Player p = (Player) s;
        if (!getBorderSupport().inSkyBlockWorld(p)) {
            p.sendMessage(COMMAND_AVAILABLE_ON_ISLAND.getString(p.getUniqueId()));
            return;
        }
        getBorderGUI().open(p);
    }

    @Override
    public boolean canSee(CommandSender s) {
        return hasPermission(s);
    }
}
