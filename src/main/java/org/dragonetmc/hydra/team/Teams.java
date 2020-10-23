package org.dragonetmc.hydra.team;

import org.atteo.classindex.IndexAnnotated;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@IndexAnnotated
public @interface Teams {
    int min();

    int max();

    int amount();
}
