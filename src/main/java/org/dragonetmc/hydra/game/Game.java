package org.dragonetmc.hydra.game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.dragonetmc.hydra.GameManager;
import org.dragonetmc.hydra.annotation.AnnotationScanner;
import org.dragonetmc.hydra.level.Level;
import org.dragonetmc.hydra.team.ModeType;
import org.dragonetmc.hydra.team.Team;

public abstract class Game {

    private final String id;
    private int minimumPlayers;
    private int currentPlayers;
    private int maximumPlayers;

    public Game(String id, Level level) {
        this.id = id;
        GameManager.setLevel(level);

        if (GameManager.getMode() == ModeType.PARTY) {
            if (!GameManager.createTeam(this))
                Bukkit.getServer().getLogger().severe("Could not create party for game");
            else
                Bukkit.getServer().getLogger().info("Created party for your game!");
        } else if (GameManager.getMode() == ModeType.SOLO) {
            if (!GameManager.createTeam(this))
                Bukkit.getServer().getLogger().severe("Could not create solo play for game");
            else
                Bukkit.getServer().getLogger().info("Created solo play your game!");
        } else if (GameManager.getMode() == ModeType.TEAMS) {
            int[] settings = AnnotationScanner.getModeSettings(this);
            if (!GameManager.createTeams(settings[2])) {
                minimumPlayers = settings[0];
                maximumPlayers = settings[1];
                Bukkit.getServer().getLogger().severe("Could not create teams for game");
            } else {
                Bukkit.getServer().getLogger().info("Created teams for your game!");
            }
        } else if (GameManager.getMode() == ModeType.FFA) {
            int[] settings = AnnotationScanner.getModeSettings(this);
            if (!GameManager.createTeams(settings[2])) {
                minimumPlayers = settings[0];
                maximumPlayers = settings[1];
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


    public final void initialize() {

    }

    public final void finish() {

    }

    public void join(Player player) {
        if (GameManager.isJoinable()) {
            int index = 0;

            for (int i = 0; i < GameManager.getTeams().size(); i++) {
                Team team = GameManager.getTeams().get(i);
                if (team.getPlayers().size() < GameManager.getTeams().get(index).getPlayers().size())
                    index = i;
            }
            GameManager.getTeams().get(index).join(player);
        } else {
            player.kickPlayer("This lobby is not joinable!");
        }
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
