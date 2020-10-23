package org.dragonetmc.hydra.game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.dragonetmc.hydra.GameManager;
import org.dragonetmc.hydra.level.Level;
import org.dragonetmc.hydra.team.*;
import org.dragonetmc.hydra.util.AnnotationUtil;

import java.util.List;

public abstract class Game {

    private final String id;
    private int minimumPlayers;
    private int currentPlayers;
    private int maximumPlayers;

    public Game(String main, String id, Level level) {
        this.id = id;
        GameManager.setMainPackage(main);
        GameManager.setLevel(level);

        if (AnnotationUtil.checkTeamType(Party.class)) {
            if (!GameManager.createTeam("Party"))
                Bukkit.getServer().getLogger().severe("Could not create party for game");
            else
                Bukkit.getServer().getLogger().info("Created party for your game!");
        } else if (AnnotationUtil.checkTeamType(Solo.class)) {
            if (!GameManager.createTeam("Solo"))
                Bukkit.getServer().getLogger().severe("Could not create solo play for game");
            else
                Bukkit.getServer().getLogger().info("Created solo play your game!");
        } else if (AnnotationUtil.checkTeamType(Teams.class)) {
            List<Integer> conditions = AnnotationUtil.checkTeamConditions(Teams.class);
            if (!GameManager.createTeams(conditions.get(2))) {
                minimumPlayers = conditions.get(0);
                maximumPlayers = conditions.get(1);
                Bukkit.getServer().getLogger().severe("Could not create teams for game");
            } else {
                Bukkit.getServer().getLogger().info("Created teams for your game!");
            }
        } else if (AnnotationUtil.checkTeamType(FFA.class)) {
            List<Integer> conditions = AnnotationUtil.checkTeamConditions(FFA.class);
            if (!GameManager.createTeams(conditions.get(2))) {
                minimumPlayers = conditions.get(0);
                maximumPlayers = conditions.get(1);
                Bukkit.getServer().getLogger().severe("Could not create FFA play for game");
            } else {
                Bukkit.getServer().getLogger().info("Created FFA play for your game!");
            }
        }
    }

    public String getId() {
        return id;
    }

    public int getMinimumPlayers() {
        return minimumPlayers;
    }

    public void setMinimumPlayers(int minimumPlayers) {
        this.minimumPlayers = minimumPlayers;
    }

    public int getCurrentPlayers() {
        return currentPlayers;
    }

    public void setCurrentPlayers(int currentPlayers) {
        this.currentPlayers = currentPlayers;
    }

    public int getMaximumPlayers() {
        return maximumPlayers;
    }

    public void setMaximumPlayers(int maximumPlayers) {
        this.maximumPlayers = maximumPlayers;
    }

    @GameState(id = "initialize", priority = -1)
    public final void initialize() {

    }

    @GameState(id = "finish", priority = 2147483647)
    public final void finish() {

    }

    public void join(Player player) {
        int index = 0;

        for (int i = 0; i < GameManager.getTeams().size(); i++) {
            Team team = GameManager.getTeams().get(i);
            if (team.getPlayers().size() < GameManager.getTeams().get(index).getPlayers().size())
                index = i;
        }
        GameManager.getTeams().get(index).join(player);
    }

    public void leave(Player player) {
        for (Team team : GameManager.getTeams())
            if (team.getPlayers().contains(player)) team.leave(player);
    }

    public abstract void init();

    public abstract void start();

    public abstract void results();

    public abstract void end();

    public abstract void cancel();
}
