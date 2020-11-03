package org.dragonetmc.hydra.team;

import org.bukkit.entity.Player;

public class SoloTeam extends Team {

    public SoloTeam(String name) {
        super(name);
    }

    @Override
    public void join(Player player) {
        if (getSize() < 1) {
            getPlayers().add(player);
        }
    }

    @Override
    public void leave(Player player) {
        getPlayers().remove(player);
    }
}
