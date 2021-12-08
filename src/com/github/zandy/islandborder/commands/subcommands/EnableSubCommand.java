package com.github.zandy.islandborder.commands.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.magenpurp.api.command.SubCommand;

import static com.github.zandy.islandborder.Main.getBorder;
import static com.github.zandy.islandborder.Main.getBorderSupport;
import static com.github.zandy.islandborder.files.languages.Languages.LanguageEnum.COMMAND_AVAILABLE_ON_ISLAND;

public class EnableSubCommand extends SubCommand {

    public EnableSubCommand() {
        super("enable", new String[]{"isborder.toggle", "isborder.*"});
    }

    @Override
    public void execute(CommandSender s, String[] arg) {
        if (!(s instanceof Player)) return;
        Player p = (Player) s;
        if (!getBorderSupport().inSkyBlockWorld(p)) {
            p.sendMessage(COMMAND_AVAILABLE_ON_ISLAND.getString(p.getUniqueId()));
            return;
        }
        getBorder().setState(p.getUniqueId(), true);
    }

    @Override
    public boolean canSee(CommandSender s) {
        return hasPermission(s);
    }
}
