package org.dragonetmc.hydra.level;

import lombok.Getter;
import org.bukkit.World;

public class SchematicLevel extends Level {

    @Getter
    private final World world;

    public SchematicLevel(String id, World world) {
        super(id);
        this.world = world;
    }

    @Override
    public void create() {
        // todo Implement schematic pasting
    }

    @Override
    public void destroy() {
    }
}
