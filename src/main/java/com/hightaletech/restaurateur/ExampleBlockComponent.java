package com.hightaletech.restaurateur;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;

import javax.annotation.Nullable;

public class ExampleBlockComponent implements Component<ChunkStore> {
    public static final BuilderCodec CODEC;

    public ExampleBlockComponent() {}

    public static ComponentType getComponentType() {
        return RestaurateurPlugin.getInstance().getExampleBlockComponentType();
    }

    public void runBlockAction(int x, int y, int z, World world) {
        world.execute(() -> {
            world.setBlock(x + 1, y, z, "Rock_Ice");
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
