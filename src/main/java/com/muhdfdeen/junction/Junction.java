package com.muhdfdeen.junction;

import net.luckperms.api.LuckPerms;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.muhdfdeen.junction.permission.LuckPermsProvider;
import com.muhdfdeen.junction.permission.PermissionProvider;
import com.muhdfdeen.junction.util.Logger;

public final class Junction extends JavaPlugin {
    private static Junction plugin;
    private PermissionProvider permissionProvider;
    private Logger log;

    @Override
    public void onEnable() {
        plugin = this;
        this.log = new Logger(this);
        saveDefaultConfig();
        setupPermissionProvider();
        getServer().getPluginManager().registerEvents(new com.muhdfdeen.junction.listener.PlayerJoinListener(this), this);
        log.info("Plugin enabled successfully");
    }

    private void setupPermissionProvider() {
        if (!getConfig().getBoolean("permissions.enabled")) {
            log.info("Permission management disabled");
            return;
        }

        String providerType = getConfig().getString("permissions.provider", "LuckPerms");
        if (providerType.equalsIgnoreCase("LuckPerms")) {
            if (setupLuckPerms()) {
                log.info("LuckPerms provider initialized");
            } else {
                log.warn("Failed to initialize LuckPerms, is it installed?");
            }
         } else if (providerType.equalsIgnoreCase("Vault")) {
            log.info("Vault support not yet implemented");
         } else {
            log.warn("Unknown permission provider: " + providerType);
         }
    }

    private boolean setupLuckPerms() {
        if (getServer().getPluginManager().getPlugin("LuckPerms") == null) {
            return false;
        }
        RegisteredServiceProvider<LuckPerms> provider = getServer().getServicesManager().getRegistration(LuckPerms.class);
        if (provider == null) {
            return false;
        }
        LuckPerms luckPerms = provider.getProvider();
        this.permissionProvider = new LuckPermsProvider(luckPerms);
        return true;
    }

    public PermissionProvider getPermissionProvider() {
        return permissionProvider;
    }

    public Logger getPluginLogger() {
        return log;
    }

    public static Junction getPlugin() {
        return plugin;
    }
}
