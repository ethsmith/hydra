package org.dragonetmc.hydra.level;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import org.bukkit.World;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SchematicLevel extends Level {

    private final World world;

    public SchematicLevel(String id, World world) {
        super(id);
        this.world = world;
    }

    @Override
    public void create() {
        // todo Probably needs reflection for versions
//        File file = new File("plugins/WorldEdit/schematics/" + getId() + ".schematic");
//        boolean allowUndo = false;
//        Clipboard clipboard;
//
//        ClipboardFormat format = ClipboardFormats.findByFile(file);
//        try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
//            clipboard = reader.read();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            com.sk89q.worldedit.world.World adaptedWorld = BukkitAdapter.adapt(world);
//            EditSession editSession = WorldEdit.getInstance().newEditSessionBuilder().;
//        }
    }

    @Override
    public void destroy() {
    }
}
