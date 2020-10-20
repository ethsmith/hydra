package org.dragonetmc.hydra.game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.dragonetmc.hydra.GameManager;
import org.dragonetmc.hydra.level.Level;
import org.dragonetmc.hydra.team.Party;
import org.dragonetmc.hydra.team.Solo;
import org.dragonetmc.hydra.team.Teams;
import org.dragonetmc.hydra.util.AnnotationUtil;

public abstract class Game {

    public Game(Level level) {
        GameManager.setLevel(level);

        if (AnnotationUtil.checkTeamType(Party.class))
            if (!GameManager.createTeam("Party"))
                Bukkit.getServer().getLogger().severe("Could not create party for game");
        else if (AnnotationUtil.checkTeamType(Solo.class))
            if (!GameManager.createTeam("Solo"))
                Bukkit.getServer().getLogger().severe("Could not create solo play for game");
        else if (AnnotationUtil.checkTeamType(Teams.class))
            if (!GameManager.createTeams(AnnotationUtil.checkTeamConditions(Teams.class).get(2)))
                Bukkit.getServer().getLogger().severe("Could not create teams for game");
    }

    @GameState(id = "initialize", priority = -1)
    public final void init() {

    }

    @GameState(id = "finish", priority = 2147483647)
    public final void finish() {

    }

    public abstract void start();

    public abstract void results();

    public abstract void end();

    public abstract void cancel();

    public abstract void join(Player player);

    public abstract void leave(Player player);
}
