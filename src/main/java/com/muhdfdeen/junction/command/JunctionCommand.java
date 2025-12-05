package com.muhdfdeen.junction.command;

import org.bukkit.command.CommandSender;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;

import com.muhdfdeen.junction.Junction;

public class JunctionCommand {
    private static final String PREFIX = "<color:#00D4FF><bold>Junction</bold> ➟ </color>";
    private final Junction plugin;

    public JunctionCommand(Junction plugin) {
        this.plugin = plugin;
    }

    public LiteralCommandNode<CommandSourceStack> createCommand(final String commandName) {
        return Commands.literal(commandName)
            .executes(ctx -> {
                CommandSender sender = ctx.getSource().getSender();
                sender.sendRichMessage(PREFIX + "Plugin version: " + plugin.getPluginMeta().getVersion());
                sender.sendRichMessage("<green>🛈</green> <gray>Type <white>/junction reload</white> to reload the configuration.</gray>");
                return Command.SINGLE_SUCCESS;
            })
            .then(Commands.literal("reload")
                .requires(sender -> sender.getSender().hasPermission("junction.reload"))
                .executes(ctx -> {
                    CommandSender sender = ctx.getSource().getSender();
                    if (plugin.reload()) {
                        sender.sendRichMessage(PREFIX + "Plugin configuration reloaded successfully.");
                    } else {
                        sender.sendRichMessage(PREFIX + "<red>Failed to reload config! Check console for errors.</red>");
                    }
                    return Command.SINGLE_SUCCESS;
                })
            )
            .build();
    }
}
