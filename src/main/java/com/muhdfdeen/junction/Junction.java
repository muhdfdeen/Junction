package com.muhdfdeen.junction;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;

import net.luckperms.api.LuckPerms;
import net.milkbowl.vault2.permission.Permission;

import com.muhdfdeen.junction.command.JunctionCommand;
import com.muhdfdeen.junction.listener.PlayerJoinListener;
import com.muhdfdeen.junction.permission.LuckPermsProvider;
import com.muhdfdeen.junction.permission.PermissionProvider;
import com.muhdfdeen.junction.permission.VaultProvider;
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
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            JunctionCommand junctionCommand = new JunctionCommand(this);
            event.registrar().register(junctionCommand.createCommand("junction"), "Main Junction command");
        });
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
            if (setupVault()) {
                log.info("Vault provider initialized");
            } else {
                log.warn("Failed to initialize Vault, is it installed?");
            }
        } else {
            log.warn("Unknown permission provider: " + providerType);
        }
    }

    private boolean setupLuckPerms() {
        if (getServer().getPluginManager().getPlugin("LuckPerms") == null) {
            return false;
        }
        RegisteredServiceProvider<LuckPerms> provider = getServer().getServicesManager()
                .getRegistration(LuckPerms.class);
        if (provider == null) {
            return false;
        }
        LuckPerms luckPerms = provider.getProvider();
        String groupName = getConfig().getString("permissions.group");
        if (groupName != null && !groupName.isEmpty()) {
            if (luckPerms.getGroupManager().getGroup(groupName) == null) {
                log.warn("Group '" + groupName + "' not found in LuckPerms");
            } else {
                log.debug("Found group '" + groupName + "' in LuckPerms");
            }
        }
        this.permissionProvider = new LuckPermsProvider(luckPerms);
        return true;
    }

    private boolean setupVault() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Permission> provider = getServer().getServicesManager()
                .getRegistration(Permission.class);
        if (provider == null) {
            return false;
        }
        Permission permission = provider.getProvider();
        String groupName = getConfig().getString("permissions.group");
        if (groupName != null && !groupName.isEmpty()) {
            String[] groups = permission.getGroups();
            boolean groupExists = false;
            for (String group : groups) {
                if (group.equalsIgnoreCase(groupName)) {
                    groupExists = true;
                    break;
                }
            }
            if (!groupExists) {
                log.warn("Group '" + groupName + "' not found in " + permission.getName());
                log.warn("Available groups: " + String.join(", ", groups));
            } else {
                log.debug("Found group '" + groupName + "' in " + permission.getName());
            }
        }
        this.permissionProvider = new VaultProvider(permission);
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
