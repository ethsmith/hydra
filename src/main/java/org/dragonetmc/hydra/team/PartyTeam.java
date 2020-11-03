package org.dragonetmc.hydra.team;

import org.bukkit.entity.Player;

public class PartyTeam extends Team {

    private final int min;
    private final int max;

    public PartyTeam(String name, int[] settings) {
        super(name);
        min = settings[0];
        max = settings[1];
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
}
