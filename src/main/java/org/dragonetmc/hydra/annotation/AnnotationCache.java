package org.dragonetmc.hydra.annotation;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class AnnotationCache {

    @Getter
    @Setter
    private static Set<Method> gameStateCache = new HashSet<>();
}
