package com.github.zandy.islandborder.commands.subcommands;

import com.github.zandy.bamboolib.command.SubCommand;
import com.github.zandy.islandborder.features.guis.BorderGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.github.zandy.islandborder.Main.getBorderSupport;
import static com.github.zandy.islandborder.files.languages.Languages.LanguageEnum.COMMAND_AVAILABLE_ON_ISLAND;
import static com.github.zandy.islandborder.files.languages.Languages.LanguageEnum.INFO_SUBCOMMAND_GUI;

public class GUISubCommand extends SubCommand {

    public GUISubCommand() {
        super("gui", INFO_SUBCOMMAND_GUI.getString(), new String[]{"isborder.gui", "isborder.*"});
    }

    @Override
    public void execute(CommandSender s, String[] arg) {
        if (!(s instanceof Player)) return;
        Player p = (Player) s;
        if (!getBorderSupport().inSkyBlockWorld(p)) {
            p.sendMessage(COMMAND_AVAILABLE_ON_ISLAND.getString(p.getUniqueId()));
            return;
        }
        BorderGUI.getInstance().open(p);
    }

    @Override
    public boolean canSee(CommandSender s) {
        return hasPermission(s);
    }
}
