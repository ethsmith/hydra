package org.dragonetmc.hydra.scoreboard;

import lombok.Getter;

public abstract class Scoreboard {

    @Getter
    private final String name;

    public Scoreboard(String name) {
        this.name = name;
    }

    public abstract void show();

    public abstract void hide();
}
