package com.github.zandy.islandborder.commands.subcommands;

import com.github.zandy.bamboolib.command.SubCommand;
import com.github.zandy.bamboolib.utils.BambooUtils;
import com.github.zandy.bamboolib.versionsupport.utils.BorderColor;
import com.github.zandy.islandborder.features.borders.Border;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.github.zandy.islandborder.Main.getBorderSupport;
import static com.github.zandy.islandborder.files.languages.Languages.LanguageEnum.*;
import static com.github.zandy.islandborder.files.languages.Languages.getLocaleFiles;
import static com.github.zandy.islandborder.files.languages.Languages.getPlayerLocale;
import static java.util.Arrays.asList;
import static net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND;
import static net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND;

public class ColorSubCommand extends SubCommand {

    public ColorSubCommand() {
        super("color", INFO_SUBCOMMAND_COLOR.getString(), new String[]{"isborder.color", "isborder.*", "isborder.color.red", "isborder.color.green", "isborder.color.blue"});
    }

    @Override
    public void execute(CommandSender s, String[] arg) {
        if (!(s instanceof Player)) return;
        Player p = (Player) s;
        if (!getBorderSupport().inSkyBlockWorld(p)) {
            p.sendMessage(COMMAND_AVAILABLE_ON_ISLAND.getString(p.getUniqueId()));
            return;
        }
        if (arg.length == 0) {
            sendHelp(p);
            return;
        }
        String color = arg[0].toUpperCase();
        if (!(color.equals("RED") || color.equals("GREEN") || color.equals("BLUE"))) {
            sendHelp(p);
            return;
        }
        if (!(p.hasPermission("isborder.color." + color.toLowerCase()) || p.hasPermission("isborder.*"))) {
            p.sendMessage(NO_PERMISSION_COLOR.getString(p.getUniqueId()));
            return;
        }
        Border.getInstance().setColorState(p.getUniqueId(), BorderColor.valueOf(color));
    }

    @Override
    public boolean canSee(CommandSender s) {
        return hasPermission(s);
    }

    private void sendHelp(Player p) {
        p.sendMessage(" ");
        p.sendMessage(" ");
        p.sendMessage(COMMAND_USAGE_WRONG.getString(p.getUniqueId()));
        BambooUtils.sendTextComponent(p, COMMAND_USAGE_EXAMPLE.getString(p.getUniqueId()).replace("[command]", COMMAND_USAGE_COLOR.getString(p.getUniqueId())), "/isborder color ", COMMAND_CLICK_TO_SUGGEST.getString(p.getUniqueId()), SUGGEST_COMMAND);
        for (String s : asList("Red", "Green", "Blue")) BambooUtils.sendTextComponent(p, COMMAND_USAGE_CLICK.getString(p.getUniqueId()).replace("[name]", getLocaleFiles().get(getPlayerLocale().get(p.getUniqueId())).getString("Color." + s)), "/isborder color " + s, COMMAND_CLICK_TO_RUN.getString(p.getUniqueId()), RUN_COMMAND);
    }
}
