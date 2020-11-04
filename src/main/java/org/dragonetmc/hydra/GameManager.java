package org.dragonetmc.hydra;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.Plugin;
import org.dragonetmc.hydra.annotation.AnnotationCache;
import org.dragonetmc.hydra.annotation.AnnotationScanner;
import org.dragonetmc.hydra.game.Game;
import org.dragonetmc.hydra.level.Level;
import org.dragonetmc.hydra.objective.Objective;
import org.dragonetmc.hydra.team.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class GameManager {

    @Getter
    @Setter
    private static Plugin plugin;
    @Getter
    @Setter
    private static Game game;
    @Getter
    @Setter
    private static Level level;

    private static ModeType mode;

    @Getter
    private static final List<Team> teams = new LinkedList<>();
    @Getter
    private static final Set<Objective> objectives = new HashSet<>();

    @Getter
    private static String gameState;

    @Getter
    @Setter
    private static int objectiveScanId;

    @Getter
    @Setter
    private static boolean scanningObjectives = false;
    @Getter
    @Setter
    private static boolean objectivesComplete = false;
    @Getter
    @Setter
    private static boolean objectivesCompleteStateChangeOn = true;
    @Getter
    @Setter
    private static boolean joinable = true;

    @Getter
    @Setter
    private static Object[] objectivesCompleteStateArgs = {};

    public static ModeType getMode() {
        if (mode == null)
            mode = AnnotationScanner.getModeType(game);
        return mode;
    }

    public static void setGameState(String state, Object... args) {
        if (AnnotationCache.getGameStateCache().isEmpty())
            AnnotationCache.setGameStateCache(AnnotationScanner.getGameStates(getGame(), true));

        for (Method method : AnnotationCache.getGameStateCache()) {
            if (method.getName().contains(state)) {
                gameState = state;
                try {
                    method.invoke(game, args);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                    plugin.getServer().getLogger().warning("Cause of error:\n");
                    e.getCause();
                }
            }
        }
    }

    public static void addObjective(Objective objective) {
        getObjectives().add(objective);

        if (!isScanningObjectives()) {
            setObjectiveScanId(plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, GameManager::checkObjectives, 10, 10));
            setScanningObjectives(true);
        }
    }

    public static boolean createTeam() {
        if (getMode().toString().equalsIgnoreCase("party")) {
            int[] settings = AnnotationScanner.getModeSettings(game);
            Team team = new PartyTeam("party", settings);
            teams.add(team);
        } else if (getMode().toString().equalsIgnoreCase("solo")) {
            Team team = new SoloTeam("solo");
            teams.add(team);
        }
        return !teams.isEmpty();
    }

    public static boolean createTeams(int amount) {
        for (int i = 0; i < amount; i++) {
            Team team = new StandardTeam("team-" + GameManager.getTeams().size());
            teams.add(team);
        }
        return !teams.isEmpty();
    }

    private static void checkObjectives() {
        int objectivesCompleted = 0;
        for (Objective objective : objectives)
            if (objective.isComplete()) objectivesCompleted++;

        if (objectivesCompleted == objectives.size()) {
            objectivesComplete = true;
            plugin.getServer().getScheduler().cancelTask(getObjectiveScanId());
            if (isObjectivesCompleteStateChangeOn())
                setGameState("results");
        }
    }
}
