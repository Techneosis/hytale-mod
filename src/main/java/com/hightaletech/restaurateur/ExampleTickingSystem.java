package com.hightaletech.restaurateur;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.math.util.ChunkUtil;
import com.hypixel.hytale.server.core.asset.type.blocktick.BlockTickStrategy;
import com.hypixel.hytale.server.core.universe.world.chunk.BlockComponentChunk;
import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
import com.hypixel.hytale.server.core.universe.world.chunk.section.ChunkSection;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class ExampleTickingSystem extends EntityTickingSystem {
    @Override
    public void tick(float v, int i, @NonNullDecl ArchetypeChunk archetypeChunk, @NonNullDecl Store store, @NonNullDecl CommandBuffer commandBuffer) {
        BlockSection blocks = (BlockSection) archetypeChunk.getComponent(i, BlockSection.getComponentType());
        assert blocks != null;
        if (blocks.getTickingBlocksCount() == 0) return;

        ChunkSection section = (ChunkSection) archetypeChunk.getComponent(i, ChunkSection.getComponentType());
        assert section != null;

        BlockComponentChunk blockComponentChunk = (BlockComponentChunk) commandBuffer.getComponent(section.getChunkColumnReference(), BlockComponentChunk.getComponentType());
        assert blockComponentChunk != null;

        blocks.forEachTicking(blockComponentChunk, commandBuffer, section.getY(), ((blockComponentChunk1, commandBuffer1, localX, localY, localZ, blockId) ->
        {
            Ref<ChunkStore> blockRef = blockComponentChunk1.getEntityReference(ChunkUtil.indexBlockInColumn(localX, localY, localZ));
            // If there's no block.. Ignore
            if (blockRef == null) return BlockTickStrategy.IGNORED;

            // If there's no ExampleBlockComponent on the block.. ignore
            ExampleBlockComponent exampleBlockComponent = (ExampleBlockComponent) commandBuffer1.getComponent(blockRef, ExampleBlockComponent.getComponentType());
            if (exampleBlockComponent == null) return BlockTickStrategy.IGNORED;

            WorldChunk worldChunk = (WorldChunk) commandBuffer.getComponent(section.getChunkColumnReference(), WorldChunk.getComponentType());
            if (worldChunk == null) return BlockTickStrategy.IGNORED;

            int globalX = localX + (worldChunk.getX() * 32);
            int globalZ = localZ + (worldChunk.getZ() * 32);

            // Do Stuff on the component every tick
            exampleBlockComponent.runBlockAction(globalX, localY, globalZ, worldChunk.getWorld());
            // And Keep Ticking
            return BlockTickStrategy.CONTINUE;
        } ));
    }

    @NullableDecl
    @Override
    public Query getQuery() {
        return Query.and(BlockSection.getComponentType(), ChunkSection.getComponentType());
    }
}
