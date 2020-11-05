package org.dragonetmc.hydra.scoreboard;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.dragonetmc.hydra.GameManager;

public abstract class Scoreboard {

    @Getter
    private final String name;

    @Getter
    private boolean autoUpdate = false;

    @Getter
    @Setter
    private int updateInterval = 10;

    private BukkitTask updateTask = null;

    public Scoreboard(String name) {
        this.name = name;
        setAutoUpdate(true);
    }

    public abstract void show();

    public abstract void hide();

    public void setAutoUpdate(boolean autoUpdate) {
        this.autoUpdate = autoUpdate;
        Plugin plugin = GameManager.getPlugin();

        if (updateTask != null)
            updateTask.cancel();

        if (autoUpdate)
            updateTask = plugin.getServer().getScheduler().runTaskTimer(plugin, this::show, updateInterval, updateInterval);
    }
}
