package org.dragonetmc.hydra.team;

import org.bukkit.entity.Player;

public class StandardTeam extends Team {

    public StandardTeam(String name) {
        super(name);
    }

    @Override
    public void join(Player player) {
        getPlayers().add(player);
    }

    @Override
    public void leave(Player player) {
        getPlayers().remove(player);
    }
}
