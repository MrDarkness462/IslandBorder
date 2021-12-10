package com.github.zandy.islandborder.api.island;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class IslandEnterEvent extends Event {
    private final Player p;

    public static final HandlerList handlers = new HandlerList();

    public IslandEnterEvent(Player p) {
        this.p = p;
    }

    public Player getPlayer() {
        return p;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
