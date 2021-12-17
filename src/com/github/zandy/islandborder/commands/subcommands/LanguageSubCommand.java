package com.github.zandy.islandborder.commands.subcommands;

import com.github.zandy.bamboolib.command.SubCommand;
import com.github.zandy.bamboolib.database.Database;
import com.github.zandy.bamboolib.utils.BambooUtils;
import com.github.zandy.islandborder.api.player.PlayerLanguageChangeEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

import static com.github.zandy.islandborder.files.languages.Languages.*;
import static com.github.zandy.islandborder.files.languages.Languages.LanguageEnum.*;
import static net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND;
import static org.bukkit.Bukkit.getPluginManager;

public class LanguageSubCommand extends SubCommand {

    public LanguageSubCommand() {
        super("language", INFO_SUBCOMMAND_LANGUAGE.getString(), new String[]{"isborder.language", "isborder.*"});
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
        if (!getLanguageAbbreviations().contains(argument)) {
            p.sendMessage(LANGUAGE_NOT_FOUND.getString(uuid));
            for (String lang : getLanguageAbbreviations()) BambooUtils.sendTextComponent(p, LANGUAGE_LIST_FORMAT.getString(uuid).replace("[languageAbbreviation]", lang).replace("[languageName]", getLocaleFiles().get(lang).getString(LANGUAGE_DISPLAY.getPath())), "/isborder language " + lang, COMMAND_CLICK_TO_RUN.getString(uuid), RUN_COMMAND);
            return;
        }
        String oldISO = getPlayerLocale().get(uuid);
        getPlayerLocale().put(uuid, argument);
        Database.getInstance().setString(uuid, argument, "Language", "Island-Border");
        p.sendMessage(LANGUAGE_CHANGED.getString(uuid).replace("[languageName]", LANGUAGE_DISPLAY.getString(uuid)).replace("[languageAbbreviation]", argument));
        getPluginManager().callEvent(new PlayerLanguageChangeEvent(p, oldISO, argument));
    }

    private void sendUsage(Player p) {
        p.sendMessage(" ");
        p.sendMessage(" ");
        p.sendMessage(COMMAND_USAGE_WRONG.getString(p.getUniqueId()));
        p.sendMessage(LANGUAGE_AVAILABLE.getString(p.getUniqueId()));
        for (String lang : getLanguageAbbreviations()) BambooUtils.sendTextComponent(p, LANGUAGE_LIST_FORMAT.getString(p.getUniqueId()).replace("[languageAbbreviation]", lang).replace("[languageName]", getLocaleFiles().get(lang).getString(LANGUAGE_DISPLAY.getPath())), "/isborder language " + lang, COMMAND_CLICK_TO_RUN.getString(p.getUniqueId()), RUN_COMMAND);
    }

    @Override
    public boolean canSee(CommandSender s) {
        return hasPermission(s);
    }
}
