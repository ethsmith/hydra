package org.dragonetmc.hydra.game;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.dragonetmc.hydra.GameManager;
import org.dragonetmc.hydra.level.Level;
import org.dragonetmc.hydra.team.ModeType;
import org.dragonetmc.hydra.team.Team;
import org.dragonetmc.hydra.util.AnnotationUtil;

@Getter
@Setter
public abstract class Game {

    private final String id;
    private int minimumPlayers;
    private int currentPlayers;
    private int maximumPlayers;

    public Game(Plugin plugin, String id, Level level) {
        this.id = id;
        GameManager.setPlugin(plugin);
        GameManager.setGame(this);
        GameManager.setLevel(level);

        ModeType mode = GameManager.getMode();
        int[] settings = AnnotationUtil.getModeSettings(this);
        if (mode == ModeType.PARTY) {
            if (!GameManager.createTeam()) {
                Bukkit.getServer().getLogger().severe("Could not create party for game");
            } else {
                minimumPlayers = settings[0];
                maximumPlayers = settings[1];
                Bukkit.getServer().getLogger().info("Created party for your game!");
            }
        } else if (mode == ModeType.SOLO) {
            if (!GameManager.createTeam())
                Bukkit.getServer().getLogger().severe("Could not create solo play for game");
            else
                Bukkit.getServer().getLogger().info("Created solo play your game!");
        } else if (mode == ModeType.TEAMS) {
            if (!GameManager.createTeams(settings[2])) {
                Bukkit.getServer().getLogger().severe("Could not create teams for game");
            } else {
                minimumPlayers = settings[0];
                maximumPlayers = settings[1];
                Bukkit.getServer().getLogger().info("Created teams for your game!");
            }
        } else if (mode == ModeType.FFA) {
            if (!GameManager.createTeams(settings[2])) {
                Bukkit.getServer().getLogger().severe("Could not create FFA play for game");
            } else {
                minimumPlayers = settings[0];
                maximumPlayers = settings[1];
                Bukkit.getServer().getLogger().info("Created FFA play for your game!");
            }
        }
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
            currentPlayers++;
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
