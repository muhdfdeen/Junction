package com.muhdfdeen.junction.config;

import java.io.File;
import java.nio.file.Path;

import de.exlll.configlib.ConfigLib;
import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import de.exlll.configlib.YamlConfigurationProperties;
import de.exlll.configlib.YamlConfigurations;

public final class Config {
    public static MainConfiguration load(File dataFolder) {
        YamlConfigurationProperties properties = ConfigLib.BUKKIT_DEFAULT_PROPERTIES.toBuilder().build();
        Path configFile = new File(dataFolder, "config.yml").toPath();
        return YamlConfigurations.update(configFile, MainConfiguration.class, properties);
    }

    @Configuration
    public static class BaseConfiguration {
        @Comment("Should debug mode be enabled for detailed logs?")
        public boolean debug = false;
    }

    public record PermissionSettings(
        @Comment("Should this module be enabled?")
        boolean enabled,
        @Comment({"Which permission provider should be used?", "Available options: LuckPerms, Vault"})
        String provider,
        @Comment("Which permission group should players be assigned to?")
        String group
    ) {}

    public record MessageSettings(
        @Comment("Prefix for all messages sent by the plugin.")
        String prefix,
        @Comment("Message displayed when the plugin is reloaded.")
        String reloadSuccess,
        @Comment("Message displayed when the plugin fails to reload.")
        String reloadFail,
        @Comment("Message displayed when a new version of the plugin is available.")
        String updateAvailable
    ) {}

    @Configuration
    public static final class MainConfiguration extends BaseConfiguration {
        @Comment("This module automatically assigns Bedrock Edition players to a specific group.")
        public PermissionSettings permissions = new PermissionSettings(false, "LuckPerms", "geyser");
        @Comment("Settings related to messages sent by the plugin.")
        public MessageSettings messages = new MessageSettings(
            "<color:#00D4FF><bold>Junction</bold> ➟ </color>",
            "Plugin configuration has been reloaded successfully.",
            "<red>Failed to reload plugin configuration! Check console for errors.</red>",
            "A new version is available! <gray>(Current: <red>{current_version}</red> | Latest: <green>{latest_version}</green>)</gray>");
    }
}
