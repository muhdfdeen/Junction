package com.muhdfdeen.junction.permission;

import com.muhdfdeen.junction.Junction;
import com.muhdfdeen.junction.config.ConfigManager;
import com.muhdfdeen.junction.util.Logger;

public class ProviderManager {
    public static PermissionProvider initializeProvider(Junction plugin) {
        Logger log = plugin.getPluginLogger();
        ConfigManager config = plugin.getConfiguration();
        PermissionProvider provider = null;

        if (!config.getMainConfig().permissions.enabled()) {
            log.info("Permission management disabled.");
            return null;
        }

        String providerType = config.getMainConfig().permissions.provider();

        if (providerType.equalsIgnoreCase("LuckPerms")) {
            provider = LuckPermsProvider.setupProvider(plugin, config.getMainConfig().permissions.group());
        } else if (providerType.equalsIgnoreCase("Vault")) {
            provider = VaultProvider.setupProvider(plugin, config.getMainConfig().permissions.group());
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
