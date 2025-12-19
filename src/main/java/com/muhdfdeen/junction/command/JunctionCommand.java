package com.muhdfdeen.junction.command;

import org.bukkit.command.CommandSender;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;

import com.muhdfdeen.junction.Junction;
import com.muhdfdeen.junction.config.ConfigManager;

public class JunctionCommand {
    private final Junction plugin;

    public JunctionCommand(Junction plugin) {
        this.plugin = plugin;
    }

    public LiteralCommandNode<CommandSourceStack> createCommand(final String commandName) {
        ConfigManager config = plugin.getConfiguration();
        return Commands.literal(commandName)
            .executes(ctx -> {
                CommandSender sender = ctx.getSource().getSender();
                sender.sendRichMessage(config.getMessageConfig().messages.prefix() + "Plugin version: " + plugin.getPluginMeta().getVersion());
                sender.sendRichMessage("<green>🛈</green> <gray>Type <white>/junction reload</white> to reload the configuration.</gray>");
                return Command.SINGLE_SUCCESS;
            })
            .then(Commands.literal("reload")
                .requires(sender -> sender.getSender().hasPermission("junction.reload"))
                .executes(ctx -> {
                    CommandSender sender = ctx.getSource().getSender();
                    if (plugin.reload()) {
                        plugin.getPluginLogger().info("Configuration reloaded by " + sender.getName());
                        sender.sendRichMessage(config.getMessageConfig().messages.prefix() + config.getMessageConfig().messages.reloadSuccess());
                    } else {
                        plugin.getPluginLogger().warn("Failed to reload configuration by " + sender.getName());
                        sender.sendRichMessage(config.getMessageConfig().messages.prefix() + config.getMessageConfig().messages.reloadFail());
                    }
                    return Command.SINGLE_SUCCESS;
                })
            )
            .build();
    }
}
