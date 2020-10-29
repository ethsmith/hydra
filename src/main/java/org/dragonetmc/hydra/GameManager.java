package org.dragonetmc.hydra;

import org.dragonetmc.hydra.annotation.AnnotationCache;
import org.dragonetmc.hydra.annotation.AnnotationScanner;
import org.dragonetmc.hydra.game.Game;
import org.dragonetmc.hydra.level.Level;
import org.dragonetmc.hydra.objective.Objective;
import org.dragonetmc.hydra.team.*;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameManager {

    private static final List<Team> teams = new LinkedList<>();
    private static final Set<Objective> objectives = new HashSet<>();
    private static final ScheduledExecutorService objectiveTracker = Executors.newScheduledThreadPool(1);
    private static String gameState = "initialize";
    private static Level level;
    private static ModeType mode;
    private static boolean objectivesComplete;
    private static boolean changeStateOnObjectivesComplete = true;
    private static boolean joinable = true;
    private static String objectivesCompleteState = "results";
    private static Object[] objectivesCompleteStateArgs = null;

    public static String getGameState() {
        return gameState;
    }

    public static Set<Method> getGameStates() {
        return AnnotationCache.getGameStateCache();
    }

    public static void setGameState(String gameState, Object... args) {
        try {
            setGameStateByIdentifier(gameState, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Team> getTeams() {
        return teams;
    }

//    public static Set<Objective> getObjectives() {
//        return objectives;
//    }

    public static void addObjective(Objective objective) {
        objectives.add(objective);
        objectiveTracker.scheduleAtFixedRate(() -> {
            objective.execute();
            checkObjectives();
            if (objectivesComplete)
                if (changeStateOnObjectivesComplete)
                    if (objectivesCompleteStateArgs != null)
                        // The scheduling may be unnecessary, I will test and find out
                        objectiveTracker.schedule(() -> setGameState(objectivesCompleteState, objectivesCompleteStateArgs), 2, TimeUnit.SECONDS);
                    else
                        setGameState(objectivesCompleteState);
        }, 500, 500, TimeUnit.MILLISECONDS);
    }

    public static void changeStateOnObjectivesComplete(boolean changeState) {
        GameManager.changeStateOnObjectivesComplete = changeState;
    }

    public static boolean isJoinable() {
        return joinable;
    }

    public static void setJoinable(boolean joinable) {
        GameManager.joinable = joinable;
    }

    public static void setObjectivesCompleteState(String objectivesCompleteState, Object... args) {
        GameManager.objectivesCompleteState = objectivesCompleteState;
        if (args != null && args.length > 0)
            objectivesCompleteStateArgs = args;
    }

    public static void setObjectivesCompleteStateArgs(Object[] objectivesCompleteStateArgs) {
        GameManager.objectivesCompleteStateArgs = objectivesCompleteStateArgs;
    }

    public static Level getLevel() {
        return level;
    }

    public static void setLevel(Level level) {
        GameManager.level = level;
    }

    public static ModeType getMode() {
        return mode;
    }

    public static void setMode(ModeType mode) {
        GameManager.mode = mode;
    }

    public static boolean isObjectivesComplete() {
        return objectivesComplete;
    }

    public static void setObjectivesComplete(boolean objectivesComplete) {
        GameManager.objectivesComplete = objectivesComplete;
    }

    public static boolean createTeam(Game game) {
        if (getMode().toString().equalsIgnoreCase("party")) {
            int[] settings = AnnotationScanner.getModeSettings(game);
            Team team = new PartyTeam(settings);
            teams.add(team);
        } else if (getMode().toString().equalsIgnoreCase("solo")) {
            Team team = new SoloTeam();
            teams.add(team);
        }
        return teams.size() != 0;
    }

    public static boolean createTeams(int amount) {
        for (int i = 0; i < amount; i++) {
            Team team = new StandardTeam();
            teams.add(team);
        }
        return teams.size() != 0;
    }

    private static void setGameStateByIdentifier(String identifier, Object... args) throws Exception {
        for (Method method : AnnotationCache.getGameStateCache()) {
            if (method.getName().contains(identifier)) {
                GameManager.gameState = identifier;
                method.invoke(GameManager.class, args);
            }
        }
    }

    private static void checkObjectives() {
        int objectivesCompleted = 0;
        for (Objective objective : objectives)
            if (objective.isComplete()) objectivesCompleted++;

        if (objectivesCompleted == objectives.size())
            objectivesComplete = true;
    }
}
