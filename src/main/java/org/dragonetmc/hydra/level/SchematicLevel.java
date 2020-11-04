package org.dragonetmc.hydra.level;

import lombok.Getter;
import org.bukkit.Location;
import org.dragonetmc.hydra.util.SchematicUtil;

public class SchematicLevel extends Level {

    @Getter
    private final Location location;

    public SchematicLevel(String id, Location location) {
        super(id);
        this.location = location;
    }

    @Override
    public void create() {
        SchematicUtil.pasteSchematic(getId(), location);
    }

    @Override
    public void destroy() {
        SchematicUtil.deleteSchematic(getId(), location);
    }
}
