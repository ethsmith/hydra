package org.dragonetmc.hydra.team;

import org.bukkit.entity.Player;

public class SoloTeam extends Team {

    private String name = "solo-player";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
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

    @Override
    public int getSize() {
        return getPlayers().size();
    }
}
