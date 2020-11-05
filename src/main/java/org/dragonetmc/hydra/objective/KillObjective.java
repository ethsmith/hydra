package org.dragonetmc.hydra.objective;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.dragonetmc.hydra.GameManager;
import org.dragonetmc.hydra.team.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class KillObjective implements Objective {

    @Getter
    private final Map<Player, Integer> kills = new HashMap<>();

    @Getter
    @Setter
    private int killGoal;

    @Getter
    @Setter
    private boolean complete = false;

    public KillObjective(int killGoal) {
        this.killGoal = killGoal;

        for (Team team : GameManager.getTeams()) {
            for (Player player : team.getPlayers())
                getKills().put(player, 0);
        }
    }

    @Override
    public String getName() {
        return "Player Kills";
    }

    @Override
    public String getScoreboardStat() {
        StringBuilder scoreBoardStat = new StringBuilder();
        for (Player player : getPlayers()) {
            scoreBoardStat.append(ChatColor.GREEN).append(player.getName()).append(ChatColor.GRAY).append(": ").append(ChatColor.AQUA).append(getKills(player)).append(",");
        }
        return scoreBoardStat.toString();
    }

    public Player getWinner() {
        for (Player player : getPlayers()) {
            if (getKills(player) == getKillGoal())
                return player;
        }
        return null;
    }

    public int getKills(Player player) {
        return getKills().get(player);
    }

    public void setKills(Player player, int amount) {
        getKills().replace(player, amount);
    }

    public Set<Player> getPlayers() {
        return getKills().keySet();
    }

    @Override
    public void execute() {
        for (Player player : getPlayers()) {
            if (getKills(player) == getKillGoal())
                complete = true;
        }
    }
}
