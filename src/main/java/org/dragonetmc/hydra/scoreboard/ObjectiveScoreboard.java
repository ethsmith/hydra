package org.dragonetmc.hydra.scoreboard;

import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.dragonetmc.hydra.GameManager;
import org.dragonetmc.hydra.objective.Objective;
import org.dragonetmc.hydra.team.Team;

import java.util.Set;

public class ObjectiveScoreboard extends Scoreboard {

    @Getter
    private static boolean enabled = false;

    @Getter
    @Setter
    private ChatColor objectiveColor = ChatColor.GREEN;

    @Getter
    @Setter
    private ChatColor colonColor = ChatColor.GRAY;

    @Getter
    @Setter
    private ChatColor notCompleteColor = ChatColor.RED;

    @Getter
    @Setter
    private ChatColor completeColor = ChatColor.GREEN;

    public ObjectiveScoreboard() {
        super("Objectives");
        show();
        enabled = true;
    }

    public ObjectiveScoreboard(String name) {
        super(name);
        show();
        enabled = true;
    }

    @Override
    public void show() {
        for (Team team : GameManager.getTeams()) {
            for (Player player : team.getPlayers()) {
                BPlayerBoard board = Netherboard.instance().createBoard(player, getName());
                Set<Objective> objectives = GameManager.getObjectives();

                int i = 0;
                for (Objective objective : objectives) {
                    String status = notCompleteColor + "✗";
                    if (objective.isComplete())
                        status = completeColor + "✓";

                    String stat = "";
                    if (!objective.getScoreboardStat().isEmpty())
                        stat = objective.getScoreboardStat();

                    board.set(objectiveColor + objective.getName() + colonColor + ": " + stat + " " + status, i);
                    i++;
                }
            }
        }
    }

    @Override
    public void hide() {
        for (Team team : GameManager.getTeams()) {
            for (Player player : team.getPlayers()) {
                player.setScoreboard(GameManager.getPlugin().getServer().getScoreboardManager().getNewScoreboard());
            }
        }
    }
}
