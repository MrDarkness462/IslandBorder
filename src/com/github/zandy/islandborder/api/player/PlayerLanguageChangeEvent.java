package com.github.zandy.islandborder.api.player;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLanguageChangeEvent extends Event {
    private final Player p;
    private final String oldISO, newISO;

    public static final HandlerList handlers = new HandlerList();

    public PlayerLanguageChangeEvent(Player p, String oldISO, String newISO) {
        this.p = p;
        this.oldISO = oldISO;
        this.newISO = newISO;
    }

    public Player getPlayer() {
        return p;
    }

    public String getOldISO() {
        return oldISO;
    }

    public String getNewISO() {
        return newISO;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
