package org.dragonetmc.hydra.util;

import org.dragonetmc.hydra.game.GameState;
import org.dragonetmc.hydra.team.Party;
import org.dragonetmc.hydra.team.Teams;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class AnnotationUtil {

    public static Method findGameStateWithId(String id) throws Exception {
        Set<Method> methods = findMethodsWithAnnotation(GameState.class);

        for (Method m : methods) {
            GameState state = m.getAnnotation(GameState.class);
            if (state.id().equals(id))
                return m;
        }
        return null;
    }

    public static Method findGameStateWithPriority(int priority) throws Exception {
        Set<Method> methods = findMethodsWithAnnotation(GameState.class);

        for (Method m : methods) {
            GameState state = m.getAnnotation(GameState.class);
            if (state.priority() == priority)
                return m;
        }
        return null;
    }

    public static boolean checkTeamType(Class<? extends Annotation> annotation) {
        Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forJavaClassPath()).setScanners(new TypeAnnotationsScanner()));
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(annotation);

        return !classes.isEmpty();
    }

    public static List<Integer> checkTeamConditions(Class<? extends Annotation> annotation) {
        Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forJavaClassPath()).setScanners(new TypeAnnotationsScanner()));
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(annotation);

        for (Class<?> clazz : classes) {
            if (clazz.getAnnotation(annotation) instanceof Party) {
                Party anno = (Party) clazz.getAnnotation(annotation);
                return Arrays.asList(anno.min(), anno.max());
            } else if (clazz.getAnnotation(annotation) instanceof Teams) {
                Teams anno = (Teams) clazz.getAnnotation(annotation);
                return Arrays.asList(anno.min(), anno.max());
            }
        }
        return null;
    }

    private static Set<Method> findMethodsWithAnnotation(Class<? extends Annotation> annotation) {
        Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forJavaClassPath()).setScanners(new MethodAnnotationsScanner()));
        return reflections.getMethodsAnnotatedWith(annotation);
    }
}
