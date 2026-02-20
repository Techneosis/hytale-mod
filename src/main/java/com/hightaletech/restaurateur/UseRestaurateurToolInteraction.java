package com.hightaletech.restaurateur;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.protocol.InteractionState;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.EntityUtils;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.entity.LivingEntity;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.modules.entity.component.DisplayNameComponent;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInteraction;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.SimpleBlockInteraction;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class UseRestaurateurToolInteraction extends SimpleBlockInteraction {
    public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
    public static final BuilderCodec<UseRestaurateurToolInteraction> CODEC = BuilderCodec.builder(UseRestaurateurToolInteraction.class, UseRestaurateurToolInteraction::new, SimpleInteraction.CODEC).build();

    public UseRestaurateurToolInteraction(){ }

    @Override
    protected void interactWithBlock(@NonNullDecl World var1, @NonNullDecl CommandBuffer<EntityStore> var2, @NonNullDecl InteractionType var3, @NonNullDecl InteractionContext var4, @NullableDecl ItemStack var5, @NonNullDecl Vector3i var6, @NonNullDecl CooldownHandler var7) {
        LOGGER.atInfo().log("Interact With Block");
    }

    @Override
    protected void simulateInteractWithBlock(@NonNullDecl InteractionType var1, @NonNullDecl InteractionContext var2, @NullableDecl ItemStack var3, @NonNullDecl World var4, @NonNullDecl Vector3i var5) {
        LOGGER.atInfo().log("Simulate Interact With Block");
    }

    @Override
    protected void tick0(boolean firstRun, float time, @NonNullDecl InteractionType type, @NonNullDecl InteractionContext context, @NonNullDecl CooldownHandler cooldownHandler) {
//        super.tick0(firstRun, time, type, context, cooldownHandler);
        if(!firstRun) {
            context.getState().state = InteractionState.Finished;
            return;
        }

        CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
        if (commandBuffer == null) {
            context.getState().state = InteractionState.Failed;
            return;
        }

        Ref<EntityStore> interactorRef = context.getEntity();
        if (!(EntityUtils.getEntity(interactorRef, commandBuffer) instanceof Player player)) {
            context.getState().state = InteractionState.Failed;
            // Only players can do this y'all
            return;
        }


        Ref<EntityStore> interacteeRef = context.getTargetEntity();
        if (!(EntityUtils.getEntity(interacteeRef, commandBuffer) instanceof LivingEntity livingInteracteeEntity)) {
            context.getState().state = InteractionState.Failed;
            // Can only do this to living things dawg
            return;
        }

       DisplayNameComponent interacteeDisplayName = commandBuffer.getComponent(interacteeRef, DisplayNameComponent.getComponentType());
        if (interacteeDisplayName == null) {
            context.getState().state = InteractionState.Failed;
            return;
        }

        var displayName = interacteeDisplayName.getDisplayName();
        if (displayName == null) {
            LOGGER.atInfo().log("A living thing interacted with a living thing that has no name.");
        } else {
            player.sendMessage(Message.empty().insert("You Interacted with a <").insert(displayName.bold(true)).insert(">"));
        }
    }
}
