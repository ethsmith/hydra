package org.dragonetmc.hydra;

import org.dragonetmc.hydra.game.GameState;
import org.dragonetmc.hydra.level.Level;
import org.dragonetmc.hydra.objective.Objective;
import org.dragonetmc.hydra.team.*;
import org.dragonetmc.hydra.util.AnnotationUtil;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameManager {

    private static final Map<String, Method> gameStatesById = new HashMap<>();
    private static final Map<Integer, Method> gameStatesByPriority = new HashMap<>();
    private static final List<Team> teams = new LinkedList<>();
    private static final Set<Objective> objectives = new HashSet<>();
    private static String gameState = "initialize";
    private static Level level;

    private static final ScheduledExecutorService objectiveTracker = Executors.newScheduledThreadPool(1);

    public static String getGameState() {
        return gameState;
    }

    public static Map<String, Method> getGameStatesById() {
        return gameStatesById;
    }

    public static Map<Integer, Method> getGameStatesByPriority() {
        return gameStatesByPriority;
    }

    public static void setGameState(String gameState, Object... args) {
        try {
            setGameStateByIdentifier(gameState, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setGameState(int priority, Object... args) {
        try {
            setGameStateByIdentifier(priority, args);
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
        objectiveTracker.scheduleAtFixedRate((Runnable) objective::execute, 500, 500, TimeUnit.MILLISECONDS);
    }

    public static Level getLevel() {
        return level;
    }

    public static void setLevel(Level level) {
        GameManager.level = level;
    }

    public static boolean createTeam(String type) {
        if (type.equals("Party")) {
            List<Integer> conditions = AnnotationUtil.checkTeamConditions(Party.class);
            Team team = new PartyTeam(conditions);
            teams.add(team);
        } else if (type.equals("Solo")) {
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

    private static void setGameStateByIdentifier(Object identifier, Object... args) throws Exception {
        Method method;
        String gameState = "";
        int priority;

        if (identifier instanceof String) {
            gameState = (String) identifier;
            method = AnnotationUtil.findGameStateWithId(gameState);
        } else {
            priority = (int) identifier;
            method = AnnotationUtil.findGameStateWithPriority(priority);
        }

        if (method != null) {
            GameState gameStateAnnotation = method.getAnnotation(GameState.class);
            GameManager.gameState = gameStateAnnotation.id();
            GameManager.getGameStatesById().put(gameStateAnnotation.id(), method);

            method.invoke(GameManager.class, args);
        }
    }
}
