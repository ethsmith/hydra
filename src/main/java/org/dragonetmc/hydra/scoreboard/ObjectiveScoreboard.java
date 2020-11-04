package org.dragonetmc.hydra.scoreboard;

import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.dragonetmc.hydra.GameManager;
import org.dragonetmc.hydra.objective.Objective;
import org.dragonetmc.hydra.team.Team;

import java.util.Set;

public class ObjectiveScoreboard {

    private final String name;

    public ObjectiveScoreboard() {
        this.name = "Objectives";
        show();
    }

    public ObjectiveScoreboard(String name) {
        this.name = name;
        show();
    }

    public void show() {
        for (Team team : GameManager.getTeams()) {
            for (Player player : team.getPlayers()) {
                BPlayerBoard board = Netherboard.instance().createBoard(player, name);
                Set<Objective> objectives = GameManager.getObjectives();

                int i = 0;
                for (Objective objective : objectives) {
                    String status = ChatColor.RED + "✗";
                    if (objective.isComplete())
                        status = ChatColor.GREEN + "✓";

                    String stat = "";
                    if (!objective.getScoreboardStat().isEmpty())
                        stat = objective.getScoreboardStat();

                    board.set(objective.getName() + ": " + stat + " " + status, i);
                    i++;
                }
            }
        }
    }

    public void remove() {
        for (Team team : GameManager.getTeams()) {
            for (Player player : team.getPlayers()) {
                player.setScoreboard(GameManager.getPlugin().getServer().getScoreboardManager().getNewScoreboard());
            }
        }
    }
}
