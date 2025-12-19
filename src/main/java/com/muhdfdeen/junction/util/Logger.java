package com.muhdfdeen.junction.util;

import com.muhdfdeen.junction.Junction;
import com.muhdfdeen.junction.config.ConfigManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;

public class Logger {
    private final Junction plugin;

    public Logger(Junction plugin) {
        this.plugin = plugin;
    }

    private void log(String colorTag, String message) {
        ConfigManager config = plugin.getConfiguration();
        Bukkit.getConsoleSender().sendMessage(
            MiniMessage.miniMessage().deserialize(config.getMessageConfig().messages.prefix() + colorTag + message)
        );
    }

    public void debug(String message) {
        ConfigManager config = plugin.getConfiguration();
        if (config != null && config.getMainConfig().debug) {
            log("<gray>[DEBUG] </gray>", message);
        }
    }

    public void info(String message) {
        log("", message);
    }

    public void warn(String message) {
        log("<yellow>", message);
    }

    public void error(String message) {
        log("<red>", message);
    }
}