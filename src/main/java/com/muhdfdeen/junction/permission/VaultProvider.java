package com.muhdfdeen.junction.permission;

import net.milkbowl.vault2.permission.Permission;
import org.bukkit.entity.Player;

public class VaultProvider implements PermissionProvider {
    private final Permission permission;

    public VaultProvider(Permission permission) {
        this.permission = permission;
    }

    @Override
    public boolean addPlayerToGroup(Player player, String group) {
        try {
            if (isPlayerInGroup(player, group)) {
                return true;
            }
            return permission.playerAddGroup(null, player, group);
        } catch (Exception e) {
            return false;
        }
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
