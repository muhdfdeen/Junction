package com.muhdfdeen.junction.permission;

import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.node.Node;

import com.muhdfdeen.junction.Junction;
import com.muhdfdeen.junction.util.Logger;

public class LuckPermsProvider implements PermissionProvider {
    private final LuckPerms luckPerms;

    private LuckPermsProvider(LuckPerms luckPerms) {
        this.luckPerms = luckPerms;
    }

    public static LuckPermsProvider setupProvider(Junction plugin, String groupName) {
        Logger log = plugin.getPluginLogger();
        if (plugin.getServer().getPluginManager().getPlugin("LuckPerms") == null) {
            log.warn("LuckPerms plugin not found!");
            return null;
        }
        RegisteredServiceProvider<LuckPerms> provider = plugin.getServer().getServicesManager()
                .getRegistration(LuckPerms.class);
        if (provider == null) {
            log.warn("LuckPerms registration failed!");
            return null;
        }
        LuckPerms api = provider.getProvider();
        if (groupName != null && !groupName.isEmpty()) {
            if (api.getGroupManager().getGroup(groupName) == null) {
                log.warn("Group '" + groupName + "' not found in LuckPerms.");
            } else {
                log.debug("Found group '" + groupName + "' in LuckPerms.");
            }
        }
        log.info("LuckPerms provider initialized.");
        return new LuckPermsProvider(api);
    }

    @Override
    public boolean addPlayerToGroup(Player player, String group) {
        if (isPlayerInGroup(player, group))
            return true;

        this.luckPerms.getUserManager().modifyUser(player.getUniqueId(), user -> {
            Node node = Node.builder("group." + group).build();
            user.data().add(node);
        });

        return true;
    }

    @Override
    public boolean isPlayerInGroup(Player player, String group) {
        return player.hasPermission("group." + group);
    }

    @Override
    public String getName() {
        return "LuckPerms";
    }
}
