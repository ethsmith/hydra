package org.dragonetmc.hydra.scoreboard;

import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.dragonetmc.hydra.GameManager;
import org.dragonetmc.hydra.objective.KillObjective;
import org.dragonetmc.hydra.objective.Objective;

public class KillScoreboard extends Scoreboard {

    @Getter
    private KillObjective killObjective;

    public KillScoreboard() {
        super("Kills");
        checkForDeps();
    }

    public KillScoreboard(String name) {
        super(name);
        checkForDeps();
    }

    @Override
    public void show() {
        for (Player player : getKillObjective().getPlayers()) {
            BPlayerBoard board = Netherboard.instance().createBoard(player, getName());
            board.setAll(getKillObjective().getScoreboardStat().split(","));
        }
    }

    @Override
    public void hide() {
        for (Player player : getKillObjective().getPlayers())
            player.setScoreboard(GameManager.getPlugin().getServer().getScoreboardManager().getNewScoreboard());
    }

    private void checkForDeps() {
        boolean killObjectiveRegistered = false;
        for (Objective objective : GameManager.getObjectives()) {
            if (objective instanceof KillObjective) {
                killObjectiveRegistered = true;
                killObjective = (KillObjective) objective;
            }
        }

        if (!killObjectiveRegistered)
            throw new NullPointerException("You must have instantiated KillObjective");
    }
}
