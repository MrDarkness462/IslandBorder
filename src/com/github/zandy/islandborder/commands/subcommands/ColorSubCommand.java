package com.github.zandy.islandborder.commands.subcommands;

import com.github.zandy.bamboolib.command.SubCommand;
import com.github.zandy.bamboolib.utils.BambooUtils;
import com.github.zandy.bamboolib.versionsupport.utils.BorderColor;
import com.github.zandy.islandborder.features.borders.Border;
import com.github.zandy.islandborder.files.languages.Languages;
import com.github.zandy.islandborder.files.languages.Languages.LanguageEnum;
import com.github.zandy.islandborder.support.BorderSupport;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ColorSubCommand extends SubCommand {

    public ColorSubCommand() {
        super("color", LanguageEnum.INFO_SUBCOMMAND_COLOR.getString(), new String[]{"isborder.color", "isborder.*", "isborder.color.red", "isborder.color.green", "isborder.color.blue"});
    }

    @Override
    public void execute(CommandSender s, String[] arg) {
        if (!(s instanceof Player)) return;
        Player p = (Player) s;
        if (!BorderSupport.getInstance().inSkyBlockWorld(p)) {
            p.sendMessage(LanguageEnum.COMMAND_AVAILABLE_ON_ISLAND.getString(p.getUniqueId()));
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
            p.sendMessage(LanguageEnum.NO_PERMISSION_COLOR.getString(p.getUniqueId()));
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
        p.sendMessage(LanguageEnum.COMMAND_USAGE_WRONG.getString(p.getUniqueId()));
        BambooUtils.sendTextComponent(p, LanguageEnum.COMMAND_USAGE_EXAMPLE.getString(p.getUniqueId()).replace("[command]", LanguageEnum.COMMAND_USAGE_COLOR.getString(p.getUniqueId())), "/isborder color ", LanguageEnum.COMMAND_CLICK_TO_SUGGEST.getString(p.getUniqueId()), Action.SUGGEST_COMMAND);
        for (String s : Arrays.asList("Red", "Green", "Blue")) BambooUtils.sendTextComponent(p, LanguageEnum.COMMAND_USAGE_CLICK.getString(p.getUniqueId()).replace("[name]", Languages.getInstance().getLocaleFiles().get(Languages.getInstance().getPlayerLocale().get(p.getUniqueId())).getString("Color." + s)), "/isborder color " + s, LanguageEnum.COMMAND_CLICK_TO_RUN.getString(p.getUniqueId()), Action.RUN_COMMAND);
    }
}
