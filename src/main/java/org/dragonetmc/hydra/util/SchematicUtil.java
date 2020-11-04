package org.dragonetmc.hydra.util;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldedit.world.block.BlockTypes;
import lombok.Getter;
import org.bukkit.Location;
import org.dragonetmc.hydra.GameManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SchematicUtil {

    @Getter
    private static final Map<String, BlockVector3> schematics = new HashMap<>();

    public static void pasteSchematic(String name, Location location) {
        try {
            com.sk89q.worldedit.world.World adaptedWorld = BukkitAdapter.adapt(location.getWorld());
            EditSession editSession = WorldEdit.getInstance().newEditSessionBuilder().world(adaptedWorld).maxBlocks(-1).build();
            BlockVector3 blockVector3 = BlockVector3.at(location.getX(), location.getY(), location.getZ());

            Operation operation = new ClipboardHolder(loadSchematic(name))
                    .createPaste(editSession)
                    .to(blockVector3)
                    .ignoreAirBlocks(true)
                    .build();

            Operations.complete(operation);
            editSession.close();
            schematics.put(name, blockVector3);
        } catch (WorldEditException e) {
            e.printStackTrace();
        }
    }

    public static void deleteSchematic(String name, Location location) {
        BlockVector3 blockVector3 = SchematicUtil.getSchematics().get(name);
        com.sk89q.worldedit.world.World world = BukkitAdapter.adapt(location.getWorld());
        CuboidRegion selection = new CuboidRegion(world, blockVector3.getMinimum(blockVector3), blockVector3.getMaximum(blockVector3));

        try {
            EditSession editSession = WorldEdit.getInstance().newEditSessionBuilder().world(world).maxBlocks(-1).build();
            BlockType air = BlockTypes.AIR;
            editSession.setBlocks(selection, air.getDefaultState());
        } catch (MaxChangedBlocksException e) {
            e.printStackTrace();
        }
    }

    private static Clipboard loadSchematic(String name) {
        File schematic = new File(GameManager.getPlugin().getDataFolder().getAbsolutePath() + "/schematics/" + name + ".schematic");

        ClipboardFormat format = ClipboardFormats.findByFile(schematic);
        ClipboardReader reader = null;
        Clipboard clipboard = null;

        try {
            reader = format.getReader(new FileInputStream(schematic));
            clipboard = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return clipboard;
    }
}
