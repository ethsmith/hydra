package org.dragonetmc.hydra.level;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.util.FileUtil;

import java.io.File;

public class WorldLevel extends Level {

    private World world;

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
}
