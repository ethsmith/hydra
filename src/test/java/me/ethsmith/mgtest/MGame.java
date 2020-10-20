package me.ethsmith.mgtest;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.dragonetmc.hydra.GameManager;
import org.dragonetmc.hydra.game.Game;
import org.dragonetmc.hydra.game.GameState;
import org.dragonetmc.hydra.level.Level;
import org.dragonetmc.hydra.level.WorldLevel;
import org.dragonetmc.hydra.team.Team;
import org.dragonetmc.hydra.team.Teams;

@Teams(min = 2, max = 4, amount = 2)
public class MGame extends Game {

    public MGame(Level level) {
        super(level);
    }

    @Override
    @GameState(id = "start", priority = 1)
    public void start() {
        // ...
        GameManager.setGameState("results");
        if (GameManager.getLevel() instanceof WorldLevel) {
            WorldLevel level = (WorldLevel) GameManager.getLevel();
            World world = level.getWorld();

            for (Team team : GameManager.getTeams()) {
                for (Player player : team.getPlayers()) {
                    player.teleport(world.getSpawnLocation());
                }
            }
        }
    }

    @Override
    @GameState(id = "results", priority = 2)
    public void results() {
        // ...
        GameManager.setGameState("end");
    }

    @Override
    @GameState(id = "end", priority = 3)
    public void end() {
        // ...
    }

    @Override
    @GameState(id = "cancel", priority = 4)
    public void cancel() {
        // ...
    }

    @Override
    public void join(Player player) {
        // ...
    }

    @Override
    public void leave(Player player) {
        // ...
    }
}
