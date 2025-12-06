package com.muhdfdeen.junction.permission;

import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault2.permission.Permission;

import com.muhdfdeen.junction.Junction;
import com.muhdfdeen.junction.util.Logger;

public class VaultProvider implements PermissionProvider {
    private final Permission permission;

    public VaultProvider(Permission permission) {
        this.permission = permission;
    }

    public static VaultProvider setupProvider(Junction plugin, String groupName) {
        Logger log = plugin.getPluginLogger();

        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            log.warn("Vault plugin not found!");
            return null;
        }

        RegisteredServiceProvider<Permission> provider = plugin.getServer().getServicesManager()
                .getRegistration(Permission.class);
        if (provider == null) {
            log.warn("Vault registration failed!");
            return null;
        }

        Permission vault = provider.getProvider();

        if (groupName != null && !groupName.isEmpty()) {
            String[] groups = vault.getGroups();
            boolean groupExists = false;
            for (String group : groups) {
                if (group.equalsIgnoreCase(groupName)) {
                    groupExists = true;
                    break;
                }
            }
            if (!groupExists) {
                log.warn("Group '" + groupName + "' not found in " + vault.getName());
                log.warn("Available groups: " + String.join(", ", groups));
            } else {
                log.debug("Found group '" + groupName + "' in " + vault.getName());
            }
        }

        return new VaultProvider(vault);
    }

    @Override
    public boolean addPlayerToGroup(Player player, String group) {
        if (isPlayerInGroup(player, group))
            return true;
        return permission.playerAddGroup(null, player, group);
    }

    @Override
    public boolean isPlayerInGroup(Player player, String group) {
        return permission.playerInGroup(null, player, group);
    }

    @Override
    public String getName() {
        return "Vault";
    }
}
