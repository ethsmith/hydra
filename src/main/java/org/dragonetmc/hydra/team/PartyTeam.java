package org.dragonetmc.hydra.team;

import org.bukkit.entity.Player;
import org.dragonetmc.hydra.GameManager;

import java.util.List;

public class PartyTeam extends Team {

    private final int min;
    private final int max;
    private String name = "team-" + GameManager.getTeams().size();

    public PartyTeam(List<Integer> conditions) {
        min = conditions.get(0);
        max = conditions.get(1);
    }

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
        if (getSize() < max)
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
