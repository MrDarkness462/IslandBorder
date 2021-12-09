package com.github.zandy.islandborder.features;

import com.github.zandy.islandborder.files.languages.Languages.LanguageEnum;
import com.github.zandy.islandborder.player.PlayerData;
import org.bukkit.entity.Player;
import org.magenpurp.api.placeholder.Placeholder;
import org.magenpurp.api.placeholder.PlaceholderManager;

import static com.github.zandy.islandborder.Main.getBorderSupport;
import static com.github.zandy.islandborder.Main.getPlayerEngine;
import static com.github.zandy.islandborder.files.languages.Languages.LanguageEnum.*;
import static org.magenpurp.api.placeholder.PlaceholderManager.addPlaceholder;
import static org.magenpurp.api.placeholder.PlaceholderManager.setIdentifier;

public class Placeholders {

    public Placeholders() {
        new PlaceholderManager();
        setIdentifier("isborder");
        addPlaceholder(new Placeholder("status") {
            @Override
            public String request(Player p) {
                if (!getPlayerEngine().hasAccount(p.getUniqueId()) || !PlayerData.get(p.getUniqueId()).getBorderState()) return PLACEHOLDERS_STATE_DISABLED.getString(p.getUniqueId());
                return PLACEHOLDERS_STATE_ENABLED.getString(p.getUniqueId());
            }
        });
        addPlaceholder(new Placeholder("size") {
            @Override
            public String request(Player p) {
                return getBorderSupport().size(p);
            }
        });
        addPlaceholder(new Placeholder("color") {
            @Override
            public String request(Player p) {
                if (!PlayerData.isCached(p.getUniqueId())) return PLACEHOLDERS_COLOR_NONE.getString(p.getUniqueId());
                return LanguageEnum.valueOf("COLOR_" + PlayerData.get(p.getUniqueId()).getBorderColor()).getString(p.getUniqueId());
            }
        });
        addPlaceholder(new Placeholder("cooldown") {
            @Override
            public String request(Player p) {
                if (!PlayerData.isCached(p.getUniqueId())) return PLACEHOLDERS_COOLDOWN.getString(p.getUniqueId());
                int cooldown = PlayerData.get(p.getUniqueId()).getCooldownSeconds();
                return cooldown == 0 ? PLACEHOLDERS_COOLDOWN.getString(p.getUniqueId()) : String.valueOf(cooldown);
            }
        });
    }
}
