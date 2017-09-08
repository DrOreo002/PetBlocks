package com.github.shynixn.petblocks.business.bukkit.nms.v1_9_R1;

import com.github.shynixn.petblocks.lib.SimpleListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.plugin.Plugin;

import java.util.List;

public final class Listener19 extends SimpleListener {
    private final List<Player> carryingPet;

    public Listener19(List<Player> carryingPet, Plugin plugin) {
        super(plugin);
        this.carryingPet = carryingPet;
    }

    @EventHandler
    public void onPlayerSwapItems(PlayerSwapHandItemsEvent event) {
        if (this.carryingPet.contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }
}