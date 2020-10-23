package org.dragonetmc.hydra.team;

import org.atteo.classindex.IndexAnnotated;

@IndexAnnotated
public @interface Teams {
    int min();

    int max();

    int amount();
}
