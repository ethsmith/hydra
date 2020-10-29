package org.dragonetmc.hydra.annotation;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class AnnotationCache {

    private static Set<Method> gameStateCache = new HashSet<>();

    public static Set<Method> getGameStateCache() {
        return gameStateCache;
    }

    public static void setGameStateCache(Set<Method> gameStateCache) {
        AnnotationCache.gameStateCache = gameStateCache;
    }
}
