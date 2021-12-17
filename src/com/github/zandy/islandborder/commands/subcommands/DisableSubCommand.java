package com.github.zandy.islandborder.commands.subcommands;

import com.github.zandy.bamboolib.command.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.github.zandy.islandborder.Main.getBorder;
import static com.github.zandy.islandborder.Main.getBorderSupport;
import static com.github.zandy.islandborder.files.languages.Languages.LanguageEnum.COMMAND_AVAILABLE_ON_ISLAND;
import static com.github.zandy.islandborder.files.languages.Languages.LanguageEnum.INFO_SUBCOMMAND_DISABLE;

public class DisableSubCommand extends SubCommand {

    public DisableSubCommand() {
        super("disable", INFO_SUBCOMMAND_DISABLE.getString(), new String[]{"isborder.toggle", "isborder.*"});
    }

    @Override
    public void execute(CommandSender s, String[] arg) {
        if (!(s instanceof Player)) return;
        Player p = (Player) s;
        if (!getBorderSupport().inSkyBlockWorld(p)) {
            p.sendMessage(COMMAND_AVAILABLE_ON_ISLAND.getString(p.getUniqueId()));
            return;
        }
        getBorder().setState(p.getUniqueId(), false);
    }

    @Override
    public boolean canSee(CommandSender s) {
        return hasPermission(s);
    }
}
