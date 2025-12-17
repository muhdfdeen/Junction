package com.muhdfdeen.junction.permission;

import com.muhdfdeen.junction.Junction;
import com.muhdfdeen.junction.config.Config.MainConfiguration;
import com.muhdfdeen.junction.util.Logger;

public class ProviderManager {
    public static PermissionProvider initializeProvider(Junction plugin) {
        Logger log = plugin.getPluginLogger();
        MainConfiguration config = plugin.getConfiguration();
        PermissionProvider provider = null;

        if (!config.permissions.enabled()) {
            log.info("Permission management disabled.");
            return null;
        }

        String providerType = config.permissions.provider();

        if (providerType.equalsIgnoreCase("LuckPerms")) {
            provider = LuckPermsProvider.setupProvider(plugin, config.permissions.group());
        } else if (providerType.equalsIgnoreCase("Vault")) {
            provider = VaultProvider.setupProvider(plugin, config.permissions.group());
        } else {
            log.warn("Unknown permission provider in config: " + providerType);
            return null;
        }

        if (provider == null) {
            return null;
        }

        return provider;
    }
}
