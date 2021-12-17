package com.github.zandy.islandborder.features;

import com.github.zandy.bamboolib.placeholder.PlaceholderManager;
import com.github.zandy.bamboolib.placeholder.utils.Placeholder;
import com.github.zandy.islandborder.files.languages.Languages.LanguageEnum;
import com.github.zandy.islandborder.player.PlayerData;
import org.bukkit.entity.Player;

import static com.github.zandy.islandborder.Main.getBorderSupport;
import static com.github.zandy.islandborder.Main.getPlayerEngine;
import static com.github.zandy.islandborder.files.languages.Languages.LanguageEnum.*;

public class Placeholders {

    public Placeholders() {
        new PlaceholderManager();
        PlaceholderManager.getInstance().setIdentifier("isborder");
        PlaceholderManager.getInstance().addPlaceholder(new Placeholder("status") {
            @Override
            public String request(Player p) {
                if (!getPlayerEngine().hasAccount(p.getUniqueId()) || !PlayerData.get(p.getUniqueId()).getBorderState()) return PLACEHOLDERS_STATE_DISABLED.getString(p.getUniqueId());
                return PLACEHOLDERS_STATE_ENABLED.getString(p.getUniqueId());
            }
        });
        PlaceholderManager.getInstance().addPlaceholder(new Placeholder("size") {
            @Override
            public String request(Player p) {
                return getBorderSupport().size(p);
            }
        });
        PlaceholderManager.getInstance().addPlaceholder(new Placeholder("color") {
            @Override
            public String request(Player p) {
                if (!PlayerData.isCached(p.getUniqueId())) return PLACEHOLDERS_COLOR_NONE.getString(p.getUniqueId());
                return LanguageEnum.valueOf("COLOR_" + PlayerData.get(p.getUniqueId()).getBorderColor()).getString(p.getUniqueId());
            }
        });
        PlaceholderManager.getInstance().addPlaceholder(new Placeholder("cooldown") {
            @Override
            public String request(Player p) {
                if (!PlayerData.isCached(p.getUniqueId())) return PLACEHOLDERS_COOLDOWN.getString(p.getUniqueId());
                int cooldown = PlayerData.get(p.getUniqueId()).getCooldownSeconds();
                return cooldown == 0 ? PLACEHOLDERS_COOLDOWN.getString(p.getUniqueId()) : String.valueOf(cooldown);
            }
        });
    }
}
