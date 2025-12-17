package com.muhdfdeen.junction;

import org.bukkit.plugin.java.JavaPlugin;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;

import org.bstats.bukkit.Metrics;

import com.muhdfdeen.junction.command.JunctionCommand;
import com.muhdfdeen.junction.config.Config;
import com.muhdfdeen.junction.config.Config.MainConfiguration;
import com.muhdfdeen.junction.listener.PlayerJoinListener;
import com.muhdfdeen.junction.permission.PermissionProvider;
import com.muhdfdeen.junction.permission.ProviderManager;
import com.muhdfdeen.junction.util.Logger;
import com.muhdfdeen.junction.util.UpdateChecker;

public final class Junction extends JavaPlugin {
    private static Junction plugin;
    private MainConfiguration config;
    private PermissionProvider permissionProvider;
    private Logger log;

    @Override
    public void onEnable() {
        plugin = this;
        this.log = new Logger(this);
        UpdateChecker updateChecker = new UpdateChecker(this);
        updateChecker.checkForUpdates();
        if (!reload()) {
            log.error("Disabling plugin due to critical configuration error.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        @SuppressWarnings("unused")
        Metrics metrics = new Metrics(this, 28238);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(updateChecker, this);
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            JunctionCommand junctionCommand = new JunctionCommand(this);
            event.registrar().register(junctionCommand.createCommand("junction"), "Main Junction command");
        });
        log.info("Plugin enabled successfully");
    }

    public boolean reload() {
        try {
            this.config = Config.load(getDataFolder());
            this.permissionProvider = ProviderManager.initializeProvider(this);
            return true;
        } catch (Exception e) {
            log.error("Failed to load configuration: " + e.getMessage());
            return false;
        }
    }

    public static Junction getPlugin() {
        return plugin;
    }

    public Logger getPluginLogger() {
        return log;
    }

    public MainConfiguration getConfiguration() {
        return config;
    }

    public PermissionProvider getPermissionProvider() {
        return permissionProvider;
    }
}
