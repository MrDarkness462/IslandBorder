package com.github.zandy.islandborder.features;

import com.github.zandy.bamboolib.placeholder.PlaceholderManager;
import com.github.zandy.bamboolib.placeholder.utils.Placeholder;
import com.github.zandy.islandborder.files.languages.Languages.LanguageEnum;
import com.github.zandy.islandborder.player.PlayerData;
import com.github.zandy.islandborder.player.PlayerEngine;
import com.github.zandy.islandborder.support.BorderSupport;
import org.bukkit.entity.Player;

public class Placeholders {

    public Placeholders() {
        new PlaceholderManager();
        PlaceholderManager.getInstance().setIdentifier("isborder");
        PlaceholderManager.getInstance().addPlaceholder(new Placeholder("status") {
            @Override
            public String request(Player p) {
                if (!PlayerEngine.getInstance().hasAccount(p.getUniqueId()) || !PlayerData.get(p.getUniqueId()).getBorderState()) return LanguageEnum.PLACEHOLDERS_STATE_DISABLED.getString(p.getUniqueId());
                return LanguageEnum.PLACEHOLDERS_STATE_ENABLED.getString(p.getUniqueId());
            }
        });
        PlaceholderManager.getInstance().addPlaceholder(new Placeholder("size") {
            @Override
            public String request(Player p) {
                return BorderSupport.getInstance().size(p);
            }
        });
        PlaceholderManager.getInstance().addPlaceholder(new Placeholder("color") {
            @Override
            public String request(Player p) {
                if (!PlayerData.isCached(p.getUniqueId())) return LanguageEnum.PLACEHOLDERS_COLOR_NONE.getString(p.getUniqueId());
                return LanguageEnum.valueOf("COLOR_" + PlayerData.get(p.getUniqueId()).getBorderColor()).getString(p.getUniqueId());
            }
        });
        PlaceholderManager.getInstance().addPlaceholder(new Placeholder("cooldown") {
            @Override
            public String request(Player p) {
                if (!PlayerData.isCached(p.getUniqueId())) return LanguageEnum.PLACEHOLDERS_COOLDOWN.getString(p.getUniqueId());
                int cooldown = PlayerData.get(p.getUniqueId()).getCooldownSeconds();
                return cooldown == 0 ? LanguageEnum.PLACEHOLDERS_COOLDOWN.getString(p.getUniqueId()) : String.valueOf(cooldown);
            }
        });
    }
}
