package com.github.zandy.islandborder.commands.subcommands;

import com.github.zandy.bamboolib.command.SubCommand;
import com.github.zandy.bamboolib.database.Database;
import com.github.zandy.bamboolib.utils.BambooUtils;
import com.github.zandy.islandborder.api.player.PlayerLanguageChangeEvent;
import com.github.zandy.islandborder.files.languages.Languages;
import com.github.zandy.islandborder.files.languages.Languages.LanguageEnum;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class LanguageSubCommand extends SubCommand {

    public LanguageSubCommand() {
        super("language", LanguageEnum.INFO_SUBCOMMAND_LANGUAGE.getString(), new String[]{"isborder.language", "isborder.player", "isborder.*"});
    }

    @Override
    public void execute(CommandSender s, String[] arg) {
        if (!(s instanceof Player)) return;
        Player p = (Player) s;
        UUID uuid = p.getUniqueId();
        if (arg.length == 0) {
            sendUsage(p);
            return;
        }
        String argument = arg[0].toUpperCase();
        if (!Languages.getInstance().getLanguageAbbreviations().contains(argument)) {
            p.sendMessage(LanguageEnum.LANGUAGE_NOT_FOUND.getString(uuid));
            for (String lang : Languages.getInstance().getLanguageAbbreviations()) BambooUtils.sendTextComponent(p, LanguageEnum.LANGUAGE_LIST_FORMAT.getString(uuid).replace("[languageAbbreviation]", lang).replace("[languageName]", Languages.getInstance().getLocaleFiles().get(lang).getString(LanguageEnum.LANGUAGE_DISPLAY.getPath())), "/isborder language " + lang, LanguageEnum.COMMAND_CLICK_TO_RUN.getString(uuid), Action.RUN_COMMAND);
            return;
        }
        String oldISO = Languages.getInstance().getPlayerLocale().get(uuid);
        Languages.getInstance().getPlayerLocale().put(uuid, argument);
        Database.getInstance().setString(uuid, argument, "Language", "Island-Border");
        p.sendMessage(LanguageEnum.LANGUAGE_CHANGED.getString(uuid).replace("[languageName]", LanguageEnum.LANGUAGE_DISPLAY.getString(uuid)).replace("[languageAbbreviation]", argument));
        Bukkit.getPluginManager().callEvent(new PlayerLanguageChangeEvent(p, oldISO, argument));
    }

    private void sendUsage(Player p) {
        p.sendMessage(" ");
        p.sendMessage(" ");
        p.sendMessage(LanguageEnum.COMMAND_USAGE_WRONG.getString(p.getUniqueId()));
        p.sendMessage(LanguageEnum.LANGUAGE_AVAILABLE.getString(p.getUniqueId()));
        for (String lang : Languages.getInstance().getLanguageAbbreviations()) BambooUtils.sendTextComponent(p, LanguageEnum.LANGUAGE_LIST_FORMAT.getString(p.getUniqueId()).replace("[languageAbbreviation]", lang).replace("[languageName]", Languages.getInstance().getLocaleFiles().get(lang).getString(LanguageEnum.LANGUAGE_DISPLAY.getPath())), "/isborder language " + lang, LanguageEnum.COMMAND_CLICK_TO_RUN.getString(p.getUniqueId()), Action.RUN_COMMAND);
    }

    @Override
    public boolean canSee(CommandSender s) {
        return hasPermission(s);
    }
}
