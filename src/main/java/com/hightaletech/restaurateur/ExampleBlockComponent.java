package com.hightaletech.restaurateur;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;

import javax.annotation.Nullable;

public class ExampleBlockComponent implements Component<ChunkStore> {
    public static final BuilderCodec CODEC;
    public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    public static ComponentType getComponentType() {
        return RestaurateurPlugin.getInstance().getExampleBlockComponentType();
    }

    public void runBlockAction(int x, int y, int z, World world) {
        LOGGER.atInfo().log("RUNNING BLOCK ACTION");
        world.execute(() -> {
            world.setBlock(x, y + 1, z, "Rock_Ice");
        });
    }

    @Nullable
    public Component<ChunkStore> clone() {
        return new ExampleBlockComponent();
    }

    static {
        CODEC = BuilderCodec.builder(ExampleBlockComponent.class, ExampleBlockComponent::new).build();
    }
}
