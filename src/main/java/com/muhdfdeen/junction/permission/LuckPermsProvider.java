package com.muhdfdeen.junction.permission;

import org.bukkit.entity.Player;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;

public class LuckPermsProvider implements PermissionProvider {
    private final LuckPerms luckPerms;

    public LuckPermsProvider(LuckPerms luckPerms) {
        this.luckPerms = luckPerms;
    }

    @Override
    public boolean addPlayerToGroup(Player player, String group) {
        User user = luckPerms.getUserManager().getUser(player.getUniqueId());
        if (user == null)
            return false;
        if (isPlayerInGroup(player, group)) {
            return true;
        }

        Node node = Node.builder("group." + group).build();
        user.data().add(node);
        luckPerms.getUserManager().saveUser(user);

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
