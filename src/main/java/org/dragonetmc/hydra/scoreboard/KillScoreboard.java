package org.dragonetmc.hydra.scoreboard;

import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.dragonetmc.hydra.GameManager;
import org.dragonetmc.hydra.events.EventCriteria;
import org.dragonetmc.hydra.objective.KillObjective;
import org.dragonetmc.hydra.objective.Objective;

public class KillScoreboard extends Scoreboard {

    @Getter
    private KillObjective killObjective;

    public KillScoreboard(EventCriteria criteria) {
        super("Kills");
        checkForDeps(criteria);
        show();
    }

    public KillScoreboard(String name, EventCriteria criteria) {
        super(name);
        checkForDeps(criteria);
        show();
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

    private void checkForDeps(EventCriteria criteria) {
        if (!(criteria.equals(EventCriteria.PLAYERS) || criteria.equals(EventCriteria.MOBS) || criteria.equals(EventCriteria.MOBS_AND_PLAYERS)))
            throw new IllegalArgumentException("Valid arguments for kill scoreboard: PLAYERS, MOBS, and MOBS_AND_PLAYERS");

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
