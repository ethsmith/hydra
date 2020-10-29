package org.dragonetmc.hydra.team;

import org.bukkit.entity.Player;
import org.dragonetmc.hydra.GameManager;

public class PartyTeam extends Team {

    private final int min;
    private final int max;
    private String name = "team-" + GameManager.getTeams().size();

    public PartyTeam(int[] settings) {
        min = settings[0];
        max = settings[1];
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
