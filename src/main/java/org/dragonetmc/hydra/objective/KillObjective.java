package org.dragonetmc.hydra.objective;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.dragonetmc.hydra.GameManager;
import org.dragonetmc.hydra.event.EventCriteria;
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

    @Getter
    private final EventCriteria criteria;

    public KillObjective(EventCriteria criteria, int killGoal) {
        this.killGoal = killGoal;
        this.criteria = criteria;

        if (!(criteria.equals(EventCriteria.PLAYERS) || criteria.equals(EventCriteria.MOBS) || criteria.equals(EventCriteria.MOBS_AND_PLAYERS)))
            throw new IllegalArgumentException("Valid arguments for kill scoreboard: PLAYERS, MOBS, and MOBS_AND_PLAYERS");

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
