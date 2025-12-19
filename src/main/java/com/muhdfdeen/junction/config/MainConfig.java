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
        EventCommands java,
        @Comment("Commands specific to Bedrock Edition players.")
        EventCommands bedrock
    ) {}

    public record EventCommands(
        @Comment("List of console commands to execute when a player joins.")
        List<String> join,
        @Comment("List of console commands to execute when a player quits.")
        List<String> quit
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
            new EventCommands(
                List.of("say Java player {player} joined"),
                List.of("say Java player {player} left")
            ),
            new EventCommands(
                List.of("say Bedrock player {player} joined"),
                List.of("say Bedrock player {player} left")
            )
        );
    }
}
