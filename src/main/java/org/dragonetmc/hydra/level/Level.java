package org.dragonetmc.hydra.level;

public abstract class Level {
    private String id;
    private Level source;
    private final boolean clone;

    public Level(String id) {
        this.id = id;
        this.clone = false;
    }

    public Level(String id, Level source) {
        this.id = id;
        this.source = source;
        this.clone = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isClone() {
        return clone;
    }

    public Level getSource() {
        return source;
    }

    public void setSource(Level source) {
        this.source = source;
    }

    public abstract void create();

    public abstract void destroy();
}
