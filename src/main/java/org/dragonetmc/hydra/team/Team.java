package org.dragonetmc.hydra.team;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public abstract class Team {

    private final Set<Player> players = new HashSet<>();

    public abstract String getName();

    public abstract void setName(String name);

    public abstract void join(Player player);

    public abstract void leave(Player player);

    public abstract int getSize();

    public Set<Player> getPlayers() {
        return players;
    }
}
