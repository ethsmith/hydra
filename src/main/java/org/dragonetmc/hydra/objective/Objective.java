package org.dragonetmc.hydra.objective;

public interface Objective {

    String getName();

    boolean isComplete();

    boolean setComplete();

    void execute();
}
