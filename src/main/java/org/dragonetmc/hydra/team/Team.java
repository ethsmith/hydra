package org.dragonetmc.hydra.team;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Getter
public abstract class Team {

    @Setter
    private String name;
    private final Set<Player> players = new HashSet<>();
    private final List<Location> spawns = new LinkedList<>();

    public Team(String name) {
        this.name = name;
    }

    public abstract void join(Player player);

    public abstract void leave(Player player);

    public int getSize() {
        return players.size();
    }
}
