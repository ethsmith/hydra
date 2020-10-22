package org.dragonetmc.hydra.team;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public abstract class Team {

    private final Set<Player> players = new HashSet<>();
    private final List<Location> spawns = new LinkedList<>();

    public abstract String getName();

    public abstract void setName(String name);

    public abstract void join(Player player);

    public abstract void leave(Player player);

    public abstract int getSize();

    public Set<Player> getPlayers() {
        return players;
    }

    public List<Location> getSpawns() {
        return spawns;
    }
}
