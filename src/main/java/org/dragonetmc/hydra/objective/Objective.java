package org.dragonetmc.hydra.objective;

public interface Objective {

    String getName();

    boolean isComplete();

    void setComplete(boolean complete);

    String getScoreboardStat();

    void execute();
}
