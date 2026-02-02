package com.hightaletech.restaurateur;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.event.EventRegistry;
import com.hypixel.hytale.logger.HytaleLogger;

import com.hightaletech.restaurateur.commands.RestaurateurPluginCommand;
import com.hightaletech.restaurateur.listeners.PlayerListener;

import javax.annotation.Nonnull;
import java.util.logging.Level;

/**
 * Restaurateur - A Hytale server plugin.
 */
public class RestaurateurPlugin extends JavaPlugin {

    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
    private static RestaurateurPlugin instance;
    private ComponentType exampleBlockComponentType;

    public RestaurateurPlugin(@Nonnull JavaPluginInit init) {
        super(init);
        instance = this;
    }

    /**
     * Get the plugin instance.
     * @return The plugin instance
     */
    public static RestaurateurPlugin getInstance() {
        return instance;
    }

    @Override
    protected void setup() {
        LOGGER.at(Level.INFO).log("[Restaurateur] Setting up...");

        // Register commands
        registerCommands();

        // Register event listeners
        registerListeners();

        // Register Components
        registerComponents();

        LOGGER.at(Level.INFO).log("[Restaurateur] Setup complete!");
    }

    /**
     * Register plugin commands.
     */
    private void registerCommands() {
        try {
            getCommandRegistry().registerCommand(new RestaurateurPluginCommand());
            LOGGER.at(Level.INFO).log("[Restaurateur] Registered /res command");
        } catch (Exception e) {
            LOGGER.at(Level.WARNING).withCause(e).log("[Restaurateur] Failed to register commands");
        }
    }

    /**
     * Register event listeners.
     */
    private void registerListeners() {
        EventRegistry eventBus = getEventRegistry();

        try {
            new PlayerListener().register(eventBus);
            LOGGER.at(Level.INFO).log("[Restaurateur] Registered player event listeners");
        } catch (Exception e) {
            LOGGER.at(Level.WARNING).withCause(e).log("[Restaurateur] Failed to register listeners");
        }
    }

    private void registerComponents() {
        this.exampleBlockComponentType = this.getChunkStoreRegistry().registerComponent(ExampleBlockComponent.class, "Example Block", ExampleBlockComponent.CODEC);
    }

    @Override
    protected void start() {
        LOGGER.at(Level.INFO).log("[Restaurateur] Started!");
        LOGGER.at(Level.INFO).log("[Restaurateur] Use /res help for commands");
    }

    @Override
    protected void shutdown() {
        LOGGER.at(Level.INFO).log("[Restaurateur] Shutting down...");
        instance = null;
    }

    public ComponentType getExampleBlockComponentType() {
        return this.exampleBlockComponentType;
    }
}