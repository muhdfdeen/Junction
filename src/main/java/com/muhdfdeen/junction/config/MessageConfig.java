package com.muhdfdeen.junction.config;

import java.io.File;
import java.nio.file.Path;

import de.exlll.configlib.ConfigLib;
import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import de.exlll.configlib.YamlConfigurationProperties;
import de.exlll.configlib.YamlConfigurations;

public final class MessageConfig {
    public static MessageConfiguration load(File dataFolder) {
        YamlConfigurationProperties properties = ConfigLib.BUKKIT_DEFAULT_PROPERTIES.toBuilder().build();
        Path configFile = new File(dataFolder, "messages.yml").toPath();
        return YamlConfigurations.update(configFile, MessageConfiguration.class, properties);
    }

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
    public static class MessageConfiguration {
        @Comment("Settings related to messages sent by the plugin.")
        public MessageSettings messages = new MessageSettings(
            "<color:#00D4FF><bold>Junction</bold> ➟ </color>",
            "Plugin configuration has been reloaded successfully.",
            "<red>Failed to reload plugin configuration! Check console for errors.</red>",
            "A new version is available! <gray>(Current: <red>{current_version}</red> | Latest: <green>{latest_version}</green>)</gray>");
    }
}
