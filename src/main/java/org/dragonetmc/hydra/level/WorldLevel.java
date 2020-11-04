package org.dragonetmc.hydra.level;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.dragonetmc.hydra.GameManager;
import org.dragonetmc.hydra.team.Team;

import java.io.File;
import java.util.Random;

@Getter
@Setter
public class WorldLevel extends Level {

    private World world;
    private Location lobby;

    public WorldLevel(String id) {
        super(id);
    }

    public WorldLevel(String id, Level source) {
        super(id, source);
    }

    @Override
    public void create() {
        if (isClone()) {
            world = Bukkit.createWorld(new WorldCreator(getId()).copy(GameManager.getPlugin().getServer().getWorld(getSource().getId())));
        } else {
            world = Bukkit.createWorld(new WorldCreator(getId()));
        }
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
            if (team.getSpawns().isEmpty()) {
                GameManager.getPlugin().getServer().getLogger().severe("No spawns set for team: " + team.getName());
                return;
            }

            if (random) {
                for (Player player : team.getPlayers()) {
                    int min = 0;
                    int max = team.getSpawns().size();
                    int index = rand.nextInt(max - min + 1) + min;
                    player.teleport(team.getSpawns().get(index - 1));
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
