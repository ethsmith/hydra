package org.dragonetmc.hydra.game;

import org.atteo.classindex.IndexAnnotated;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@IndexAnnotated
public @interface GameStates {
    String[] states();
}
