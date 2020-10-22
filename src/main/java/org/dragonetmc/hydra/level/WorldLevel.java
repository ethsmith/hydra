package org.dragonetmc.hydra.level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.util.FileUtil;
import org.dragonetmc.hydra.GameManager;
import org.dragonetmc.hydra.team.Team;

import java.io.File;
import java.util.Random;

public class WorldLevel extends Level {

    private World world;
    private Location lobby;

    public WorldLevel(String id) {
        super(id);
    }

    public WorldLevel(String id, Level source) {
        super(id, source);
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void create() {
        new Thread(() -> {
            if (isClone()) {
                File from = Bukkit.getWorld(getSource().getId()).getWorldFolder();
                File f = new File(Bukkit.getWorldContainer().getAbsolutePath(), getId());

                if (f.exists()) {
                    world = Bukkit.getWorld(getId());
                    return;
                }

                FileUtil.copy(from, f);
                new File(f.getAbsolutePath(), "uid.dat").delete();
            } else
                Bukkit.createWorld(WorldCreator.name(getId()));
        }).start();
    }

    @Override
    public void destroy() {
        File worldFolder = world.getWorldFolder();
        String[] files = worldFolder.list();
        Bukkit.unloadWorld(getId(), false);

        if (files != null) {
            for (String file : files) {
                File current = new File(worldFolder.getPath(), file);
                current.delete();
            }
        }
    }

    public void teleport(boolean random) {
        Random rand = new Random();
        for (Team team : GameManager.getTeams()) {
            if (random) {
                for (Player player : team.getPlayers()) {
                    int min = 0;
                    int max = team.getSpawns().size();
                    int index = rand.nextInt(max - min + 1) + min;
                    player.teleport(team.getSpawns().get(index));
                }
            } else {
                int i = 0;
                for (Player player : team.getPlayers()) {
                    if (i == team.getSpawns().size()) i = 0;
                    player.teleport(team.getSpawns().get(i));
                    i++;
                }
            }
        }
    }
}
