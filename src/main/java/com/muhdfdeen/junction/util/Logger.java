package com.muhdfdeen.junction.util;

import com.muhdfdeen.junction.Junction;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class Logger {
    private final Junction plugin;

    public Logger(Junction plugin) {
        this.plugin = plugin;
    }

    public void debug(String message) {
        if (plugin.getConfig().getBoolean("debug", false)) {
            plugin.getComponentLogger().info(Component.text("[DEBUG] " + message, NamedTextColor.GRAY));
        }
    }

    public void info(String message) {
        plugin.getComponentLogger().info(Component.text(message));
    }

    public void warn(String message) {
        plugin.getComponentLogger().warn(Component.text(message));
    }

    public void error(String message) {
        plugin.getComponentLogger().error(Component.text(message));
    }
}
