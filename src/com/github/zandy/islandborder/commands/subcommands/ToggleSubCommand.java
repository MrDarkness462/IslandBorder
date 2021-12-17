package com.github.zandy.islandborder.commands.subcommands;

import com.github.zandy.bamboolib.command.SubCommand;
import com.github.zandy.islandborder.features.borders.Border;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.github.zandy.islandborder.Main.getBorderSupport;
import static com.github.zandy.islandborder.files.languages.Languages.LanguageEnum.COMMAND_AVAILABLE_ON_ISLAND;
import static com.github.zandy.islandborder.files.languages.Languages.LanguageEnum.INFO_SUBCOMMAND_TOGGLE;

public class ToggleSubCommand extends SubCommand {

    public ToggleSubCommand() {
        super("toggle", INFO_SUBCOMMAND_TOGGLE.getString(), new String[]{"isborder.toggle", "isborder.*"});
    }

    @Override
    public void execute(CommandSender s, String[] arg) {
        if (!(s instanceof Player)) return;
        Player p = (Player) s;
        if (!getBorderSupport().inSkyBlockWorld(p)) {
            p.sendMessage(COMMAND_AVAILABLE_ON_ISLAND.getString(p.getUniqueId()));
            return;
        }
        Border.getInstance().toggleState(p.getUniqueId());
    }

    @Override
    public boolean canSee(CommandSender s) {
        return hasPermission(s);
    }
}
