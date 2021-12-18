package com.github.zandy.islandborder.commands.subcommands;

import com.github.zandy.bamboolib.command.SubCommand;
import com.github.zandy.islandborder.features.borders.Border;
import com.github.zandy.islandborder.files.languages.Languages.LanguageEnum;
import com.github.zandy.islandborder.support.BorderSupport;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DisableSubCommand extends SubCommand {

    public DisableSubCommand() {
        super("disable", LanguageEnum.INFO_SUBCOMMAND_DISABLE.getString(), new String[]{"isborder.toggle", "isborder.player", "isborder.*"});
    }

    @Override
    public void execute(CommandSender s, String[] arg) {
        if (!(s instanceof Player)) return;
        Player p = (Player) s;
        if (!BorderSupport.getInstance().inSkyBlockWorld(p)) {
            p.sendMessage(LanguageEnum.COMMAND_AVAILABLE_ON_ISLAND.getString(p.getUniqueId()));
            return;
        }
        Border.getInstance().setState(p.getUniqueId(), false);
    }

    @Override
    public boolean canSee(CommandSender s) {
        return hasPermission(s);
    }
}
