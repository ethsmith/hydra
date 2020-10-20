package org.dragonetmc.hydra.team;

import org.bukkit.entity.Player;
import org.dragonetmc.hydra.GameManager;

public class StandardTeam extends Team {

    private String name = "team-" + GameManager.getTeams().size();

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
        getPlayers().add(player);
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
