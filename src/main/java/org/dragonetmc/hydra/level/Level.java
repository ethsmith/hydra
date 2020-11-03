package org.dragonetmc.hydra.level;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Level {
    private final boolean clone;
    private String id;
    private Level source;

    public Level(String id) {
        this.id = id;
        this.clone = false;
    }

    public Level(String id, Level source) {
        this.id = id;
        this.source = source;
        this.clone = true;
    }

    public abstract void create();

    public abstract void destroy();
}
