package org.dragonetmc.hydra.util;

import org.atteo.classindex.ClassIndex;
import org.dragonetmc.hydra.game.GameStates;
import org.dragonetmc.hydra.team.FFA;
import org.dragonetmc.hydra.team.Party;
import org.dragonetmc.hydra.team.Solo;
import org.dragonetmc.hydra.team.Teams;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotationUtil {

    public static Method findGameStateWithId(String id) throws Exception {
        Map<String, Method> methods = findMethodsWithAnnotation(GameStates.class);

        for (String name : methods.keySet()) {
            if (name.equals(id))
                return methods.get(name);
        }
        return null;
    }

    public static String checkTeamType() {
        if (ClassIndex.getAnnotated(Party.class).iterator().hasNext())
            return "Party";
        else if (ClassIndex.getAnnotated(Teams.class).iterator().hasNext())
            return "Teams";
        else if (ClassIndex.getAnnotated(FFA.class).iterator().hasNext())
            return "FFA";
        else if (ClassIndex.getAnnotated(Solo.class).iterator().hasNext())
            return "Solo";
        else
            return "Unknown Type";
    }

    public static List<Integer> checkTeamConditions(Class<? extends Annotation> annotation) {
        for (Class<?> clazz : ClassIndex.getAnnotated(annotation)) {
            if (clazz.getAnnotation(annotation) instanceof Party) {
                Party annotate = (Party) clazz.getAnnotation(annotation);
                return Arrays.asList(annotate.min(), annotate.max());
            } else if (clazz.getAnnotation(annotation) instanceof Teams) {
                Teams annotate = (Teams) clazz.getAnnotation(annotation);
                return Arrays.asList(annotate.min(), annotate.max(), annotate.amount());
            }
        }
        return null;
    }

    private static Map<String, Method> findMethodsWithAnnotation(Class<? extends Annotation> annotation) throws NoSuchMethodException {
        Iterable<Class<?>> classes = ClassIndex.getAnnotated(annotation);
        Map<String, Method> states = new HashMap<>();

        for (Class<?> clazz : classes) {
            if (clazz.getAnnotation(annotation) instanceof GameStates) {
                GameStates gameStates = (GameStates) clazz.getAnnotation(annotation);
                for (String state : gameStates.states())
                    states.put(state, clazz.getMethod(state));
            }
        }
        return states;
    }
}
