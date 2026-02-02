package com.hightaletech.restaurateur;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.RefSystem;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.math.util.ChunkUtil;
import com.hypixel.hytale.server.core.modules.block.BlockModule;
import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.logging.Level;

// RefSystems are systems that react whenever any Entity is added to the world (players, blocks, NPCs, etc)
public class ExampleRefSystem extends RefSystem {
    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    @Override
    public void onEntityAdded(@NonNullDecl Ref ref, @NonNullDecl AddReason addReason, @NonNullDecl Store store, @NonNullDecl CommandBuffer commandBuffer) {
        // This Method tells Hytale that the block should be "Ticking" - eg, each tick, the server should
        // process this block entity via any EntityTickingSystems.
        // TODO: Make an Entity Ticking System that actually utilizes this block and it's custom component.

        BlockModule.BlockStateInfo info = (BlockModule.BlockStateInfo) commandBuffer.getComponent(ref, BlockModule.BlockStateInfo.getComponentType());
        if (info == null) return; // Defensive programming I guess? IDK why there wouldn't be info about the block

        ExampleBlockComponent generator = (ExampleBlockComponent) commandBuffer.getComponent(ref, RestaurateurPlugin.getInstance().getExampleBlockComponentType());
        if (generator == null) return;

        int x = ChunkUtil.xFromBlockInColumn(info.getIndex());
        int y = ChunkUtil.yFromBlockInColumn(info.getIndex());
        int z = ChunkUtil.zFromBlockInColumn(info.getIndex());
        LOGGER.at(Level.INFO).log("Example Block added to world at %d,%d,%d".formatted(x,y,z));

        WorldChunk worldChunk = (WorldChunk) commandBuffer.getComponent(info.getChunkRef(), WorldChunk.getComponentType());
        if (worldChunk == null) return;
        worldChunk.setTicking(x,y,z, true);
    }

    @Override
    public void onEntityRemove(@NonNullDecl Ref ref, @NonNullDecl RemoveReason removeReason, @NonNullDecl Store store, @NonNullDecl CommandBuffer commandBuffer) {
        // Cleanup, if needed
        LOGGER.at(Level.INFO).log("Example Block Removed from world");
    }

    @NullableDecl
    @Override
    public Query getQuery() {
        // Query is a filter that tells Hytale when to inform this System that an entity ref has been added or removed
        // to the world.
        return Query.and(BlockModule.BlockStateInfo.getComponentType(), ExampleBlockComponent.getComponentType());
    }
}
