package org.dragonetmc.hydra.annotation;

import org.dragonetmc.hydra.GameManager;
import org.dragonetmc.hydra.game.Game;
import org.dragonetmc.hydra.game.GameState;
import org.dragonetmc.hydra.team.Mode;
import org.dragonetmc.hydra.team.ModeType;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class AnnotationScanner {

    // Do not use this if the cache is filled
    public static Set<Method> getGameStates(Game game, boolean updateCache) {
        Set<Method> methods = new HashSet<>();

        for (Method method : game.getClass().getDeclaredMethods()) {
            GameState gameState = method.getAnnotation(GameState.class);
            if (gameState != null)
                methods.add(method);
        }

        if (updateCache)
            AnnotationCache.setGameStateCache(methods);

        return methods;
    }

    public static ModeType getModeType(Game game) {
        ModeType type = ModeType.FFA;
        Mode mode = getMode(game);

        if (mode != null) {
            type = mode.value();
            if (GameManager.getMode() == null || GameManager.getMode() != type)
                GameManager.setMode(type);
        }
        return type;
    }

    public static int[] getModeSettings(Game game) {
        Mode mode = getMode(game);
        ModeType type = mode.value();

        if (type == ModeType.FFA || type == ModeType.TEAMS)
            return new int[]{mode.min(), mode.max(), mode.amount()};
        else if (type == ModeType.PARTY)
            return new int[]{mode.min(), mode.max()};
        else
            return new int[]{};
    }

    private static Mode getMode(Game game) {
        return game.getClass().getAnnotation(Mode.class);
    }
}
