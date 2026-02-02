package com.hightaletech.restaurateur.commands;

import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;

/**
 * Main command for Restaurateur plugin.
 *
 * Usage:
 * - /res help - Show available commands
 * - /res info - Show plugin information
 * - /res reload - Reload plugin configuration
 * - /res ui - Open the plugin dashboard
 */
public class RestaurateurPluginCommand extends AbstractCommandCollection {

    public RestaurateurPluginCommand() {
        super("res", "Restaurateur plugin commands");

        // Add subcommands
        this.addSubCommand(new HelpSubCommand());
        this.addSubCommand(new InfoSubCommand());
        this.addSubCommand(new ReloadSubCommand());
        this.addSubCommand(new UISubCommand());
    }

    @Override
    protected boolean canGeneratePermission() {
        return false; // No permission required for base command
    }
}