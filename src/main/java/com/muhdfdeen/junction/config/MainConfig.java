package com.muhdfdeen.junction.config;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import de.exlll.configlib.ConfigLib;
import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import de.exlll.configlib.YamlConfigurationProperties;
import de.exlll.configlib.YamlConfigurations;

public final class MainConfig {

    public static MainConfiguration load(File dataFolder) {
        YamlConfigurationProperties properties = ConfigLib.BUKKIT_DEFAULT_PROPERTIES.toBuilder().build();
        Path configFile = new File(dataFolder, "config.yml").toPath();
        return YamlConfigurations.update(configFile, MainConfiguration.class, properties);
    }

    public record PermissionSettings(
        @Comment("Should this module be enabled?")
        boolean enabled,
        @Comment({"Which permission provider should be used?", "Available options: LuckPerms, Vault"})
        String provider,
        @Comment("Which permission group should players be assigned to?")
        String group
    ) {}

    public record CommandSettings(
        @Comment("Should this module be enabled?")
        boolean enabled,
        @Comment("Commands specific to Java Edition players.")
        EditionCommands java,
        @Comment("Commands specific to Bedrock Edition players.")
        EditionCommands bedrock
    ) {}

    public record EditionCommands(
        @Comment("Commands to execute when a player joins the server.")
        CommandSource join,
        @Comment("Commands to execute when a player leaves the server.")
        CommandSource quit
    ) {}

    public record CommandSource(
        @Comment("List of commands to execute as console.")
        List<String> console,
        @Comment("List of commands to execute as the player.")
        List<String> player
    ) {}

    @Configuration
    public static class MainConfiguration {
        @Comment("Should debug mode be enabled for detailed logs?")
        public boolean debug = false;
        @Comment("This module automatically assigns Bedrock Edition players to a specific group.")
        public PermissionSettings permissions = new PermissionSettings(false, "LuckPerms", "geyser");
        @Comment("This module automatically executes commands based on Minecraft editions.")
        public CommandSettings commands = new CommandSettings(
            false,
            new EditionCommands(
                new CommandSource(List.of("say Java player, {player}, has joined the server."), List.of("msg {player} Hello, I am on Java edition!")),
                new CommandSource(List.of(), List.of())
            ),
            new EditionCommands(
                new CommandSource(List.of("say Bedrock player, {player}, has joined the server."), List.of("msg {player} Hello, I am on Bedrock edition!")),
                new CommandSource(List.of(), List.of())
            )
        );
    }
}
