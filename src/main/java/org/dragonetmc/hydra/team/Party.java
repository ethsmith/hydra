package org.dragonetmc.hydra.team;

import org.atteo.classindex.IndexAnnotated;

@IndexAnnotated
public @interface Party {
    int min();

    int max();
}
