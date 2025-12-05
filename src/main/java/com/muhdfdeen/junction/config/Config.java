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

    @Configuration
    public static final class MainConfiguration extends BaseConfiguration {
        @Comment("This module automatically assigns Bedrock Edition players to a specific group.")
        public PermissionSettings permissions = new PermissionSettings(false, "LuckPerms", "geyser");
    }
}
